# Daylight saving testing

I haven't found an automatic way to change the time on the emulator to be able to run these tests automatically.

## Test: AM/PM wheel should have proper values

1. Set the timezone to GMT-07:00 Pacific Standard Time.
1. Set the time to 2020-11-01
1. Set 24h setting to false. (AM/PM=true)
1. Go to time or datetime picker
1. Expect: Both AM and PM being in visible in AM/PM wheel.

## Test: Hour wheel should have proper values

1. Set the timezone to GMT-07:00 Pacific Standard Time.
1. Set the time to 2020-10-31 8:00 PM
1. Set 24h setting to false. (AM/PM=true)
1. Go to time or datetime picker.
1. Expect: All hours to exist on hour wheel and no hour missing.
