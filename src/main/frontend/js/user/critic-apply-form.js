const $ = require('jquery');
const _ = require('lodash');
const alert = require('../alert');

module.exports.deps = [
  '#critc-apply-form'
];

module.exports.init = () => {
  alert.init();

  $('#critc-apply-form').submit(evt => {
    evt.preventDefault();
    const emailSelector = $('#login-email');
    const passwordSelector = $('#login-password');
    const data = {
      firstName: $('#first-name').val(),
      lastName: $('#last-name').val(),
      body: $('#reason').val()
    };
    $.ajax(
      "/user/criticApplication/apply",
      {
        method: "POST",
        data: JSON.stringify(data),
        contentType: "application/json",
        success: (body, status, xhr) => {
          if (_.isEqual(xhr.status, 200)) {
            alert.display('Your application has been submitted', false);
            window.location.href = '/user/me';
          }
        },
        error: (xhr, status, err) => {
          if (xhr.status !== 500) {
            alert.display(xhr.responseText, true);
          } else if (xhr.status ===  500) {
            alert.display("Something's wrong with our server. Please try again later", true);
          }
        }
      });
  });
};