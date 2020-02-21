#!/bin/bash

usage() {
    echo "Usage: $0 <key-location> <user-name> <public-ip> <path-to-file>"
    exit 1
}

if [ $# -ne 4 ]
then
    usage
fi

key_location="$1"
user_name="$2"
public_ip="$3"
path_to_file="$4"

ssh -i "$key_location" "$user_name"@"$public_ip" "sudo rm -f $path_to_file"
