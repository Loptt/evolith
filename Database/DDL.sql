BEGIN;
/* CREATE TABLE player(
player_id INT NOT NULL AUTO_INCREMENT,
player_name  VARCHAR(20),
PRIMARY KEY (player_id)
); */
CREATE TABLE game (
game_id INT NOT NULL AUTO_INCREMENT,
game_duration INT,
/*game_score INT NOT NULL,*/
PRIMARY KEY (game_id)
/*
player_id INT NOT NULL,
FOREIGN KEY(player_id) REFERENCES player(player_id)*/
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
organism_speed INT,
organism_stealth INT,
organism_strength INT,
organism_max_health INT,
/*organism_kills INT,
organism_lifespan INT,*/
species_id INT NOT NULL,
PRIMARY KEY (organism_id),
FOREIGN KEY(species_id) REFERENCES species(species_id)
);
CREATE TABLE backup_organism(
backup_organism_id INT NOT NULL AUTO_INCREMENT,
backup_organism_alive INT(1),
backup_organism_generation INT,
backup_organism_speed INT,
backup_organism_stealth INT,
backup_organism_strength INT,
backup_organism_max_health INT,
/*organism_kills INT,
organism_lifespan INT,*/
species_id INT NOT NULL,
PRIMARY KEY (backup_organism_id),
FOREIGN KEY(species_id) REFERENCES species(species_id)
);

/* CREATE TABLE mutation(
mutation_id INT NOT NULL AUTO_INCREMENT,
mutation_name VARCHAR(20),
mutation_value INT,
organism_id INT NOT NULL,
PRIMARY KEY (mutation_id),
FOREIGN KEY(organism_id) REFERENCES organism(organism_id)
); */
COMMIT;