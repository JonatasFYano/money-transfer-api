package com.money_transfer_api.app.repository;

import com.money_transfer_api.app.service.AccountService;

import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;
import org.h2.tools.RunScript;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.money_transfer_api.app.utils.Utils;

/**
 * H2 DAO
 */
public class H2DataFactory{

	private static Utils utils = new Utils();

	private static final String h2_driver = utils.getProperty("h2_driver");
	private static final String h2_connection_url = utils.getProperty("h2_connection_url");
	private static final String h2_user = utils.getProperty("h2_user");
	private static final String h2_password = utils.getProperty("h2_password");

	private final AccountService accountService = new AccountService();

	public H2DataFactory() {
		DbUtils.loadDriver(h2_driver);
	}

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(h2_connection_url, h2_user, h2_password);
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
