# Download test butler
curl -f -o ~/test-butler-2.2.1.apk https://repo1.maven.org/maven2/com/linkedin/testbutler/test-butler-app/2.2.1/test-butler-app-2.2.1.apk

# Install test butler
adb install ~/test-butler-2.2.1.apk

# Launch the test butler background service!!!
adb shell am startservice com.linkedin.android.testbutler/com.linkedin.android.testbutler.ButlerService

# Check that the service is indeed running... Expected output is:
# system        9448  5306 4866736  96372 0                   0 S com.linkedin.android.testbutler
adb shell ps | grep butler