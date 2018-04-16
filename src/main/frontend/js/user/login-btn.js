const $ = require('jquery');
const _ = require('lodash');
const urlBuilder = require('../url-builder');

module.exports.deps = [
  '#login-form'
];

module.exports.init = () => {
  $('#login-form').submit(evt => {
    const emailSelector = $('#login-email');
    const passwordSelector = $('#login-password');
    const data = {
      email: emailSelector.val(),
      password: passwordSelector.val()
    };

    console.log(data);
    $.ajax(
      urlBuilder.buildLogin(),
      {
        method: "POST",
        data: JSON.stringify(data),
        contentType: "application/json",
        success: res => {
          console.log('...redirecting...');
          window.location = '/';
        },
        error: res => {
          console.error('...failed...');
          console.error(res.status);
        }
      });

    evt.preventDefault();
  });
};