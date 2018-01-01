sudo su - postgres
rmdir /uis/postgres/lojav002_ts/
mkdir /uis/postgres/lojav002_ts/

psql -d postgres -a -f /uis/postgres_createdb.sql
psql -d xapp -a -f /uis/postgres_database.sql
