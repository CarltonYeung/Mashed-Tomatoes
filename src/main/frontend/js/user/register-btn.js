const $ = require('jquery');
const _ = require('lodash');
const urlBuilder = require('../url-builder');

module.exports.deps = [
  '#register-btn'
];

module.exports.init = () => {
  $('#registration-form').submit(function (e) {
    const displayNameSelector = $('#input-register-display-name');
    const emailSelector = $('#input-register-email');
    const pwdSelector = $('#input-register-pwd');
    const displayName = displayNameSelector.val();
    const email = emailSelector.val();
    const password = pwdSelector.val();
    const data = {
      displayName, email, password
    };

    console.log(data);
    $.ajax(
      urlBuilder.buildRegister(),
      {
        method: "POST",
        data: JSON.stringify(data),
        contentType: "application/json",
        dataType: "application/json",
        success: res => {
          if (res.status == 204) {
            console.log('Register Success');
            window.location = '/';
          }
        },
        error: res => {
          console.error(res.status);
        }
      });
    e.preventDefault();
  });
};
