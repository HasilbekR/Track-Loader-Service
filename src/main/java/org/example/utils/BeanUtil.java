package org.example.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BeanUtil {
    private static Connection connection;
    private static ObjectMapper objectMapper;
    public static Connection getConnection(){
        if(connection == null){
            try{
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection(
                        "jdbc:postgresql://db2:5432/postgres",
                        "postgres", "1234"
                );
            } catch (SQLException | ClassNotFoundException e){
                throw new RuntimeException(e);
            }
        }
        return connection;
    }
    public static ObjectMapper getObjectMapper(){
        if(objectMapper == null){
            objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        }
        return objectMapper;
    }
}
