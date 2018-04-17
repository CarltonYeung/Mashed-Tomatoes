const $ = require('jquery');
const _ = require('lodash');
const urlBuilder = require('../url-builder');

module.exports.deps = [
  '#registration-form'
];

module.exports.init = () => {
  $('#registration-form').submit(evt => {
    const displayNameSelector = $('#register-display-name');
    const emailSelector = $('#register-email');
    const passwordSelector = $('#register-password');

    const data = {
      displayName: displayNameSelector.val(),
      email: emailSelector.val(),
      password: passwordSelector.val()
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
            window.location.href = '/';
          }

          console.error(`Unexpected success code: ${res.status}`);
        },
        error: (xhr, status, err) => {
          console.error(xhr.responseText);
        }
      });
    evt.preventDefault();
  });
};
