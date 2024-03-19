Expects postgres db on localhost: 

```
docker run --name chess-db -p 5432:5432 -e POSTGRES_PASSWORD=password -d postgres
psql -U postgres -h localhost
```