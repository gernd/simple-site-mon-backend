#!/bin/bash
echo "Getting all monitored sites"
curl -i -H "Accept: application/json" "localhost:8081/monitored-sites/" 
