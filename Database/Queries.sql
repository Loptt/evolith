BEGIN;

    SELECT 'TOP RANKING of players';

    SELECT player_name AS 'User', species_name AS "Species' Name", game_duration AS 'Duration (Seconds)', game_score as 'Total Score',
    SUM(organism_alive) as 'Survivors' , MAX(organism_generation) as 'Last Generation', SUM(organism_kills) as 'Total Kills', MAX(organism_lifespan) 'Oldest Organism'
    FROM player P
    JOIN game G ON P.player_id = G.player_id
    JOIN species S ON S.game_id = G.game_id
    JOIN organism O ON O.species_id = S.species_id
    GROUP BY O.species_id
    ORDER BY G.game_score DESC
    LIMIT 10;
    
    SELECT player_name, species_name, game_duration, game_score, SUM(O.organism_alive), MAX(O.organism_generation), SUM(O.organism_kills), MAX(O.organism_lifespan)
    FROM player P, game G, species S, organism O
    WHERE P.player_id = G.player_id AND S.game_id = G.game_id AND O.species_id = S.species_id
    GROUP BY O.species_id
    ORDER BY G.game_score DESC
    LIMIT 10;


    SELECT 'Top Ranking Basic';
    SELECT player_name, species_name, game_duration, game_score
    FROM player P, game G, species S
    WHERE P.player_id = G.player_id AND S.game_id = G.game_id
    ORDER BY G.game_score DESC
    LIMIT 10;
    SELECT 'LAST GAME ID'
    SELECT game_id FROM game ORDER BY game_id DESC LIMIT 1;
    
    /*
    Get last record
    SELECT game_id FROM game ORDER BY game_id DESC LIMIT 1;


-----------UPDATES
    Update specific game id
    UPDATE game set game_duration = _newduration_, WHERE game_id = "";
    UPDATE game SET game_duration = 1, WHERE game_id = 1;

-----------INSERTS
INSERT INTO organism(organism_alive,organism_generation, organism_kills, organism_lifespan,species_id) VALUES

    */
