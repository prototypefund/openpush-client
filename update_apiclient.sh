#!/bin/bash

./gradlew generateSwaggerCodePushclient
sourcePackage=src/main/java/eu/bubu1/pushclient/
cp -r ./app/build/swagger-code-pushclient/${sourcePackage}api ./app/build/swagger-code-pushclient/${sourcePackage}apimodels ./app/${sourcePackage}
sed -i -e '/CollectionFormats/d' ./app/${sourcePackage}/api/*.java
