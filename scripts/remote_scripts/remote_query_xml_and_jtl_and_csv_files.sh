#!/bin/bash

usage() {
    echo "Usage: $0 <key-location> <user-name> <public-ip> <base-folder> <pattern>"
    exit 1
}

if [ $# -ne 5 ]
then
    usage
fi

key_location="$1"
user_name="$2"
public_ip="$3"
base_folder="$4"
pattern="$5"

ssh -i "$key_location" "$user_name"@"$public_ip" "find $base_folder -maxdepth 1 -type f -iname "'"'"$pattern*.xml"'"'
ssh -i "$key_location" "$user_name"@"$public_ip" "find $base_folder -maxdepth 1 -type f -iname "'"'"$pattern*.jtl"'"'
ssh -i "$key_location" "$user_name"@"$public_ip" "find $base_folder -maxdepth 1 -type f -iname "'"'"$pattern*.csv"'"'
