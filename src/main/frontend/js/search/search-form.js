const $ = require('jquery');
const _ = require('lodash');
const urlBuilder = require('../url-builder');

module.exports.deps = [
  '#search-form',
];

module.exports.init = () => {
  $('#search-form').submit(evt => {
    const inputSelector = $('#search-input');
    let inputValue = inputSelector.val();
    inputValue = _.trim(inputValue);
    inputValue = _.replace(inputValue, /[^0-9a-z\s]/gi, '');
    const searchValue = _.replace(inputValue, /\s+/g, '+');
    console.log(searchValue);
    window.location.href = urlBuilder.buildSearch(searchValue);
    evt.preventDefault();
  });
};