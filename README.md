
## Opis projektu
Projekt GitHub Repositories API to aplikacja Spring Boot, która umożliwia pobieranie informacji o repozytoriach użytkownika GitHub, które nie są forkami. Aplikacja wykonuje zapytania do oficjalnego API GitHub i udostępnia własny endpoint REST.

## API Endpoints
### Pobieranie repozytoriów użytkownika
```
GET localhost:8080/api/github/{username}/repos
```

#### Parametry URL
- `username` - nazwa użytkownika GitHub

#### Przykładowa odpowiedź sukcesu (200 OK)
```json
[
  {
    "repositoryName": "Atipera_git",
    "ownerName": "SzymonBartkowiak43",
    "branches": [
      {
        "name": "main",
        "lastCommitSha": "203de2af6e61600f5a3423e9b3027b5310affc19"
      }
    ]
  }
]
```

#### Odpowiedź dla nieistniejącego użytkownika (404 Not Found)
```json
{
  "status": 404,
  "message": "User not found"
}
```

## Instrukcja uruchomienia
1. Sklonuj repozytorium:
   ```
   git clone https://github.com/SzymonBartkowiak43/Atipera_git.git
   ```

2. Zbuduj aplikację:
   ```
   ./mvnw clean install
   ```

3. Uruchom aplikację:
   ```
   ./mvnw spring-boot:run -DGITHUB_TOKEN=YOUR_TOKEN
   ```

4. Aplikacja jest dostępna pod adresem:
   ```
   http://localhost:8080
   ```

## Testy
Aplikacja zawiera testy integracyjne sprawdzające:
- Odpowiedź dla nieistniejącego użytkownika
- Pobieranie repozytoriów dla istniejącego użytkownika
- Weryfikację, że zwrócone repozytoria nie są forkami

Aby uruchomić testy:
```
./mvnw test
```
