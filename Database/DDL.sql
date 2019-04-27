BEGIN;
SET DATE DMY;
CREATE TABLE Game (
game_id INT NOT NULL AUTO_INCREMENT,
game_date_created DATE NOT NULL,
game_duration INT NOT NULL,
game_score INT NOT NULL,
PRIMARY KEY (game_id)
);
CREATE TABLE Enemy(
enemy_id INT NOT NULL AUTO_INCREMENT,
enemy_position_x INT NOT NULL,
enemy_position_y INT NOT NULL,
enemy_size INT,
PRIMARY KEY (enemy_id)
);
CREATE TABLE Species(
species_id INT NOT NULL AUTO_INCREMENT,
species_color VARCHAR(20),
species_name VARCHAR(20),
PRIMARY KEY (species_id)
);
CREATE TABLE Organism(
organism_id INT NOT NULL AUTO_INCREMENT,
organism_name VARCHAR(20),
organism_alive BOOL,
organism_generation INT,
organism_position_x INT,
organism_position_y INT,
organism_parent_id INT,
PRIMARY KEY (organism_id),
FOREIGN KEY(organism_parent_id) REFERENCES Organism(organism_id)
);
CREATE TABLE Mutation(
mutation_id INT NOT NULL AUTO_INCREMENT,
strength INT,
size INT,
stealth INT,
speed INT,
mutation_name VARCHAR(20),
PRIMARY KEY (mutation_id)
);
CREATE TABLE Resources(
resources_id INT NOT NULL AUTO_INCREMENT,
resources_position_x INT NOT NULL,
resources_position_y INT NOT NULL,
resources_quantity INT,
PRIMARY KEY (resources_id)
);
CREATE TABLE Player(
player_id INT NOT NULL AUTO_INCREMENT,
player_name  VARCHAR(20)
PRIMARY KEY (player_id )
);

COMMIT;