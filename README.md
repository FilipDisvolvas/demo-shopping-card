Demo-Warenkorb
==============
![build status](https://github.com/FilipDisvolvas/demo-shopping-cart/actions/workflows/test-master.yml/badge.svg?branch=master)
![test status](https://raw.githubusercontent.com/FilipDisvolvas/demo-shopping-cart/master/.github/badges/jacoco.svg?cache-buster=22625)


Dies ist nur ein Spielwiesen-Projekt in Kotlin mit Spring-Boot.

Ein paar Produkte werden auf der Startseite  aufgelistet, die sich der
Kunde in den Warenkorb legen kann. Damit er das kann,  muss er sich im
aktuellen Entwicklungsstand erstmal registrieren und dann anmelden.

Ein paar Learnings sind [hier](IMPLEMENTATION.md) zu finden.

Tech Stack
--------------
* Gradle
* Kotlin / Java 17
* Spring Boot mit Spring Web
* Eigener Login-Mechanismus mit BCryptPasswordEncoder
* JUnit 5
* In-Memory-Datenbank H2 out-of-the-box (switch auf MySQL / PostgreSQL kann gerne einfach so über die Properties konfiguriert werden.)
* [Bootstrap](https://getbootstrap.com/)
* [Font Awesome](https://fontawesome.com/)

### GitHub Actions
Ein [kleiner Workflow in GitHub Actions](https://github.com/FilipDisvolvas/bookmarkr/actions) ist auch vorhanden.
Lässt immerhin die Tests durchlaufen und benachrichtigt, wenn sich der master-, develop- oder feature-Branch nicht bauen lässt.

Die Build Pipeline sieht vor, dass ein Push in den develop-Branch automagisch getestet wird. Wenn der Build klappt, dann
erfolgt sofort ein Merge in den master-Branch. Im Master-Branch erfolgt nochmals ein Test (denn dort könnten inzwischen
Hotfixes enthalten sein...). Bei Erfolg wird das Badge für die Test Coverage in diesen README.md generiert
sowie der Badge-URL README.md ausgetauscht, um Caching-Probleme vom GitHub-CDN zu umgehen. Mit diesen Aktualisierungen
erfolgt wieder ein Merge vom master-Branch in den develop-Branch. -- Look, Mum. No hands!


### Verwendete Datenbanken
Im "dev"-Profil des Projekts wird die In-Memory-Datenbank H2 verwendet
und Demo-Daten  gleich in die Datenbank geschrieben. In diesem Fall
stehen beispielhafte Credentials  hervorgehoben im Logfile.

Dieses Projekt kommt mit der ausgelieferten Konfiguration erstmal ohne
Abhängigkeiten zu externen Datenbank-Servern  aus. In einem späteren
Stadium könnte der Session Store in Redis gespeichert  werden.

Verwendung
--------------
Laufen lassen der Tests:
```bash
./gradlew test integrationTest
```

Laufen lassen des Application Servers im "dev"-Profil:
```bash
SPRING_PROFILES_ACTIVE=dev ./gradlew bootRun
```

Zukunftsmusik
-----------------
Es wäre schön, wenn der Warenkorb in der  Session  gespeichert wird,
falls der Benutzer noch nicht eingeloggt ist.  Sobald er dann  eingeloggt ist,
wird der Warenkorb in die Datenbank überführt.

Das Führen des Warenkorbes in der Datenbank hat mehrere Vorteile:
* Der Kunde kann den Warenkorb über mehrere Tage hinweg bearbeiten, ohne seine Session zu verlieren.
* Der Kunde kann den Warenkorb geräteübergreifend bearbeiten.

In einem späteren Checkout könnte der `Basket` in eine `Order` und
die `BasketEntries` entsprechend `OrderEntries` überführt werden. Da
Produktpreise und Adressdaten veränderlich sind,  würde ich sie direkt
in die Bestelldaten reinschreiben. Also würde ich die Produktpreise  in
den `OrderEntries` festhalten und die Adressdaten in der `Order`.

Die Testabdeckung muss auch noch angehoben werden. Immerhin ist strukturell
alles darauf ausgelegt, damit dies gelingt!

