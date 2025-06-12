@echo off

echo copying reset script...
docker cp .\database.reset.sql postgres_database:\database.reset.sql

echo running reset script...
docker exec -u postgres postgres_database psql -f /database.reset.sql
