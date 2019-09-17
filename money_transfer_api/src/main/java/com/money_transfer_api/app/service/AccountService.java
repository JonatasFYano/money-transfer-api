package com.money_transfer_api.app.service;

import com.money_transfer_api.app.model.AccountModel;
import com.money_transfer_api.app.repository.H2DataFactory;
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

public class AccountService{
    private final static String SQL_CREATE_ACC = "INSERT INTO Account (UserName, Balance, CurrencyCode) VALUES (?, ?, ?)";


    /**
	 * Create account
	 */
	public long createAccount(AccountModel account){
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet generatedKeys = null;
		try {
			conn = H2DataFactory.getConnection();
			stmt = conn.prepareStatement(SQL_CREATE_ACC);
			stmt.setString(1, account.getUserName());
			stmt.setBigDecimal(2, account.getTotalBalance());
			stmt.setString(3, account.getCurrency());
			int affectedRows = stmt.executeUpdate();
			if (affectedRows == 0) {
				// throw new MessageException("Account Cannot be created");
				return 0;
			}
			generatedKeys = stmt.getGeneratedKeys();
			if (generatedKeys.next()) {
				return generatedKeys.getLong(1);
			} else {
				// throw new MessageException("Account Cannot be created");
				return 0;
			}
		} catch (SQLException e) {
			// throw new MessageException("createAccount(): Error creating user account " + account, e);
			return 0;
		} finally {
			DbUtils.closeQuietly(conn, stmt, generatedKeys);
		}
	}
}