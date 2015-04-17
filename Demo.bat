@echo off
cd demos
javac -cp .;../build/libs/Operation_Make_A_Better_Argument_Parser_Than_Everyone_Else-1.0.jar VolumeCalculator.java
echo "VolumeCalculator 7 --color blue 5 -a 99 2 --weight 209.1"
java -cp .;../build/libs/Operation_Make_A_Better_Argument_Parser_Than_Everyone_Else-1.0.jar VolumeCalculator 7 --color blue 5 -a 99 2 --weight 209.1
pause