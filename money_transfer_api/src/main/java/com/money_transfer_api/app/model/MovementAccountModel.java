package com.money_transfer_api.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class MovementAccountModel {

    @JsonProperty(required = true)
    private BigDecimal amount;

    public MovementAccountModel() {
    }

    public MovementAccountModel(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
