#!/bin/bash
 rm -rf Output*
 rm -rf FindMaxValue.jar

javac -classpath  ${HADOOP_PREFIX}/hadoop-core-${HADOOP_VERSION}.jar:${HADOOP_PREFIX}/lib/commons-cli-1.2.jar -d fd_Classes ./*.java
jar -cvf ./FindMaxValue.jar -C fd_Classes/ .
hadoop fs -rmr Input
hadoop fs -mkdir Input
hadoop fs -copyFromLocal data.txt Input
hadoop fs -rmr Output*
hadoop jar FindMaxValue.jar FindMaxValue Input  Output
hadoop fs -getmerge Output/ .
cat Output
