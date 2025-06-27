# Chess API Documentation

## Endpoints

### Get Lichess Games
- **URL:** `/game/lichess/{userName}`
- **Method:** `GET`
- **Path Variable:** `userName` (String): The username of the Lichess player.
- **Response:** `List<ModelGame>`
- **Description:** Fetches the games of a Lichess user.

### Get Chess.com Games
- **URL:** `/game/chessdotcom/{userName}`
- **Method:** `GET`
- **Path Variable:** `userName` (String): The username of the Chess.com player.
- **Response:** `List<ModelGame>`
- **Description:** Fetches the games of a Chess.com user.

### Get Games From Position
- **URL:** `/game`
- **Method:** `GET`
- **Query Parameters:**
    - `fen` (String): The FEN string representing the board position.
    - `colour` (String): The colour of the player (`black` or `white`).
    - `sources` (List<String>): The sources to fetch the games from.
- **Response:** `List<MoveWithFrequency>`
- **Description:** Fetches the games from a specific board position and returns the frequency of moves.

### Get Position Note
- **URL:** `/note`
- **Method:** `GET`
- **Query Parameters:** `fen` (String): The FEN string representing the board position.
- **Response:** `PositionNote`
- **Description:** Fetches the note for a specific board position.

### Upsert Position Note
- **URL:** `/note`
- **Method:** `POST`
- **Query Parameters:**
    - `fen` (String): The FEN string representing the board position.
    - `note` (String): The note to be associated with the board position.
- **Response:** `PositionNote`
- **Description:** Sets or updates the note for a specific board position.

### Import Lichess Games
- **URL:** `/import/lichess/{userName}`
- **Method:** `POST`
- **Path Variable:** `userName` (String): The username of the Lichess player.
- **Response:** `int`
- **Description:** Imports the games of a Lichess user.

### Import Chess.com Games
- **URL:** `/import/chessdotcom/{userName}`
- **Method:** `POST`
- **Path Variable:** `userName` (String): The username of the Chess.com player.
- **Response:** `int`
- **Description:** Imports the games of a Chess.com user.
