-- Définition des tables pour le project GestionSuivi
-- fichier en UTF-8
--
-- Pour créer la base de donnée elle-même,
-- lire ce fichier dans psql.
-- nom de la base de données : "gestionsuivi"

create table comptes(
	id_compte serial primary key
	, name text
    	);

create table types(
	id_type serial primary key
	, name text
    	);

create table placements(
	id_placement serial primary key
	, name text
	, mnemo text
	, id_type integer references types
	, ISIN text
	, codeMaJ
	);

create table transactions(
	id_transac serial primary key
	, id_placement references placements
	, id_compte references comptes
	, jour date
	, coursUnit money
	, addUC real
	, dimUC real
	, addEUR money
	, dimEUR money
    	);
    	
create table ordres(
	id_ordre serial primary key
	, id_placement references placements
	, id_compte references comptes
	, coursUnit money
	, addUC real
	, dimUC real
	, addEUR money
	, dimEUR money
    	);
    	
create table cours(
	id_cours serial primary key
	, id_placement references placements
	, jour date
	, coursUnit money
	);