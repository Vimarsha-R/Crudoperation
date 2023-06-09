CREATE TABLE IF NOT EXISTS users (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) UNIQUE NOT NULL,
  email VARCHAR(255) UNIQUE NOT NULL,
  mobile BIGINT UNIQUE NOT NULL,
  age INT NOT NULL,
  CONSTRAINT duplicate_check UNIQUE (name, email,mobile)
);