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

# 全局/局部/环境变量 默认是全局,连在function内定义的也是全局,作用域只在当前进程
# local var 作用域只在function内
# export var 作用域在当前进程及其子进程,但不能从子进程反向传递给父进程,同时没有父子关系的进程无法传递
# source/. 不会新启子进程, 而是在当前进程执行对应的脚本
##########common cmd#########
sysctl: 针对内核参数, 修改即时生效但需配置文件持久化(/etc/sysctl.conf)
systemctl: 针对系统服务, 管理服务生命周期和依赖关系(替代传统init)

date -d "20230109 21:33:11 2 minutes ago" +"%Y-%m-%d %H:%M:%S"
date -d "2023-01-09 21:33:11 2 days" +"%Y-%m-%d %H:%M:%S"
date -d "2 days" +"%Y-%m-%d %H:%M:%S"

python3 UnicodeEncodeError: ‘ascii‘ codec:
locale
export LC_ALL="en_US.utf8"

# 2>&1 要写到最后!!! 或者 nohup ./filebeat -c poa.yml 2>> err.log >> std.log &
nohup ./bin/logstash -f filebeat_input.conf >> logstash.log 2>&1 &
nohup ./filebeat -c poa.yml >> /dev/null 2>&1 &
./bin/kibana &
./bin/kibana-plugin install file:///home/pluging.zip

curl -o /etc/yum.repos.d/CentOS-Base.repo http://mirrors.aliyun.com/repo/Centos-7.repo

tcpdump tcp port 80 or 443 -nn -s 0 -i eth1 -X -w out.cap
tcpdump 'tcp port 80 and tcp[13] == 2' -nn -s 0 -i eth1 // sync packet only

rsa密码1024 pem格式:
ssh-keygen -t rsa -b 1024 -m pem -f rsa1024pem
ssh-keygen -e -m pem -f rsa1024pem.pub

.vimrc
set encoding=utf-8
set fileencodings=utf-8
set termencoding=utf-8

adduser johnny -> passwd johnny -> chown -R johnny ./

tar -cvf xiaoq.tar xiaoq/
tar -czf xiaoq.tar.gz xiaoq/
tar -xvf xiaoq.tar
jar -xvf test.jar

ln -s sourcename linkname

ps显示命令不全 -> ps -aux > cmd.txt or ps -aux | grep xxx or top -c
find . -maxdepth 6 -type f -name 'restart*'
find . -maxdepth 1 -type d -print | sed -e 's;[^/]*/;|__;g;s;__|; |;g'
ls *.jpg | xargs -I {} cp {} /data/images
grep -rn 'hello' ./ (先查询是否有现成的功能再自己写命令)
ps -aux | grep test | grep -v grep | awk '{print $2}' | xargs kill
cut -b 1-3
cut -d " " -f 2,6 #分隔符只能是一个字符!!!还是用awk靠谱
sed -nE 's/^.*"subProductCode":"([^"]+)".*/\1/p'

curl -i -H "Content-Type:application/json" -X POST --data '{"RequestId":"1082"}' http://127.0.0.1:5000/service
ab -n 5000 -c 500 -H 'token:xxxxxxxxxxxxx' -T application/json -p data.json http://127.0.0.1:8081/api/cfp/invite/query

mvn package -Dmaven.test.skip=true
docker images | awk '$1~/myapp/{print $3}' | xargs docker rmi
docker run -d --name poa-api -e APP_ENV=pre -v /data/filebeat/conf:/home/filebeat/conf
docker run -id --name=mysql_prod -p 3306:3306 -e MYSQL_ROOT_PASSWORD=xxxx docker.io/mysql:5.7.29

pip freeze >  requirements.txt
pip install pipreqs
pipreqs ./  --encoding=utf8

mvn deploy:deploy-file -Dfile=myapp-1.0.0-SNAPSHOT.jar -DgroupId=com.xxx.xxxx -DartifactId=myapp -Dversion=1.0.0-SNAPSHOT -Dpackaging=jar -Durl=https://mirrors.xxx.com/repository/maven/my_repo -DrepositoryId=my_repo

gentool --db=mysql --dsn="root:xxxxxxx@tcp(localhost:3306)/demo?charset=utf8&parseTime=True&loc=Local" -fieldWithTypeTag
##########common cmd#########

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
#for i in `cat $FILE` # 逐行读取(注意每行中间不能有空格，默认IFS按照空格分割)
#for ((i=1; i<=100; i++)) 
#for i in {1..100}
for i in `echo "10 11"`
do
  echo "$i"
done
# ip.txt: 10.1.1.11,root,123
IFS=","
while read ip user pass
do
    echo "$ip--$user--$pass"
done < ip.txt

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
