# Fiszkord
Spring backend.

## Użytkownicy
---
> POST /api/auth/register

Rejestracja. Wymagane body:
- firstname: String
- lastname: String
- email: String
- password: String
- role: enum(USER, MANAGER, ADMIN)

Zwraca:
- access_token: String
- refresh_token: String

---

> GET /api/check

Zwraca wiadomość zalogowanemu użytkownikowi.

---

> GET /api/users[?id={}]

Zwraca dane użytkownika. Parametr id jest opcjonalny, w przypadku
jego braku zwraca aktualnie zalogowanego użytkownika.

---

> POST /api/auth/login

Logowanie. Wymagane body:
- email: String
- password: String

Zwraca:
- access_token: String
- refresh_token: String

---

> PATCH /api/users

Zmiana hasła. Wymagane body:
- currentPassword: String
- newPassword: String
- confirmationPassword: String

---

> POST /api/auth/refresh-token

Zmiana tokenu. Wymagany *refresh_token* w nagłówku Authorization. Zwraca: 
- access_token: String
- refresh_token: String

---

> GET /api/auth/logout

Wylogowanie.

---
## Grupy (wymaga roli USER)  
### Tworzenie grupy   
> POST /api/group/create  

Wymaga access token jako bearer token. Wymagane body:  
- name: String
- code: String

### Dołączanie do grupy  
> POST /api/group/join  

Wymaga access token jako bearer token. Wymagane body:  
- code: String

### Lista grup użytkownika
> GET /api/group/user-groups

Wymaga access token jako bearer token.

---
## Przedmioty (wymaga roli USER)  
### Dodanie przedmiotu do grupy   
> POST /api/subject/create-subject

Wymaga access token jako bearer token. Wymagane body:  
- groupId: Integer
- name: String

### Znajdź przedmioty danej grupy
> GET /api/subject/get-subjects?groupId={}

Wymaga access token jako bearer token. Wymagany parametr:  
- groupId: Integer

---
## Wiadomości
### Podłączanie do brokera czatu
Lokalnie uruchomiony serwer będzie działał pod adresem `http://localhost:8080/ws`.

Dla wiadomości czatu danego przedmiotu należy zasubskrybować topic `/topic/{subjectId}`.

Przy wysyłaniu wiadomości należy użyć ścieżki `/app/{subjectId}`.
Zawartość wiadomości to tekstowy JSON z polami:

- sender: String (id użytkownika)
- content: String 

Serwer przesyła do subskrybentów JSON z polami (sender, content, id, date).

### Wiadomości przedmiotu
> GET /api/message/?subjectId={}&timestamp={}

Zwraca najnowsze 20 wiadomości dla przedmiotu, opcjonalnie 20
ostatnich wiadomości przed datą *timestamp*.

Wymagany parametr:
- subjectId: Integer

Opcjonalny parametr
- timestamp: Timestamp 
(String formatu yyyy-mm-dd hh:mm:ss.[fff...])
---

## Fiszki (wymaga roli USER)

### Stworzenie talii fiszek

> POST /api/deck/create

Wymaga access token jako bearer token. Wymagane body:
- name: String
- groupId: Integer
- subjectId: Integer

### Dodanie fiszki do talii

> POST /api/flashcards/create-flashcard

Wymaga access token jako bearer token. Wymagane body:
- front: String
- back: String
- groupId: Integer
- deckId: Integer

### Talie danego przedmiotu

> GET /api/decks/subject-decks?subjectId={}  

Zwraca wszystkie talie i ich fiszki dla danego przedmiotu  
Wymaga access token jako bearer token. Wymagany parametr:
- subjectId: Integer

### Fiszki danej talii

> GET /api/flashcards/deck-flashcards?groupId={}&deckId={}

Zwraca fiszki danej talii.
Wymaga access token jako bearer token. Wymagane parametry:
- groupId: Integer
- subjectId: Integer
---

## GPT (wymaga roli USER)

### Podpowiedź rewersu dla fiszki

> #### GET /api/gpt/flashcard-hint?groupName={}&userPrompt={}

Zwraca odpowiedź od gpt-3.5-turbo na zapytanie:
> Podaj wyjaśnienie zagadnienia z kategorii `groupName' na max 256 znaków. 
> Nie opisuj co oznacza podana wcześniej kategoria, 
> tylko weź ją pod uwagę wyjaśniając następujące zagadnienie: 'userPrompt'.

Wymaga access token jako bearer token. Parametry:
- groupName: String
- userPrompt: String
---