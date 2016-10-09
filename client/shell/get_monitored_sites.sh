#!/bin/bash

echo "Trying to access the backend on localhost"
curl localhost:8081/monitored-sites/
curl -i -H "Accept: application/json" "localhost:8081/monitored-sites/" 
