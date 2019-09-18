package com.money_transfer_api.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class TransactionModel {

	@JsonProperty(required = true)
	private BigDecimal amount;

	@JsonProperty(required = true)
	private String fromAccountUsername;

	@JsonProperty(required = true)
	private String toAccountUsername;

	public TransactionModel() {
	}

	public TransactionModel(String currencyCode, BigDecimal amount, String fromAccountUsername, String toAccountUsername) {
		this.amount = amount;
		this.fromAccountUsername = fromAccountUsername;
		this.toAccountUsername = toAccountUsername;
	}


	public BigDecimal getAmount() {
		return amount;
	}

	public String getfromAccountUsername() {
		return fromAccountUsername;
	}

	public String gettoAccountUsername() {
		return toAccountUsername;
	}

}
