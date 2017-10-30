-- Pour des entrées non-triviales dans les tables du projet JIBapp
-- fichier en UTF-8
--
-- lire ce fichier dans psql.
-- nom de la base de données : 'gestionsuivi'

INSERT INTO types(name) VALUES('ETF');
INSERT INTO types(name) VALUES('OPCVM');
INSERT INTO types(name) VALUES('Obligations');
INSERT INTO types(name) VALUES('Immo');
INSERT INTO types(name) VALUES('Action');

INSERT INTO sourcequotes(name, url, id_transf) VALUES('FT ETF', 'https://markets.ft.com/data/etfs/tearsheet/summary?s=',1);
INSERT INTO sourcequotes(name, url, id_transf) VALUES('FT ordinaire', 'https://markets.ft.com/data/funds/tearsheet/Summary?s=',2);
INSERT INTO sourcequotes(name, url, id_transf) VALUES('Yahoo', 'http://finance.yahoo.com/q?s=',1);
INSERT INTO sourcequotes(name, url, id_transf) VALUES('Boursorama',	'http://www.boursorama.com/bourse/opcvm/opcvm.phtml?symbole=',3);
INSERT INTO sourcequotes(name, url, id_transf) VALUES('A la main ! (pas de MaJ)','http://a_la_mano!!!',4);
