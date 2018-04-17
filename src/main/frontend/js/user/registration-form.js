const $ = require('jquery');
const _ = require('lodash');
const urlBuilder = require('../url-builder');

module.exports.deps = [
  '#registration-form',
  '#alert-danger',
  '#alert-success'
];

module.exports.init = () => {
  $('#alert-success').on('close.bs.alert', evt => {
    evt.preventDefault();
    $(evt.currentTarget).prop('hidden', true);
  });

  $('#alert-danger').on('close.bs.alert', evt => {
    evt.preventDefault();
    $(evt.currentTarget).prop('hidden', true);
  });

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
            const alertSelector = $('#alert-success');
            alertSelector.find('strong').text('You have been registered!');
            alertSelector.prop('hidden', false);
            setTimeout(() => {
              window.location.href = '/';
            }, 1000);
          }

          console.error(`Unexpected success code: ${res.status}`);
        },
        error: (xhr, status, err) => {
          const alertSelector = $('#alert-danger');
          alertSelector.find('strong').text(xhr.responseText);
          alertSelector.prop('hidden', false);
        }
      });
    evt.preventDefault();
  });
};
