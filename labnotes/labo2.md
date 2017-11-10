# Labo 2 systeemontwerp op 20/10

voor de init van de manager

docker swarm init --advertise-addr 10... 

dan krijg je token/

docker swarm join --token ...


export REGISTRY=10.2.33.218:5000

docker service create --name redis --replicas 2 $REGISTRY/redis

what do you notice? - master makes new one in the same worker 
what do you notice? - the one running on the master still running other one stopped on the worker and on other node new one was started



services beheert container en tasks worden door de manager gegeven

(effectief container starten)


difference replicated and global 

global = overal 
replicas is voor redundancy



op manager docker service rm redis
op manager docker swarm join-token worker

en op worker den join da je krijgt

docker build...
docker run...

docker tag api localhost:5000/api
docker push localhost:5000/api



docker node ps 



docker service update --update-delay 2s --update-failure-action rollback --image $REGISTRY/desktop:v3 ownStack_desktop