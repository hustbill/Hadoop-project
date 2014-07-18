

1. Source file
/*************************************************************
 * FileName: 	 FindMaxValue.java
 * Author:   	 Billy Zhang (billyzhang2010@gmail.com)
 * Date:          	May 27, 2014
 * Version:			0.2
 * Description: 
 * 	array1 = [2,3...655], length = 300
 * 		array2 = [4,6,6,466], length = 300
 * 			...
 * 				array9 ...
 *
 * 				Find the max elem globally from all array.
 * ****************************************************************/


2. Input file: data.txt
12
36
90
21
45
410
78
69
231
345
901
821
82
77
38
129
561


3. Compile , run(Hadoop 1.2.3 Version)
  run test.sh by command
  *****************************************************
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
******************************************************

3 Result
Max elem is :	901