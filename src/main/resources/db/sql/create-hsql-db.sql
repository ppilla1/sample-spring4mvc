create table route(
	id integer IDENTITY PRIMARY KEY,
	tenantid varchar(50),
	featureid varchar(50),
	routeid varchar(50),
	active integer,
	routexml longvarchar
);
