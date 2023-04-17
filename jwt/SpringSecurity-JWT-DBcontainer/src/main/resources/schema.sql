CREATE TABLE enduser (
                         enduser_id INT PRIMARY KEY AUTO_INCREMENT,
                         username VARCHAR(255) UNIQUE,
                         email VARCHAR(255),
                         image LONGBLOB,
                         password VARCHAR(255),
                         role VARCHAR(255)
);

CREATE TABLE token (
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       token VARCHAR(255) UNIQUE,
                       token_type VARCHAR(255),
                       revoked BOOLEAN,
                       expired BOOLEAN,
                       enduser_id INT,
                       FOREIGN KEY (enduser_id) REFERENCES enduser(enduser_id)
);
