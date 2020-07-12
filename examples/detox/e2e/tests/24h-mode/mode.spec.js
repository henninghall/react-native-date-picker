const { setMode } = require('../../utils')

describe('Modes - 24h', () => {
  before(async () => {
    await device.reloadReactNative()
    await element(by.text('Advanced')).tap()
  })

  it('datetime', async () => {
    await setMode('datetime')

    await expect(element(by.id('day'))).toBeVisible()
    await expect(element(by.id('minutes'))).toBeVisible()
    await expect(element(by.id('hour'))).toBeVisible()

    await expect(element(by.id('ampm'))).toNotExist()
    await expect(element(by.id('month'))).toNotExist()
    await expect(element(by.id('date'))).toNotExist()
    await expect(element(by.id('year'))).toNotExist()
  })

  it('date', async () => {
    await setMode('date')

    await expect(element(by.id('month'))).toBeVisible()
    await expect(element(by.id('date'))).toBeVisible()
    await expect(element(by.id('year'))).toBeVisible()

    await expect(element(by.id('day'))).toNotExist()
    await expect(element(by.id('minutes'))).toNotExist()
    await expect(element(by.id('hour'))).toNotExist()
    await expect(element(by.id('ampm'))).toNotExist()
  })

  it('time', async () => {
    await setMode('time')

    await expect(element(by.id('minutes'))).toBeVisible()
    await expect(element(by.id('hour'))).toBeVisible()

    await expect(element(by.id('ampm'))).toNotExist()
    await expect(element(by.id('day'))).toNotExist()
    await expect(element(by.id('month'))).toNotExist()
    await expect(element(by.id('date'))).toNotExist()
    await expect(element(by.id('year'))).toNotExist()
  })
})
