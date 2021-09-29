BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "admins" (
	"id"	INTEGER NOT NULL,
	"username"	TEXT NOT NULL,
	"password"	TEXT NOT NULL,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "users" (
	"id"	INTEGER NOT NULL,
	"name"	TEXT,
	"username"	TEXT NOT NULL,
	"email"	TEXT NOT NULL,
	"password"	TEXT NOT NULL,
	"workhours"	NUMERIC,
	"roomates"	INTEGER,
	"homework"	INTEGER,
	"yard"	INTEGER,
	"pets"	INTEGER,
	"appointment"	TEXT,
	"approved"	INTEGER,
	"profile"	INTEGER,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "pets" (
	"id"	INTEGER NOT NULL,
	"name"	TEXT,
	"sex"	TEXT,
	"age"	TEXT,
	"breed"	TEXT,
	"imgsrc"	TEXT,
	"dedicationhours"	REAL,
	"peopletolerance"	INTEGER,
	"yardneed"	INTEGER,
	"pettolerance"	INTEGER,
	"adopted"	INTEGER,
	"urgent"	INTEGER,
	"type"	TEXT,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "likedpets" (
	"id"	INTEGER NOT NULL,
	"petid"	INTEGER,
	"userid"	INTEGER,
	FOREIGN KEY("petid") REFERENCES "pets"("id"),
	FOREIGN KEY("userid") REFERENCES "users"("id"),
	PRIMARY KEY("id")
);
INSERT or IGNORE INTO "admins" VALUES (1,'admin','admin');
INSERT or IGNORE INTO "users" VALUES (1,'Dinija Seferovic','dseferovic','dseferovic@gmail.com','ds',0,0,1,0,1,'2021-10-10 ',NULL,1);
INSERT or IGNORE INTO "users" VALUES (2,'Miho Mihic','miho123','miho@gmail.com','mihom',0,4,0,0,0,'2021-09-24',NULL,1);
INSERT or IGNORE INTO "users" VALUES (3,'Maja Majic','majamaj','name@gmail.com','maja',8.1,3,0,0,1,NULL,NULL,1);
INSERT or IGNORE INTO "pets" VALUES (1,'Basi','M','1 year','Basenji','/img/dogbreeds/basenji.jpg',6.0,1,0,1,0,1,'dog');
INSERT or IGNORE INTO "pets" VALUES (2,'Pea','F','4 years','Beagle','/img/dogbreeds/beagle.jpg',8.0,1,1,1,0,NULL,'dog');
INSERT or IGNORE INTO "pets" VALUES (3,'Jack','M','5 months','Boerboel','/img/dogbreeds/boxer.jpg',9.0,1,1,1,0,NULL,'dog');
INSERT or IGNORE INTO "pets" VALUES (4,'Ali','M','2 years','Boxer','/img/dogbreeds/boxer.jpg',10.0,1,1,0,0,NULL,'dog');
INSERT or IGNORE INTO "pets" VALUES (5,'Lisa','F','1 year','French Bulldog','/img/dogbreeds/frenchbulldog.jpg',8.0,1,0,0,0,NULL,'dog');
INSERT or IGNORE INTO "pets" VALUES (6,'Rex','M','3 years','German Shepherd','/img/dogbreeds/germanshepherd.jpg',10.0,1,1,1,0,NULL,'dog');
INSERT or IGNORE INTO "pets" VALUES (7,'Rosy','F','1 year','Golden Retriever','/img/dogbreeds/goldenretriever.jpg',10.0,1,1,1,0,NULL,'dog');
INSERT or IGNORE INTO "pets" VALUES (8,'Frost','M','3 years','Jack Russell Terrier','/img/dogbreeds/jackrussellterrier.jpg',5.0,0,1,0,0,NULL,'dog');
INSERT or IGNORE INTO "pets" VALUES (9,'Maya','F','6 years','Pug','/img/dogbreeds/pug.jpg',10.0,1,0,1,0,NULL,'dog');
INSERT or IGNORE INTO "pets" VALUES (10,'Nucco','M','2 years','Bombay','/img/catbreeds/bombay.jpg',5.0,1,0,1,0,NULL,'cat');
INSERT or IGNORE INTO "pets" VALUES (11,'Fata','F','4 years','British Shorthair','/img/catbreeds/britishshorthair.jpg',4.0,1,0,1,0,NULL,'cat');
INSERT or IGNORE INTO "pets" VALUES (12,'Maus','M','1 year','Egyptian Mau','/img/catbreeds/egyptianmau.jpg',3.0,0,0,1,0,NULL,'cat');
INSERT or IGNORE INTO "pets" VALUES (13,'Purrito','M','2 months','Domestic Mix','/img/catbreeds/kitten.jpg',6.0,1,0,0,0,NULL,'cat');
INSERT or IGNORE INTO "pets" VALUES (14,'Jennifurr','F','5 years','Mainecoon','/img/catbreeds/mainecoon.jpg',5.0,1,0,1,0,NULL,'cat');
INSERT or IGNORE INTO "pets" VALUES (15,'Muffin','M','10 months','Munchkin','/img/catbreeds/munchkin.jpg',7.0,1,0,1,0,NULL,'cat');
INSERT or IGNORE INTO "pets" VALUES (16,'Clawdia','F','2 years','Wirehair','/img/catbreeds/wirehair.jpg',5.0,1,0,1,0,NULL,'cat');
INSERT or IGNORE INTO "pets" VALUES (17,'Ciki','M','3 years','Tabby Domestic','/img/catbreeds/tabby.jpg',7.0,0,0,0,0,NULL,'cat');
INSERT or IGNORE INTO "pets" VALUES (18,'Garfield','M','6 years','Bengal Orange','/img/catbreeds/orange.jpg',8.0,1,0,1,0,NULL,'cat');
COMMIT;
