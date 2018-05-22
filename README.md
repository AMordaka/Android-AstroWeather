# AstroWeather

-minSdkVersion 22

-targetSdkVersion 27


Ćwiczenie 2: AstroWeather

Celem ćwiczenia jest zapoznanie się z następującymi zagadnieniami:
- Interfejs użytkownika bazujący na fragmentach.
- Komunikacja pomiędzy fragmentami i aktywnością.
- Dostosowanie interfejsu użytkownika do różnych ekranów.
- Usługi sieciowe dostępne w systemie Android.
- Parsowanie plików XML/JSON.
- Przechowywanie danych w pamięci urządzenia (np. w prywatnym katalogu
aplikacji).
- Przechowywanie danych w bazie danych.
- Interfejs użytkownika bazujący na fragmentach

Założenia:
1) Zadaniem aplikacji jest obliczanie i prezentacja informacji astronomicznych dotyczących
Słońca i Księżyca.
3
2) Informacje prezentowane przez aplikację to:
A. Dla Słońca:
• Wchód (czas i azymut).
• Zachód (czas i azymut).
• Zmierz i świt cywilny (czas).
B. Dla Księżyca:
• Wchód i zachód (czas).
• Najbliższy nów i pełnia (data).
• Faza księżyca (w procentach).
• Dzień miesiąca synodycznego.
3) Ponadto aplikacja powinna wyświetlać aktualny czas urządzenia (odświeżany co 1
sekundę) oraz lokalizację wprowadzoną przez użytkownika (długość i szerokość
geograficzną), dla której obliczane są w/w dane.
4) Informacje astronomiczne powinny być odświeżane co pewien czas, zdefiniowany przez
użytkownika w ustawieniach (np. co 15 minut).
5) Do obliczenia informacji astronomicznych można wykorzystać bibliotekę
AstroCalculator. Bibliotekę w formie pliku JAR można znaleźć pod adresem: http://
fiona.dmcs.pl/~pperek/pam_2015_2016/AstroCalculator_lib/astrocalculator.jar
Dokumentacja biblioteki znajduje się pod adresem: http://fiona.dmcs.pl/~pperek/
pam_2015_2016/AstroCalculator_lib/doc/index.html

6) Obliczone dane powinny być prezentowane w przejrzystej formie niezależnie od typu
urządzenia (telefon/tablet). W tym celu należy zaprojektować interfejs bazujący na co
najmniej dwóch fragmentach (1 – dla Słońca, 2 – dla Księżyca).
Układ fragmentów na ekranie powinien być zależny od jego orientacji i rozdzielczości.
Aplikacja powinna uwzględniać co najmniej 4 różne layouty (tablet/telefon, orientacja
pionowa/pozioma).
W układzie pionowym na telefonie fragmenty powinny być przewijane (w tym celu można
wykorzystać widok ViewPager). W przypadku tabletów wszystkie fragmenty mogą być
jednocześnie widoczne na ekranie.
4
7) W aplikacji powinno być dostępne menu, które umożliwi ustawienie:
• Lokalizacji (długość i szerokość geograficzna).
• Częstotliwości odświeżania danych astronomicznych (można w tym celu wykorzystać
Android Spinner adapter).

Założenia:
1) Niniejsze ćwiczenie zakłada rozszerzenie aplikacji przygotowanej w ćwiczeniu 2. o
dodatkowe funkcjonalności umożliwiające pobieranie i wyświetlanie informacji o
warunkach pogodowych (bieżących i prognozowanych) dla lokalizacji wybranych przez
użytkownika.
2) Dane pogodowe można pobrać korzystając z API udostępnianego przez serwis
yahoo.com. Dane można pobierać w postaci plików JSON lub XML. Szczegółowe
informacje dla programistów chcących korzystać z plików udostępnianych w ramach
portalu yahoo.com można znaleźć pod adresem: http://developer.yahoo.com/weather/
Przykładowe zapytania:
• Do pobrania identyfikatora WOEID na podstawie nazwy miejscowości:
Link1 (https://query.yahooapis.com/v1/public/yql?q=select * from
geo.places(1) where text="lodz, pl”)
• Do pobrania informacji pogodowych na podstawie identyfikatora WOEID:
Link2 (https://query.yahooapis.com/v1/public/yql?q=select * from
weather.forecast where woeid=505120 and u=“c")
3) Aplikacja powinna umożliwiać użytkownikowi zdefiniowanie listy ulubionych lokalizacji,
dla których będą pobierane dane pogodowe. Po wprowadzeniu lokalizacji aplikacja
5
sprawdza w serwisie Yahoo czy wpisane dane są poprawne i pobiera identyfikator
WOEID, a następnie zapamiętuje lokalizację w bazie danych.
4) W momencie uruchomienia aplikacja powinna sprawdzać czy możliwe jest połączenie z
internetem. Jeżeli tak, pobierane są aktualne informacje na temat pogody i zapisywane
w pamięci telefonu (w prywatnym katalogu aplikacji).
5) Jeżeli żadne połączenie internetowe nie jest aktywne w chwili uruchomienia aplikacji,
informacje na temat pogody powinny zostać wczytane z pliku, który został zapisany
podczas ostatniego połączenia. Ponadto użytkownik powinien być poinformowany o
tym, że dane mogą być nieaktualne, a do aktualizacji wymagane jest połączenie
internetowe.
Uwaga: Warto zastanowić się czy konieczne jest pobieranie informacji z internetu przy
każdym uruchomieniu aplikacji. Wśród informacji dostarczanych w pliku XML znajduje się
czas, przez który dane mogą być trzymane w pamięci podręcznej, bez konieczności
odświeżania. Można również z góry założyć pewien czas, przez który dane nie będę
odświeżane.
6) Dane pogodowe powinny być prezentowane w przejrzystej formie. W tym celu należy
rozbudować dotychczasowy interfejs aplikacji z ćwiczenia 2. o dodatkowe fragmenty,
np.:
• Fragment 1 – podstawowe dane, tj.: nazwa miejscowości, współrzędne geograficzne,
czas, temperatura, ciśnienie, opis i reprezentacja graficzna warunków pogodowych.
• Fragment 2 – dane dodatkowe np.: informacje o sile i kierunku wiatru, wilgotności,
widoczności.
• Fragment 3 – prognoza pogody na nadchodzące dni.
Układ fragmentów na ekranie powinien być zależny od jego orientacji i rozdzielczości.
7) W aplikacji powinno być dostępne menu, które umożliwi:
• Odświeżenie informacji z internetu na żądanie użytkownika.
• Ustawienie/zmianę lokalizacji, dla których pobierane są dane.
• Wybór jednostek miary stosowanych w aplikacji.

![alt text](https://github.com/AMordaka/Android-AstroWeather/blob/master/app/src/main/res/drawable/1.PNG)

![alt text](https://github.com/AMordaka/Android-AstroWeather/blob/master/app/src/main/res/drawable/2.png)

![alt text](https://github.com/AMordaka/Android-AstroWeather/blob/master/app/src/main/res/drawable/3.png)

![alt text](https://github.com/AMordaka/Android-AstroWeather/blob/master/app/src/main/res/drawable/4.png)

![alt text](https://github.com/AMordaka/Android-AstroWeather/blob/master/app/src/main/res/drawable/5.png)
