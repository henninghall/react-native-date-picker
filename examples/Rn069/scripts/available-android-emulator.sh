emulators=$(emulator -list-avds )

for emulator in $emulators
do
    # Prefer non TV emulators
    if [[ "$emulator" != *"TV"* ]]; then
        EMULATOR=$emulator
    fi
done

echo $EMULATOR