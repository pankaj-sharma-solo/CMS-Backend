DROP TABLE IF EXISTS CONTACTS;
CREATE TABLE CONTACTS (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL ,
    last_name VARCHAR(50),
    phone VARCHAR(10) NOT NULL ,
    email VARCHAR(200) NOT NULL,
    created_date TIMESTAMP NOT NULL ,
    updated_date TIMESTAMP,
    is_deleted BOOLEAN DEFAULT FALSE
);

DROP TABLE IF EXISTS APIKEYS;
CREATE TABLE APIKEYS (
    id INT AUTO_INCREMENT PRIMARY KEY,
    client_id VARCHAR(10) NOT NULL ,
    api_key VARCHAR(50) NOT NULL,
    created_date TIMESTAMP NOT NULL ,
    updated_date TIMESTAMP,
    is_deleted BOOLEAN DEFAULT FALSE
);