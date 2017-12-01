# Lesweek 10

(Op 01/12)

Vorige keer

## Two categories of storage engines

Onderscheid tussen OLTP en OLAP. 

Analytic - typisch in datawarehouses. Typisch bijna alles nodig. 

### B-tree index

B =~ balanced
Beperkte diepte

b, c, cr, f, h, kar, ... zijn ook blokken op de disk.

Sequentieel lezen/schrijven gaat zeer snel op een harde schrijf. 
Traag als je schrijft. Als je nieuw record maakt dan maak je een nieuwe sleutel. 

Denk bottom up.
Je schrijft item in bepaald blok, da zit vol en je moet splitsen, daarna kan het ook weer vol zitten en ook weer naar boven gaan. Daarom snel lezen maar schrijven traag. 

Inverse (beter schrijven, traag lezen) -> log structured merge tree.

Snel naar geheugen opslaan in (bvb rb) tree. 
SSTable = sorted string table. Zeer snel door gesorteerde tabel. 

Per tree en per SSTable kan het maar 1 keer voorkomen maar in totaal dus wel meerdere keren. 

**Bloom filter**

key -> f1, ..., fK (meerdere hashfuncties)
key..
[000100101]

Staan er sommige op 0 dan weet je zeker dat het er niet in zit (garantie het zit er niet in) en moet je dus die structuur niet ophalen. 

**Compaction and merging process** soms reden da sommige databases wa blijven hangen omdat dit proces bezig is. 
Compact omdat je maar 1 over houdt, merging omdat je meerdere segments bij mekaar pakt. 

Voor **Reliability**

Men schrijft toch iets weg naar de schijf (**write-ahead log**) - vaak allereerste operatie die gebeurd. 


### Vergelijking

Write amplification = schrijf versterking
als ik iets schrijf is dat niet 1 schrijf operaties, neen die resulteert in meerdere schrijfoperaties. 

Sequentieel wegschrijven gaat zeer snel. 

Bij B-trees heb je wel zoiets als lock zodat maar 1 gebruiker tegelijk aan die sleutel kan werken. 



### Star-schema for analytics

Vaak geen json enzo maar eerder relationele tabel en gaat vertrekken van de facts table. 

Column (manier hoe het georganiseerd w op de harde schijf) verwar niet met cassandra. 




# Distributed Data: replication

Distributed (wrm)
1 - replicatie
  - betrouwbaarheid (backups)
  - workload spreiden
2 - partionering


Shared nothing architectuur
dwz dat alle coordinatie door software w geregeld. Fysiek hardwarematig delen we niets. 

Bij leader-follower -> leader bottleneck


Ofwel betaal je de kost als je schrijft, ofwel als je leest. 

Eventual consistency -> uiteindelijk komt het goed
Iedereen kan snel lezen maar soms mogelijks oude data

Er zijn sterkere modellen vb reading your own writes.


Door load balancing kan je soms een oudere versie lezen na een refresh waar je nieuwere data zag.

Eerst tweede user naar Leader daarna naar nen follower (tekening).

Monotonic reads -> nooit terugkeren in de tijd, die geven dus garantie.


**Leaderless**

Zelf naar iedereen schrijven (zelf is nie per se user kan ook proxy fzo zijn = coordinator).


Read repair is niet voldoende als er bijna nooit geread w. 

Daardoor bestaat anti-entropy process die da continu in den achtergrond draait om die zaken te doen. 


### QUORUMS 
(belangijk)

NWR notatie

N = aantal replicaties waar database systeem naar **streeft**
W = aantal succesvolle schrijfoperaties (W kleiner dan N)
R = aantal replicaties waar je antwoord van wacht voor je naar client terugstuurt

en dan heb je quorum condition:

als W + R > N dan weet je dat je de laatste (meest recente waarde sowieso)

je leest 3, je schrijft 3 er zijn er 5 is sowieso overlap. 



