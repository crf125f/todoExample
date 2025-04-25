CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    team_id INT NOT NULL,
    FOREIGN KEY (team_id) REFERENCES team(id)
);
