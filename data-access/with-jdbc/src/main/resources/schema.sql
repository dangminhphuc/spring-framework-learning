DROP TABLE IF EXISTS foo;

CREATE TABLE IF NOT EXISTS foo (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    number INTEGER NOT NULL,
    string VARCHAR(255),
    bool BOOLEAN NOT NULL,
    date DATE,
    amount DECIMAL(19,2)
);

INSERT INTO foo (number, string, bool, date, amount) VALUES
(123, 'alpha', TRUE, DATE '2023-01-15', 100.00),
(234, 'beta', FALSE, DATE '2024-06-01', 250.50),
(345, NULL, TRUE, NULL, NULL);
