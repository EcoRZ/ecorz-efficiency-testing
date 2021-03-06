#!/bin/bash

usage() {
    echo "Usage: $0 <key-location> <user-name> <public-ip> <file-name> <dest-folder>"
    exit 1
}

if [ $# -ne 5 ]
then
    usage
fi

key_location="$1"
user_name="$2"
public_ip="$3"
file_name="$4"
dest_folder="$5"

scp -i "$key_location" "$file_name" "$user_name"@"$public_ip":"$dest_folder"
