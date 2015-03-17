call gradle clean build jacocoTestReport
explorer "file:///%~dp0build/reports/tests/index.html"
explorer "file:///%~dp0build/reports/jacoco/test/html/index.html"
//echo "Running acceptance tests..."
//call runacceptance.bat
//explorer "file:///%~dp0acceptance/report.html"