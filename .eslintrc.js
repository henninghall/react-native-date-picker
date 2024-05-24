/** @type {import('eslint').Linter.Config} */
module.exports = {
  root: true,
  extends: '@react-native',
  rules: {
    semi: [2, 'never'],
    curly: [2, 'multi-line'],
  },
}
