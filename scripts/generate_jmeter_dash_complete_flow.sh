# (key, user-name, ip)
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

# query xml-file via Muster -> (Muster)
xml_files_old=($(remote_scripts/remote_query_xml_and_jtl_and_csv_files.sh "$key_location" "$user_name" "$public_ip" "$base_folder" "$pattern" | grep .xml))
xml_file_old=${xml_files_old[0]}

# just for full folder-name generation
jtl_files=($(remote_scripts/remote_query_xml_and_jtl_and_csv_files.sh "$key_location" "$user_name" "$public_ip" "$base_folder" "$pattern" | grep .jtl))
jtl_file=${jtl_files[0]}
pattern_dir_folder=$(dirname "$jtl_file")
pattern_deep_folder_tmp=$(basename "$jtl_file" | cut -d '.' -f 1)
pattern_deep_folder=${pattern_deep_folder_tmp:0:19}

# kopiere files mit <files-folder> <run-pattern>
remote_scripts/remote_package_run_files_into_folder.sh "$key_location" "$user_name" "$public_ip" "$pattern_dir_folder" "$pattern_deep_folder"
# speichere folder:  <files-folder>/<run-pattern>
package_folder="$pattern_dir_folder/$pattern_deep_folder"
# execute in der Form: ../remote_scripts/remote_exec_script.sh ~/.ssh/id_rsa_omistack ubuntu 134.60.64.104 convert_xml_to_csv.py jmeter/2020-02-17_08-53-14/2020-02-17_08-53-14-run-tgroup_bench-summary.xml jmeter/2020-02-17_08-53-14/2020-02-17_08-53-14-run-tgroup_bench.csv ss 2 python
xml_file="$package_folder/"$(basename $xml_file_old)
csv_file="$package_folder/${pattern_deep_folder}.csv"
remote_scripts/remote_exec_script.sh "$key_location" "$user_name" "$public_ip" transform_scripts/convert_xml_to_csv.py "$xml_file" "$csv_file" ss 2 python
# execute in der Form: ../remote_scripts/remote_exec_script.sh ~/.ssh/id_rsa_omistack ubuntu 134.60.64.104 format_to_dash.awk jmeter/2020-02-17_08-53-14/2020-02-17_08-53-14-run-tgroup_bench.csv x x 1 awk
remote_scripts/remote_exec_script.sh "$key_location" "$user_name" "$public_ip" transform_scripts/format_to_dash.awk "$csv_file" x x 1 awk
# execute in der Form: ~/bin/remote_copy_folder... des "gepackten folders"
~/bin/remote_copy_folder_from.sh "$key_location" "$user_name" "$public_ip" "$package_folder"
# mv lokalen folder nach  ~/Documents/EcoRz/ap5/praxistransfer/<run-pattern>
mv $(basename $package_folder) ~/Documents/EcoRz/ap5/praxistransfer
local_folder=~/Documents/EcoRz/ap5/praxistransfer/$(basename $package_folder)
# execute in der Form: local_generate_jmeter_dash.sh ~/Documents/EcoRz/ap5/praxistransfer/<run-pattern>/<run-pattern>.csv
local_scripts/local_generate_jmeter_dash.sh "$local_folder"/$(basename $csv_file)
# move jtl to data-store
# data_store_ip=134.60.64.78
# data_store_jtl_folder="jmeter_jtl_files"
# remote_copy_file_to.sh "$key_location" "$user_name" "$data_store_ip" "$local_folder"/$(basename $jtl_file) "$data_store_jtl_folder"
# copy file to hdd
jtl_dir="/run/media/florian/312f1f78-3c46-4fe0-8270-2b8c6948b735/florian-EcoRZ-ap5/messdaten_nach_reboot/messdaten/jmeter_jtl_files/"

if [ ! -d "$jtl_dir" ]; then
  echo "dir ${jtl_dir} does not exist, exiting..."
  exit 2
fi

mv "$local_folder"/"$(basename $jtl_file)" "$jtl_dir"

# rm "$local_folder"/$(basename $jtl_file)
remote_scripts/remote_delete_jtl_file.sh "$key_location" "$user_name" "$public_ip" "$package_folder"/$(basename $jtl_file)
