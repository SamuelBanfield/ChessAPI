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

* Shred the number of each result from each position, rather than total number of games
* UI to display number of each result rather than total number
* Add a back button to go to the previous position
* Add UI to import games
* Display white/black games separately
* Filter games by user, with UI to select users
* Fix hardcoded db creds
* Uniquely identify games and prevent them from being shredded twice