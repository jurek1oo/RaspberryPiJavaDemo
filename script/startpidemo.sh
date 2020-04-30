#!/bin/bash
export distdir=/home/pi/pidemo/dist
echo "   Start PiDemo process..."
/usr/bin/java -Dlog4j.configurationFile="$distdir/log4j2My.xml" -cp $distdir/pidemo-$1.jar inventionsource.com.au.pidemo.PiDemo
