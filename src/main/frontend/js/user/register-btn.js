const $ = require('jquery');
const _ = require('lodash');
const urlBuilder = require('../url-builder');

module.exports.deps = [
  '#register-btn'
];

module.exports.init = () => {
  $('#registration-form').submit(function (e) {
    const displayNameSelector = $('#register-display-name');
    console.log(displayNameSelector.val());
    const emailSelector = $('#register-email');
    console.log(emailSelector.val());
    const passwordSelector = $('#register-password');
    console.log(passwordSelector);
    console.log(passwordSelector.val());

    const data = {
      displayName: displayNameSelector.val(),
      email: emailSelector.val(),
      password: passwordSelector.val()
    };

    console.log(data);
    
    $.ajax(
      urlBuilder.buildRegister(),
      {
        method: "POST",
        data: JSON.stringify(data),
        contentType: "application/json",
        success: res => {
          console.log('...redirecting...');
          window.location.href = '/';
        },
        error: res => {
          console.error(res.status);
        }
      });
    e.preventDefault();
  });
};
