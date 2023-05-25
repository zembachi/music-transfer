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
helm install config-server config-server;
helm install postgres postgres;