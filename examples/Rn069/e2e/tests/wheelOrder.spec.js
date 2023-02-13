const {
  scrollWheelWithIndexAndExpectDate,
  setMode,
  setLocale,
  setMaximumDate,
  init,
} = require('../utils');

describe('Wheel order', () => {
  beforeAll(async () => {
    await init();
    await element(by.text('Advanced')).tap();
    await setMaximumDate('undefined');
  });

  describe('datetime', () => {
    beforeAll(async () => {
      await setMode('datetime');
    });

    it('US', async () => {
      await setLocale('en-US');
      await scrollWheelWithIndexAndExpectDate(0, '2000-01-02 00:00:00');
      await scrollWheelWithIndexAndExpectDate(1, '2000-01-01 01:00:00');
      await scrollWheelWithIndexAndExpectDate(2, '2000-01-01 00:01:00');
      await scrollWheelWithIndexAndExpectDate(3, '2000-01-01 12:00:00');
    });

    it('Korean', async () => {
      await setLocale('ko-KR');
      await scrollWheelWithIndexAndExpectDate(0, '2000-01-02 00:00:00');
      await scrollWheelWithIndexAndExpectDate(1, '2000-01-01 12:00:00');
      await scrollWheelWithIndexAndExpectDate(2, '2000-01-01 01:00:00');
      await scrollWheelWithIndexAndExpectDate(3, '2000-01-01 00:01:00');
    });
  });

  describe('date', () => {
    beforeAll(async () => {
      await setMode('date');
    });

    it('US', async () => {
      await setLocale('en-US');
      await scrollWheelWithIndexAndExpectDate(0, '2000-02-01 00:00:00');
      await scrollWheelWithIndexAndExpectDate(1, '2000-01-02 00:00:00');
      await scrollWheelWithIndexAndExpectDate(2, '2001-01-01 00:00:00');
    });

    it('UK', async () => {
      await setLocale('en-GB');
      await scrollWheelWithIndexAndExpectDate(0, '2000-01-02 00:00:00');
      await scrollWheelWithIndexAndExpectDate(1, '2000-02-01 00:00:00');
      await scrollWheelWithIndexAndExpectDate(2, '2001-01-01 00:00:00');
    });

    it('Korean', async () => {
      await setLocale('ko-KR');
      await scrollWheelWithIndexAndExpectDate(0, '2001-01-01 00:00:00');
      await scrollWheelWithIndexAndExpectDate(1, '2000-02-01 00:00:00');
      await scrollWheelWithIndexAndExpectDate(2, '2000-01-02 00:00:00');
    });
  });

  describe('time', () => {
    beforeAll(async () => {
      await setMode('time');
    });

    it('US', async () => {
      await setLocale('en-US');
      await scrollWheelWithIndexAndExpectDate(0, '2000-01-01 01:00:00');
      await scrollWheelWithIndexAndExpectDate(1, '2000-01-01 00:01:00');
      await scrollWheelWithIndexAndExpectDate(2, '2000-01-01 12:00:00');
    });

    it('UK', async () => {
      await setLocale('en-GB');
      await scrollWheelWithIndexAndExpectDate(0, '2000-01-01 01:00:00');
      await scrollWheelWithIndexAndExpectDate(1, '2000-01-01 00:01:00');
      await scrollWheelWithIndexAndExpectDate(2, '2000-01-01 12:00:00');
    });

    it('Korean', async () => {
      await setLocale('ko-KR');
      await scrollWheelWithIndexAndExpectDate(0, '2000-01-01 12:00:00');
      await scrollWheelWithIndexAndExpectDate(1, '2000-01-01 01:00:00');
      await scrollWheelWithIndexAndExpectDate(2, '2000-01-01 00:01:00');
    });
  });
});
