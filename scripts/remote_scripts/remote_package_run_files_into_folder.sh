#!/bin/bash

usage() {
    echo "Usage: $0 <key-location> <user-name> <public-ip> <files-folder> <run-pattern>"
    exit 1
}

if [ $# -ne 5 ]
then
    usage
fi

key_location="$1"
user_name="$2"
public_ip="$3"
files_folder="$4"
run_pattern="$5"

ssh -i "$key_location" "$user_name"@"$public_ip" "sudo mkdir -p $files_folder/$run_pattern"
ssh -i "$key_location" "$user_name"@"$public_ip" "sudo find $files_folder -maxdepth 1 -type f -iname "'"'"$run_pattern*"'"'" -exec mv {} $files_folder/$run_pattern \;"
