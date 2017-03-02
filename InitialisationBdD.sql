-- Pour des entrées non-triviales dans les tables du projet GestionSuivi
-- fichier en UTF-8
--
-- lire ce fichier dans psql.
-- nom de la base de données : 'gestionsuivi'

INSERT INTO comptes(name) VALUES('AV Epargn.');
INSERT INTO comptes(name) VALUES('Retr. Epargn.');
INSERT INTO comptes(name) VALUES('Oddo');
INSERT INTO comptes(name) VALUES('Appart. Paturle');
INSERT INTO comptes(name) VALUES('Cpt Titre BP');

INSERT INTO types(name) VALUES('ETF');
INSERT INTO types(name) VALUES('OPCVM');
INSERT INTO types(name) VALUES('Obligations');
INSERT INTO types(name) VALUES('Immo');

INSERT INTO placements(name, mnemo, id_type, ISIN, codeMaJ) VALUES('Fidelity Europe','FidelityEuro',2, 'FR0000008674', 'FE');
INSERT INTO placements(name, mnemo, id_type, ISIN, codeMaJ) VALUES('Carmignac Emergent','CarmignacEm',2, 'FR0010149302', 'CE');
INSERT INTO placements(name, mnemo, id_type, ISIN, codeMaJ) VALUES('Lyxor ETF Euro','LyxorETFEuro',1, 'FR0000443392', 'LE');
INSERT INTO placements(name, mnemo, id_type, ISIN, codeMaJ) VALUES('Appart. Parturle','BienImmo1',4, '-', 'Paturle');
INSERT INTO placements(name, mnemo, id_type, ISIN, codeMaJ) VALUES('Obligations 1', 'Obligations1', 3, 'FRbb', 'O1');
INSERT INTO placements(name, mnemo, id_type, ISIN, codeMaJ) VALUES('Lyxor ETF DJ Indust. Av.','LyxorETFDJ',1,'FR0007056841','02');
INSERT INTO placements(name, mnemo, id_type, ISIN, codeMaJ) VALUES('Gallica','Gallica',2,'FR0010031195','Gallica');
INSERT INTO placements(name, mnemo, id_type, ISIN, codeMaJ) VALUES('BP Actions Euro','LBPAMActionsEuro',2,'FR0000286320','LBPAMActionsEuro');
INSERT INTO placements(name, mnemo, id_type, ISIN, codeMaJ) VALUES('BP Actions Monde','LBPAMActionsMonde',2,'FR0000288078','LBPAMActionsMonde');
INSERT INTO placements(name, mnemo, id_type, ISIN, codeMaJ) VALUES('Lyxor ETF Euro 3-5 ANS IG','LyxorMTS35',3,'FR0010037234','LyxorMTS35');

INSERT INTO transactions(id_placement, id_compte, coursUnit, addUC, dimUC, addEUR, dimEUR) VALUES(1,1,'25,36','5.3','0','134,408','0');
INSERT INTO transactions(id_placement, id_compte, coursUnit, addUC, dimUC, addEUR, dimEUR) VALUES(1,1,'30','0','3.5','0','105');
INSERT INTO transactions(id_placement, id_compte, coursUnit, addUC, dimUC, addEUR, dimEUR) VALUES(2,1,'30','3.5','0','105','0');