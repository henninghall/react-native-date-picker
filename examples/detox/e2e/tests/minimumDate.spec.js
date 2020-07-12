const {
  setDate,
  scrollWheel,
  expectDate,
  setMinimumDate,
  setMode,
} = require('../utils')

const oneMinuteBeforeJanuary2 = new Date(2000, 0, 1, 23, 59, 0)

describe('Minimum date', () => {
  before(async () => {
    await device.reloadReactNative()
    await element(by.text('Advanced')).tap()
  })

  beforeEach(async () => {
    await setDate(oneMinuteBeforeJanuary2.toISOString())
  })

  describe('cannot pass min date - mode: ', () => {
    describe('datetime', () => {
      before(async () => {
        await setMode('datetime')
        await setMinimumDate(oneMinuteBeforeJanuary2)
      })

      it('day wheel', async () => {
        await scrollWheel(0, -2)
        await expectDate('2000-01-01 23:59:00')
      })

      it('hour wheel', async () => {
        await scrollWheel(1, -1)
        await expectDate('2000-01-01 23:59:00')
      })

      it('minute wheel', async () => {
        await scrollWheel(2, -1)
        await expectDate('2000-01-01 23:59:00')
      })
    })

    describe('date', () => {
      before(async () => {
        await setMode('date')
        await setMinimumDate(oneMinuteBeforeJanuary2)
      })

      it('month wheel', async () => {
        await scrollWheel(0, -1)
        await expectDate('2000-01-06 00:00:00')
      })

      it('date wheel', async () => {
        await scrollWheel(1, -1)
        await expectDate('2000-01-06 00:00:00')
      })

      it('year wheel', async () => {
        await scrollWheel(2, -1)
        await expectDate('2000-01-01 23:59:00')
      })
    })
  })

  describe('overshooting min date - mode:', () => {
    before(async () => {
      await setMinimumDate(oneMinuteBeforeJanuary2)
    })

    describe('datetime', () => {
      before(async () => {
        await setMode('datetime')
      })

      it('day wheel should not be possible to overshoot since it is not wrapping (no invalid dates exists)', async () => {
        await scrollWheel(0, -1)
        await expectDate('2000-01-01 23:59:00')
        await scrollWheel(0, -1)
        await expectDate('2000-01-01 23:59:00')
      })
    })

    describe('date', () => {
      before(async () => {
        await setMode('date')
      })

      it('overshooting month wheel should set all other wheels to maximum possible date', async () => {
        await scrollWheel(0, -1)
        await expectDate('2000-01-06 00:00:00')
      })

      it('overshooting date wheel should reverse to maximum possible date', async () => {
        await scrollWheel(1, -5)
        await expectDate('2000-01-06 00:00:00')
      })
    })

    describe('time mode', () => {
      before(async () => {
        await setMode('time')
        await setMinimumDate(oneMinuteBeforeJanuary2)
      })

      it('overshooting hour wheel should reverse to minimum possible time', async () => {
        await scrollWheel(0, -5)
        await expectDate('2000-01-01 23:59:00')
      })

      it('overshooting minute wheel should reverse to minimum possible time', async () => {
        await scrollWheel(1, -5)
        await expectDate('2000-01-01 23:59:00')
      })

      it('overshooting am/pm wheel should reverse to minimum possible time', async () => {
        await scrollWheel(2, -1)
        await expectDate('2000-01-01 23:59:00')
      })
    })
  })
})
