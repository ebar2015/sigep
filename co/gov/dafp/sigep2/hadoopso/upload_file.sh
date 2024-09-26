#!/bin/bash

echo "Positional Parameters"
echo '$0 = ' $0
echo '$1 = ' $1
echo '$2 = ' $2
echo '$3 = ' $3

URL="http://gauss.dafp.local:50075/webhdfs/v1/${4}/${3}/${2}?op=CREATE&user.name=hdfs&namenoderpcaddress=cluster-hdfs&createflag=&createparent=true&overwrite=false"
curl  -i -X PUT -T ${1} ${URL}

echo 'Fin...'