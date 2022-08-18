# Gracefully shut the emulator down
killall qemu-system-x86_64

# (or, if running headless)
killall qemu-system-x86_64-headless

### Side-note: If you wish to brutally shut the emulator down without having
### anything saved - for whatever reason, you can kill using the SIGKILL signal:
killall -9 qemu-system-x86_64