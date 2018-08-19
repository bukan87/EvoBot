package ru.evotor.bot.bots;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.AnswerInlineQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendVenue;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.inlinequery.InlineQuery;
import org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.InputTextMessageContent;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResultArticle;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.evotor.bot.db.dao.ProductDAO;
import ru.evotor.bot.db.dao.UserDAO;
import ru.evotor.bot.db.entity.ProductByName;
import ru.evotor.bot.db.entity.ProductInNearestShop;
import ru.evotor.bot.db.entity.User;
import ru.evotor.bot.db.mapper.LogMapper;
import ru.evotor.bot.db.mapper.ProductMapper;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * @author a.ilyin
 */
@Component
public class BeerBot extends AbilityBot {

    private final String botName;
    private final String botToken;
    private final ProductMapper productMapper;
    private final ProductDAO productDAO;
    private final LogMapper logMapper;
    private final UserDAO userDAO;
    private static final Integer CACHE_TIME = 86400;

    public BeerBot(@Value("#{property.botName}") String botName,
                   @Value("#{property.botToken}") String botToken,
                   DefaultBotOptions botOptions,
                   ProductMapper productMapper,
                   LogMapper logMapper,
                   ProductDAO productDAO,
                   UserDAO userDAO) {
        super(botToken, botName, botOptions);
        this.botName = botName;
        this.botToken = botToken;
        this.productMapper = productMapper;
        this.logMapper = logMapper;
        this.productDAO = productDAO;
        this.userDAO = userDAO;
    }

    @Override
    public int creatorId() {
        return 0;
    }

    @Override
    public void onUpdateReceived(Update update) {
        // TODO перенести в аспект
        if (update.hasInlineQuery()){
            InlineQuery inlineQuery = update.getInlineQuery();
            Location location = inlineQuery.getLocation();
            logMapper.insertLog("1",
                    inlineQuery.getQuery(),
                    inlineQuery.getFrom().getId(),
                    location != null ? location.getLongitude().toString() : null,
                    location != null ? location.getLatitude().toString() : null);
        } else {
            Location location = update.getMessage().getLocation();
            logMapper.insertLog("2",
                    update.getMessage().getText(),
                    update.getMessage().getFrom().getId(),
                    location != null ? location.getLongitude().toString() : null,
                    location != null ? location.getLatitude().toString() : null);
        }
        if (update.hasInlineQuery()){
            sendProductsInline(update.getInlineQuery());
        } else if (update.hasMessage() && update.getMessage().hasText()){
            sendProductsInShops(update.getMessage());
        } else if (update.hasMessage() && update.getMessage().hasLocation()){
            receiveLocation(update.getMessage());
        }
    }

    private void sendProductsInline(InlineQuery inlineQuery){
        if (inlineQuery.getQuery().length() < 3){
            return;
        }
        AnswerInlineQuery answerInlineQueryRes = new AnswerInlineQuery();
        answerInlineQueryRes.setInlineQueryId(inlineQuery.getId());
        answerInlineQueryRes.setCacheTime(CACHE_TIME);
        List<InlineQueryResult> results = new ArrayList<>();

        List<ProductByName> products = productMapper.getProducts(inlineQuery.getQuery());
        products.stream()
                .limit(10)
                .forEach(item -> {
                    InlineQueryResultArticle article = new InlineQueryResultArticle();
                    //
                    InputTextMessageContent messageContent = new InputTextMessageContent();
                    messageContent.disableWebPagePreview();
                    messageContent.enableMarkdown(true);
                    messageContent.setMessageText(item.getFoundWord());
                    article.setInputMessageContent(messageContent);
                    article.setId(item.getFoundWord());
                    article.setTitle(item.getFoundWord());
                    article.setDescription(item.getProductName());
                    results.add(article);
                });
        answerInlineQueryRes.setResults(results);
        sendMessage(answerInlineQueryRes);
        if (inlineQuery.hasLocation()){
            userDAO.setLocation(inlineQuery.getFrom().getId(), inlineQuery.getLocation().getLatitude(), inlineQuery.getLocation().getLongitude());
        }
    }

    private void sendProductsInShops(Message message){
        sendProductsInShops(message.getChatId(), message.getFrom().getId(), message.getText());
    }

    private void sendProductsInShops(long chatId, int userId, String product){
        if (product.length() < 3){
            sendText(chatId, "Строка поиска должна быть не меньше трёх символов");
            return;
        }
        User user = userDAO.getUser(userId);
        if (user.getLastLocationDate() == null){
            sendText(chatId, "Я не знаю где ты. Вышли геолокацию, что бы я показал ближайшие магазины");
            userDAO.setRequest(userId, product);
            return;
        } else if (user.getLastLocationDate().isAfter(LocalDateTime.now().minus(1L, ChronoUnit.HOURS))) {
            sendText(chatId, "Последний раз ты делился своей геолокацией больше часа назад. Вышли свежую геолокацию, что бы я показал ближайшие магазины");
            return;
        }
        List<ProductInNearestShop> productsInNearestShops = productDAO.getProductsInNearestShops(product, user.getLastLatitude(), user.getLastLongitude());
        productsInNearestShops
                .stream()
                .limit(3)
                .forEach(item -> {
                    SendVenue sendVenue = new SendVenue();
                    sendVenue.setChatId(chatId);
                    sendVenue.setLatitude(Float.parseFloat(item.getLatitude()));
                    sendVenue.setLongitude(Float.parseFloat(item.getLongitude()));
                    sendVenue.setAddress(getVenueAddress(item));
                    sendVenue.setTitle(getVenueTitle(item));
                    sendVenue.disableNotification();
                    sendMessage(sendVenue);
                });
        userDAO.setRequest(userId, null);
    }

    private void receiveLocation(Message message){
        userDAO.setLocation(message.getFrom().getId(), message.getLocation().getLatitude(), message.getLocation().getLongitude());
        User user = userDAO.getUser(message.getFrom().getId());
        if (user.getLastRequest() != null && !user.getLastRequest().isEmpty()){
            sendProductsInShops(message.getChatId(), message.getFrom().getId(), user.getLastRequest());
        }
    }

    private void sendText(long chatId, String text){
        SendMessage sendMessage = new SendMessage(chatId, text);
        sendMessage(sendMessage);
    }

    private void sendMessage(BotApiMethod<?> method){
        try {
            execute(method);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private String getVenueTitle(ProductInNearestShop productInNearestShop){
        StringBuilder result = new StringBuilder();
        result.append(productInNearestShop.getProductName());
        result.append(", ");
        result.append(String.valueOf(productInNearestShop.getPrice()));
        result.append(" руб.");
        return result.toString();
    }

    private String getVenueAddress(ProductInNearestShop productInNearestShop){
        StringBuilder result = new StringBuilder();
        result.append(productInNearestShop.getAddress());
        result.append(", расстояние: ");
        result.append(Math.round(productInNearestShop.getDistance()));
        result.append(" м.");
        return result.toString();
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}