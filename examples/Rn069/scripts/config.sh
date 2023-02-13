
# Attributes in .ini file:
# hw.lcd.density=440
# hw.lcd.height=2280
# hw.lcd.width=1080

# OR, it you need an automated script - they can be simply appended to the existing file:

path=~/.android/avd/Pixel_4_API_29.avd/config.ini


echo "hw.lcd.density=440" >> $path
echo "hw.lcd.height=2280" >> $path
echo "hw.lcd.width=1080" >> $path
echo "vm.heapSize=576" >> $path
echo "hw.ramSize=2048" >> $path


