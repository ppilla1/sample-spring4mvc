CREATE TABLE route(
   id  SERIAL PRIMARY KEY,
   tenantid           TEXT      NOT NULL,
   featureid           TEXT      NOT NULL,
   routeid           TEXT      NOT NULL,
   active	integer default 0,
   routexml           TEXT      NOT NULL
);