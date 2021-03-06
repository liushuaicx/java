package com.kaishengit.pojo;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author 刘帅
 */
@Entity
@Table(name = "kaola")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "product_name")
    private String productName;
    private BigDecimal price;
    @Column(name = "market_price")
    private BigDecimal marketPrice;
    private String place;
    @Column(name = "comment_num")
    private Integer commentNum;
    @JoinColumn(name = "type_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private ProductType productType;

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

}
