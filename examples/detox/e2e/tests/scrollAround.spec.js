const { scrollWheel, expectDate } = require('../utils')

describe('Scroll around', () => {
  before(async () => {
    await device.reloadReactNative()
    await element(by.text('Advanced')).tap()
  })

  it.skip('Hour wheel should scroll all way around and switch AM/PM when passing 12', async () => {
    await scroll3HoursAndExpect('2000-01-01 03:00:00')
    await scroll3HoursAndExpect('2000-01-01 06:00:00')
    await scroll3HoursAndExpect('2000-01-01 09:00:00')
    await scrollWheel(1, 2)
    await expectDate('2000-01-01 11:00:00')
    await scrollWheel(1, 2)
    await expectDate('2000-01-01 13:00:00')
    await scroll3HoursAndExpect('2000-01-01 16:00:00')
    await scroll3HoursAndExpect('2000-01-01 19:00:00')
    await scroll3HoursAndExpect('2000-01-01 22:00:00')
    await scroll3HoursAndExpect('2000-01-01 01:00:00')
  })

  it('Minute wheel should be possible to scroll all way around', async () => {
    await scrollWheel(2, 55)
    await expectDate('2000-01-01 00:55:00')
    await scrollWheel(2, 10)
    await expectDate('2000-01-01 00:05:00')
  })

  it('Day wheel should change year when passing new year', async () => {
    await scrollWheel(0, -1)
    await expectDate('1999-12-31 00:00:00')
    await scrollWheel(0, 1)
    await expectDate('2000-01-01 00:00:00')
  })

  const scroll3HoursAndExpect = async date => {
    await scrollWheel(1, 3)
    await expectDate(date)
  }
})
