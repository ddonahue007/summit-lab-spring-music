package com.redhat.summit2019.springmusic.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@ApiModel(description = "A Trade")
public class Trade {
    @Id
    @Column(length = 40)
    @GeneratedValue(generator = "randomId")
    @GenericGenerator(name = "randomId", strategy = "com.redhat.summit2019.springmusic.domain.RandomIdGenerator")
    @ApiModelProperty(value = "The trade id", hidden = true)
    private String id;

    @ApiModelProperty("The time of trade")
    private String time;

    @ApiModelProperty("The ticker symbol")
    private String ticker;

    @ApiModelProperty("The number of shares")
    private String num_shares;

    @ApiModelProperty("The price per share")
    private String price;

    @ApiModelProperty("The trade id")
    private String tradeId;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTicker() {
        return this.ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getNum_shares() {
        return this.num_shares;
    }

    public void setNum_shares(String num_shares) {
        this.num_shares = num_shares;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTradeId() {
        return this.tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }
}
