# Trails Project - Projekt Końcowy Natalia Kopczyńska

Trails Project to aplikacja służąca do wyświetlania listy szlaków górskich oraz ich specyfikacji. Umożliwia dodawanie i edytowanie szlaków.
Po podaniu współrzędnych geograficznych punktu początkowego i końcowego, program zapisuje szlak, oblicza jego długość w kilometrach i przypisuje
go do odpowiedniej kategorii trudności (easy, mid, hard). Zapisane szlaki można później przeglądać, sortować oraz wyświetlać ich podsumowanie.
Szlaki przypisane są także do tagów (encja Tag). Tagi można tworzyć, edytować i przeglądać. Szlaki można sortować również według tagów,
na przykład gdy wybieramy się na wycieczkę z małym dzieckiem, możemy sprawdzić, które szlaki oznaczone są tagiem „dla rodzin”.
Aplikacja jest przeznaczona dla ambitnych turystów, dlatego posiada także opcję wyświetlenia najdłuższego szlaku dla wybranego tagu.

Wykorzystane technologie: Java, SpringBoot, Hibernate, SpringData JPA
Wykorzystane biblioteki: Lombok

Encje: Trail, Tag, Category, MountRange, Point
Relacje: @ManyToMany - Trail+Tag, @ManyToOne - Trail+Category, Trail+MountRange, Trail+Point

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

- Dodawanie szlaku za pomocą TrailDTO:

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

Dodatkowe informacje:
- Przy dodawaniu nowych szlaków, program sprawdza czy podane Start i End Point już istnieją, w przeciwnym przypadku tworzy takie pozycje. Program 
działa tak samo przy zczytywaniu pasma górskiego


  