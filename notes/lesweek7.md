# Lesweek 7

(Op 10/11)

(Nog 2 theorielessen hierna)

## Vervolg message broker

Doel: Toch dezelfde message door meerdere consumers af te handelen is.

### Apache kafka

Broker -> conceptueel hier aparte machine

Shard = scherf -> je hebt 1 geheel in meerdere kleinere laten uitéénvallen. 

Wie gaat die topic uitlezen?

Zie slide! -> C4 kan niet uit P0 lezen

Offset in partitie per client en het is aan de gebruiker om die offset op te schuiven. 

~> gebruikte use case is buffer (message bijhouden)
(niet oorspronkelijke bedoeling maar werkt wel goed)


### MMOG

Loging moet voor trade komen dus moet je die op dezelfde topic zetten.

[Bron](http://blog.cloudera.com/blog/2014/09/apache-kafka-for-beginners/)


**Garantie** waar je rekening mee moet houden: At-least-once-guarantee

Vanuit bedrijfperspectief zijn de meeste zaken niet idempotent. Op technisch vlak hoeft het niet per se maar 1 keer zijn maar op business vlak wel (je wil niet twee keer factureren).


# Multi-service coordination and data consistency

Concreet vb:

Meerdere microservices nodig hebben. 

### Choreografphy

Reageer op wat de omgeving doet.

Nadeel: je ziet nergens meer hoe de logica van u applicatie werkt

### Orchestration

Dirigent die zegt nu doe jij dit, jij dit, jij dit.


In praktijk gebruik je beide door mekaar.

## 2PC = 2 phase commit

Grijze lijn zijn locks die je voortduren vasthoudt.

## Saga: sequence of local transactions

Locaal loggen -> nadeel je kan tijdelijk nog geen loyaltie punten hebben op je rekening bij aanmaken van gebruiker

eventually --> vertalen als "Uiteindelijk wel" --> ooit zijn ze terug consistent 


Nu kankt wel zijn da je rollbacks moet doen!

### Example

Merk op die orderauthorized event -> choreography stijl
en die verify restaurant -> orchestration stijl


```
	->	Domein		Order
	<-	Model
Eventhandler
|
 ->	Kafka
```

Er kan iets mislopen op business gebied -> cancellen enzo, auth failed...
Hier moet je dan kijken op welke stap zit ik en hoe keerde terug

### Exercise food-to-go

Zoiets kan gevraagd worden op het oefeningexamen.

Stel je moet implementeren:

Wat is de flow tussen de communicatie van de microservices.

Web UI -> kijk of auth is -> billing
billing -> web ui ja of nee


Businessbeslising: tot wnr sta ik het toe da een gebruiker zijn order can cancellen

Slide 87 -> NO-SQL moet weg want da gebeurt ook met andere sql dingen.

Via die event store kan je ziet wat er gebeurd is enzo je kan zien hoeveel keer is er gecancelled.
Dit is de data die je zelf als bedrijf verzamelt!
En je kan daar dan advanced analyses over doen.


# Data Intensive Systems

De **4 v's** => uitdagingen

- volume: de hoeveelheid data (we spreken over peta en exa)
- velocity: quasi ogenblikkelijke respons
- variety: veelzijdigheid van data, tegenwoordig heb je op veel manieren informatie (foto's, videos, text, email)
- veracity: waarachtigheid: er zit soms ruis op u data; vroeger in relationele modellen de data die er in zat was betrouwbaar. Nu zit er ruis in, een stuk onbetrouwbaarheid denk maar aan een tweet met hastags of afkortingen.


We moeten puzzelaars worden!

## Data Models

De manieren waarop jij data gaat structureren.
Er zijn er een aantal. 

Het relationele model heeft ook veel nadelen -> daarom is document model opgekomen voor one to many relations.
Kleur van kledij & cpukracht van pc in eenzelfde record das maar raar.

schema-on-write (relationeel) vs schema-on-read (document). Degene die json leest moet zelf maar ontdekken.

Alle informatie zit bij mekaar op 1 plaats, nadeel is natuurlijk dat als je maar 1 klein ding nodig hebt da je toch alles moet inladen!

No object-relational impedance mismatch.

Probleem met document databases is:

Toch meer dan 1 document nodig als je bvb alle regios wil hebben.
Dus minder geschikt voor many-to-one of many to many relationships

Neo4J -> via graph-like datamodel.
Veel geschikter voor many to many of many to one (maar relationeel werkt ook voor many to one)



Column family structure bestaat ook

column family = table

cassandra gebruikt die woorden door mekaar

Niet elke rij heeft elke kolom

family groep van kolommen die overeen komen
bij friends nie allemaal

denk aan geneste hashmap
met buitenste rowkey en die mapt op een gesorteerde collectie van cellen

sql ~~ relationeel

niet lezen als geen sql maar als geen relationeel


## Data storage and retrieval


2 soorten storage engines

**OLTP** Transactie vanuit business 
belangrijkste is zoektocht vanop de hdd
de tijd om ze te vinden vooral


**OLAP** da is wa business analyst gaat doen, die heeft massa data en wil analyses doen.
Je hebt niet zoveel business analysten en typisch is het over al de data.
Bottleneck is de tijd om ze uit te lezen vooral want ge moet nie zoeken want ge pakt ze toch zo goe als allemaal.

Vergelijk zeer vaag met front-end en back-end


deze column oriented heeft niets te maken met daarnet deze is de manier op schijf

