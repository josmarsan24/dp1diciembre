-- One admin user, named admin1 with passwor 4dm1n and authority admin
INSERT INTO users(username,password,enabled) VALUES ('admin1','4dm1n',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (1,'admin1','admin');
-- One owner user, named owner1 with passwor 0wn3r
INSERT INTO users(username,password,enabled) VALUES ('owner1','0wn3r',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (2,'owner1','owner');
-- One vet user, named vet1 with passwor v3t
INSERT INTO users(username,password,enabled) VALUES ('vet1','v3t',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (3,'vet1','veterinarian');
-- One owner user, named fersilleo with passwor 0wn3r
INSERT INTO users(username,password,enabled) VALUES ('fersilleo','fslowner',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (4,'fersilleo','entrenador');
-- One owner user, named jualeoval with passwor p4ss
INSERT INTO users(username,password,enabled) VALUES ('jualeoval','p4ss',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (5,'jualeoval','owner');
-- One owner user, named josmarsan24 with passwor 0wn3r

INSERT INTO users(username,password,enabled) VALUES ('luicharom','luiowner',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (6,'luicharom','owner');

INSERT INTO users(username,password,enabled) VALUES ('julcarcos','julowner',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (7,'julcarcos','owner');

INSERT INTO users(username,password,enabled) VALUES ('josmarsan24','joseowner',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (8,'josmarsan24','owner');

INSERT INTO users(username,password,enabled) VALUES ('e1','e1',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (9,'e1','entrenador');

INSERT INTO users(username,password,enabled) VALUES ('e2','e2',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (10,'e2','entrenador');

INSERT INTO users(username,password,enabled) VALUES ('d1','d1',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (11,'d1','deportista');

INSERT INTO users(username,password,enabled) VALUES ('d2','d2',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (12,'d2','deportista');



INSERT INTO vets VALUES (1, 'James', 'Carter');
INSERT INTO vets VALUES (2, 'Helen', 'Leary');
INSERT INTO vets VALUES (3, 'Linda', 'Douglas');
INSERT INTO vets VALUES (4, 'Rafael', 'Ortega');
INSERT INTO vets VALUES (5, 'Henry', 'Stevens');
INSERT INTO vets VALUES (6, 'Sharon', 'Jenkins');

INSERT INTO specialties VALUES (1, 'radiology');
INSERT INTO specialties VALUES (2, 'surgery');
INSERT INTO specialties VALUES (3, 'dentistry');

INSERT INTO vet_specialties VALUES (2, 1);
INSERT INTO vet_specialties VALUES (3, 2);
INSERT INTO vet_specialties VALUES (3, 3);
INSERT INTO vet_specialties VALUES (4, 2);
INSERT INTO vet_specialties VALUES (5, 1);

INSERT INTO types VALUES (1, 'cat');
INSERT INTO types VALUES (2, 'dog');
INSERT INTO types VALUES (3, 'lizard');
INSERT INTO types VALUES (4, 'snake');
INSERT INTO types VALUES (5, 'bird');
INSERT INTO types VALUES (6, 'hamster');




INSERT INTO owners VALUES (1, 'George', 'Franklin', '110 W. Liberty St.', 'Madison', '6085551023', 'owner1');
INSERT INTO owners VALUES (2, 'Betty', 'Davis', '638 Cardinal Ave.', 'Sun Prairie', '6085551749', 'owner1');
INSERT INTO owners VALUES (3, 'Eduardo', 'Rodriquez', '2693 Commerce St.', 'McFarland', '6085558763', 'owner1');
INSERT INTO owners VALUES (4, 'Harold', 'Davis', '563 Friendly St.', 'Windsor', '6085553198', 'owner1');
INSERT INTO owners VALUES (5, 'Peter', 'McTavish', '2387 S. Fair Way', 'Madison', '6085552765', 'owner1');
INSERT INTO owners VALUES (6, 'Jean', 'Coleman', '105 N. Lake St.', 'Monona', '6085552654', 'owner1');
INSERT INTO owners VALUES (7, 'Jeff', 'Black', '1450 Oak Blvd.', 'Monona', '6085555387', 'owner1');
INSERT INTO owners VALUES (8, 'Maria', 'Escobito', '345 Maple St.', 'Madison', '6085557683', 'owner1');
INSERT INTO owners VALUES (9, 'David', 'Schroeder', '2749 Blackhawk Trail', 'Madison', '6085559435', 'owner1');
INSERT INTO owners VALUES (10, 'Carlos', 'Estaban', '2335 Independence La.', 'Waunakee', '6085555487', 'owner1');
INSERT INTO owners VALUES (11, 'Fernando', 'Silva', '4 Privet Drive', 'Haddonfield', '6085553032', 'fersilleo');
INSERT INTO owners VALUES (12, 'Juanjo', 'Leon', '7 North St.', 'Madison', '6085553776', 'jualeoval');
INSERT INTO owners VALUES (13, 'Luis', 'Chacon', '28 Godofredo Ortega y Muñoz', 'Badajoz', '654345653', 'luicharom');
INSERT INTO owners VALUES (14, 'Jose', 'Martin', '24 Ruben Castro', 'Sevilla', '609503047', 'josmarsan24');



INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (1, 'Leo', '2010-09-07', 1, 1);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (2, 'Basil', '2012-08-06', 6, 2);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (3, 'Rosy', '2011-04-17', 2, 3);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (4, 'Jewel', '2010-03-07', 2, 3);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (5, 'Iggy', '2010-11-30', 3, 4);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (6, 'George', '2010-01-20', 4, 5);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (7, 'Samantha', '2012-09-04', 1, 6);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (8, 'Max', '2012-09-04', 1, 6);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (9, 'Lucky', '2011-08-06', 5, 7);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (10, 'Mulligan', '2007-02-24', 2, 8);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (11, 'Freddy', '2010-03-09', 5, 9);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (12, 'Lucky', '2010-06-24', 2, 10);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (13, 'Sly', '2012-06-08', 1, 10);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (14, 'Lisa', '2019-10-23', 6, 11);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (15, 'Laurie', '2012-07-12', 1, 11);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (16, 'Juan', '2017-10-20', 1, 13);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (17, 'Finidi', '2014-11-20', 1, 14);



INSERT INTO visits(id,pet_id,visit_date,description) VALUES (1, 7, '2013-01-01', 'rabies shot');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (2, 8, '2013-01-02', 'rabies shot');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (3, 8, '2013-01-03', 'neutered');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (4, 7, '2013-01-04', 'spayed');

--INSERT INTO torneos(id,name,fecha_inicio, fecha_fin) VALUES (1,'Lanzamiento de Javalina','2020-12-17','2020-12-18');

INSERT INTO deportes(id,name) VALUES (1,'100 metros lisos');
INSERT INTO deportes(id,name) VALUES (2,'200 metros vallas');
INSERT INTO deportes(id,name) VALUES (3,'Lanzamiento de jabalina');

INSERT INTO pistas(id,name,aforo,ciudad) VALUES (1,'Pista de Huelva de 100 metros',2000,'Huelva');
INSERT INTO pistas(id,name,aforo,ciudad) VALUES (2,'Pista de Sevilla de 200 metros',3000,'Sevilla');
INSERT INTO pistas(id,name,aforo,ciudad) VALUES (3,'Pista de Dos Hermanas de jabalina',2500,'Dos Hermanas');
INSERT INTO pistas(id,name,aforo,ciudad) VALUES (4,'Pista de Sevilla de jabalina',3500,'Sevilla');
INSERT INTO pistas(id,name,aforo,ciudad) VALUES (5,'Pista de Dos Hermanas de 100 metros',1700,'Dos Hermanas');


INSERT INTO entrenadores(id, nombre, apellidos, dni, email, deporte_id) VALUES (1, 'Paco', 'Fernández','77788888J','pacofernandez@gmail.com',1);
INSERT INTO entrenadores(id, nombre, apellidos, dni, email, deporte_id) VALUES (2, 'Rosa', 'Romero','21512426M','rosaromero@gmail.com',2);
INSERT INTO entrenadores(id, nombre, apellidos, dni, email, deporte_id) VALUES (3, 'Mario', 'González','72638153S','marioGonz123@gmail.com',1);
INSERT INTO entrenadores(id, nombre, apellidos, dni, email, deporte_id, username) VALUES (4, 'Pedro', 'García Rojas','52891643F','pedrogarcia94@hotmail.com',2,'e1');
INSERT INTO entrenadores(id, nombre, apellidos, dni, email, deporte_id, username) VALUES (5, 'Noa', 'Herrero ','84531765F','',3,'e2');

INSERT INTO torneos(id,name,fecha_inicio,fecha_fin,pista_id,deporte_id) VALUES (1,'Torneo Huelva de 100 metros','2021-12-16','2021-12-20',1,1);
INSERT INTO torneos(id,name,fecha_inicio,fecha_fin,pista_id,deporte_id) VALUES (2,'Torneo Sevilla de 200 metros de Primavera','2020-05-16','2020-05-20',2,2);
INSERT INTO torneos(id,name,fecha_inicio,fecha_fin,pista_id,deporte_id) VALUES (3,'Torneo Sevilla de 200 metros de Invierno','2021-12-05','2021-12-16',2,2);
INSERT INTO torneos(id,name,fecha_inicio,fecha_fin,pista_id,deporte_id) VALUES (4,'Torneo Dos Hermanas Javalina','2021-01-05','2021-01-10',3,3);
INSERT INTO torneos(id,name,fecha_inicio,fecha_fin,pista_id,deporte_id) VALUES (5,'Torneo Dos Hermanas 100 metros','2021-11-26','2021-11-30',5,1);

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

