#!/usr/bin/env bash
while ! nc -z config-server 8888 ; do
    echo "Waiting for upcoming Config Server"
    sleep 2
done
java -Djava.security.egd=file:/dev/./urandom -jar /app.jar