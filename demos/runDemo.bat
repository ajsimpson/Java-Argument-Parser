@echo off
javac -cp .;../build/libs/Operation_Make_A_Better_Argument_Parser_Than_Everyone_Else-1.0.jar VolumeCalculator.java
java -cp .;../build/libs/Operation_Make_A_Better_Argument_Parser_Than_Everyone_Else-1.0.jar VolumeCalculator 7 5 2 --color blue -a 99 --weight 209.1
pause