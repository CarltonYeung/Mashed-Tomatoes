const $ = require('jquery');
const _ = require('lodash');
const alert = require('../alert');

module.exports.deps = [
    '#change-password-form'
];

module.exports.init = () => {
    alert.init();

    $('#change-password-form').submit(evt => {
        evt.preventDefault();
        const data = {
            oldPlaintextPassword: $('#oldPassword').val(),
            newPlaintextPassword: $('#newPassword').val(),
        };

        $.ajax(
            '/user/changePassword',
            {
                method: "POST",
                data: JSON.stringify(data),
                contentType: "application/json",
                success: (body, status, xhr) => {
                    if (_.isEqual(xhr.status, 200)) {
                        alert.display('Password changed!', false);
                        setTimeout(() => {
                            window.location.href = "/login";
                        }, 1000);
                    }
                },
                error: (xhr, status, err) => {
                    if (xhr.status != 500) {
                        alert.display('Wrong password. Try again', true);
                    } else if (xhr.status ==  500) {
                        alert.display("Something's wrong with our server. Please try again later", true);
                    }
                }
            });
    });
};
