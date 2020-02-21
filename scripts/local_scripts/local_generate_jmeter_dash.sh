#!/bin/bash

usage() {
    echo "Usage: $0 <filepath-of-csv-file>"
    exit 1
}

if [ $# -ne 1 ]
then
    usage
fi

filepath_of_csv_file="$1"
path_of_csv_file=$(dirname $filepath_of_csv_file)
html_folder_name="html_dash"
html_folder_path="$path_of_csv_file/$html_folder_name"

if [[ -d "$html_folder_path" ]]
then
    echo "$html_folder_path exists already on your filesystem." 1>&2
    exit 2
fi

cd $path_of_csv_file && mkdir $html_folder_name  && jmeter -g $filepath_of_csv_file -o $html_folder_name
