CREATE TABLE participants (
    id char(36) NOT NULL,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    is_confirmed BOOLEAN NOT NULL,
    trip_id char(36),
    FOREIGN KEY (trip_id) REFERENCES trips(id) ON DELETE CASCADE,
    PRIMARY KEY (id)
)