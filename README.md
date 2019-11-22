Use with:
* deploy:
  * ansible-playbook site.yml --extra-vars "inventories/development/group_vars/all" --extra-vars "key_path={{ path_to_ssh_key }} key_name={{ key_name_ }} deploy=1"
* delete:
  * ansible-playbook site.yml --extra-vars "inventories/development/group_vars/all" --extra-vars "key_path={{ path_to_ssh_key }} key_name={{ key_name_ }} delete=1"
