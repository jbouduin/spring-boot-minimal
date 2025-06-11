@echo off

echo copying initialization script...
docker cp .\database.init.sql postgres_database:\database.init.sql

echo running initialization script...
docker exec -u postgres postgres_database psql -d minimal -f /database.init.sql
