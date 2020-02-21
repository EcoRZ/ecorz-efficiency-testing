#!/bin/bash

usage() {
    echo "Usage: $0 <key-location> <user-name> <public-ip> <script> <arg1> <arg2> <arg3> <nmbr-args> <script_type>"
    exit 1
}

if [ $# -ne 9 ]
then
    usage
fi

key_location="$1"
user_name="$2"
public_ip="$3"
script="$4"
arg1="$5"
arg2="$6"
arg3="$7"
nmbr_args="$8"
script_type="$9"

script_basename=$(basename $script)

scp -i "$key_location" "$script" "$user_name"@"$public_ip":"~/$script_basename"

if [ "$script_type" = "bash" ]; then
    if [ "$nmbr_args" -eq "1" ]; then
        ssh -i "$key_location" "$user_name"@"$public_ip" "sudo bash -s $script_basename $arg1"
    elif [ "$nmbr_args" -eq "2" ]; then
        ssh -i "$key_location" "$user_name"@"$public_ip" "sudo bash -s $script_basename $arg1 $arg2"
    elif [ "$nmbr_args" -eq "3" ]; then
        ssh -i "$key_location" "$user_name"@"$public_ip" "sudo bash -s $script_basename $arg1 $arg2 $arg3"
    else
        echo "1, 2 or 3 args but not $nmbr_args are supported" 1>&2
    fi
elif [ "$script_type" = "python" ]; then
    if [ "$nmbr_args" -eq "1" ]; then
        ssh -i "$key_location" "$user_name"@"$public_ip" "sudo python3 $script_basename $arg1"
    elif [ "$nmbr_args" -eq "2" ]; then
        ssh -i "$key_location" "$user_name"@"$public_ip" "sudo python3 $script_basename $arg1 $arg2"
    elif [ "$nmbr_args" -eq "3" ]; then
        ssh -i "$key_location" "$user_name"@"$public_ip" "sudo python3 $script_basename $arg1 $arg2 $arg3"
    else
        echo "1, 2 or 3 args but not $nmbr_args are supported" 1>&2
    fi
elif [ "$script_type" = "awk" ]; then
    if [ "$nmbr_args" -eq "1" ]; then
        ssh -i "$key_location" "$user_name"@"$public_ip" "sudo ./$script_basename $arg1 > tmp; sudo mv tmp $arg1"
    elif [ "$nmbr_args" -eq "2" ]; then
        ssh -i "$key_location" "$user_name"@"$public_ip" "sudo ./$script_basename $arg1 $arg2 > tmp; sudo mv tmp $arg1"
    elif [ "$nmbr_args" -eq "3" ]; then
        ssh -i "$key_location" "$user_name"@"$public_ip" "sudo ./$script_basename $arg1 $arg2 $arg3 > tmp; sudo mv tmp $arg1"
    else
        echo "1, 2 or 3 args but not $nmbr_args are supported" 1>&2
    fi
else
    echo "\"bash\", \"awk\" or \"python\" are only supported but not \"$script_type\"" 1>&2
fi

ssh -i "$key_location" "$user_name"@"$public_ip" "sudo rm -f $script_basename"
