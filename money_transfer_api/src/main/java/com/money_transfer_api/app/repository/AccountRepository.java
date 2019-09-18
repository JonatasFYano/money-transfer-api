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

    private final static String SQL_CREATE_ACCOUNT = "INSERT INTO Account (UserName, TotalBalance) VALUES (?, ?)";
    private final static String SQL_GET_ACC = "SELECT * FROM Account";
    private final static String SQL_GET_ACC_BY_ID = "SELECT * FROM Account WHERE UserName = ?";
    private final static String SQL_UPDATE_ACC_BALANCE = "UPDATE Account SET TotalBalance = ? WHERE UserName = ? ";
    private final static String SQL_LOCK_ACC_BY_ID = "SELECT * FROM Account WHERE AccountId = ? FOR UPDATE";


    public long createAccount(AccountModel account) throws MessageException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet generatedKeys = null;
        try {
            conn = H2DataFactory.getConnection();
            stmt = conn.prepareStatement(SQL_CREATE_ACCOUNT);
            stmt.setString(1, account.getUserName());
            stmt.setBigDecimal(2, account.getTotalBalance());
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


    public List<AccountModel> getAllAccounts() throws MessageException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        List<AccountModel> accounts = new ArrayList<AccountModel>();
        try {
            conn = H2DataFactory.getConnection();
            stmt = conn.prepareStatement(SQL_GET_ACC);
            resultSet = stmt.executeQuery();
            while (resultSet.next()) {

				AccountModel account = new AccountModel(resultSet.getLong("AccountId"), resultSet.getString("UserName"), resultSet.getBigDecimal("TotalBalance"));

                accounts.add(account);
            }
			return accounts;
		} catch (SQLException e) {
            System.out.println(e);
			throw new MessageException("getAccountById(): Error reading account data", e);
		} finally {
			DbUtils.closeQuietly(conn, stmt, resultSet);
		}
    }

    
    public AccountModel getAccount(String userNameAccount) throws MessageException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        AccountModel account = null;
        try {
            conn = H2DataFactory.getConnection();
            stmt = conn.prepareStatement(SQL_GET_ACC_BY_ID);
            stmt.setString(1, userNameAccount);
            resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                System.out.println(resultSet);
				account = new AccountModel(resultSet.getLong("AccountId"), resultSet.getString("UserName"), resultSet.getBigDecimal("TotalBalance"));
            }
			return account;
		} catch (SQLException e) {
            System.out.println(e);
			throw new MessageException("getAccountById(): Error reading account data", e);
		} finally {
			DbUtils.closeQuietly(conn, stmt, resultSet);
		}
    }


    public AccountModel updateTotalBalance(String userNameAccount, BigDecimal amount) throws MessageException {
        Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
        int updateCount = -1;
        AccountModel account = null;

		try {

			conn = H2DataFactory.getConnection();
			stmt = conn.prepareStatement(SQL_UPDATE_ACC_BALANCE);
			stmt.setBigDecimal(1, amount);
			stmt.setString(2, userNameAccount);
            updateCount = stmt.executeUpdate();
            System.out.println(updateCount);
            conn.commit();
            

            stmt = conn.prepareStatement(SQL_GET_ACC_BY_ID);
			stmt.setString(1, userNameAccount);
            resultSet = stmt.executeQuery();
            if (resultSet.next()) {
				account = new AccountModel(resultSet.getLong("AccountId"), resultSet.getString("UserName"), resultSet.getBigDecimal("TotalBalance"));
            }
			return account;
		}  catch (SQLException e) {
            System.out.println(e);
			throw new MessageException("getAccountById(): Error reading account data", e);
		} finally {
			DbUtils.closeQuietly(conn);
			DbUtils.closeQuietly(resultSet);
			DbUtils.closeQuietly(stmt);
		}
    }


}