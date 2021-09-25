CREATE DATABASE activites_scolaires;

CREATE TABLE inscription (login VARCHAR(50) PRIMARY KEY, nom VARCHAR(45),
                         prenom VARCHAR(45), telephone VARCHAR(45),password VARCHAR(45));

CREATE TABLE activite (codeact VARCHAR(15) PRIMARY KEY, nomact VARCHAR(45),descriptionact VARCHAR(200));

CREATE TABLE inscription_activite (codeact VARCHAR(15), login VARCHAR(50),noteact NUMERIC(5,2),
                                  CONSTRAINT pk_inscription_activite PRIMARY KEY (codeact, login),
                                  CONSTRAINT fk_login FOREIGN KEY (login) REFERENCES inscription(login) ON UPDATE CASCADE ON DELETE CASCADE,
                                  CONSTRAINT fk_codecat FOREIGN KEY (codeact) REFERENCES activite (codeact) ON UPDATE CASCADE ON DELETE CASCADE);
                                  
                                  
                        

INSERT INTO inscription (login,nom,prenom,telephone,password) 
                VALUES('balasan','Balana','Sandrine','4355672345','sandrine'),
                      ('ngamag','Ngassa','Magloire','4383085853','magloire'),
                      ('sagdur','Sagenor','Durand','5143458765','durand');


INSERT INTO activite (codeact,nomact,descriptionact)
                VALUES('tir1','Tir a la corde','Les candidats se mettent de part et d''autre d''une corde de 5m ainsi que leur équipe...  '),
                      ('volet2','Volet ball','Un match de volet ball est organisé entre... '),
                      ('course3','Course dans le sac','Une course est organié entre les menbre de l''équipe avec les deux pieds dans un sac...'); 

INSERT INTO inscription_activite(codeact,login,noteact)
                         VALUES ('tir1','balasan','0'),
                                ('tir1','ngamag','0'),
                                ('volet2','balasan','0'),
                                ('volet2','sagdur','0'),
                                ('volet2','ngamag','0'),
                                ('course3','sagdur','0');


