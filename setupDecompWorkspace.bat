cd %~dp0
java -cp DownloadAndExtract.jar com.kneelawk.downloadandextract.Main
gradlew.bat setupDecompWorkspace %~1
pause
