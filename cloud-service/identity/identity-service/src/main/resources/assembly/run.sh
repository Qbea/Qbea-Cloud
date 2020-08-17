#!/bin/sh
function runShell(){
    DIR=`dirname $0`
    cd $DIR
    nohup java -noverify -Xms256m -Xmx512m -Dloader.path=libs   -jar `ls | grep jar` > /dev/null 2>error.log &
    echo $!> tpid
    echo Starting...
    echo please use tailf logs/*.log to check success or not.  
}
if [ ! -f "tpid" ];
then
   runShell
else
    tpid=$(cat "tpid")
    running=$(ps -ef |awk '{print $2}'|grep -w $tpid)
    if [ "$running" ];
    then
        echo "Process(pid:$tpid) is already running."
    else
        runShell
    fi
fi
