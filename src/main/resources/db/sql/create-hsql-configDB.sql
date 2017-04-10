create table integrationconfig(
	appname varchar(50),
	configkey varchar(50),
	configvalue varchar(50),
	primary key(appname,configkey)
);