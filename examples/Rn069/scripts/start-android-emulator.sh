currentDir=$( cd "$(dirname "${BASH_SOURCE[0]}")" ; pwd -P )
emulatorName=$(bash ${currentDir}/available-android-emulator.sh)
echo "Starting $emulatorName ..."
~/Library/Android/sdk/emulator/emulator -avd $emulatorName &
