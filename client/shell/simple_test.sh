#!/bin/bash

echo "Trying to access the backend on localhost"
curl localhost:8081 > /dev/null
if [ $? -ne 0 ]; then
    echo "Could not access localhost"
    exit 1
fi
echo "Accessing has worked!"
