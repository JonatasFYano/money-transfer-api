package com.money_transfer_api.app.repository;

import com.money_transfer_api.app.repository.RepoFactory;
import com.money_transfer_api.app.service.AccountService;

import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;
import org.h2.tools.RunScript;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.Properties;

/**
 * H2 DAO
 */
public class H2DataFactory extends RepoFactory {

	private static Properties properties = new Properties();

	private static final String h2_driver = "org.h2.Driver";
	private static final String h2_connection_url = "jdbc:h2:mem:moneyapp;DB_CLOSE_DELAY=-1";
	private static final String h2_user = "sa";
	private static final String h2_password = "sa";

	private final AccountService accountService = new AccountService();

	public H2DataFactory() {
		DbUtils.loadDriver(h2_driver);
	}

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(h2_connection_url, h2_user, h2_password);
	}

	public AccountService getAccountService() {
		return accountService;
	}

	public static String getStringProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            value = System.getProperty(key);
        }
        return value;
	}
	
	public void populateInicialData() {
		Connection conn = null;
		try {
			conn = getConnection();
			RunScript.execute(conn, new FileReader("src/main/resources/demo.sql"));
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} finally {
			DbUtils.closeQuietly(conn);
		}
	}

}
