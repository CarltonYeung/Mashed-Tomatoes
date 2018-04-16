const $ = require('jquery');
const _ = require('lodash');
const urlBuilder = require('../url-builder');

module.exports.deps = [
  '#search-input',
  '#search-btn'
];

module.exports.init = () => {
  $('#search-btn').on('click', evt => {
    const inputSelector = $('#search-input');
    let inputValue = inputSelector.val();
    inputValue = _.replace(inputValue, /\s+/g, ' ');
    inputValue = _.replace(inputValue, /[^0-9a-z\s]/gi, '');
    const searchValue = _.replace(inputValue, /\s/g, '+');
    console.log(searchValue);
    window.location.href = urlBuilder.buildSearch(searchValue);
  });
};