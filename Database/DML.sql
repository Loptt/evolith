INSERT INTO 
player(player_name) VALUES
("Erick"),
("Carlos"),
("Moises"),
("Victor"),
("Pablo");

INSERT INTO 
game(game_duration,game_score,player_id) 
VALUES 
(23,223,1),
(23,2343,1), 
(5,10,2),
(5,23,2), 
(12344,1000,4), 
(133,323,5),
(1344,333,3),
(1432,634,1),
(1223,56,2); 

INSERT INTO 
species(species_name,game_id) 
VALUES 
("Mongolitos",1),
("Juanitos",2),
("Panthera",3),
("Ocasus",4),
("Ralsum",5),
("Oceanica",6);

INSERT INTO organism(organism_alive,organism_generation, organism_kills, organism_lifespan,species_id) VALUES
(1,1,2,120,1),
(0,1,3,120,1),
(0,2,2,120,1),
(0,2,1,120,1),
(0,3,2,120,1),
(0,4,1,120,1),
(0,5,0,120,1),
(0,6,0,120,1),
(0,7,0,120,1),
(0,8,0,120,1),
(0,9,0,120,1),
(0,10,3,120,1),
(0,11,3,120,1),
(0,132,3,120,1),
(0,41,3,120,1),
(0,15,3,120,1),
(1,1,3,120,1),
(0,1,3,120,1),
(1,1,3,120,1),
(1,1,3,120,1),
(1,1,3,120,2),
(1,1,3,120,2),
(1,1,3,120,2),
(1,1,3,120,2),
(0,1,3,120,2),
(0,1,3,120,2),
(0,1,3,120,2),
(0,1,3,120,2),
(0,1,3,120,2),
(0,1,3,120,2),
(0,1,3,120,2),
(1,1,3,120,2),
(1,1,3,120,2),
(1,1,3,120,2),
(1,1,3,120,2),
(1,1,3,120,2),
(1,1,3,120,2),
(0,1,3,120,2),
(1,1,3,120,2),
(1,1,3,120,2),
(1,1,3,120,2),
(0,1,3,120,2),
(1,1,3,120,2),
(1,1,3,120,2),
(1,1,3,120,2),
(1,1,3,120,2),
(0,1,3,120,2),
(1,10,3,120,2),
(1,1,3,120,2),
(1,1,3,120,2),
(1,1,3,120,2),
(1,12,3,120,2),
(0,1,3,120,2),
(1,1,3,120,2),
(1,1,3,120,2),
(1,10,3,120,2),
(1,1,3,120,2),
(1,1,3,120,2),
(1,1,3,120,2),
(0,1,3,120,3),
(0,1,3,120,3),
(0,1,3,120,3),
(0,1,3,120,3),
(0,1,3,120,3),
(1,1,3,120,3),
(1,1,3,120,3),
(0,1,3,120,3),
(0,1,3,120,3),
(0,1,3,120,3),
(0,1,3,120,3),
(0,1,3,120,3),
(0,1,3,120,3),
(0,1,3,120,3),
(0,1,3,120,3),
(0,1,3,120,3),
(0,1,3,120,3),
(1,1,3,120,3),
(1,1,3,120,3),
(1,1,3,120,3),
(1,1,3,120,3),
(1,1,3,120,3),
(1,1,3,120,3),
(1,1,3,120,3),
(1,45,3,120,3),
(1,1,3,120,3),
(1,1,3,120,3),
(1,1,3,120,3),
(1,1,3,120,3),
(1,1,3,120,3),
(1,1,3,120,3),
(1,1,3,120,4),
(1,1,3,120,4),
(1,67,3,120,4),
(1,1,3,120,4),
(1,1,3,120,4),
(1,1,3,120,4),
(1,1,3,120,4),
(1,1,3,120,4),
(1,1,3,120,4),
(1,1,3,120,4),
(1,1,3,120,4),
(1,1,3,120,4),
(1,1,3,120,4),
(1,1,3,120,4),
(1,1,3,120,4),
(1,1,3,120,4),
(1,1,3,120,4),
(1,1,3,120,4),
(1,1,3,120,4),
(1,1,3,120,4),
(1,1,3,120,4),
(1,1,3,120,5),
(1,1,3,120,5),
(1,1,3,120,5),
(1,1,3,120,5),
(1,1,3,120,5),
(1,1,3,120,5),
(1,1,3,120,5),
(1,1,3,120,5),
(1,1,3,120,5),
(1,1,3,120,5),
(1,1,3,120,5),
(1,1,3,120,5),
(1,1,3,120,5),
(1,1,3,120,5),
(1,1,3,120,5),
(1,78,3,120,5),
(0,1,3,120,6),
(0,1,3,120,6),
(0,1,3,120,6),
(0,1,3,120,6),
(0,1,3,120,6),
(0,1,3,120,6),
(0,1,3,120,6),
(0,1,3,120,6),
(0,1,3,120,6),
(0,1,3,120,6),
(0,1,3,120,6),
(0,45,3,120,6),
(0,1,3,120,6),
(0,1,3,120,6),
(0,1,3,120,6);


DELETE FROM organism; ALTER TABLE organism AUTO_INCREMENT = 1;
DELETE FROM species; ALTER TABLE species AUTO_INCREMENT = 1;
DELETE FROM game; ALTER TABLE game AUTO_INCREMENT = 1;
DELETE FROM player;ALTER TABLE player AUTO_INCREMENT = 1;
