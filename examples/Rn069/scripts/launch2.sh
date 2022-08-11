# Enable on-screen indicators of taps, swipes and scroll actions.
adb shell settings put system show_touches 1
adb shell settings put system pointer_location 1

# (*OPTIONAL for Detox users*) Restart adb in root mode (i.e. allow root access to the emulator).
# Detox may need this in order to be able to install apps on the emulator in a way more
# stable than using "adb install path/to/app.apk".
# If you wish to use Detox and avoid that nevertheless, apply the "--force-adb-install" argument
# to your "detox test" commands.
# See https://github.com/wix/Detox/blob/master/docs/APIRef.DetoxCLI.md#test for more info.
adb root