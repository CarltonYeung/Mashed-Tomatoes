const $ = require('jquery');
const _ = require('lodash');
const urlBuilder = require('../url-builder');

module.exports.deps = [
  '#logout-btn',
];

module.exports.init = () => {
  $('#logout-btn').on('click', () => {
    $.ajax(
      urlBuilder.buildLogout(),
      {
        method: "POST",
        success: res => {
          window.location.href = "/";
        },
        error: res => {
          console.error(res.status);
        }
      });
  });
};