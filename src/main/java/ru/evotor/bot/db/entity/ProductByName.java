package ru.evotor.bot.db.entity;

import java.io.Serializable;

/**
 * @author a.ilyin
 */
public class ProductByName implements Serializable {
    private static final long serialVersionUID = 317334118342623739L;
    private String foundWord;
    private String productName;
    private Double minPrice;
    private Double maxPrice;

    public String getFoundWord() {
        return foundWord;
    }

    public void setFoundWord(String foundWord) {
        this.foundWord = foundWord;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }
}