DROP TABLE IF EXISTS organism; 
DROP TABLE IF EXISTS backup_organism;
DROP TABLE IF EXISTS species;
DROP TABLE IF EXISTS game; 

DELETE FROM organism; ALTER TABLE organism AUTO_INCREMENT = 1;
DELETE FROM backup_organism; ALTER TABLE backup_organism AUTO_INCREMENT = 1;
DELETE FROM species; ALTER TABLE species AUTO_INCREMENT = 1;
DELETE FROM game; ALTER TABLE game AUTO_INCREMENT = 1;

INSERT INTO 
player(player_name) VALUES
("Erick"),
("Carlos"),
("Moises"),
("Victor"),
("Pablo");

INSERT INTO 
game(game_duration) 
VALUES 
(223),
(1800), 
(1500),
(1400), 
(2000), 
(1400),
(1900),
(334),
(1223); 

INSERT INTO 
species(species_name,game_id,species_intelligence) 
VALUES 
("Mongolitos",1,700),
("Juanitos",2,800),
("Panthera",3,400),
("Ocasus",4,400),
("Ralsum",5,300),
("Oceanica",6,3120),
("Pablitos",7,3120),
("Zas",8,3120),
("Culebra",9,3120);

INSERT INTO organism(organism_alive, organism_generation,organism_speed,organism_stealth,organism_strength,organism_max_health,species_id) 
VALUES
(0,1,0,0,0,0,1),
(0,2,20,0,0,0,1),
(0,2,00,0,20,0,1),
(0,3,20,40,0,0,1),
(0,3,20,40,0,0,1),
(0,3,20,40,0,0,1),
(0,3,20,40,0,0,1),
(0,3,20,40,0,0,1),
(0,4,20,40,60,60,1),
(0,4,20,40,60,60,1),
(0,4,20,40,60,60,1),
(0,4,20,40,60,60,1),
(0,4,20,40,60,60,1),
(0,4,20,40,60,60,1),
(0,4,20,40,60,60,1),
(0,4,20,40,60,60,1),
(0,4,20,40,60,60,1),
(0,4,20,40,60,60,1),
(0,4,20,40,60,60,1),
(0,4,20,40,60,60,1),
(0,4,20,40,60,60,1),
(0,4,20,40,60,60,1),
(0,4,20,40,60,60,1),
(0,4,20,40,60,60,1), 
(0,5,80,40,80,80,1),
(0,5,80,40,80,80,1),
(0,5,80,40,80,80,1),
(0,5,80,40,80,80,1), 
(0,5,80,40,80,80,1),
(0,5,80,40,80,80,1),
(0,5,80,40,80,80,1),
(0,5,80,40,80,80,1),    
(0,5,80,40,80,80,1),
(0,5,80,40,80,80,1),
(0,5,80,40,80,80,1),
(0,5,80,40,80,80,1),    
(0,5,80,40,80,80,1),
(0,5,80,40,80,80,1),
(0,5,80,40,80,80,1),
(0,5,80,40,80,80,1),
(0,5,80,40,80,80,1),
(0,5,80,40,80,80,1),
(0,5,80,40,80,80,1),
(0,5,80,40,80,80,1), 
(0,5,80,40,80,80,1),
(0,5,80,40,80,80,1),
(0,5,80,40,80,80,1),
(0,5,80,40,80,80,1),    
(0,5,80,40,80,80,1),
(0,5,80,40,80,80,1),
(0,5,80,40,80,80,1),
(0,5,80,40,80,80,1),    
(0,5,80,40,80,80,1),
(0,5,80,40,80,80,1),
(0,5,80,40,80,80,1),
(0,5,80,40,80,80,1),  
(0,5,80,40,80,80,1),
(0,5,80,40,80,80,1),
(0,5,80,40,80,80,1),
(0,5,80,40,80,80,1), 
(0,5,80,40,80,80,1),
(0,5,80,40,80,80,1),
(0,5,80,40,80,80,1),
(0,5,80,40,80,80,1),    
(0,5,80,40,80,80,1),
(0,5,80,40,80,80,1),
(0,5,80,40,80,80,1),
(0,5,80,40,80,80,1),    
(0,5,80,40,80,80,1),
(0,5,80,40,80,80,1),
(0,5,80,40,80,80,1),
(0,5,80,40,80,80,1),  
(0,5,80,40,80,80,1),
(0,5,80,40,80,80,1),
(0,5,80,40,80,80,1),
(0,5,80,40,80,80,1), 
(0,5,80,40,80,80,1),
(0,5,80,40,80,80,1),
(0,5,80,40,80,80,1),
(0,5,80,40,80,80,1),    
(0,5,80,40,80,80,1),
(0,5,80,40,80,80,1),
(0,5,80,40,80,80,1),
(0,5,80,40,80,80,1),    
(0,5,80,40,80,80,1),
(0,5,80,40,80,80,1),
(0,5,80,40,80,80,1),
(0,5,80,40,80,80,1),  
(0,5,80,40,80,80,1),
(0,5,80,40,80,80,1),
(0,5,80,40,80,80,1),
(0,5,80,40,80,80,1), 
(0,5,80,40,80,80,1),
(0,5,80,40,80,80,1),
(0,5,80,40,80,80,1),
(0,5,80,40,80,80,1),    
(0,5,80,40,80,80,1),
(0,5,80,40,80,80,1),
(0,5,80,40,80,80,1),
(0,5,80,40,80,80,1),    
(0,5,80,40,80,80,1),
(0,5,80,40,80,80,1),
(0,5,80,40,80,80,1),
(0,5,80,40,80,80,1),
/*Species 2 */
(0,1,0,0,0,0,2),
(0,2,20,0,0,0,2),
(0,2,00,0,20,0,2),
(0,3,20,40,0,0,2),
(0,3,20,40,0,0,2),
(0,3,20,40,0,0,2),
(0,3,20,40,0,0,2),
(0,3,20,40,0,0,2),
(0,4,20,40,60,60,2),
(0,4,20,40,60,60,2),
(0,4,20,40,60,60,2),
(0,4,20,40,60,60,2),
(0,4,20,40,60,60,2),
(0,4,20,40,60,60,2),
(0,4,20,40,60,60,2),
(0,4,20,40,60,60,2),
(0,4,20,40,60,60,2),
(0,4,20,40,60,60,2),
(0,4,20,40,60,60,2),
(0,4,20,40,60,60,2),
(0,4,20,40,60,60,2),
(0,4,20,40,60,60,2),
(0,4,20,40,60,60,2),
(0,4,20,40,60,60,2), 
(0,5,80,40,80,80,2),
(0,5,80,40,80,80,2),
(0,5,80,40,80,80,2),
(0,5,80,40,80,80,2), 
(0,5,80,40,80,80,2),
(0,5,80,40,80,80,2),
(0,5,80,40,80,80,2),
(0,5,80,40,80,80,2),    
(0,5,80,40,80,80,2),
(0,5,80,40,80,80,2),
(0,5,80,40,80,80,2),
(0,5,80,40,80,80,2),    
(0,5,80,40,80,80,2),
(0,5,80,40,80,80,2),
(0,5,80,40,80,80,2),
(0,5,80,40,80,80,2),
(0,5,80,40,80,80,2),
(0,5,80,40,80,80,2),
(0,5,80,40,80,80,2),
(0,5,80,40,80,80,2), 
(0,5,80,40,80,80,2),
(0,5,80,40,80,80,2),
(0,5,80,40,80,80,2),
(0,5,80,40,80,80,2),    
(0,5,80,40,80,80,2),
(0,5,80,40,80,80,2),
(0,5,80,40,80,80,2),
(0,5,80,40,80,80,2),    
(0,5,80,40,80,80,2),
(0,5,80,40,80,80,2),
(0,5,80,40,80,80,2),
(0,5,80,40,80,80,2),  
(0,5,80,40,80,80,2),
(0,5,80,40,80,80,2),
(0,5,80,40,80,80,2),
(0,5,80,40,80,80,2), 
(0,5,80,40,80,80,2),
(0,5,80,40,80,80,2),
(0,5,80,40,80,80,2),
(0,5,80,40,80,80,2),    
(0,5,80,40,80,80,2),
(0,5,80,40,80,80,2),
(0,5,80,40,80,80,2),
(0,5,80,40,80,80,2),    
(0,5,80,40,80,80,2),
(0,5,80,40,80,80,2),
(0,5,80,40,80,80,2),
(0,5,80,40,80,80,2),  
(0,5,80,40,80,80,2),
(0,5,80,40,80,80,2),
(0,5,80,40,80,80,2),
(0,5,80,40,80,80,2), 
(0,5,80,40,80,80,2),
(0,5,80,40,80,80,2),
(0,5,80,40,80,80,2),
(0,5,80,40,80,80,2),    
(0,5,80,40,80,80,2),
(0,5,80,40,80,80,2),
(0,5,80,40,80,80,2),
(0,5,80,40,80,80,2),    
(0,5,80,40,80,80,2),
(0,5,80,40,80,80,2),
(0,5,80,40,80,80,2),
(0,5,80,40,80,80,2),  
(0,5,80,40,80,80,2),
(0,5,80,40,80,80,2),
(0,5,80,40,80,80,2),
(0,5,80,40,80,80,2), 
(0,5,80,40,80,80,2),
(0,5,80,40,80,80,2),
(0,5,80,40,80,80,2),
(0,5,80,40,80,80,2),    
(0,5,80,40,80,80,2),
(0,5,80,40,80,80,2),
(0,5,80,40,80,80,2),
(0,5,80,40,80,80,2),
(0,5,80,40,80,80,2),
(0,5,80,40,80,80,2),
(0,5,80,40,80,80,2),
(0,5,80,40,80,80,2);