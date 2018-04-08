const path = require('path');
const webpack = require('webpack');

const srcJSMain = 'js/main.js';
const distJS = '../resources/static/js';
module.exports = {
  entry: srcJSMain,
  output: {
    path: path.resolve(__dirname, distJS),
    filename: "bundle.js"
  },
  devtool: 'source-map',
  plugins: [
    new webpack.DefinePlugin({
      'process.env.USE_MOCK_AJAX': JSON.stringify(process.env.USE_MOCK_AJAX || false)
    })
  ]
};