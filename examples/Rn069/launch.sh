
# Launch the emulator!
~/Library/Android/sdk/tools/emulator -verbose -no-boot-anim @Pixel_4_API_29 &
# (or, if running on a headless machine)
~/Library/Android/sdk/tools/emulator -verbose -no-boot-anim -no-audio -no-window -gpu swiftshader_indirect @Pixel_4_API_29 &

# Wait for the emulator to boot up completely.
# This script is actually a bit naive. There are various suggestions out there
# as to make this more robust. One alternative is to utilize the "adb wait-for-device" command.
booted=0
while [ "$booted" != "1" ]
do
  echo "Waiting for emulator..."
  booted=`adb shell getprop dev.bootcomplete`
  sleep 1
done

# Wait for running processes to cool down.
# Run:
adb shell top
# And continuously inspect the refreshing output - wait for the idle
# percent indicator to settle down, as in line 4 (L4):
#