#!/bin/sh

CLUSTERING_HOSTNAME=${CLUSTERING_HOSTNAME:-localhost}

API=http://$CLUSTERING_HOSTNAME:8080/clustering

cat sign_data.csv | while read line; do        
    curl -L -v --post301 --post302 -i -X POST -d "$line"  -H "Content-Type: text/plain"  $API 
done

