describe('Modal', () => {
  before(async () => {
    await device.reloadReactNative()
    await element(by.text('Modal')).tap()
  })

  it('can open and close modal', async () => {
    await element(by.id('openModal')).tap()
    await expect(element(by.id('day'))).toBeVisible()
    await expect(element(by.id('minutes'))).toBeVisible()
    await expect(element(by.id('hour'))).toBeVisible()
    await expect(element(by.id('ampm'))).toBeVisible()
    await expect(element(by.id('month'))).not.toExist()
    await expect(element(by.id('date'))).not.toExist()
    await expect(element(by.id('year'))).not.toExist()

    await element(by.text('CONFIRM')).tap()
    await expect(element(by.id('day'))).not.toBeVisible()
  })

  it('can open modal and press neutral button', async () => {
    await element(by.id('openModal')).tap()
    await expect(element(by.text('Neutral'))).toBeVisible()
    await element(by.text('Neutral')).tap()

    await element(by.text('Neutral button pressed!')).toBeVisible()
    await element(by.text('OK')).tap()
    await expect(element(by.id('day'))).not.toBeVisible()
  })
})
