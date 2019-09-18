package com.money_transfer_api.app;

import com.money_transfer_api.app.controller.AccountController;
import com.money_transfer_api.app.repository.H2DataFactory;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

/**
 * Main Class (Starting point) 
 */
public class ApplicationConfig {

	public static void main(String[] args) throws Exception {
		H2DataFactory h2DataFactory = new H2DataFactory();
		h2DataFactory.populateInicialData();
		// Host service on jetty
		startService();
	}

		private static void startService() throws Exception {
		Server server = new Server(8001);
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.setContextPath("/");
		server.setHandler(context);
		ServletHolder servletHolder = context.addServlet(ServletContainer.class, "/*");
		servletHolder.setInitParameter("jersey.config.server.provider.classnames",
                AccountController.class.getCanonicalName());
		try {
			server.start();
			server.join();
		} finally {
			server.destroy();
		}
	}

}
