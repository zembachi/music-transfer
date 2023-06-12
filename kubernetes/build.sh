#!/usr/bin/env bash
cd ..;
./gradlew clean bootJar;

eval $(minikube docker-env);
images=$(docker images | grep music-transfer | awk '{print $3}');
for image in $images
do
  docker rmi -f $image;
done
docker build -t music-transfer-config ./config-server;



cd ./kubernetes;
#helm install config-server config-server;
#helm install postgres postgres;

#helm install postgresql bitnami/postgresql --values keycloak/postgres-values.yaml --namespace security;
#helm install keycloakx codecentric/keycloakx --values keycloak/values.yaml --namespace security;