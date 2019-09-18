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
    private final static String SQL_LOCK_ACC_BY_ID = "SELECT * FROM Account WHERE UserName = ? FOR UPDATE";


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
                throw new MessageException("Account Cannot be created ");
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
            conn.commit();

            account = getAccount(userNameAccount);

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
    
    
    public List<AccountModel> transaction(String userNameAccountFrom, String userNameAccountTo, BigDecimal amount) throws MessageException {
		int result = -1;
		Connection conn = null;
		PreparedStatement lockStmt = null;
		PreparedStatement updateStmt = null;
		ResultSet rs = null;
		AccountModel fromAccount = null;
        AccountModel toAccount = null;
        List<AccountModel> accounts = new ArrayList<AccountModel>();

		try {
			conn = H2DataFactory.getConnection();
			conn.setAutoCommit(false);
			// lock the credit and debit account for writing:
			lockStmt = conn.prepareStatement(SQL_LOCK_ACC_BY_ID);
			lockStmt.setString(1, userNameAccountFrom);
			rs = lockStmt.executeQuery();
			if (rs.next()) {
				fromAccount = new AccountModel(rs.getLong("AccountId"), rs.getString("UserName"),
						rs.getBigDecimal("TotalBalance"));
            }
            
			lockStmt = conn.prepareStatement(SQL_LOCK_ACC_BY_ID);
			lockStmt.setString(1, userNameAccountTo);
			rs = lockStmt.executeQuery();
			if (rs.next()) {
                toAccount = new AccountModel(rs.getLong("AccountId"), rs.getString("UserName"),
                        rs.getBigDecimal("TotalBalance"));
			}
			// check locking status
			if (fromAccount == null || toAccount == null) {
				throw new MessageException("Fail to lock both accounts for write");
			}

			// check enough fund in source account
			BigDecimal fromAccountLeftOver = fromAccount.getTotalBalance().subtract(amount);
			if (fromAccountLeftOver.compareTo(BigDecimal.ZERO) < 0) {
				throw new MessageException("Not enough Fund from source Account ");
            }
            
			// proceed with update
			updateStmt = conn.prepareStatement(SQL_UPDATE_ACC_BALANCE);
			updateStmt.setBigDecimal(1, fromAccountLeftOver);
			updateStmt.setString(2, userNameAccountFrom);
			updateStmt.addBatch();
			updateStmt.setBigDecimal(1, toAccount.getTotalBalance().add(amount));
			updateStmt.setString(2, userNameAccountTo);
			updateStmt.addBatch();
			int[] rowsUpdated = updateStmt.executeBatch();
            result = rowsUpdated[0] + rowsUpdated[1];
			if(result < 2){
                throw new MessageException("Only one account has changed ");
            }
            conn.commit();
            
            fromAccount = getAccount(userNameAccountFrom);
            toAccount = getAccount(userNameAccountTo);

            accounts.add(fromAccount);
            accounts.add(toAccount);
		} catch (SQLException se) {
			// rollback transaction if exception occurs
			try {
				if (conn != null)
					conn.rollback();
			} catch (SQLException re) {
				throw new MessageException("Fail to rollback transaction", re);
			}
		} finally {
			DbUtils.closeQuietly(conn);
			DbUtils.closeQuietly(rs);
			DbUtils.closeQuietly(lockStmt);
			DbUtils.closeQuietly(updateStmt);
        }
        return accounts;
    }
}