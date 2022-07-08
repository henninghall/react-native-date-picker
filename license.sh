yes | ~/Library/Android/sdk/cmdline-tools/latest/bin/sdkmanager --licenses
echo "✨ Licenses"
yes | ~/Library/Android/sdk/cmdline-tools/latest/bin/sdkmanager --install emulator
echo "✨ Installed emulator"
yes | ~/Library/Android/sdk/cmdline-tools/latest/bin/sdkmanager --install "system-images;android-29;default;x86_64"
echo "✨ Installed image"
avdmanager --verbose create avd --force --name Pixel_4_API_29 --abi x86_64 --device "pixel" --package "system-images;android-29;default;x86_64"
echo "✨ Created AVD" 