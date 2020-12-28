const { scrollWheel, expectDate, setDate, setMode, setMaximumDate, setMinimumDate } = require('../utils')

const scrollDays = (days) => scrollWheel(1, days) 

describe('Invalid dates', () => {
  before(async () => {
    await device.reloadReactNative()
    await element(by.text('Advanced')).tap()
    await setMinimumDate(undefined)
    await setMaximumDate(undefined)
    await setMode('date')
  })


  it('scrolls back to last valid date', async () => {
    await setDate(new Date("2001-02-28 00:00"))
    await scrollDays(1)
    await expectDate('2001-02-28 00:00:00')
  })

  it('scrolls back after scrolling multiple dates', async () => {
    await setDate(new Date("2001-02-27 00:00"))
    await scrollDays(2)
    await expectDate('2001-02-28 00:00:00')
  })

  it('not scrolling back on unusual valid dates', async () => {
    await setDate(new Date("2000-02-28 00:00"))
    await scrollDays(1)
    await expectDate('2000-02-29 00:00:00')
  })
  
  it('not scrolling back after scrolling past invalid dates', async () => {
    await setDate(new Date("2001-02-28 00:00"))
    await scrollDays(4)
    await expectDate('2001-02-01 00:00:00')
  })
  
  it('works on months with 30 days', async () => {
    await setDate(new Date("2001-04-30 00:00"))
    await scrollDays(1)
    await expectDate('2001-04-30 00:00:00')
  })

  it('works on months with 31 days', async () => {
    await setDate(new Date("2001-05-30 00:00"))
    await scrollDays(1)
    await expectDate('2001-05-31 00:00:00')
  })


})
