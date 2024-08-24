CREATE TABLE activities (
    id char(36) NOT NULL,
    title VARCHAR(255) NOT NULL,
    occurs_at TIMESTAMP NOT NULL,
    trip_id char(36),
    FOREIGN KEY (trip_id) REFERENCES trips(id) ON DELETE CASCADE,
    PRIMARY KEY (id)
);