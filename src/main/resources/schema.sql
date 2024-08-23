DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS cart_items;
DROP TABLE IF EXISTS menus;
DROP TABLE IF EXISTS tables;
DROP TABLE IF EXISTS restaurants;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(50) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       role VARCHAR(20) NOT NULL
);

CREATE TABLE restaurants (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             name VARCHAR(100) NOT NULL,
                             owner_id BIGINT,
                             FOREIGN KEY (owner_id) REFERENCES users(id)
);

CREATE TABLE tables (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        restaurant_id BIGINT,
                        table_number INT NOT NULL,
                        FOREIGN KEY (restaurant_id) REFERENCES restaurants(id),
                        UNIQUE (restaurant_id, table_number)
);

CREATE TABLE menus (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       restaurant_id BIGINT,
                       name VARCHAR(100) NOT NULL,
                       price DECIMAL(10, 2) NOT NULL,
                       description TEXT,
                       FOREIGN KEY (restaurant_id) REFERENCES restaurants(id)
);

CREATE TABLE cart_items (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            user_id BIGINT,
                            menu_id BIGINT,
                            quantity INT NOT NULL,
                            FOREIGN KEY (user_id) REFERENCES users(id),
                            FOREIGN KEY (menu_id) REFERENCES menus(id)
);

CREATE TABLE orders (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        user_id BIGINT,
                        table_id BIGINT,
                        status VARCHAR(20) NOT NULL,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        FOREIGN KEY (user_id) REFERENCES users(id),
                        FOREIGN KEY (table_id) REFERENCES tables(id)
);

CREATE TABLE order_items (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             order_id BIGINT,
                             menu_id BIGINT,
                             quantity INT NOT NULL,
                             price DECIMAL(10, 2) NOT NULL,
                             FOREIGN KEY (order_id) REFERENCES orders(id),
                             FOREIGN KEY (menu_id) REFERENCES menus(id)
);