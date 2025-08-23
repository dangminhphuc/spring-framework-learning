CREATE TABLE orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    customer_name VARCHAR(255),
    status VARCHAR(50)
);

CREATE TABLE products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    stock INT
);

CREATE TABLE audit_log (
    id INT AUTO_INCREMENT PRIMARY KEY,
    event_name VARCHAR(255),
    event_timestamp TIMESTAMP
);

-- Initial data
INSERT INTO products (name, stock) VALUES ('Laptop', 10);