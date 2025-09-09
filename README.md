# Trails Project - Projekt Końcowy Natalia Kopczyńska

Trails Project to aplikacja służąca do wyświetlania listy szlaków górskich oraz ich specyfikacji. Umożliwia dodawanie i edytowanie szlaków.
Po podaniu współrzędnych geograficznych punktu początkowego i końcowego, program zapisuje szlak, oblicza jego długość w kilometrach i przypisuje
go do odpowiedniej kategorii trudności (easy, mid, hard). Zapisane szlaki można później przeglądać, sortować oraz wyświetlać ich podsumowanie.
Szlaki przypisane są także do tagów (encja Tag). Tagi można tworzyć, edytować i przeglądać. Szlaki można sortować również według tagów,
na przykład gdy wybieramy się na wycieczkę z małym dzieckiem, możemy sprawdzić, które szlaki oznaczone są tagiem „dla rodzin”.
Aplikacja jest przeznaczona dla ambitnych turystów, dlatego posiada także opcję wyświetlenia najdłuższego szlaku dla wybranego tagu.

Wykorzystane technologie: Java, SpringBoot, Hibernate, SpringData JPA, Thymeleaf
Wykorzystane biblioteki: Lombok

Encje: Trail, Tag, Category, MountRange, Point
Relacje: @ManyToMany - Trail+Tag, @ManyToOne - Trail+Category, Trail+MountRange, Trail+Point

Podstawowe informacje:
- Operacje CRUD dostępne są dla encji Trail i Tag
- Dodatkowe funkcjonalności: podsumowanie, wyświetlenie szlaków dla danej kategorii, 
- Szata graficzna została zaimplementowana z szablonu znalezionego na portalu BootstrapMade (https://bootstrapmade.com/grandoria-bootstrap-hotel-template/). 

Dodatkowe informacje:
- Przy dodawaniu nowych szlaków, program sprawdza czy podane Start i End Point już istnieją, w przeciwnym przypadku tworzy takie pozycje. Program 
działa tak samo przy zczytywaniu pasma górskiego


  