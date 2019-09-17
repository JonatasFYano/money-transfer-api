package com.money_transfer_api.app.repository;


import com.money_transfer_api.app.model.AccountModel;
import com.money_transfer_api.app.exception.MessageException;

import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountRepository{

    private final static String SQL_CREATE_ACCOUNT = "INSERT INTO Account (UserName, Balance, CurrencyCode) VALUES (?, ?, ?)";

    public long createAccount(AccountModel account) throws MessageException { 
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet generatedKeys = null;
        try {
            conn = H2DataFactory.getConnection();
            stmt = conn.prepareStatement(SQL_CREATE_ACCOUNT);
            stmt.setString(1, account.getUserName());
            stmt.setBigDecimal(2, account.getTotalBalance());
            stmt.setString(3, account.getCurrency());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new MessageException("Account Cannot be created");
            }
            generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getLong(1);
            } else {
                throw new MessageException("Account Cannot be created");
            }
        } catch (SQLException e) {
            System.out.println(e);
            throw new MessageException("createAccount(): Error creating user account " + account, e);
        } finally {
            DbUtils.closeQuietly(conn, stmt, generatedKeys);
        }
    }
}