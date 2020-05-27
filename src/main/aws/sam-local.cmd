#!/bin/sh
docker build . -t prime-finder # <1>
mkdir -p build
docker run --rm --entrypoint cat prime-finder  /home/application/function.zip > build/function.zip # <2>

sam local start-api -t sam.yaml -p 3000 # <3>
