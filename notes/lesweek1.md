# Lesweek 1

(Op 29/09)


## Inleiding 

Verschil programma & systeem:
* programma zelf al veel geschreven - reeks van instructies
* een systeem - een hoop componenten samen

De dag vandaag aanvaarden we niet meer dan een service onbeschikbaar is! Availability is dus cruciaal. 
Hoe zorgen we er dus voor dat als er 1000'n gebruikers tegelijk surfen dat alles nog steeds werkt?

## Software Architecture

Zie metafoor bij het bouwen van een huis. Welke vereisten heb je/zijn er?

Daar heb je twee soorten: De functionele en de niet functionele requirements.

**100% availibility** is niet gelijk aan 100% functionaliteit.

Je blijft dus available maar da wil nie zeggen dat je het correcte systeem terug geeft.

**Interoperability** - samenwerking met andere systemen/technologiën (of zelf api aanbieden voor anderen bvb)

**Portability** - stel google verhoogt prijs dan wil je naar microsoft te gaan, is het daar mogelijk om snel over te gaan?

**Throughput** - doorvoersnelheid : hoeveel lees/schrijf-operaties kan ik doen naar datalaag

**Compliance** - denk aan de wetgeving voor bijhouden van data. Iemand die data bijhoudt wordt verantwoordelijk voor de data. Je moet dus compliant zijn aan de wetgeving (sommige data moet in europa blijven en mag niet in de states opgeslaan worden)

NFR's zijn de beuzakken FR meestal de toffe!

## 4 + 1 view model

Je moet dus vanuit al de perspectieven dus eigenlijk eens bekijken.


### Logical View
- Meest vertrouwde - OO manier

### Process View
- (Multi-)Threading

### Developent View
- Hoe organiseer ik de code (packages/modules)
- Structuur van de github

### Physical View
- Op welke hardware ga ik dit deployen

### Use cases (beetje overbodig)


## Reactive Manifesto

Software van vorig jaar is niet me de software van vandaag. Die dingen evolueren. 

"Een goede software architecture moet aan die 4 principes voldoen"


Ze moeten **message driven** zijn. Eerder via boodschappen dan method calls. We sturen messages en iemand anders moet daarop reageren. -> reactive! Dit is de grondlaag.

Daarmee kunnen we **robuustheid** tegen fouten op bouwen. Dus weer reactive zodat het opgevangen worden (verminderen van functionaliteit)

**Elastic** als er veel gebruikers zijn - maar ook als er bijna geen zijn. Elastisch zijn - reageren - react to load. 

React to users/ **responsive** - ik moet de gebruiker altijd voorzien van een antwoord.


Assynchroon te werk gaan (dia op p17). Beperkt aantal boodschappen da je kan sturen. 

Een message (gerichte verzoeken) is gericht - vooral via events.
Responsive -> altijd een antw geven. 

Als een website traag lijkt dan GRRRRRRRRRRR

Elastic - via load balancer - jij weet niet hoeveel replicaes erachter zitten (afh van het aantal klanten)

Stel je zet office365 op dan weet je snachts zal het minder zwaar zijn -> **proactieve maatregelen** treffen
In tegenstelling tot **reactief** (continu monitoren).


Tegenwoordig is de insteek: software zal wel falen we gaan daar van uit. De software moet nu in staat zijn om zichzelf te genezen -> metrieken monitoren (2):

- aantal schrijfoperaties = service metrics

- technisch kan alles juist maar er is misschien toch iets aan de hand = business metrics (orders per minute)


## Microservices

Logischer wijs zijn die dingen ok maar op den duur zijn er veel zaken die naar 1 deployable unit gaan. 

Eén groot systeem die in productie moet komen.

Voor de kleur van 1 knop moet alles opnieuw gebuild worden.
Deze logische architectuur is "ok" voor sommige dingen maar toch ...

~> Spaghetticode (van een mooie lasagna naar spaghetti)


### Voorbeeldje (delivoroo) food-to-go inc

"Man in the middle"

Onion-model (business logica zit in het midden)

GOD Classes

### Drawback of monolithic arch

- Je zit vast aan één vaste technologie
- Agile manier die "voortdurend" updates doen nen **lock-step** stappen zijn gelocked op mekaar.
- Belasting - software die populair aan het worden is (met die ene jar is dat niet meer te doen).
- Da monolitisch gegeven die voortvloeit uit het gelaagd ding (die wel goed is) is nie goed.

### The microservice approach

- Allemaal aparte services. Deployable units. En je kan die elk individueel gaan schalen. Je ontkoppelt zo ook de technologië.

"Loosely coupled" -> management bullshit bingo!

Een service ~ API (REST)

microservice - service die 1 ding goed doet. Niet op technisch gebied maar op business gebied. 


"size does not matter"

wrm:
- sterk afhankelijk van de programmeerstijl
- het een is ingewikkelder dan het ander
- externe dependencies (daar zit vaak overbodige code in)

Een microservice is dus niet per se klein in lijntjes code

"interface"

die doet 1 ding maar doet het goed

what is **loosely coupled**
= change in one service should not require a change to another
- geen lock stepping
- geen volledige stack

Je moet rekening houden met latency en je moet IPC doen (inter process communication over het netwerk) dus wel nieuw soort probleem -> veel aandacht voor API contracten. 
Je legt da vast en dan pas begin je te implementeren. 

**"Over microservices heen gaan we geen datastores, services delen"**

Polyglot = meertalig. Elke services heeft zijn eigen vrijheid om alles zelf te ontwerpen. Je hebt dus niet langer 1 gedeelte databank. Al die dao (database access objects?) zitten bij mekaar traditioneel bij die monolith. 

Ook nadelen!
Het hangt er dus van af eh.


## Micro-service deployment

belangrijk verschil tss container vm

bij vm kan je makkelijk delen ik op mijn windows aan u op uw debian

container ga nie dan MOET het dezelfde OS + kernel zijn. 

### Linux Control Groups

packet tagging + op bvb netwerk caard een tag zetten van tis voor dienen container

je hebt namespaces nodig om ervoor te zorgen dat een process zo nie kan zien da er nog andere zijn
iedereen heeft dus precies unieke toegang tot /root

docker zal dus twee nrs hebben - 1 in zijn eigen namespace en 1 op de host zelf

Der is maar 1 root dus hoe los je da op 

/ -> /ns1 ~> / (nieuwe root)
  -> /ns2 ~> / (nieuwe root)

Er zijn exploits dus er is nog een check ook in.

Een container is minder veilig dan een VM!!
Container in vm





