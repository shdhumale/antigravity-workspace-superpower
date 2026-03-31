-- Create database if it doesn't already exist (matching application.properties)
CREATE DATABASE IF NOT EXISTS ticketdb;
USE ticketdb;

-- Create tickets table mapping our TicketEntity
CREATE TABLE IF NOT EXISTS tickets (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    status VARCHAR(50) NOT NULL
);

-- Optional: Insert dummy data representing the various states 
INSERT INTO tickets (name, description, status) VALUES 
('Setup MySQL', 'Install and start the local MySQL server for testing.', 'DONE'),
('Fix Login Bug', 'User sees an error when entering special characters on login screen', 'ASSIGNED'),
('Update Dependencies', 'Update Spring Boot to the latest stable release.', 'NEW'),
('Server Outage', 'The staging environment is returning 502 Bad Gateway.', 'ESCALATE');
