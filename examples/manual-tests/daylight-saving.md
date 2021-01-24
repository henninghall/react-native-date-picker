# Daylight saving testing

I haven't found an automatic way to change the time on the emulator to be able to run these tests automatically.

## Test: Make sure AM/PM wheel is rendered properly

1. Set the timezone to GMT-08:00 Pacific Standard Time.
1. Set the time to 2020-11-01
1. Set 24h setting to false. (AM/PM=true)
1. Go to any picker
1. Expect: Both AM and PM being in visible in AM/PM wheel.
