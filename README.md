Demo-Warenkorb
==============
![build status](https://github.com/FilipDisvolvas/demo-shopping-cart/actions/workflows/test-coverage-master.yml/badge.svg?branch=master)
![test status](https://raw.githubusercontent.com/FilipDisvolvas/demo-shopping-cart/master/.github/badges/jacoco.svg?cache-buster=14537)


Dies ist nur ein Spielwiesen-Projekt in Kotlin mit Spring-Boot.

Ein paar Produkte werden auf der Startseite  aufgelistet, die sich der
Kunde in den Warenkorb legen kann. Damit er das kann,  muss er sich im
aktuellen Entwicklungsstand erstmal registrieren und dann anmelden.

### Verwendete Datenbanken
Im "dev"-Profil des Projekts wird die In-Memory-Datenbank H2 verwendet
und Demo-Daten  gleich in die Datenbank geschrieben. In diesem Fall
stehen beispielhafte Credentials  hervorgehoben im Logfile.

Dieses Projekt kommt mit der ausgelieferten Konfiguration erstmal ohne
Abhängigkeiten zu externen Datenbank-Servern  aus. In einem späteren
Stadium könnte der Session Store in Redis gespeichert  werden.


### Zukunftsmusik
Es fehlen noch Validations und es wäre schön, wenn der Warenkorb in der
Session  gespeichert wird, falls der Benutzer noch nicht eingeloggt ist.
Sobald er dann  eingeloggt ist, wird der Warenkorb in die Datenbank überführt.

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

### Verwendung
Laufen lassen der Tests:
```bash
./gradlew test integrationTest
```

Laufen lassen des Application Servers im "dev"-Profil:
```bash
SPRING_PROFILES_ACTIVE=dev ./gradlew bootRun
```
