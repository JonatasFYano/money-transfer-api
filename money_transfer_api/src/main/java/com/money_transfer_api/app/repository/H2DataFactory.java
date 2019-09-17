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

	private static final String h2_driver = getStringProperty("h2_driver");
	private static final String h2_connection_url = getStringProperty("h2_connection_url");
	private static final String h2_user = getStringProperty("h2_user");
	private static final String h2_password = getStringProperty("h2_password");

	private final AccountService accountService = new AccountService();

	H2DataFactory() {
		// init: load driver
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

}