Verzwakte noemt men sloppy qourums.
Bij netwerk deel verlies bvb kan het zijn dat je het qourum dus niet meer haalt.
Die writes ga je accepteren op nodes die niet per se die replica bevatten. -> sloppy

Om garantie te bieden voor durability maar da geeft niets voor de consistency. 

tl;dr

Single leader makkelijkste -> geen conflicten


# Distributed data: partitioning/sharding

Door meer nodes los je het probleem niet op maar maak je het erger.

-> tijd voor logs geen goed idee omda je altijd leest/schrijft van 1 node

-> hashing 



primary key (id, song_order)
id -> bepaalt de node
song_order -> bepaalt de volgorde van ordenen

soms kan je daardoor wel nie altijd alles lezen en "normaal werken met de queries" want die werken dan soms niet.


compound partition key voor nen stop te hebben


nodes toevoegen/verwijderen -> meer of minder partities toevoegen.

NIET: van module 4 naar module 5 gaan want anders moede weer alles opnieuw berekenen. 

Veel fijnere manier als je veel partities neemt van in het begin.

Te veel -> veel overhead
te weinig -> vroeg of laat tegen de lamp lopen


shard =~~= partities (ander woord)

aantal partities schaalt mee met hoeveelheid data bij mongo (dynamic partitioning)

Request routing 

1 alle nodes weten alles
2 mongo model met router
3 client houdt het bij (minst gebruikt) want je wil nie da je client zicht heeft op u data. 


zookeeper heeft alle logica, clients sturen dan enkel nog nen heartbeat 


# Consistency and consensus

"Eventually everyone is dead"

men zegt ook niets van de volgorde waarin het goed komt. 

eventual consistenty = zeer zwak

## strict consitency = linearizability

= er is maar 1 copy

aar die schrijfoperatie echt durable w da weet je niet! (van client c)

een versterking

van zodra een client een 1 heeft gelezen -> lezen de rest ook al 1

wnr?

- meestal om leader te verkiezen 
- locking voor usernames bvb (dingen die uniek moeten zijn) (hypothetisch geval da 2 users op zelfde moment 2 plaatsen in cinema willen registreren)
- cross channel (veronderstel fotos uploaden voor u profiel, met een image resizer daar heb je 2 parallele kanalen) moest het niet linearizabl zijn dan zou je 0 kunnen krijgen of oude versie. 

## CAP theorem

consistency = staat voor linearizabl die zeer sterke garantie, vanzodra 1 client het krijgt krijgt iedereen het
availability = een node kan requests blijven processen (minder kritisch)
partition tolerance = kan je het opsplitsen

je maakt dus de keuze "pick any 2 out of 3" (merk op vkeerd), je kan ze niet alle 3 tegelijk hebben.

CA = altijd perfect consistent maar alle nodes up en running dan moet dat 1 node zijn kan je nie partitioneren

CP = systeem blijft strict consistent maar is niemeer available, stel dit breekt dan moet je 1 van de 2 nodes offline nemene -> niemeer available

AP = systeem blijft avaiable, alle nodes blijven alle requests processen maar het is niemeer consistent


One kind of faults : network, wat gebeurd er als node crashed, wat als een node foute data begint te genereren; cap theorema zegt daar niets over


Het is vooral de afweging linearzi vs strongly available


betere formulering 

PACELC


CONSENSUS

= leader election van daarnet

requirements

- iedereen legt zich neer bij de beslissing
- je kan niet twee keer beslissing
- geldigheid - een gekozen moet ooit voorgesteld zijn (staat er omdat je anders alles op 0 zet = triviaal)
(noemt men de safety requirements maar zegt niets over tijdigheid dat zegt de laatste)
- termination

allemaal beslissingsprocessen

compare-set registers = ik geef oude waarde mee en nieuwe waarde
cs(5,6) dan zeg je "als de huidige waarde nog steeds 5 is dan zet je er 6, is het veranderd dan geef je me maar een error".


bij single leader - moet je een leader election hebben want als leader wegvalt ist er een probleem
leader nodig voor consensusalgo te bouwen maar weer leader nodig. 
