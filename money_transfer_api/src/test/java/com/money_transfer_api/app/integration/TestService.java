package com.money_transfer_api.app.integration;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.money_transfer_api.app.repository.H2DataFactory;
import com.money_transfer_api.app.controller.AccountController;
import com.money_transfer_api.app.controller.TransactionController;

import org.apache.http.client.HttpClient;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;
import org.junit.AfterClass;
import org.junit.BeforeClass;


public abstract class TestService {

    protected static Server server = null;
    protected static PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
    protected static HttpClient client ;
    protected static H2DataFactory h2DataFactory = new H2DataFactory();
    protected ObjectMapper mapper = new ObjectMapper();
    protected URIBuilder builder = new URIBuilder().setScheme("http").setHost("localhost:8002");


    //Create Database, manage connections and start server
    @BeforeClass
    public static void setup() throws Exception {
        h2DataFactory.populateInicialData();
        startServer();
        connManager.setDefaultMaxPerRoute(100);
        connManager.setMaxTotal(200);
        client= HttpClients.custom()
                .setConnectionManager(connManager)
                .setConnectionManagerShared(true)
                .build();
    }


    // End Connection
    @AfterClass
    public static void closeClient() throws Exception {
        HttpClientUtils.closeQuietly(client);
    }



    // Start Server
    private static void startServer() throws Exception {
        if (server == null) {
            server = new Server(8002);
            ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
            context.setContextPath("/");
            server.setHandler(context);
            ServletHolder servletHolder = context.addServlet(ServletContainer.class, "/*");
            servletHolder.setInitParameter("jersey.config.server.provider.classnames",
                        AccountController.class.getCanonicalName() + "," + TransactionController.class.getCanonicalName());
            server.start();
        }
    }
}
