const $ = require('jquery');
const _ = require('lodash');
const urlBuilder = require('../url-builder');
const alert = require('../alert');

module.exports.deps = [
  '#login-form'
];

module.exports.init = () => {
  alert.init();

  $('#login-form').submit(evt => {
    evt.preventDefault();
    const emailSelector = $('#login-email');
    const passwordSelector = $('#login-password');
    const data = {
      email: emailSelector.val(),
      password: passwordSelector.val()
    };

    $.ajax(
      urlBuilder.buildLogin(),
      {
        method: "POST",
        data: JSON.stringify(data),
        contentType: "application/json",
        success: (body, status, xhr) => {
          if (_.isEqual(xhr.status, 200)) {
            alert.display('Welcome!', false);
            setTimeout(() => {
              window.location.href = '/user/me';
            }, 1000);
          }
        },
        error: (xhr, status, err) => {
          if (xhr.status == 401) {
            alert.display(xhr.responseText, true);
          } else if (xhr.status ==  500) {
            alert.display("Something's wrong with our server. Please try again later", true);
          }
        }
      });

  });
};