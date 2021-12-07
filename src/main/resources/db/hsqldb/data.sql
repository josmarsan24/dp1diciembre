-- One admin user, named admin1 with passwor 4dm1n and authority admin
INSERT INTO users(username,password,enabled) VALUES ('admin1','4dm1n',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (1,'admin1','admin');

INSERT INTO users(username,password,enabled) VALUES ('e1','e1',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (9,'e1','entrenador');

INSERT INTO users(username,password,enabled) VALUES ('e2','e2',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (10,'e2','entrenador');

INSERT INTO users(username,password,enabled) VALUES ('d1','d1',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (11,'d1','deportista');

INSERT INTO users(username,password,enabled) VALUES ('d2','d2',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (12,'d2','deportista');

INSERT INTO deportes(id,name) VALUES (1,'100 metros lisos');
INSERT INTO deportes(id,name) VALUES (2,'200 metros vallas');
INSERT INTO deportes(id,name) VALUES (3,'Lanzamiento de jabalina');

INSERT INTO pistas(id,name,aforo,ciudad,deporte_id) VALUES (1,'Pista de Huelva de 100 metros',2000,'Huelva',1);
INSERT INTO pistas(id,name,aforo,ciudad,deporte_id) VALUES (2,'Pista de Sevilla de 200 metros',3000,'Sevilla',2);
INSERT INTO pistas(id,name,aforo,ciudad,deporte_id) VALUES (3,'Pista de Dos Hermanas de jabalina',2500,'Dos Hermanas',3);
INSERT INTO pistas(id,name,aforo,ciudad,deporte_id) VALUES (4,'Pista de Sevilla de jabalina',3500,'Sevilla',3);
INSERT INTO pistas(id,name,aforo,ciudad,deporte_id) VALUES (5,'Pista de Dos Hermanas de 100 metros',1700,'Dos Hermanas',1);


INSERT INTO entrenadores(id, nombre, apellidos, dni, email, deporte_id) VALUES (1, 'Paco', 'Fernández','77788888J','pacofernandez@gmail.com',1);
INSERT INTO entrenadores(id, nombre, apellidos, dni, email, deporte_id) VALUES (2, 'Rosa', 'Romero','21512426M','rosaromero@gmail.com',2);
INSERT INTO entrenadores(id, nombre, apellidos, dni, email, deporte_id) VALUES (3, 'Mario', 'González','72638153S','marioGonz123@gmail.com',1);
INSERT INTO entrenadores(id, nombre, apellidos, dni, email, deporte_id, username) VALUES (4, 'Pedro', 'García Rojas','52891643F','pedrogarcia94@hotmail.com',2,'e1');
INSERT INTO entrenadores(id, nombre, apellidos, dni, email, deporte_id, username) VALUES (5, 'Noa', 'Herrero ','84531765F','',3,'e2');

INSERT INTO torneos(id,name,fecha_inicio,fecha_fin,pista_id,deporte_id) VALUES (1,'Torneo Huelva de 100 metros','2021-12-26','2021-12-30',1,1);
INSERT INTO torneos(id,name,fecha_inicio,fecha_fin,pista_id,deporte_id) VALUES (2,'Torneo Sevilla de 200 metros de Primavera','2020-05-16','2020-05-20',2,2);
INSERT INTO torneos(id,name,fecha_inicio,fecha_fin,pista_id,deporte_id) VALUES (3,'Torneo Sevilla de 200 metros de Invierno','2021-12-25','2021-12-26',2,2);
INSERT INTO torneos(id,name,fecha_inicio,fecha_fin,pista_id,deporte_id) VALUES (4,'Torneo Dos Hermanas Javalina','2021-01-05','2021-01-10',3,3);
INSERT INTO torneos(id,name,fecha_inicio,fecha_fin,pista_id,deporte_id) VALUES (5,'Torneo Dos Hermanas 100 metros','2022-01-26','2021-01-30',5,1);

INSERT INTO patrocinadores(id,name,tipo,twitter,instagram) VALUES (1,'Powerade','Bebidas energeticas','PoweradeMx','powerade');
INSERT INTO patrocinadores(id,name,tipo,twitter,instagram) VALUES (2,'Adidas','Ropa deportiva','adidas_ES','adidas_es');
INSERT INTO patrocinadores(id,name,tipo,twitter,instagram) VALUES (3,'Myprotein','Ropa y nutrición deportiva','Myprotein','myproteines');

INSERT INTO athletes(id, nombre, apellidos, dni, email, height, weight, genero,entrenador_id, deporte_id, patrocinador_id) VALUES (1,'Lucas','Martínez', '77788899J','lucasmartinez@gmail.com','1.80','86',0,1,1,1);
INSERT INTO athletes(id, nombre, apellidos, dni, email, height, weight, genero,entrenador_id, deporte_id, patrocinador_id) VALUES (2,'Carlos','Fernández','77678849S','carlosfernandez@gmail.com','1.76','70',0,1,1,2);
INSERT INTO athletes(id, nombre, apellidos, dni, email, height, weight, genero,entrenador_id, deporte_id, patrocinador_id) VALUES (3,'Pablo','Aguilar', '55973188H','pabloAgui33@gmail.com','1.74','80',0,1,1,3);
INSERT INTO athletes(id, nombre, apellidos, dni, email, height, weight, genero,entrenador_id, deporte_id) VALUES (4,'María','López','48612357K','marialopez@gmail.es','1.65','60',1,2,2);
INSERT INTO athletes(id, nombre, apellidos, dni, email, height, weight, genero,entrenador_id, deporte_id) VALUES (5,'Pedro','Duran','14752386J','','1.80','83',0,4,2);
INSERT INTO athletes(id, nombre, apellidos, dni, email, height, weight, genero,entrenador_id, deporte_id) VALUES (6,'Pilar','Mora','12547638F','pilarmora43@gmail.com','1.70','77',1,5,3);
INSERT INTO athletes(id, nombre, apellidos, dni, email, height, weight, genero,entrenador_id, deporte_id) VALUES (7,'Rocío','Giménez','47129348L','','1.65','57',1,4,2);
INSERT INTO athletes(id, nombre, apellidos, dni, email, height, weight, genero,entrenador_id, deporte_id) VALUES (8,'Iker','Lorenzo', '12853476A','','1.78','87',0,5,3);
INSERT INTO athletes(id, nombre, apellidos, dni, email, height, weight, genero,entrenador_id, deporte_id) VALUES (9,'Laura','Hidalgo','45612378S','lauraHidalgo@hotmail.com','1.80','66',1,null,1);
INSERT INTO athletes(id, nombre, apellidos, dni, email, height, weight, genero,entrenador_id, deporte_id) VALUES (10,'Koldo','Ferrer','45638849I','koldoferr@gmail.com','1.80','80',0,null,3);
INSERT INTO athletes(id, nombre, apellidos, dni, email, height, weight, genero,entrenador_id, deporte_id) VALUES (11,'Narciso','Fernández','47319286F','narcisofer@hotmail.com','1.77','76',0,null,3);
INSERT INTO athletes(id, nombre, apellidos, dni, email, height, weight, genero,entrenador_id, deporte_id) VALUES (12,'Omar','Aguirre', '77375931K','omaraguirre@gmail.com','1.70','67',0,null,1);
INSERT INTO athletes(id, nombre, apellidos, dni, email, height, weight, genero,entrenador_id, deporte_id) VALUES (13,'Oriol','Montero','41968574G','','1.68','65',0,null,2);
INSERT INTO athletes(id, nombre, apellidos, dni, email, height, weight, genero,entrenador_id, deporte_id,username) VALUES (14,'Teresa','Santana','84562197S','','1.64','59',1,null,2,'d1');
INSERT INTO athletes(id, nombre, apellidos, dni, email, height, weight, genero,entrenador_id, deporte_id,username) VALUES (15,'Tatiana','Duran', '12493746U','tatiduran@gmail.com','1.72','71',1,null,3,'d2');

INSERT INTO torneo_participantes(torneo_id,athlete_id) VALUES (1, 1);
INSERT INTO torneo_participantes(torneo_id,athlete_id) VALUES (1, 3);
INSERT INTO torneo_participantes(torneo_id,athlete_id) VALUES (1, 9);
INSERT INTO torneo_participantes(torneo_id,athlete_id) VALUES (2, 4);
INSERT INTO torneo_participantes(torneo_id,athlete_id) VALUES (2, 5);
INSERT INTO torneo_participantes(torneo_id,athlete_id) VALUES (2, 13);
INSERT INTO torneo_participantes(torneo_id,athlete_id) VALUES (2, 14);
INSERT INTO torneo_participantes(torneo_id,athlete_id) VALUES (3, 4);
INSERT INTO torneo_participantes(torneo_id,athlete_id) VALUES (4, 6);
INSERT INTO torneo_participantes(torneo_id,athlete_id) VALUES (4, 8);
INSERT INTO torneo_participantes(torneo_id,athlete_id) VALUES (4, 11);
INSERT INTO torneo_participantes(torneo_id,athlete_id) VALUES (4, 15);

INSERT INTO resultados(id, torneo_id, athlete_id, posicion) VALUES (1,1,1,1);
INSERT INTO resultados(id, torneo_id, athlete_id, posicion) VALUES (2,1,3,2);
INSERT INTO resultados(id, torneo_id, athlete_id, posicion) VALUES (3,1,9,3);
INSERT INTO resultados(id, torneo_id, athlete_id, posicion) VALUES (4,2,4,1);
INSERT INTO resultados(id, torneo_id, athlete_id, posicion) VALUES (5,2,5,2);

INSERT INTO sanciones(id,athlete_id,fecha_fin,descripcion) VALUES(1,1,'2020-10-16','El deportista fue sancionado por saltarse una norma');
INSERT INTO sanciones(id,athlete_id,fecha_fin,descripcion) VALUES(2,2,'2021-12-15','El deportista fue sancionado por saltarse una norma');
INSERT INTO sanciones(id,athlete_id,fecha_fin,descripcion) VALUES(3,7,'2020-11-06','El deportista fue sancionado por saltarse una norma');
INSERT INTO sanciones(id,athlete_id,fecha_fin,descripcion) VALUES(4,7,'2021-12-16','El deportista fue sancionado por saltarse una norma');

