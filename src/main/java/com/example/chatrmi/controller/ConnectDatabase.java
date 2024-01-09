package com.example.chatrmi.controller;

import javax.swing.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class ConnectDatabase {
    private static Connection conn;
    private static String url = "jdbc:mysql://localhost:3306/chatsocket";
    private static String user = "root";
    private static String password = "admin";

    public static Connection getConnection() {
        if(conn == null){
            try{
                conn = DriverManager.getConnection(url, user, password);
            }catch (SQLException e){
                System.out.println("Failed "+ e.getMessage());
            }
        }

        return conn;
    }


    public static boolean registerUser(String username, String password) {
        boolean ck = false;
        try {
            Statement statement = getConnection().createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM user WHERE username='" + username + "'");
            if (result.next()) {
                JOptionPane.showMessageDialog(null, "This user is already registered.");
            } else {
                String hashedPassword = hashPassword(password);
                Statement statement2 = getConnection().createStatement();
                statement2.executeUpdate("INSERT INTO user(username, password) VALUES ('" + username + "', '" + hashedPassword + "')");
                statement2.close();
                ck = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ck;
    }


    private static String hashPassword(String password) {
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());

            StringBuilder sb = new StringBuilder();
            for(byte b : hashedBytes) {
                sb.append(String.format("%02x",b));
            }
            return sb.toString();
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
            throw new RuntimeException("Error hashing password: "+ e.getMessage());
        }
    }

    public static boolean loginUser(String username, String enteredPassword) {
        boolean success = false;
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement("SELECT * FROM user WHERE username=?");
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String hashedPasswordFromDatabase = resultSet.getString("password");
                if (checkPassword(enteredPassword, hashedPasswordFromDatabase)) {
                    success = true;
                } else {
                    JOptionPane.showMessageDialog(null, "Username or password is not valid");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Username or password is not valid");
            }

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }


    private static boolean checkPassword(String plainTextPassword, String hashedPassword) {
        return hashPassword(plainTextPassword).equals(hashedPassword);
    }



}
