# Trails Project - Projekt Końcowy Natalia Kopczyńska

Trails Project to aplikacja do wyświetlania i edytowania listy szlaków górskich.


Funkcjonalności dla encji Trail:
- Dodawanie nowego Trail (POST)
- Wyświetlanie Trail po ID oraz całej kolekcji szlaków (GET)
- Usuwanie Trail po ID (GET)
- Edytowanie Trail po ID (POST))
- Wyświetlanie wszystkich szlaków dla danej kategorii, od najkrótszego do najdłuższego
- Wyświetlenie podsumowania ('summary') wszystkich szlaków w kolekcji

Funkcjonalności dla encji Tag:
- Dodawanie nowego Tagu (POST)
- Wyświetlanie Tagu po ID oraz całej kolekcji tagów (GET)
- Usuwanie Tagu po ID (GET)
- Edytowanie Tagu po ID (POST)
- Wyświetlenie wszystkich tagów dla danego Trail ID (GET)
- Znalezienie najdłuższego Trail dla podanego Tagu ID (GET)

Przykłady:
Dodawanie szlaku za pomocą TrailDTO:
POST http://localhost:8080/trails/add-post
Content-Type: application/json

{
  "trailName": "K2",
  "startPoint": {
    "latitude": 30,
"longitude": 20
  },
  "endPoint": {
    "latitude": 27,
    "longitude": 23
  },
  "mountRangeName": "Himalaje"
}