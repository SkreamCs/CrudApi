CREATE TABLE labels(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50),
    status VARCHAR(30)
);
CREATE TABLE writers(
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       name VARCHAR(50),
                       status VARCHAR(30)
);

CREATE TABLE posts(
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       name VARCHAR(50),
                       PostStatus VARCHAR(30),
                       writer_id INT,
                       FOREIGN KEY (writer_id) REFERENCES writers(id)
);
CREATE TABLE post_labels(
    post_id INT NOT NULL ,
    label_id INT NOT NULL ,
    FOREIGN KEY (post_id) REFERENCES posts(id),
    FOREIGN KEY (label_id) REFERENCES labels(id),
    UNIQUE (post_id, label_id)
)
