------------------------------------------------------------------
-- Queries in this file will be executed during the app startup --
------------------------------------------------------------------

-- Shows
INSERT INTO Shows (title, release_year) VALUES ('The Lord of The Rings: Fellowship of the Ring', 2001);
INSERT INTO Shows (title, release_year) VALUES ('The Lord of The Rings: The Two Towers', 2002);
INSERT INTO Shows (title, release_year) VALUES ('The Lord of The Rings: The Return of the King', 2003);

INSERT INTO Shows (title, release_year) VALUES ('The Hobbit: An Unexpected Journey', 2012);
INSERT INTO Shows (title, release_year) VALUES ('The Hobbit: The Desolation of Smaug', 2013);
INSERT INTO Shows (title, release_year) VALUES ('The Hobbit: The Battle of the Five Armies', 2014);

-- Actors
INSERT INTO actors (first_name, last_name, role_name) VALUES ('Elijah', 'Wood', 'Frodo Baggins');
INSERT INTO actors (first_name, last_name, role_name) VALUES ('Viggo', 'Mortensen', 'Aragorn');
INSERT INTO actors (first_name, last_name, role_name) VALUES ('Sala', 'Baker', 'Sauron');
INSERT INTO actors (first_name, last_name, role_name) VALUES ('Hugo', 'Weaving', 'Elrond');
INSERT INTO actors (first_name, last_name, role_name) VALUES ('Martin', 'Freeman', 'Bilbo Baggins');

-- Links
INSERT INTO shows_actors (show_id, actor_id) VALUES (1, 1);
INSERT INTO shows_actors (show_id, actor_id) VALUES (1, 2);
INSERT INTO shows_actors (show_id, actor_id) VALUES (1, 3);
INSERT INTO shows_actors (show_id, actor_id) VALUES (1, 4);

INSERT INTO shows_actors (show_id, actor_id) VALUES (2, 1);
INSERT INTO shows_actors (show_id, actor_id) VALUES (2, 2);

INSERT INTO shows_actors (show_id, actor_id) VALUES (3, 1);
INSERT INTO shows_actors (show_id, actor_id) VALUES (3, 2);
INSERT INTO shows_actors (show_id, actor_id) VALUES (3, 3);
INSERT INTO shows_actors (show_id, actor_id) VALUES (3, 4);

INSERT INTO shows_actors (show_id, actor_id) VALUES (4, 4);
INSERT INTO shows_actors (show_id, actor_id) VALUES (4, 5);

INSERT INTO shows_actors (show_id, actor_id) VALUES (5, 5);

INSERT INTO shows_actors (show_id, actor_id) VALUES (6, 5);
