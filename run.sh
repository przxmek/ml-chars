#!/bin/bash

TARGET_PATH='target/'
JAR_PATH=${TARGET_PATH}'ml-chars-0.1-SNAPSHOT.jar'

DATA_DIR=$1
OUTPUT_FILE=$2

if [ -z "$1" ] || [ -z "$2" ]
	then
	echo "Invalid use of $0. You must run it like:"
	echo "$0 DATA_DIR OUTPUT_FILE"
	exit 1
fi

java -jar ${JAR_PATH} ${DATA_DIR} ${OUTPUT_FILE} | tee ${OUTPUT_FILE}
