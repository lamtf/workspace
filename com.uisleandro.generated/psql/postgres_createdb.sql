	CREATE TABLESPACE "salesv001_ts"
	OWNER "postgres"
	LOCATION E'/uis/postgres/salesv001_ts/';

	CREATE DATABASE "salesv001"
	WITH OWNER = "postgres"
	ENCODING = 'UTF8'
	TABLESPACE "salesv001_ts"
	LC_COLLATE = 'pt_BR.UTF-8'
	LC_CTYPE = 'pt_BR.UTF-8'
	TEMPLATE template0
	CONNECTION LIMIT -1;
