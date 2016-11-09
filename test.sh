#!/usr/bin/env bash

clear

DIR="$(dirname "$0")"
source $DIR/h2/db-properties

$DIR/h2/StopH2TcpServer.sh

printf "\nDeleting the database...\n"
rm -rf /h2db/*

$DIR/h2/StartH2TcpServer.sh

printf "\nBuilding the project...\n"
gradle flywayMigrate clean build jacocoTestReport
