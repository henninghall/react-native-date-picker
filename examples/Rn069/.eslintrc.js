module.exports = {
  root: true,
  extends: '@react-native-community',
  plugins: ['detox'],
  overrides: [
    {
      files: ['**/*.spec.js', '**/*.spec.jsx', '*.js'],
      env: {
        'detox/detox': true,
        jest: true,
        'jest/globals': true,
      },
    },
  ],
};
