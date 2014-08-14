cd %~dp0
java -jar DownloadAndExtract.jar
gradlew.bat setupDecompWorkspace %~1
