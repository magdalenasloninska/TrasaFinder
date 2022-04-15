# Przewodnik po kodzie

### Zaktualizowany diagram UML

![](https://i.imgur.com/sFSB91s.jpg)

### Instrukcja kompilacji i uruchomienia programu

Program wystarczy skompilować poleceniem `javac ./*.java` (znajdując się w docelowym folderze), nie potrzeba żadnych bibliotek poza standardową. Sieć połączeń budowana jest na podstawie pliku z nazwami innych plików zawierających opisy poszczególnych linii. U mnie na komputerze te pliki umieszczone są w `"C:\Users\magda\Documents\UWr\PO\TrasaFinder\test"`, po pobraniu projektu należy zmienić ścieżkę funkcji `main` (i w konstruktorze `Linia`).

Następnie uruchamiamy program z katalogu `...\TrasaFinder\src` poleceniem `java projektpo.Main`.

Głównymi klasami służącymi nam do zbudowania sieci połączeń jest `Linia` oraz `Przystanek`. Po odczytaniu każdej linii z pliku, dodajemy ją do zbioru wszystkich linii i tworzymy odpowiednią listę przystanków na podstawie tych już istniejących (żeby istniał tylko jeden przystanek o danej nazwie - tym sposobem rzeczywiście są ze sobą połączone). Później do wykonania analizy każdego zapytania wystarczy nam ten zbiór wszystkich przystanków, bo zawiera on całą sieć.

### `Przystanek`

Analogicznie do prawdziwego życia na każdym przystanku mamy listę kursujących tam linii oraz rozkład jazdy z godzinami odjazdów danych autobusów czy tramwajów.

### `Linia`

Każda linia składa się z listy przystanków na trasie, informacji o tym ile minut zajmuje dojechanie do każdego przystanku z zajezdni i godziny wyjazdów z tej zajezdni. Obiekt tej klasy konstruowany jest na podstawie pliku, którego nazwa jest argumentem konstruktora. Przy tworzeniu obiektu tej klasy propagujemy również dane nowo utworzonej linii do wszystkich przystanków na trasie ()

#### `LiniaAutobusowa` i `LiniaTramwajowa`

To rozróżnienie będzie przydatne w rozszerzeniach programu.

### `Zapytanie`

Każde zapytanie składa się z nazwy przystanku początkowego i końcowego oraz zestawu kryteriów. W metodzie `znajdzTrase` chcemy znaleźć sekwencję linii, których trzeba użyć, żeby dotrzeć do celu. W tym celu wykorzystujemy kolejkę, elementami której są takie możliwe sekwencje. Na początek lokalizujemy przystanek początkowy.

Każdą linię możemy wykorzystać tylko raz (jej numer powtarza się tylko, jeśli potem ta trasa jest przedłużana). Wynika to z tego, że jeśli raz wsiedliśmy już do pojazdu danej linii, to skorzystanie z niej ponownie nie da żadnego rezultatu.

Na przykład dla takich linii:

| Numer    | Przystanki          |
| -------- | ------------------- |
| 6        | Kowale, Toruńska, Hala Targowa |
| 8        | Pl. Bema, Hala Targowa, Tarnogaj |
| 11       | Pl. Bema, Grabiszyńska |
| 23       | Kromera, Kowale |
| 9        | Hala Targowa, Sępolno, Dworzec Główny |

Kolejne iteracje dla zapytania o trasę z Kowali do dworca wyglądają następująco:
```
<<6>, <23>>
<<23>, <6, 8>, <6, 9>>
<<6, 8>, <6, 9>>
<<6, 9>, <6, 8, 11>>
```
Wtedy pierwszy element kolejki jest zestawem linii, które dowiozą nas do celu i możemy zakończyć wyszukiwanie.

### `Trasa`

Obiekt tej klasy stanowi odpowiedź na `Zapytanie`. W szczególnym przypadku może być to `BrakTrasy`. Każdą trasę konstruujemy na podstawie wspomnianej wcześniej sekwencji linii, którą uzupełniamy o inne potrzebne informacje (przystanki, gdzie się przesiadamy oraz godziny odjazdów).

### `Godzina`

Jest to klasa pomocnicza dla całego projektu, pozwala nam w elegancki sposób przechowywać, obliczać i wyświetlać godziny odjazdów. W przypadku gdy odpowiadajacy nam kurs nie istnieje, zwracamy `BrakGodziny`. To znaczy również, że jeśli w którymkolwiek momencie natrafimy na `BrakGodziny` cała trasa nie istnieje i musimy zwrócić `BrakTrasy` jako wynik wyszukiwania.

### `Main`

W tym miejscu budujemy sieć połączeń - czytamy nazwy kolejnych plików zawierających linie i interpretujemy je odpowiednio jako autobusy bądź tramwaje (zależnie od znaku przed nazwą pliku). Po zbudowaniu sieci ruchamiane są 4 przykładowe zapytania (bardzo podobne, ale z uwzględnieniem różnych kryteriów wyszukiwania).
