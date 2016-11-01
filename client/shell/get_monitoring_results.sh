#!/bin/bash
echo "Getting monitoring results"
curl -i -H "Accept: application/json" "localhost:8081/results/" 
