# Lesweek 12

(Op 15/12)


Vervolg requirements for consensus algorithms

Hoe breekt men uit deze consensus -> epochs

Ik wil consensus bereiken. 

x x x x x 
|-^ (ok)
|---^ (ok)

x = 3 (partitie - hier laatste twee niet meer uit) 

Iemand ga zeggen "ik wil leider worden" en die gaat ook een stemronde organiseren (die zat bvb in EP=5)
Die ziet er is geen leider en die gaat EP=6 voorstellen "kzal kik leider worden"


PARTITIE

x x x    |     x x 
|-^            |-^ (ok) 
|---^          ^-| (ok)


x x x x x 

|-------^ (ok) (voor epoch 6)

Dus door meerdere epochs lossen we dit op.

Je hebt dus overlap in quorums van minstens 1 node tussen degene die gecontacteerd worden in election process en nodes in communicatieproces.

- - -
x x x x x 
    - - - 
    ^ overlap



# Deriving Data - Data Analytics

Onderscheid externa data en interne data (logs van user op webserver, beursinfo).

BI - aantal aangelogde gebruikers (napoleon games)

Zowel interne als externe data nodig om antwoorden te geven op 'what if'-vragen. 

Staat is geen ruwe data want kan afgeleid worden van (logs). 

Big Data - kom er niet aan!
(Gooi niets weg, ...)

"Principe van data lake"
-> groot meer waar iedereen data in duwt.
(ongestructureerde data)
(want elke processing doet info weg + snelheid)
(ook sales, frontend site, ...)

ETL = extract transform load
(rechts van lake olap (weinig queries maar alles))


Data warehouse -> opsplitsen in data marts (uitsplitsing van datawarehouse bvb sales)



## Distributed File System

Computation brengen naar de data zelf ipv omgekeerd.

Architecture

bloksize ligt vast clusterwide bvb 256 mb
dus file van 1,2 is dan 4 volle blokken en 1 niet vol blok


HDFS client draait op fysiek zelfde node als application


Aan client side wordt er gewacht op volledige blok en dan pas transfer (want doorsturen is vaak sneller en je wil niet wachten als blok nie vol is)

Bepaalde overhead per file. Maar heb je nu maar 1 dag nodig dan is per maand ook nen overhead. 

Als blok nie vaste grootte heeft dan w hij niet opgevuld me andere file! (anders soep)

## Batch processing

"Er is geen gebruiker aan het wachten". 

Je weet exact ik ga deze set van files verwerken -> dus je weet de grootte. 

mapreduce -> zowel programmeermodel als deploymodel. 


Splitting is niet genoeg -> alle woorden breng je terug samen en die passen misschien niet in geheugen/harde schijf. 

Dus opl -> kleinere dataset aan outputkant.


hier shuffle niet random maar op key


## Joins

Dump user databank naar de data lake. 

Reduce doe je maar aan de join kant

Misschin is user databank klein genoeg om geheel in geheugen op te slaan van de amppers. 
(dan wa tekortkomingen)


## Yarn Architecture
...

## Apache spark

Resilient!


snelheidswinst van spark zit in het execution model
nie enkel memory is relevantie die analyse van parallele dingen ook

## Stream processing

hashtags van twitter vandaag is anders dan morgen.

spout -> bron


bolt -> beetje zoals mapreduce je krijgt iets binnen en je stuurt iets buiten


stream groupings

hoe bepaal je nu hoe tuples van de ene bolt naar de andere gaan?

- shuffle
- fields (typisch woord)
- all (soort broadcast, veel load) 
- global (maar 1 instantie)


Vb.

welke zin naar welke splitter gaat doet er niet echt toe


Examen: definieer topologie (vorig jaar nummerplaatherkenning)

fieldsgrouping op nummerplaat

toepassingsvraag



namiddag -> microservices | messagekanalen | databanken (repositories) meerdere microservices in docker steken in docker compose file steken 