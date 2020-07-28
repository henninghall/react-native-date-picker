const {
  setTimeZoneOffsetInMinutes,
  expectDate,
  scrollWheel,
  expectDateString,
  setMaximumDate,
  setDate,
} = require('../utils')

const scrollMinuteWheel = () => scrollWheel(2, 1)

// I haven't found a way to change the timezone on the emulator to be able to run these tests.
// Until possible, run these tests locally when needed with Europe/Stockholm timezone.
describe.skip('Timezone offset', () => {
  before(async () => {
    await device.reloadReactNative()
    await element(by.text('Advanced')).tap()
  })

  it('undefined (default)', async () => {
    await setTimeZoneOffsetInMinutes(undefined)
    await scrollMinuteWheel()
    await expectDate('2000-01-01 00:01:00')
    await expectDateString('Sat Jan 11201 AM ')
  })

  it('0', async () => {
    await setTimeZoneOffsetInMinutes(0)
    await scrollMinuteWheel()
    await expectDate('2000-01-01 00:01:00')
    await expectDateString('Fri Dec 311101 PM ')
  })

  it('180', async () => {
    await setTimeZoneOffsetInMinutes(180)
    await scrollMinuteWheel()
    await expectDate('2000-01-01 00:01:00')
    await expectDateString('Sat Jan 1201 AM ')
  })

  it('-180', async () => {
    await setTimeZoneOffsetInMinutes(-180)
    await scrollMinuteWheel()
    await expectDate('2000-01-01 00:01:00')
    await expectDateString('Fri Dec 31801 PM ')
  })

  describe('daylight saving', () => {
    const firstOfJuly = new Date(2000, 6, 1, 0, 0)
    const firstOfJune = new Date(2000, 5, 1, 0, 0)

    before(async () => {
      await setMaximumDate(firstOfJuly)
      await setDate(firstOfJune)
    })

    it('undefined', async () => {
      await setDate(firstOfJune)
      await setTimeZoneOffsetInMinutes(undefined)
      await scrollMinuteWheel()
      await expectDate('2000-06-01 00:01:00')
      await expectDateString('Thu Jun 11201 AM ')
    })

    it('0', async () => {
      await setDate(firstOfJune)
      await setTimeZoneOffsetInMinutes(0)
      await scrollMinuteWheel()
      await expectDate('2000-06-01 00:01:00')
      await expectDateString('Wed May 311001 PM ')
    })

    it('180', async () => {
      await setDate(firstOfJune)
      await setTimeZoneOffsetInMinutes(180)
      await scrollMinuteWheel()
      await expectDate('2000-06-01 00:01:00')
      await expectDateString('Thu Jun 1101 AM ')
    })
  })
})
