const detox = require('detox')
const config = require('../package.json').detox
const adapter = require('detox/runners/mocha/adapter')
const { reset } = require('./utils')

before(async () => {
  await detox.init(config)
})

beforeEach(async function () {
  try {
    await reset()
  } catch (e) {
    // some tests cannot reset, it's ok.
  }
  await adapter.beforeEach(this)
})

afterEach(async function () {
  await adapter.afterEach(this)
})

after(async () => {
  await detox.cleanup()
})
