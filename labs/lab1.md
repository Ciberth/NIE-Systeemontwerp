# Lab 1: Docker & Docker compose

## Linux commands (reminder)

- CTRL + C: Send interrupt (kill)
- CTRL + D: Logout/Exit
- Mongo client with docker container -> ``mongo ``
- MySQL client with docker container -> ``mysql -h localhost -P 3306 -u root -p --protocol=tcp``
- Poorten die open staan controleren via ``netstat -tulpn``

## Interesting docker commands

> For help add the --help flag with a docker command.

> For man pages follow convention of ``man docker-run``, ``man docker-ps`` etc.

> To detach from container press CTRL + P followed by CTRL + Q (or exit?)

| Command                                         | Explanation                       |
| ----------------------------------------------- |:---------------------------------:|
| `docker ps -a`                                  | Overview of containers            |
| `docker images`                                 | Overview of images (locally)      |
| `docker search httpd`                           | Search on docker hub              |
| `docker pull httpd`                             | Pull from docker hub              |
| `docker start mycontainer`                      | Start mycontainer                 |
| `docker stop mycontainer`                       | Stop mycontainer                  |
| `docker rm mycontainer`                         | Remove mycontainer                |
| `docker rename oldname newname`                 | Rename mycontainer                |
| `docker container prune`                        | Remove all stopped containers     |
| `docker inspect mycontainer`                    | Information from mycontainer      |
| `docker logs mycontainer`                       | Logs from mycontainer             |
| `docker build -t my-image .`                    | Build image with Dockerfile       |
| `docker attach containername`                   | Attach to running container       |
| `docker exec -it containername echo "Hello!"`   | Exec command in running container |
| `docker run -d -p 12345:3306 my-image`          | Example: Run (detach/port)        |
| `docker run -it my-image`                       | Example: Run (interactive/attach) |
| `docker run -it --link redis my_node /bin/bash` | Example: Run (it/linking)         |

## Dockerfile

### Example 1: Dockerfile

```
# To build this nodejs image run: docker build -t nodejs .

# Set the base image to ubuntu
FROM ubuntu
 
# update and install packages using apt-get
#	-y option: assume "yes" as answer to all prompts and run non-interactively
RUN apt-get update && \ 
    apt-get install -y curl nodejs npm build-essential && \ 
    curl -sL https://deb.nodesource.com/setup | bash - 

# make package.json available in the /tmp directory of your container 
COPY package.json /tmp

# run npm install in the /tmp directory so all dependencies will be installed
RUN cd /tmp && npm install

#copy the content of /tmp/node_modules into /src
RUN mkdir /src
RUN cp -r /tmp/node_modules /src

# Make sure /src is your working directory
WORKDIR /src

# make the application source code available in /src
COPY index.js /src

#COPY hello_world.html /src/public/

# set the correct command in the Dockerfile to start your node application
CMD nodejs /src/index.js 


```

### Example 2: Dockerfile

```

```

## Docker compose

1. Make Dockerfile
2. Make docker-compose.yaml
3. Run with ``docker-compose up``

### Example 1: docker-compose.yaml

```
version: '3'
services:
  nodejs:
    image: nodejs 
    depends_on:
      - redis 
    ports:
     - "80:8080" # host port -> container port
    volumes:
     - ./nodejs/public:/src/public # host directory mapped to container's /src/public directory
  redis:
    image: redis

```

### Example 2: docker-compose.yaml

```
version: '3'
services:  
  nodejs1:
    image: nodejs 
    depends_on:
      - redis 
    volumes:
     - ./public:/src/public
  nodejs2:
    image: nodejs 
    depends_on:
      - redis 
    volumes:
     - ./public:/src/public
  nodejs3:
    image: nodejs 
    depends_on:
      - redis 
    volumes:
     - ./public:/src/public
  redis:
    image: redis
  nginx:
    image: nginx
    depends_on:
      - nodejs1
      - nodejs2 
      - nodejs3 
    ports:
     - "80:80" # host port -> container port

```


TODO

(eerst uitproberen dan invullen)

docker run mijn mysql vb en mongo vb
mongo localhost:27017 command

