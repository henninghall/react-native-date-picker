const { scrollWheel, expectDate, setMinuteInterval } = require('../utils')

const scrollMinuteWheel = () => scrollWheel(2, 1)

describe('Minute interval', () => {
  before(async () => {
    await device.reloadReactNative()
    await element(by.text('Advanced')).tap()
  })

  it('1 minute (default)', async () => {
    await setMinuteInterval(1)
    await scrollMinuteWheel()
    await expectDate('2000-01-01 00:01:00')
  })

  it('5 minutes', async () => {
    await setMinuteInterval(5)
    await scrollMinuteWheel()
    await expectDate('2000-01-01 00:05:00')
  })

  it('15 minutes', async () => {
    await setMinuteInterval(15)
    await scrollMinuteWheel()
    await expectDate('2000-01-01 00:15:00')
    await scrollMinuteWheel()
    await expectDate('2000-01-01 00:30:00')
    await scrollMinuteWheel()
    await expectDate('2000-01-01 00:45:00')
  })
})
