Both the tests and application expects a postgres db on localhost:5432 with hardcoded credentials. 

Such a db can be started with (for example):
```
docker run --name chess-db -p 5432:5432 -e POSTGRES_PASSWORD=password -d postgres
```

And the database can be connected to using:

```
psql -U postgres -h localhost
```

Todo:

* Fix hardcoded db creds
* Handle incorrect username and other errors on import
* Display progress of import