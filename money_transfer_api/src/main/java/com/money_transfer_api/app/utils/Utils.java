package com.money_transfer_api.app.utils;

import java.io.FileNotFoundException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class Utils {

    private static Properties properties = new Properties();


    public Utils(){
        String configFileName = "money_transfer_api.properties";

        loadConfig(configFileName);
    }

    public void loadConfig(String fileName) {
            try {
                Properties prop = new Properties();
                // String propFileName = "money_transfer_api.properties";
                InputStream  inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
                properties.load(inputStream);

            } catch (FileNotFoundException fne) {
                System.out.println(fne);
            } catch (IOException ioe) {
                System.out.println(ioe);
            }
        
    }


    public static String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            value = System.getProperty(key);
        }
        return value;
    }

}
