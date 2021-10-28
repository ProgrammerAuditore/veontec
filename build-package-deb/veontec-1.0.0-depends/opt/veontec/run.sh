#!/bin/sh

# Add enviroment to Veontec
SFW_LIB="/usr/lib/veontec"
SFW_JAR="/opt/veontec/Veontec.jar"
PATH=$PATH:$SFW_LIB/bin

# Run Veontec Jar
$SFW_LIB/bin/java -Xrs -jar $SFW_JAR
