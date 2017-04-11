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
	, codeMaJ text
	);

create table transactions(
	id_transac serial primary key
	, id_placement serial references placements
	, id_compte serial references comptes
	, transac_date date default current_date
	, coursUnit real
	, addUC real
	, dimUC real
	, addEUR real
	, dimEUR real
    	);
    	
create table ordres(
	id_ordre serial primary key
	, id_placement serial references placements
	, id_compte serial references comptes
	, coursUnit real
	, addUC real
	, dimUC real
	, addEUR real
	, dimEUR real
	, notes text
    	);
    	
create table cours(
	id_cours serial primary key
	, id_placement serial references placements
	, jour date
	, coursUnit money
	);