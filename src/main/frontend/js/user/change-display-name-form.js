const $ = require('jquery');
const _ = require('lodash');
const alert = require('../alert');

module.exports.deps = [
    '#change-display-name-form'
];

module.exports.init = () => {
    alert.init();

    $('#change-display-name-form').submit(evt => {
        evt.preventDefault();
        const data = {
            displayName: $('#displayName').val(),
        };

        $.ajax(
            '/user/changeDisplayName',
            {
                method: "POST",
                data: JSON.stringify(data),
                contentType: "application/json",
                success: (body, status, xhr) => {
                    if (_.isEqual(xhr.status, 200)) {
                        alert.display('Display name changed!', false);
                        setTimeout(() => {
                            window.location.reload(true);
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

    });
};
