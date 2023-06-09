#!/bin/bash

docker-compose -f redis.yml up -d
localstack start -d