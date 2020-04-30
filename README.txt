# pidemo is Raspberry pi 3 java development project under GNU gpl-2.0 licence
--- https://opensource.org/licenses/gpl-2.0.php

You will be able to write your Java code on your laptop PC using your favorite IDE.
Using maven, you will compile, build executable jar, deploy and start the project on Raspberry pi.
You can do it from IDE by executing mave goal Install or from a terminal command line: mvn install.
Read on, if that is what you want to do.

------------ configuration ---------------------
Raspberry pi 3 with digital input/output pins connector and a development board.

The pidemo monitors one digital input pin connected to a hardware toggle switch
and two digital output pins connected to a green and yellow led lights.
Cirquit diagrams are provided here: ????????????????????????????

---------------------------------------------------------------------------
1. Raspberry configuration.
---------------------------------------------------------------------------
Install Raspbian on your pi. Help here:
https://projects.raspberrypi.org/en/projects/raspberry-pi-getting-started
More advancd users should check out (Debian):
https://forge.codesys.com/trg/raspberry-pi/wiki/Home/

Configure static ip address for your pi:
https://elinux.org/Configuring_a_Static_IP_address_on_your_Raspberry_Pi

Configure ssh / scp for your pi:
https://www.raspberrypi.org/documentation/remote-access/ssh/
or
https://magpi.raspberrypi.org/articles/ssh-remote-control-raspberry-pi

Install Java on your pi:
(OpenJDK version of Java easily obtainable through the Raspbian repository)
https://pimylifeup.com/raspberry-pi-java/

Once you have done all above, you can connect to your pi from laptop.

---------------------------------------------------------------------------
2. Linux Laptop
---------------------------------------------------------------------------
Not much to be done here. You should have already ssh/scp working, as well your Java.
Check Java version:
$> java -version
Install Java IDE of your choice. I have used InteliJ Idea Community 2020.1.
https://www.softpedia.com/get/Programming/Coding-languages-Compilers/IntelliJ-IDEA-Community-Edition.shtml



---------------------------------------------------------------------------
2. Windows 10 Laptop
---------------------------------------------------------------------------


---------------------------------------------------------------------------
1. Input pin Listener task handleGpioPinDigitalStateChangeEvent.
---------------------------------------------------------------------------
This event listener is monitoring digital input pin status changes.

---------------------------------------------------------------------------
2. Java Software
---------------------------------------------------------------------------

The project has been developed with Netbeans 8.2 on windows 10, and on Jetbrains Idea on Ubuntu 18.04 linux.
You can run the development as pc Windows or pc Linux.

Aleterntively, you do not have to use IDE to build and install this code on pi. Maven will do.

Download the project to your pc, and use maven command from the project root directory.

1. To build the jar file in the /target folder:

$> mvn package

2. To build and install on pi:

$> mvn install


Mind you, you need to modify pc2pi_linux.sh or pc2pi_win.tax files, 
depending on the operating system on your pc, and your public/private keys location.

Your pi needs to have ssh/scp installed.
Connect first time from development pc to your pi via ssh (or WinSCP) manually, to register the public key.

On your win pc you need to have WinSCP installed and private/public keys created. 
I would suggest, that you test your WinSCP connection to pi using win GUI first.
There are a number of links on Internet how to set it up 
e.g. https://winscp.net/eng/docs/public_key.

5. Raspberry pi

--------------------------------------------------------
Directory structure pidemo:
--------------------------------------------------------
/home/pi/pidemo/cleanupbefore.sh:
/home/pi/pidemo/startpidemo.sh:
--------------------------------------------------------
Inside pidemo/dist:
--------------------------------------------------------
/home/pi/pidemo/dist/log4j2.xml
/home/pi/pidemo/dist/pidemo-1.01-SNAPSHOT.jar
--------------------------------------------------------
Inside pidemo/log:
--------------------------------------------------------
/home/pi/pidemo/log/pidemo.log
--------------------------------------------------------

Good luck,

Jurek Kurianski jurek@inventionsource.com.au, Phuket Thailand, April 2020.

This code is avaiable under GNU GPL 2 licence.