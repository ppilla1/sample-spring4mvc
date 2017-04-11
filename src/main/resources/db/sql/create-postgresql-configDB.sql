CREATE TABLE pz_platformservice_integration(
	appname TEXT NOT NULL,
	configkey TEXT NOT NULL,
	configvalue TEXT NOT NULL,
	PRIMARY KEY(appname,configkey)
);