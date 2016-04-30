Hadoop-project
==============
**Hadoop Sample question**   

 Description:  
 	Find the max elem globally from all arrays,    
  	array1 = [2,3...655], length = 300  
  		array2 = [4,6,6,466], length = 300  
  			...
  				array9 ...  
 
 			
**1. Source file**  
FileName: 	 FindMaxValue.java  
Author:   	 Billy Zhang (billyzhang2010@gmail.com)  
Date:          	May 27, 2014  


**2. Input file: data.txt**
<br />
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



**3. Compile , run(Hadoop 1.2.3 Version)**
<br />  run test.sh 
<br /> Here is the commands in test.sh:

<pre><code>
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
hadoop fs -getmerge Output/* Output
cat Output
</code></pre>


**4 Result**
<br/> Max elem is :	901

  




