package com.app.quantitymeasurement.repository;

import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.app.quantitymeasurement.exception.DatabaseException;
import com.app.quantitymeasurement.util.ConnectionPool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class QuantityMeasurementDatabaseRepository implements IQuantityMeasurementRepository {

    private static final String INSERT_SQL = """
            INSERT INTO quantity_measurements
            (operation_type, measurement_type, operand1, operand2, result, is_error, error_message, created_at)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            """;

    private static final String SELECT_ALL_SQL = """
            SELECT id, operation_type, measurement_type, operand1, operand2, result, is_error, error_message, created_at
            FROM quantity_measurements
            ORDER BY id
            """;

    private static final String SELECT_BY_OPERATION_SQL = """
            SELECT id, operation_type, measurement_type, operand1, operand2, result, is_error, error_message, created_at
            FROM quantity_measurements
            WHERE operation_type = ?
            ORDER BY id
            """;

    private static final String SELECT_BY_MEASUREMENT_SQL = """
            SELECT id, operation_type, measurement_type, operand1, operand2, result, is_error, error_message, created_at
            FROM quantity_measurements
            WHERE measurement_type = ?
            ORDER BY id
            """;

    private static final String COUNT_SQL = "SELECT COUNT(*) FROM quantity_measurements";
    private static final String DELETE_ALL_SQL = "DELETE FROM quantity_measurements";

    private final ConnectionPool connectionPool;

    public QuantityMeasurementDatabaseRepository() {
        this(new ConnectionPool());
    }

    public QuantityMeasurementDatabaseRepository(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
        initializeSchema();
    }

    @Override
    public void save(QuantityMeasurementEntity entity) {
        Connection connection = connectionPool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, entity.getOperationType());
            statement.setString(2, entity.getMeasurementType());
            statement.setString(3, entity.getOperand1());
            statement.setString(4, entity.getOperand2());
            statement.setString(5, entity.getResult());
            statement.setBoolean(6, entity.isError());
            statement.setString(7, entity.getErrorMessage());
            statement.setTimestamp(8, Timestamp.valueOf(entity.getCreatedAt()));

            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to save quantity measurement entity", e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public List<QuantityMeasurementEntity> findAll() {
        return queryEntities(SELECT_ALL_SQL, null);
    }

    @Override
    public List<QuantityMeasurementEntity> findByOperationType(String operationType) {
        return queryEntities(SELECT_BY_OPERATION_SQL, operationType);
    }

    @Override
    public List<QuantityMeasurementEntity> findByMeasurementType(String measurementType) {
        return queryEntities(SELECT_BY_MEASUREMENT_SQL, measurementType);
    }

    @Override
    public int getTotalCount() {
        Connection connection = connectionPool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(COUNT_SQL);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new DatabaseException("Failed to get measurement count", e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public void deleteAll() {
        Connection connection = connectionPool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(DELETE_ALL_SQL)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Failed to delete all measurements", e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public String getPoolStatistics() {
        return connectionPool.getPoolStatistics();
    }

    @Override
    public void releaseResources() {
        connectionPool.shutdown();
    }

    private List<QuantityMeasurementEntity> queryEntities(String sql, String parameter) {
        List<QuantityMeasurementEntity> result = new ArrayList<>();
        Connection connection = connectionPool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            if (parameter != null) {
                statement.setString(1, parameter);
            }

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    result.add(mapRow(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to query measurements", e);
        } finally {
            connectionPool.releaseConnection(connection);
        }

        return result;
    }

    private QuantityMeasurementEntity mapRow(ResultSet rs) throws SQLException {
        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();
        entity.setId(rs.getLong("id"));
        entity.setOperationType(rs.getString("operation_type"));
        entity.setMeasurementType(rs.getString("measurement_type"));
        entity.setOperand1(rs.getString("operand1"));
        entity.setOperand2(rs.getString("operand2"));
        entity.setResult(rs.getString("result"));
        entity.setError(rs.getBoolean("is_error"));
        entity.setErrorMessage(rs.getString("error_message"));

        Timestamp timestamp = rs.getTimestamp("created_at");
        entity.setCreatedAt(timestamp != null ? timestamp.toLocalDateTime() : LocalDateTime.now());

        return entity;
    }

    private void initializeSchema() {
        String schemaSql = loadSchemaSql();

        Connection connection = connectionPool.getConnection();
        try (Statement statement = connection.createStatement()) {
            for (String sql : schemaSql.split(";")) {
                String trimmed = sql.trim();
                if (!trimmed.isEmpty()) {
                    statement.execute(trimmed);
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to initialize database schema", e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    private String loadSchemaSql() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("db/schema.sql");
        if (inputStream == null) {
            throw new DatabaseException("schema.sql not found in resources/db");
        }

        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append(System.lineSeparator());
            }
        } catch (IOException e) {
            throw new DatabaseException("Failed to load schema.sql", e);
        }
        return sb.toString();
    }
}