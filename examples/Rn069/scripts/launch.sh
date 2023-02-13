
# Launch the emulator!
~/Library/Android/sdk/emulator/emulator -verbose -no-boot-anim @Pixel_4_API_29 &

# Wait for the emulator to boot up completely.
# This script is actually a bit naive. There are various suggestions out there
# as to make this more robust. One alternative is to utilize the "adb wait-for-device" command.
booted=0
while [ "$booted" != "1" ]
do
  echo "Waiting for emulator..."
  adb shell getprop dev.bootcomplete
  booted=`adb shell getprop dev.bootcomplete`
  sleep 1
done

