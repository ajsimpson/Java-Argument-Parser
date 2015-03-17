cd acceptance
javac -cp .;..\build\classes\main ArgumentParserKeywords.java
java -cp .;..\build\classes\main;C:\RobotFramework\robotframework-2.8.7.jar org.robotframework.RobotFramework ArgumentParserTests.txt
cd ..