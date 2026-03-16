CREATE TABLE IF NOT EXISTS quantity_measurements (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    operation_type VARCHAR(50) NOT NULL,
    measurement_type VARCHAR(50),
    operand1 VARCHAR(255) NOT NULL,
    operand2 VARCHAR(255),
    result VARCHAR(255),
    is_error BOOLEAN NOT NULL,
    error_message VARCHAR(500),
    created_at TIMESTAMP NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_qm_operation_type
ON quantity_measurements(operation_type);

CREATE INDEX IF NOT EXISTS idx_qm_measurement_type
ON quantity_measurements(measurement_type);