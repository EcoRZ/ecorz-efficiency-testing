#!/bin/bash

usage() {
    echo "Usage: $0 <key-location> <user-name> <public-ip> <filepath-of-csv-file>"
    exit 1
}

if [ $# -ne 4 ]
then
    usage
fi

key_location="$1"
user_name="$2"
public_ip="$3"
filepath_of_csv_file="$4"
path_of_csv_file=$(dirname $filepath_of_csv_file)
html_folder="$path_of_csv_file/html_dash"

if [[ -d "$html_folder" ]]
then
    echo "$html_folder exists already on your filesystem." 1>&2
    exit 1
fi

ssh -i "$key_location" "$user_name"@"$public_ip" "sudo mkdir $html_folder && cd $path_of_csv_file && /home/ubuntu/jmeter/bin/jmeter -g $filepath_of_csv_file -o $html_folder"
