package ru.evotor.bot.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;
import ru.evotor.bot.types.Proxy;

import javax.sql.DataSource;
import java.net.Authenticator;
import java.net.PasswordAuthentication;

/**
 * @author a.ilyin
 */
@Configuration
@MapperScan(basePackages = "ru.evotor.bot.db.mapper")
public class Config {

    @Bean
    public DefaultBotOptions getBotOptions(@Value("#{property.proxy}") Proxy proxy){
        Authenticator.setDefault(new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(proxy.getUserName(), proxy.getPassword().toCharArray());
            }
        });
        DefaultBotOptions getBotOptions = ApiContext.getInstance(DefaultBotOptions.class);
        getBotOptions.setProxyHost(proxy.getHost());
        getBotOptions.setProxyPort(proxy.getPort());
        getBotOptions.setProxyType(DefaultBotOptions.ProxyType.SOCKS5);
        return getBotOptions;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource);
        return sqlSessionFactory.getObject();
    }
}