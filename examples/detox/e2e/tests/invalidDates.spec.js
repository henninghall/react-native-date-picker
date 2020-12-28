const { scrollWheel, expectDate, setDate, setMode, setMaximumDate, setMinimumDate } = require('../utils')

describe.only('Invalid dates', () => {
  before(async () => {
    await device.reloadReactNative()
    await element(by.text('Advanced')).tap()
  })

  const february28th = new Date(2000, 1, 25, 0, 0)

  it('scrolls back to last valid date when hitting invalid date', async () => {
    await setMinimumDate(undefined)
    await setMaximumDate(undefined)
    await setMode('date')
    await setDate(february28th)
    await scrollWheel(1, 5)
    await expectDate('2000-02-28 00:00:00')
  })

})
