# Lesweek 5

(Op 27/10)

## Microservice architectural design


### Decomposition

Vertalen naar architectectuur die de realiteit weerspiegelen in code.

Stap 0: We willen ons probleemdomein verstaan (domain expert -> iemand die de realiteit goed kent).
Gebruik ubiquitous language! (Bedrijfsspecifiek dialect).
Probeer nog niet na te denken over technische termen als je in de use cases (bedrijfs) spreekt


Dont talk IT, talk business!


Twee soorten system operations: commands & queries.

Ticket heeft vaak meer dan 1 betekenis (ticket bij support, ticket voor cinema)


DDD = domain driven design

-> op zoek gaan naar subdomeinen

Bounded contexts


Voor elke microservices moet je nu met die operations/queries nadenken hoe ze met elkaar communiceren.

## Business logic of a single microservice

voor elke querie, commando da je kan binnenkrijgen ga je een aparte operatie schrijven in behaviour


aggregates om spinnenweb/spaghetticode uiteen te trekken 

(geen object referentie opslaan maar wel een identifier)

entitity klasse waar iedereen langs moet gaan (klasses afschermen, 1 toegangspoort)



slide 62

message orderning: als je a stuurt voor b dan verwacht je dat a ook toekomt voor b
delivery guarantees: w ze afgeleverd en zonee als mag ik dan weten da het nooit meer komt
