package com.vention.trackloader.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.vention.trackloader.exceptions.BadRequestException;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Utils {
    private static Connection connection;
    private static ObjectMapper objectMapper;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Properties properties = new Properties();
                InputStream inputStream = Utils.class.getClassLoader().getResourceAsStream("dbconnection.properties");
                properties.load(inputStream);

                String DRIVER = properties.getProperty("DRIVER");
                String URL = properties.getProperty("URL");
                String USERNAME = properties.getProperty("USERNAME");
                String PASSWORD = properties.getProperty("PASSWORD");
                Class.forName(DRIVER);
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            } catch (SQLException | ClassNotFoundException | IOException e) {
                throw new BadRequestException(e.getMessage());
            }
        }
        return connection;
    }

    public static ObjectMapper getObjectMapper() {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        }
        return objectMapper;
    }

    public static String getUrl() {
        try {
            Properties properties = new Properties();
            InputStream inputStream = Utils.class.getClassLoader().getResourceAsStream("application.properties");
            properties.load(inputStream);

            String apiUrl = properties.getProperty("API_URL");
            String apiKey = properties.getProperty("API_KEY");
            return apiUrl + "&api_key=" + apiKey;
        } catch (IOException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    public static Boolean getRefresh() {
        try {
            Properties properties = new Properties();
            InputStream inputStream = Utils.class.getClassLoader().getResourceAsStream("application.properties");
            properties.load(inputStream);

            String refreshData = properties.getProperty("REFRESH_DATA");
            return Boolean.valueOf(refreshData);
        } catch (IOException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    public static String getSchedulerTime() {
        try {
            Properties properties = new Properties();
            InputStream inputStream = Utils.class.getClassLoader().getResourceAsStream("application.properties");
            properties.load(inputStream);
            return properties.getProperty("SCHEDULER_TIME");
        } catch (IOException e) {
            throw new BadRequestException(e.getMessage());
        }
    }
}
