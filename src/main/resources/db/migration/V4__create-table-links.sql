CREATE TABLE links (
    id char(36) NOT NULL,
    title VARCHAR(255) NOT NULL,
    url VARCHAR(255) NOT NULL,
    trip_id char(36),
    FOREIGN KEY (trip_id) REFERENCES trips(id) ON DELETE CASCADE,
    PRIMARY KEY (id)
);