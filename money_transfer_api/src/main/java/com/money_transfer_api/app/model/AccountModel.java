package com.money_transfer_api.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class AccountModel {

    @JsonIgnore
    private long accountId;

    @JsonProperty(required = true)
    private String userName;

    @JsonProperty(required = true)
    private BigDecimal totalBalance;

    @JsonProperty(required = true)
    private String currency;

    public AccountModel() {
    }

    public AccountModel(String userName, BigDecimal totalBalance, String currency) {
        this.userName = userName;
        this.totalBalance = totalBalance;
        this.currency = currency;
    }

    public AccountModel(long accountId, String userName, BigDecimal totalBalance, String currency) {
        this.accountId = accountId;
        this.userName = userName;
        this.totalBalance = totalBalance;
        this.currency = currency;
    }

    public long getAccountId() {
        return accountId;
    }

    public String getUserName() {
        return userName;
    }

    public BigDecimal getTotalBalance() {
        return totalBalance;
    }

    public String getCurrency() {
        return currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccountModel account = (AccountModel) o;

        if (accountId != account.accountId) return false;
        if (!userName.equals(account.userName)) return false;
        if (!totalBalance.equals(account.totalBalance)) return false;
        return currency.equals(account.currency);

    }

    @Override
    public int hashCode() {
        int result = (int) (accountId ^ (accountId >>> 32));
        result = 31 * result + userName.hashCode();
        result = 31 * result + totalBalance.hashCode();
        result = 31 * result + currency.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", userName='" + userName + '\'' +
                ", totalBalance=" + totalBalance +
                ", currency='" + currency + '\'' +
                '}';
    }
}
