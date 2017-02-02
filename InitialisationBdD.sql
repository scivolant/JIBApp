-- Pour des entrées non-triviales dans les tables du projet GestionSuivi
-- fichier en UTF-8
--
-- lire ce fichier dans psql.
-- nom de la base de données : "gestionsuivi"

INSERT INTO comptes(name) VALUES('AV Epargn.');
INSERT INTO comptes(name) VALUES('Retr. Epargn.');
INSERT INTO comptes(name) VALUES('Oddo');
INSERT INTO comptes(name) VALUES('Appart. Paturle');
INSERT INTO comptes(name) VALUES('Cpt Titre BP');

INSERT INTO types(name) VALUES('ETF');
INSERT INTO types(name) VALUES('OPCVM');
INSERT INTO types(name) VALUES('Obligations');
INSERT INTO types(name) VALUES('Immo');

INSERT INTO placements(name, mnemo, id_type, ISIN, codeMaJ) VALUES("Fidelity Europe","FidelityEuro",GestionTypes.OPCVM, "FRxx", "FE");
INSERT INTO placements(name, mnemo, id_type, ISIN, codeMaJ) VALUES("Carmignac Emergent","CarmignacEm",GestionTypes.OPCVM, "FRyy", "CE");
INSERT INTO placements(name, mnemo, id_type, ISIN, codeMaJ) VALUES("Lyxor ETF Euro","LyxorETFEuro",GestionTypes.ETF, "FRzz", "LE");
INSERT INTO placements(name, mnemo, id_type, ISIN, codeMaJ) VALUES("Appart. Parturle","BienImmo1",GestionTypes.Immo, "-", "Paturle");
INSERT INTO placements(name, mnemo, id_type, ISIN, codeMaJ) VALUES("Obligations 1", "Obligations1", GestionTypes.Obligations, "FRbb", "O1");
INSERT INTO placements(name, mnemo, id_type, ISIN, codeMaJ) VALUES("Lyxor ETF DJ Indust. Av.","LyxorETFDJ",GestionTypes.ETF,"FRcc","02");
INSERT INTO placements(name, mnemo, id_type, ISIN, codeMaJ) VALUES("Gallica","Gallica",GestionTypes.OPCVM,"FR0010031195","Gallica");
INSERT INTO placements(name, mnemo, id_type, ISIN, codeMaJ) VALUES("BP Actions Euro","LBPAMActionsEuro",GestionTypes.OPCVM,"FR0000286320","LBPAMActionsEuro");
INSERT INTO placements(name, mnemo, id_type, ISIN, codeMaJ) VALUES("BP Actions Monde","LBPAMActionsMonde",GestionTypes.OPCVM,"FR0000288078","LBPAMActionsMonde");
INSERT INTO placements(name, mnemo, id_type, ISIN, codeMaJ) VALUES("Lyxor ETF Euro 3-5 ANS IG","LyxorMTS35",GestionTypes.Obligations,"FR0010037234","LyxorMTS35");

