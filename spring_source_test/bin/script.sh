#bind 127.0.0.1
#port 6379
#protected-mode no

#cluster-enabled yes
#cluster-config-file nodes-6379.conf
#cluster-node-timeout 5000

#date: system datetime, clock/hwclock: hardware clock
#ntpdate time.nist.gov: adjust system datetime to internet datetime

#!/bin/sh
#awk {} : BEGIN{action} or END{action}, /REG/{action}, 布尔表达式($2~/REG/ or $0~var or $1 == "test" or var == 7000){action}

echo "start to create cluster for $1 and port $2 and number of master $3"

# two ways to pass params to awk:
# 1. -v arg=$1 '{print arg}'
# 2. '{print "$1"}'
# /arg/ arg is literal, it can not be params
ADDRESS=`ifconfig $1 | awk '/inet addr/{gsub(/addr:/,"",$2);print $2}'`
echo "$ADDRESS"
PORT=$2
TAG_PORT=$4

i=0
while [ $i -lt $3 ]; do

        mkdir $PORT || exit 1
        cd "$PORT"
        cat ../../redis.conf |  \
                awk -v bind=$ADDRESS -v port=$PORT \
                '{  if($0~/^bind/){print "bind " bind}
                        else if($0 ~ /^port/){print "port " port} 
                        else if($0~/^protected-mode/){print "protected-mode no"}
                        else if($0~/^logfile/){print "logfile redis.log"}
                        else{ print $0}} 
                        END{print "cluster-enabled yes";
                                print "cluster-config-file nodes.conf";
                                print "cluster-node-timeout 5000"}' > redis.txt
        test -f redis.txt && ../../src/redis-server ./redis.txt > /dev/null 2>&1 &
		HOST_LIST="$HOST_LIST $ADDRESS:$PORT"

        cd ..
		PORT=`expr $PORT +  1 \* 2`
        i=`expr $i +  1 `
done
sleep 3s #wait redis to startup before creating cluster, this is not a good way!!!
echo "$HOST_LIST"
# two ways to interact with bash:
# 1. echo "yes" | command
# 2. command <  EOF
# 	 yes
#    EOF
test -z "$HOST_LIST" || echo "yes" | ../src/redis-cli --cluster create $HOST_LIST > /dev/null 2>&1 &
#test -z "$HOST_LIST" || ../src/redis-cli --cluster add-node $HOST_LIST $ADDRESS:$TAG_PORT > /dev/null 2>&1 &

#!/bin/sh
#redis-cli reshard <host>:<port> --cluster-from <node-id> --cluster-to <node-id> --cluster-slots <number of slots> --cluster-yes

ADDRESS=`ifconfig $1 | awk '/inet addr/{gsub(/addr:/,"",$2);print $2}'`
echo "$ADDRESS"
PORT=$2
TAG_PORT=$3
NUM=$4

ORI_ID=`../src/redis-cli -h $ADDRESS -p $PORT cluster nodes | awk -v port=$PORT '$0~port{print $1}'`
TAG_ID=`../src/redis-cli -h $ADDRESS -p $PORT cluster nodes | awk -v port=$TAG_PORT '$0~port{print $1}'`
echo "$ORI_ID to $TAG_ID"

#../src/redis-cli --cluster reshard $ADDRESS:$PORT --cluster-from $ORI_ID --cluster-to $TAG_ID --cluster-slots $NUM --cluster-yes > /dev/null 2>&1 &

#!/bin/sh

echo "start to kill cluster"

#xargs use white space to split words by default
test -z $1 && exit 1
test $1 = all && ps -ef | grep redis | awk '{print $2}'| xargs kill -9
test $1 = all || ps -ef | grep redis | awk -v port=$1 '$0~port{print $2}'| xargs kill -9
#$1 = all , $1 == all , $1 = "all" all the same
#test string equal is tricky! do not use test "$1" = all, no matter what $1 is, always true (not true!!!)

#!/bin/sh
sleep 3 &
sleep 4 &
sleep 5 &
wait #wait all the background task to finish
echo 'done'

#!/bin/sh
# (()),$(()) > $[], let > expr
a=9;b=13;c=-1;d=+4;e=7.2
echo "a=9;b=13;c=-1;d=+4;e=7.2"

echo "(( )) cal:"
echo $((a+b))
echo $((a+c))
echo $((a+d))
echo $((a+e)) #error
echo $((c+d))
echo $((a*b))
echo $((a/b)) #int div
echo $((a%b))

echo "stand alone exp:"
((a=a+b))
echo $a
a=$((a+b))
echo $a

echo
echo
a=9;b=13;c=-1;d=+4;e=7.2
echo "a=9;b=13;c=-1;d=+4;e=7.2"
echo "[] cal:"
echo $[a+b]
echo $[a+c]
echo $[a+d]
echo $[a+e] #error
echo $[c+d]
echo $[a*b]
echo $[a/b] #int div
echo $[a%b]
echo "stand alone exp:"
[a=a+b] #error
echo $a
a=$[ a + b ]
echo $a

echo
echo
a=9;b=13;c=-1;d=+4;e=7.2
echo "a=9;b=13;c=-1;d=+4;e=7.2"
echo "let cal:"
let "x = a + b" # white space with ""
echo $x
let x=a+c
echo $x
let x=a+d
echo $x
let x=a+e #error
echo $x
let x=c+d
echo $x
let x=a*b
echo $x
let x=a/b #int div
echo $x
let x=a%b
echo $x
#"let" has no return value: x=`let a+b` x is null

echo
echo
a=9;b=13;c=-1;d=+4;e=7.2
echo "a=9;b=13;c=-1;d=+4;e=7.2"
echo "expr cal:"
echo `expr $a + $b` #must have white space
echo `expr $a + $c`
echo `expr $a + $d` #error
echo `expr $a + $e` #error
echo `expr $c + $d` #error
echo `expr $a \* $b` #specail characters
echo `expr $a / $b` #int div
echo `expr $a % $b`
echo "stand alone exp:"
expr $a = $a + $b #turn to logic calculation, return 0(false)
echo $a
a=`expr $a + $b`
echo $a
a="hello"

# take care of white space!!!
# == equals to =
if expr $a = "hello"
then echo "true"
fi

if test $a = "hello"
then echo "true again"
fi

if test $a = "hello" || test $a = "world"; then
    echo "good"
fi

if [ $a = "hello" ] || [ $a = "world" ]
then 
     echo "true again!"
elif [ $a = "test" ]
then 
     echo "no way"
else
     echo "last stand"
fi

x=0
while [ $x -lt 10 ]
do
   echo "$x"
   ((x++))
done
#for ((i=1; i<=100; i++)) 
#for i in {1..100}
for i in `echo "10 11"`
do
  echo "$i"
done

str="ONE,TWO,THREE,FOUR"
#arr[0]="ONE" arr[1]="TWO" ... arr[@]="ONE TWO THREE FOUR"
arr=(${str//,/ })#take care of white space
echo ${arr[@]}

arr=(`echo $str | tr ',' ' '`)
echo ${arr[@]}

IFS=","
arr=($str)
echo ${arr[@]}

arr=($(echo $str | awk 'BEGIN{FS=",";OFS=" "} {print $1,$2,$3,$4}'))
echo ${arr[@]}