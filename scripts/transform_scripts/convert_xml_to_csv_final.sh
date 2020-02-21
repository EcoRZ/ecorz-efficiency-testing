#!/bin/bash

usage() {
    echo "Usage: $0 <xml-file> <csv-file>"
    exit 1
}

if [ $# -ne 2 ]
then
    usage
fi

xml_file="$1"
csv_file="$2"

python3 convert_xml_to_csv.py "$xml_file" "$csv_file"
