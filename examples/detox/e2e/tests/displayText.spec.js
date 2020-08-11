const {
  setMode,
  setLocale,
  expectDateString,
  scrollWheel,
  setMinimumDate,
  setMaximumDate,
} = require('../utils')

describe('Display text', () => {
  before(async () => {
    await device.reloadReactNative()
    await element(by.text('Advanced')).tap()
  })

  describe('datetime', () => {
    it('vi-VN', async () => {
      await expectLocaleDateString('vi-VN', 'CN 2 thg 1' + '1200 SA ')
    })
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

    it('ja', async () => {
      await expectLocaleDateString('ja', '1月2日 日' + '1200 午前 ')
    })

    it('zh-CH', async () => {
      await expectLocaleDateString('zh-CH', '1月2日周日 上午 1200')
    })

    it('eu', async () => {
      await expectLocaleDateString('eu', 'urt. 2 ig.' + ' AM 1200')
    })
  })

  describe('date', () => {
    before(async () => {
      await device.reloadReactNative()
      await element(by.text('Advanced')).tap()
      await setMinimumDate(undefined)
      await setMaximumDate(undefined)
      await setMode('date')
    })

    it('en-US', async () => {
      await expectLocaleDateString('en-US', 'February' + '1' + '2000')
    })

    it('pt-BR', async () => {
      await expectLocaleDateString('pt-BR', '2janeiro' + '2000')
    })

    it('sv-SE', async () => {
      await expectLocaleDateString('sv-SE', '2' + 'januari' + '2000')
    })

    it('ko', async () => {
      await expectLocaleDateString('ko', '2001년' + '1월' + '1일')
    })

    it('ja', async () => {
      await expectLocaleDateString('ja', '2001年' + '1月' + '1日')
    })

    it('zh-CH', async () => {
      await expectLocaleDateString('zh-CH', '2001年' + '一月' + '1日')
    })

    it('eu', async () => {
      await expectLocaleDateString('eu', '2001' + 'urtarrila' + '1')
    })
  })

  const expectLocaleDateString = async (locale, dateString) => {
    await setLocale(locale)
    await scrollWheel(0, 1)
    await expectDateString(dateString)
  }
})
