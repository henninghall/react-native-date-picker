const { scrollWheel, expectDate } = require('../../utils')

describe('Hour wheel', () => {
  before(async () => {
    await device.reloadReactNative()
    await element(by.text('Advanced')).tap()
  })

  it('should have 24 hours', async () => {
    await scroll3HoursAndExpect('2000-01-01 03:00:00')
    await scroll3HoursAndExpect('2000-01-01 06:00:00')
    await scroll3HoursAndExpect('2000-01-01 09:00:00')
    await scroll3HoursAndExpect('2000-01-01 12:00:00')
    await scroll3HoursAndExpect('2000-01-01 15:00:00')
    await scroll3HoursAndExpect('2000-01-01 18:00:00')
    await scroll3HoursAndExpect('2000-01-01 21:00:00')
    await scroll3HoursAndExpect('2000-01-01 00:00:00')
  })

  const scroll3HoursAndExpect = async date => {
    await scrollWheel(1, 3)
    await expectDate(date)
  }
})
