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
INSERT INTO types(name) VALUES('Action');

INSERT INTO sourcequotes(name, url, id_transf) VALUES('FT ETF', 'https://markets.ft.com/data/etfs/tearsheet/summary?s=',1);
INSERT INTO sourcequotes(name, url, id_transf) VALUES('FT ordinaire', 'http://funds.ft.com/uk/Tearsheet/Summary?s=',2);
INSERT INTO sourcequotes(name, url, id_transf) VALUES('Yahoo', 'http://finance.yahoo.com/q?s=',1);
INSERT INTO sourcequotes(name, url, id_transf) VALUES('Boursorama',	'http://www.boursorama.com/bourse/opcvm/opcvm.phtml?symbole=',3);
INSERT INTO sourcequotes(name, url, id_transf) VALUES('A la main ! (pas de MaJ)','http://a_la_mano!!!',4);

INSERT INTO placements (name, mnemo, id_type, isin, codemaj, id_source) VALUES ('Appart. Parturle', 'Paturle', 3, '-', 'Paturle', 5);
INSERT INTO placements (name, mnemo, id_type, isin, codemaj, id_source) VALUES ('BP Actions Euro', 'LBPAMActionsEuro', 2, 'FR0000286320', 'MP-829809', 4);
INSERT INTO placements (name, mnemo, id_type, isin, codemaj, id_source) VALUES ('BP Actions Monde', 'LBPAMActionsMonde', 4, 'FR0000288078', 'MP-828023', 4);
INSERT INTO placements (name, mnemo, id_type, isin, codemaj, id_source) VALUES ('Carmignac Emergent', 'CarmignacEm', 4, 'FR0010149302', 'FR0010149302:EUR', 2);
INSERT INTO placements (name, mnemo, id_type, isin, codemaj, id_source) VALUES ('Fidelity Euro Small Caps', 'FidelityEuroSC', 4, 'LU0061175625', 'LU0061175625:EUR', 2);
INSERT INTO placements (name, mnemo, id_type, isin, codemaj, id_source) VALUES ('Fidelity Europe', 'FidelityEuro', 4, 'FR0000008674', 'FR0000008674:EUR', 2);
INSERT INTO placements (name, mnemo, id_type, isin, codemaj, id_source) VALUES ('Gallica', 'Gallica', 4, 'FR0010031195', 'FR0010031195:EUR', 2);
INSERT INTO placements (name, mnemo, id_type, isin, codemaj, id_source) VALUES ('Japan Topix', 'JapanTopix', 1, 'FR0011475078', 'JPN:PAR:EUR', 1);
INSERT INTO placements (name, mnemo, id_type, isin, codemaj, id_source) VALUES ('Lyxor ETF DJ Indust. Av.', 'LyxorETFDJ', 1, 'FR0007056841', 'DJE:PAR:EUR', 1);
INSERT INTO placements (name, mnemo, id_type, isin, codemaj, id_source) VALUES ('Lyxor ETF Euro', 'LyxorETFEuro', 1, 'FR0000443392', 'FR0000443392:EUR', 2);
INSERT INTO placements (name, mnemo, id_type, isin, codemaj, id_source) VALUES ('Lyxor ETF Euro 3-5 ANS IG', 'LyxorMTS35', 1, 'FR0010037234', 'MTB:PAR:EUR', 1);
INSERT INTO placements (name, mnemo, id_type, isin, codemaj, id_source) VALUES ('Lyxor Oil & Gas', 'LO&G', 1, 'FR0010344960', 'OIL:PAR:EUR', 1);

INSERT INTO transactions(id_placement, id_compte, coursUnit, addUC, dimUC, addEUR, dimEUR) VALUES(1,1,'25.36','5.3','0','134.408','0');
INSERT INTO transactions(id_placement, id_compte, coursUnit, addUC, dimUC, addEUR, dimEUR) VALUES(1,1,'30','0','3.5','0','105');
INSERT INTO transactions(id_placement, id_compte, coursUnit, addUC, dimUC, addEUR, dimEUR) VALUES(2,1,'30','3.5','0','105','0');

INSERT INTO sourcequotes(name, url, id_transf) VALUES('FT ETF', 'https://markets.ft.com/data/etfs/tearsheet/summary?s=',1);
INSERT INTO sourcequotes(name, url, id_transf) VALUES('FT ordinaire', 'http://funds.ft.com/uk/Tearsheet/Summary?s=',2);
INSERT INTO sourcequotes(name, url, id_transf) VALUES('Yahoo', 'http://finance.yahoo.com/q?s=',1);
INSERT INTO sourcequotes(name, url, id_transf) VALUES('Boursorama',	'http://www.boursorama.com/bourse/opcvm/opcvm.phtml?symbole=',3);
INSERT INTO sourcequotes(name, url, id_transf) VALUES('À la main ! (pas de MàJ)','http://a_la_mano!!!',4);
