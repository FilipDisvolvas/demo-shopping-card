Randnotizen zur Implementierung
===============================

Java und Kotlin gemischt
-------------------------
Java-Klassen befinden sich unterhalb von [src/main/java](src/main/java),
Kotlin-Klassen unterhalb von [src/main/java](src/main/kotlin). Diese Aufteilung
muss so sein, damit **gradle** ohne große Umstände weiß, wie es mit *.kt-Dateien und
*.java-Dateien richtig umgehen muss.

Validations
------------
Die Validatoren sind als Java-Klassen unterhalb von
[net.sakrak.demoshoppingcart.validations](src/main/java/net/sakrak/demoshoppingcart/validations)
implementiert.

Die Registrierung erfolgt innerhalb der Kotlin-Klasse [RegisterValidators](src/main/kotlin/net/sakrak/demoshoppingcart/bootstrap/RegisterValidators.kt)
während der Bootstrap-Phase.

