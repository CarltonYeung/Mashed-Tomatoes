const $ = require('jquery');
const _ = require('lodash');
const urlBuilder = require('../url-builder');
const alert = require('../alert');

module.exports.deps = [
  '#registration-form',
  '#alert-danger',
  '#alert-success'
];

module.exports.init = () => {
  alert.init();
  $('#registration-form').submit(evt => {
    evt.preventDefault();
    const displayNameSelector = $('#register-display-name');
    const emailSelector = $('#register-email');
    const passwordSelector = $('#register-password');
    const recaptchaResponse = $('[name=g-recaptcha-response]').val();

    const data = {
      displayName: displayNameSelector.val(),
      email: emailSelector.val(),
      password: passwordSelector.val(),
      recaptchaResponse
    };

    $.ajax(
      urlBuilder.buildRegister(),
      {
        method: "POST",
        data: JSON.stringify(data),
        contentType: "application/json",
        success: (body, status, xhr) => {
          console.log(xhr);
          console.log(body);
          console.log(status);
          if (_.isEqual(xhr.status, 201)) {
            alert.display('You have been registered!', false);
            setTimeout(() => {
              window.location.href = '/';
            }, 1000);
          }

          console.error(`Unexpected success code: ${res.status}`);
        },
        error: (xhr, status, err) => {
          if (xhr.status == 400) {
            alert.display(xhr.responseText, true);
          } else if (xhr.status == 500) {
            alert.display("Something's wrong with our server. Please try again later", true);
          }
        }
      });
  });
};
