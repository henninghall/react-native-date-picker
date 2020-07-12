const { scrollWheel, expectDate, setMaximumDate, setMode } = require('../utils')

const initialDate = new Date(2000, 0, 1, 0, 0)
const secondOfJanuary = new Date(2000, 0, 2, 0, 0)
const secondOfJanuary2001 = new Date(2001, 0, 2, 0, 0)

describe('Maximum date', () => {
  before(async () => {
    await device.reloadReactNative()
    await element(by.text('Advanced')).tap()
  })

  describe('cannot pass max date - datetime mode', () => {
    before(async () => {
      await setMode('datetime')
      await setMaximumDate(initialDate)
    })

    it('day wheel', async () => {
      await scrollWheel(0, 2)
      await expectDate('2000-01-01 00:00:00')
    })

    it('hour wheel', async () => {
      await scrollWheel(1, 1)
      await expectDate('2000-01-01 00:00:00')
    })

    it('minute wheel', async () => {
      await scrollWheel(2, 1)
      await expectDate('2000-01-01 00:00:00')
    })
  })

  describe('cannot pass max date - date mode', () => {
    before(async () => {
      await setMode('date')
      await setMaximumDate(initialDate)
    })

    it('month wheel', async () => {
      await scrollWheel(0, 1)
      await expectDate('2000-01-01 00:00:00')
    })

    it('date wheel', async () => {
      await scrollWheel(1, 1)
      await expectDate('2000-01-01 00:00:00')
    })

    it('year wheel', async () => {
      await scrollWheel(2, 1)
      await expectDate('2000-01-01 00:00:00')
    })
  })

  describe('overshooting max date', () => {
    before(async () => {
      await setMaximumDate(secondOfJanuary)
    })

    it('day wheel should not be possible to overshoot since it is not wrapping (no invalid dates exists)', async () => {
      await setMode('datetime')
      await scrollWheel(0, 1)
      await expectDate('2000-01-02 00:00:00')
      await scrollWheel(0, 1)
      await expectDate('2000-01-02 00:00:00')
    })

    describe('date mode', () => {
      before(async () => {
        await setMode('date')
        await setMaximumDate(secondOfJanuary)
      })

      it('overshooting month wheel should set all other wheels to maximum possible date', async () => {
        await scrollWheel(0, 1)
        await expectDate('2000-01-02 00:00:00')
      })

      it('overshooting date wheel should reverse to highest possible date', async () => {
        await scrollWheel(1, 5)
        await expectDate('2000-01-02 00:00:00')
      })

      it('overshooting year wheel should set all other wheels to maximum possible date', async () => {
        await setMaximumDate(secondOfJanuary2001)
        await scrollWheel(0, 1) // set month to feb
        await scrollWheel(2, 1)
        await expectDate('2001-01-02 00:00:00')
      })
    })

    describe('time mode', () => {
      before(async () => {
        await setMode('time')
        await setMaximumDate(initialDate)
      })

      it('overshooting hour wheel should reverse to highest possible time', async () => {
        await scrollWheel(0, 5)
        await expectDate('2000-01-01 00:00:00')
      })

      it('overshooting minute wheel should reverse to highest possible time', async () => {
        await scrollWheel(1, 5)
        await expectDate('2000-01-01 00:00:00')
      })

      it('overshooting am/pm wheel should reverse to highest possible time', async () => {
        await scrollWheel(2, 1)
        await expectDate('2000-01-01 00:00:00')
      })
    })
  })
})
