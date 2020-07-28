const expectDate = async date => {
  await expect(element(by.id('dateOutput'))).toHaveText(date)
}

const expectDateString = async date => {
  await expect(element(by.id('dateStringOutput'))).toHaveText(date)
}

const reset = async () => {
  await element(by.id('props/scroll')).tap()
  await element(by.id('reset')).tap()
}

const scrollWheel = async (index, times) => {
  await element(by.id('props/scroll')).tap()
  await element(by.id('wheelIndex')).replaceText(`${index}`)
  await element(by.id('scrollTimes')).replaceText(`${times}`)
  await element(by.id('doScroll')).tap()
}

const changeProp = name => async value => {
  await element(by.id('propName')).replaceText(name)
  await element(by.id('propValue')).replaceText(`${value}`)
  await element(by.id('changeProp')).tap()
}

const scrollWheelWithIndexAndExpectDate = async (index, expectedDate) => {
  await scrollWheel(index, 1)
  await expectDate(expectedDate)
  await reset()
}

exports.setDate = changeProp('date')
exports.setLocale = changeProp('locale')
exports.setMinimumDate = changeProp('minimumDate')
exports.setMaximumDate = changeProp('maximumDate')
exports.setMinuteInterval = changeProp('minuteInterval')
exports.setTimeZoneOffsetInMinutes = changeProp('timeZoneOffsetInMinutes')
exports.setMode = changeProp('mode')
exports.scrollWheel = scrollWheel
exports.expectDate = expectDate
exports.expectDateString = expectDateString
exports.scrollWheelWithIndexAndExpectDate = scrollWheelWithIndexAndExpectDate
exports.reset = reset
