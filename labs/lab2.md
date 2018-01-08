# Lab 2: Docker Swarm

## Note

Dit was docker swarm op servers op het testbed. Moeilijk opnieuw uit te voeren en te testen maar eventueel te doen via vagrant machientjes (TODO?).


## Werkwijze

1. First you need to register the machines (3) in the same swarm. A swarm has 1 master and all others are worker nodes. Use ``docker swarm init --advertise-addr <ipv4>``. The ouput is a token that you need to add to the worker nodes. You can also use ``docker swarm join-token worker`` to get the token.

2. Check if service is running ``docker service ls`` and look which nodes are running it ``docker service ps redisname``. 





## Solution files

### docker-compose.yaml

```yaml
# we have removed the environment variables from the start of the image node, so you can run the application locally.
# You must build these images first (e.g. docker build -t mobile ./mobile ).
version: '3' 
services:
  api:
    image: api #$MY_REGISTRY
    deploy:
      placement:
        constraints:
          - node.role == manager
    ports:
      - "80:8080"
    depends_on:
      - mobile 
      - desktop 
  mobile:
    image: mobile #$REGISTRY
    depends_on:
      - customer 
      - order 
    deploy:
      mode: global
  desktop:
    image: desktop:v1 #$REGISTRY
    depends_on:
      - customer 
      - order 
    deploy:
      mode: global
  customer:
    image: customer #$REGISTRY
    depends_on:
      - redis 
    deploy:
      replicas: 2
  order:
    image: order #$REGISTRY
    depends_on:
      - redis 
    deploy:
      replicas: 2
  redis:
    image: redis #$REGISTRY
    deploy:
      placement:
        constraints:
          - node.role == manager
  turbine:
    image: turbine    #$REGISTRY
    ports:
      - "8000:8000"
    depends_on:
      - mobile 
      - desktop 
      - customer 
      - order 
      - api
  hystrix: # This service was not in the worksheet, as you did not have a public IP. 
           # Graphs showing the hystrix metrix will be visible at http://localhost:9000/hystrix/monitor?stream=turbine%3A8000%2Fturbine.stream 
    image: mlabouardy/hystrix-dashboard #$REGISTRY/hystrix-dashboard 
    ports:
      - "9000:9002"
    depends_on:
      - turbine 
  auto_scale:
    image: auto_scale # MY_REGISTRY
    links:
      - turbine:turbine
    volumes:
      - "/var/run/docker.sock:/var/run/docker.sock"
    deploy:
      placement:
        constraints:
          - node.role == manager

```

### Dockerfile

```
FROM ubuntu
RUN apt-get update && \ 
    apt-get install -y curl nodejs npm build-essential && \
    curl -sL https://deb.nodesource.com/setup | bash - 

COPY package.json /tmp
RUN cd /tmp && npm install

RUN mkdir /src
RUN cp -r /tmp/node_modules /src

WORKDIR /src
ADD . /src
CMD nodejs /src/index.js 
```



TODO simplified version



## Myversion

So I have a pc the host (orion) (also some sort of gateway in this use case) who is running 3 virtual machines with vagrant (cloud, squall and tifa) and virtualbox. See the table for an overview. For those who are interested I am using the centos7 image from geerlingguy.

### Overview

| Hostname   | IP               | Function/Role        |
| ---------- | ---------------- | -------------------- |
| orion      | 192.168.1.38     | GW / Host of vms     |
| cloud      | 192.168.10.1     | VM 1: Swarm manager  |
| squall     | 192.168.10.2     | VM 2: Swarm worker 1 |
| tifa       | 192.168.10.3     | VM 3: Swarm worker 2 |


