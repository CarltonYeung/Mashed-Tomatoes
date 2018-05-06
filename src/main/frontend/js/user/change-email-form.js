const $ = require('jquery');
const _ = require('lodash');
const alert = require('../alert');

module.exports.deps = [
    '#change-email-form'
];

module.exports.init = () => {
    alert.init();

    $('#change-email-form').submit(evt => {
        const data = {
            newEmail: $('#email').val(),
            password: $('#password').val()
        };

        $.ajax(
            '/user/changeEmail',
            {
                method: "POST",
                data: JSON.stringify(data),
                contentType: "application/json",
                success: (body, status, xhr) => {
                    if (_.isEqual(xhr.status, 200)) {
                        alert.display('Email changed. Please verify it.', false);
                        setTimeout(() => {
                            window.location.href = '/';
                        }, 1000);
                    }
                },
                error: (xhr, status, err) => {
                    if (xhr.status != 500) {
                        alert.display(xhr.responseText, true);
                    } else if (xhr.status ==  500) {
                        alert.display("Something's wrong with our server. Please try again later", true);
                    }
                }
            });

        evt.preventDefault();
    });
};
