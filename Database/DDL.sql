BEGIN;
CREATE TABLE player(
player_id INT NOT NULL AUTO_INCREMENT,
player_name  VARCHAR(20),
PRIMARY KEY (player_id)
);
CREATE TABLE game (
game_id INT NOT NULL AUTO_INCREMENT,
game_duration INT,
game_score INT NOT NULL,
player_id INT NOT NULL,
PRIMARY KEY (game_id),
FOREIGN KEY(player_id) REFERENCES player(player_id)
);
CREATE TABLE species(
species_id INT NOT NULL AUTO_INCREMENT,
species_color VARCHAR(20),
species_name VARCHAR(20),
game_id INT NOT NULL,
PRIMARY KEY (species_id),
FOREIGN KEY(game_id) REFERENCES game(game_id)
);
CREATE TABLE organism(
organism_id INT NOT NULL AUTO_INCREMENT,
organism_alive INT(1),
organism_generation INT,
organism_kills INT,
organism_lifespan INT,
species_id INT NOT NULL,
PRIMARY KEY (organism_id),
FOREIGN KEY(species_id) REFERENCES species(species_id)
);
CREATE TABLE mutation(
mutation_id INT NOT NULL AUTO_INCREMENT,
mutation_name VARCHAR(20),
mutation_value INT,
organism_id INT NOT NULL,
PRIMARY KEY (mutation_id),
FOREIGN KEY(organism_id) REFERENCES organism(organism_id)
);
COMMIT;