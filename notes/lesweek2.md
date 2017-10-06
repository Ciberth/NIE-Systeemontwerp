# Lesweek 2

(Op 06/10)

Vervolg containers

# Docker

= enerzijds die kernel truckjes met een laag erboven. Wat er in de container zelf zit is eender.

Bijkomende frameworks zoals swarm/compose.

**Marketing** ~> via een mooi verhaaltje + voordelen en daardoor een succesverhaal.

Enkele specifieke commando's/operaties ongeacht wat er in zit.
Interessant omdat je meer containers kan hebben per fysieke machine.

Grootste beperking is het delen van containers omdat je dezelfde kernel moet hebben.
Containers worden gestart en gestopt in miliseconden. 

Vaak zelf request opvangen en dan pas container opstarten.

Docker is iets meer dan gebruiksvriendelijke bibliotheek bovenop lxc maar er zit daar nog die aufs in.

## Docker architecture

Docker daemon die draait en http aanvaard. Je kan deze lokaal versturen via command line maar onderliggend wordt da op een unix socket gezet en da komt zo toe in u client. 

Images zijn geen containers!

Dockerfiles:

- Eerste lijn is altijd nen FROM, vaak start je van een bestaande image (niet per se een os kan ook jvm zijn oid.)
- Files die je nodig hebt van de buitenwereld moet je erin krijgen dus je moet specifieren.
- Copy en add (add kan tar files uitpaken) idee is hetzelfde
- Environent variabele ...
- Maak onderscheidt tussen wat jij ziet binnenin de container en van buitenaf

```
FROM ubuntu:14:04

COPY html /var/www/html
ADD web-page-config.tar
...

EXPOSE 7373/udp 8080 
RUN	...

# het command er bestaat ook CMD
ENTRYPOINT ["echo", "Dockerfile entrypoint demo"]
# belangrijk als deze returnt dan geraakt je container in stopped state

``` 


Hoe bouw je zo een image up?

**Lagen**

U basislaag is altijd die FROM 

Wat is het verschill tss image en container:
je start image van container en die gaat al die lagen inaden en hij gaat ze read-only maken en daarboven zet hij een schrijfbaar laagje. En alles wa je doet gaat in die schrijfbare laag gebeuren. 

``docker history mongo:latest``

Gelaagd laagsysteem : een van grootste meerwaardes van docker

Op jezelfde host van een en dezelfde image meerdere draaien - opschalen. 

**Building a container**
id of container en id of image is anders!

### Union File System

Elke laag is niets meer dan een aantal bestanden
Bij het copy'n wordt er een nieuwe gemaakt zogezegd, referentie naar parent laag
In de bovneste laag zie je enkel de laatste laag (Copy-on-write)


# Orchestration

(service in container)

## Kubernetes

rie blokken zijn servers - master/slave structuur.

Single point of contact die dan alles zal door delegeren. 
etcd - distributed key value store

-> geen containers maar pods die je uitrolt
pods = containers me wa extra functionaliteit voor de samenwerking ertussen die kubernetes gebruikt

pod =~ container


deployment object (yaml file)

mogelijke use case voor twee containers in 1 pod -> voor bvb logging java logging en python engine en je zou kunnen zeggen ik steek daar kleine adapter in zodanig dat die omzet zoda logica nie in de service zelf zit.

Op je host moet je geen assumpties maken voor porten!! Da verandert voortdurend door die schaling.

Mekr op: container die zich wil registreren vs client die wil ontdekken

Twee manieren:
- Client-side discovery: u client is op de hoogte dat hij via registry werkt. (kan bvb round robin load balancing doen)
- Server-side discovery: client heeft altijd zelfde point of contact en die neemt de logica over en gaat alles load balancing: voordeel nettere client code nadeel is extra componentje

Kubernetes:
- maak yaml file (service abstractie file)
en in selector key/values paar en adverteer die als 1 service als 1 service met naam op poort 80


### Rolling update

vroeger: offline + nachtje doorwerken + online
hier: stel 1 replica je maakt er 2 van je gaat load balancen je haalt er 1 offline je voegt nieuwe versie en load balancer gaat naar v2 en je haalt v1 offline

vs canary release (vroeger in de mijnen nammen ze da vogelke mee voor gas)
hier eerder testing technisch werkt dit wel

A/B testing -> facebook doet da veel (meer marketing)



## Cloud computing


Wat maakt cloud computing, cloud computing -> on demand.

... karakteristieken (5) hoe kunne we nu elk van die 5 dingen toepassen?

Cloud provider = amazon, google microsoft
Cloud user = netflix
End user = wij die netflix betalen

### Cloud provider

1 ) on-demand
2 ) elasticity
3 ) resource pooling
4 ) metered
5 ) networked

Veel resources

cloud user maakt hier gebruik van voor schaalbaarheid!

Als tenant zie je het verschil niet en is het een homogene pool je hebt een pool waar je kan uithalen wat je wil/nodig hebt en anders gooi je het terug. EN toch geef je de illusie da ze daar alleen zitten.

SAAS = office365


Ubuntu MaaS je huurt metal


vaak is uploaden gratis maar downloaden niet

spot pricing -> "solden als het ware"


containers as a service: je betaalt voor container engines 


mij middleware ben je gebonden aan den technologie 
als ze eht plots nietmeer aanbieden dan jah miserie als je het zelf onderhoudt is da minder een probleem

## .

**Static vs elastic scaling**


loon berekeningen / boekhouden / fietsvereffenen
nmbs website - piekuren
Dagelijke trends

Kan je het met andere woorden voorspellen? Scriptje maken.

op twee manieren=> horizontaal schalen ofwel vertikaal schalen
horizontaal (miss is da ander bed niemeer nodig binnenkort)
of vertikaal tzal wel blijven

vaak is horizontaal interessanter en goedkoper


### auto-scaling

twee manieren (synchroon: denk http) en asynchroon (queue en ooit wel verwerkt) queue is u file die je dan monitort.

Policies definieren example dynamic scaling in ec2 (examenvraag vorig jaar)

standpy pooling 
-> ik haal ze uit de groep maar nie volledig offline halen zoda je nie weer moet opstarten/installeren...
-> niet meer gebruikt maar ze is er wel nog = een "warme" instantie.

Examenvraag: de documentatie + tabel:
"Aantal instanties over de tijd"

snap de principes dus!

wees aggressief in het opschalen maar wees niet aggresief in het terug inschalen
conservatiever neer schalen
Een ontevreden klant is vaak duurder dan de kostprijs van te huren.




noisy neighbour kunnen uitleggen!
chaos monkey installern en gebruiken