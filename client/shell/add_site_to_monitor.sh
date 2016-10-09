#!/bin/bash
echo "Adding site to monitor $1"
curl -H "Content-Type: application/json" -X POST -d "{\"url\": \"$1\"}" localhost:8081/monitored-sites/
