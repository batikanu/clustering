#!/bin/sh

cat sign_data.csv | while read line; do 
    curl --data "str=$line" http://httpbin.org/post    
done

