#!/bin/sh
echo "Build demo image"
cd "$1/demo_app"
mvn clean install -DskipTests=true
docker build . -t demo_app
