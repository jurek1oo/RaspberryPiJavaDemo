#!/bin/bash
if [ -z "$1" ]
then
  read -p "Enter process name : " process_name
else
  process_name=$1
fi
if ps -ef | grep -v grep | grep -v killprocess.sh | grep $process_name
then
   ps -ef | grep -v grep | grep -v killprocess.sh | grep $process_name  | awk '{print $2}' | xargs kill -9 $2 ;
   echo "Is still running: ";
   ps -ef | grep -v grep | grep -v killprocess.sh | grep $process_name;
else
   echo "Nothing to kill.";
fi
