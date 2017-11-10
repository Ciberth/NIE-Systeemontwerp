# Labo 1: Docker en co

## 3.1


Om opnieuw te runnen via start en te attachen met stdout gebruik je:

``docker start my-hello-world -a``


Gebruik

``docker run -ti counter`` om het op de stdout te printen (-t is attach tty en -i is interactive voor real time input)

CTRL + P en CTRL + Q voor te detachen zonder te breken en ``docker attach name`` om er terug in te gaan.

``docker run -ti echo``

## 3.2

``docker run -ti ubuntu``

### Inside the container

```
root@06...: hostname
06...

root@...: ll
ubuntu fs met een .dockerenv*

root@...: top
bash en top dus enkel top

```


## 3.3

``docker inspect --format {{.NetworkSettings.IPAddress}} <name>``
``docker diff <name>`` Wat hem gedaan heeft
``docker logs <name>`` Commands

Alle stopped containers removen 

``docker container prune -f `` -f flag voor force ipv -y


## 4

``docker search redis`` zoeker op docker registry

``docker run --name my-redis -d redis``

``docker container exec -it <name> bash``

- redis-server
- bash

``docker stats <name>``
``docker update -m 500m <name>``

...


``docker run -it --rm -p 80:8080 --mo,l my-redis my-nodejs /bin/bash``




