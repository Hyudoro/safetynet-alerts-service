# SafetyNet Alerts

Spring Boot REST microservice for emergency-response data. It holds a city's
persons, medical records and firestation coverage, and serves the endpoints
responders need.

## Stack

- Java 21, Spring Boot 4
- Maven (wrapper)
- JSON data file (default `./data/data.json`, path via `data.file.path`;
  sample dataset in `src/main/resources/data.json`)

## Run it

```sh
./mvnw spring-boot:run
```

API on http://localhost:8080. Tests:

```sh
./mvnw test
```

## Endpoints

| Endpoint | Returns |
|---|---|
| `GET /firestation?stationNumber=` | persons covered by a station, with child/adult counts |
| `GET /childAlert?address=` | children at an address, with their household members |
| `GET /phoneAlert?firestation=` | phone numbers of everyone covered by a station |
| `GET /fire?address=` | inhabitants of an address, their station and medical data |
| `GET /flood/stations?stations=` | households grouped by station, for flood-zone lists |
| `GET /personInfo?lastName=` | person(s) by last name: age, address, medications, allergies |
| `GET /communityEmail?city=` | every email address in a city |

The underlying records are managed through CRUD endpoints on `/person`,
`/medicalRecord` (including `PUT .../{firstName}/{lastName}/medications` and
`/allergies`) and `/firestation`.
