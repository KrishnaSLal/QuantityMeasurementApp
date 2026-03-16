package com.app.quantitymeasurement.util;

import com.app.quantitymeasurement.exception.DatabaseException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class ConnectionPool {

    private final String url;
    private final String username;
    private final String password;
    private final int maxPoolSize;

    private final BlockingQueue<Connection> availableConnections;
    private final AtomicInteger totalConnections = new AtomicInteger(0);

    public ConnectionPool() {
        this(
                ApplicationConfig.getDbDriver(),
                ApplicationConfig.getDbUrl(),
                ApplicationConfig.getDbUsername(),
                ApplicationConfig.getDbPassword(),
                ApplicationConfig.getInitialPoolSize(),
                ApplicationConfig.getMaxPoolSize()
        );
    }

    public ConnectionPool(
            String driver,
            String url,
            String username,
            String password,
            int initialPoolSize,
            int maxPoolSize) {

        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new DatabaseException("Database driver not found: " + driver, e);
        }

        this.url = url;
        this.username = username;
        this.password = password;
        this.maxPoolSize = maxPoolSize;
        this.availableConnections = new LinkedBlockingQueue<>();

        for (int i = 0; i < initialPoolSize; i++) {
            availableConnections.offer(createNewConnection());
            totalConnections.incrementAndGet();
        }
    }

    public Connection getConnection() {
        Connection connection = availableConnections.poll();

        try {
            if (connection != null && !connection.isClosed()) {
                return connection;
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to validate pooled connection", e);
        }

        if (totalConnections.get() < maxPoolSize) {
            totalConnections.incrementAndGet();
            return createNewConnection();
        }

        throw new DatabaseException("Connection pool exhausted");
    }

    public void releaseConnection(Connection connection) {
        if (connection == null) {
            return;
        }

        try {
            if (connection.isClosed()) {
                totalConnections.decrementAndGet();
                return;
            }
            availableConnections.offer(connection);
        } catch (SQLException e) {
            throw new DatabaseException("Failed to release connection", e);
        }
    }

    public String getPoolStatistics() {
        int available = availableConnections.size();
        int total = totalConnections.get();
        int active = total - available;

        return "ConnectionPool{total=" + total + ", active=" + active + ", idle=" + available + "}";
    }

    public void shutdown() {
        while (!availableConnections.isEmpty()) {
            Connection connection = availableConnections.poll();
            if (connection != null) {
                try {
                    connection.close();
                    totalConnections.decrementAndGet();
                } catch (SQLException e) {
                    throw new DatabaseException("Failed to close pooled connection", e);
                }
            }
        }
    }

    private Connection createNewConnection() {
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new DatabaseException("Failed to create database connection", e);
        }
    }
}