module.exports = {
  root: true,
  extends: '@react-native-community',
  plugins: [],
  overrides: [
    {
      files: ['**/*.spec.js', '**/*.spec.jsx', '*.js'],
      env: {
        jest: true,
        'jest/globals': true,
      },
    },
  ],
};
