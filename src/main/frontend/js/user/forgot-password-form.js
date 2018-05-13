const $ = require('jquery');
const _ = require('lodash');
const alert = require('../alert');

module.exports.deps = [
    '#forgot-password-form'
];

module.exports.init = () => {
    alert.init();

    $('#forgot-password-form').submit(evt => {
        evt.preventDefault();
        const data = {
            email: $('#email').val(),
        };

        $.ajax(
            "/user/resetPassword",
            {
                method: "POST",
                data: JSON.stringify(data),
                contentType: "application/json",
                success: (body, status, xhr) => {
                    if (_.isEqual(xhr.status, 200)) {
                        alert.display('New password sent! Check your email', false);
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
    });
};
