const { setLocale, expectDateString, scrollWheel } = require('../utils')

describe('Display text', () => {
  before(async () => {
    await device.reloadReactNative()
    await element(by.text('Advanced')).tap()
  })

  describe('datetime', () => {
    it('en-US', async () => {
      await expectLocaleDateString('en-US', 'Sun Jan 2' + '1200 AM ')
    })

    it('pt-BR', async () => {
      await expectLocaleDateString('pt-BR', 'dom 2 de jan' + '1200 AM ')
    })

    it('sv-SE', async () => {
      await expectLocaleDateString('sv-SE', 'sön 2 jan.' + '1200 fm ')
    })

    it('ru-RU', async () => {
      await expectLocaleDateString('ru-RU', 'вс 2 янв.' + '1200 AM ')
    })

    it('ko', async () => {
      await expectLocaleDateString('ko', '1월 2일 일 오전 1200')
    })

    it('zh-CH', async () => {
      await expectLocaleDateString('zh-CH', '1月2日周日 上午 1200')
    })
  })

  const expectLocaleDateString = async (locale, dateString) => {
    await setLocale(locale)
    await scrollWheel(0, 1)
    await expectDateString(dateString)
  }
})
