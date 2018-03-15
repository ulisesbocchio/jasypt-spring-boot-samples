#!/usr/bin/env bash

YELLOW='\033[1;33m'
NC='\033[0m'

if [ "$1" == "-b" ]
then
    echo -e "${YELLOW}Building App${NC}"
    mvn clean install
fi
echo -e "${YELLOW}Killing Container(s)${NC}"
docker-compose kill
echo -e "${YELLOW}Removing Container(s)${NC}"
docker-compose rm -f
echo -e "${YELLOW}Building Image(s)${NC}"
docker-compose build
echo -e "${YELLOW}Running App${NC}"
docker-compose run config-client
