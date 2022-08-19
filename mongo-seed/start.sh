#! /bin/bash

mongoimport --host mongodb --db mydb --collection address --type json --file /init.json --jsonArray