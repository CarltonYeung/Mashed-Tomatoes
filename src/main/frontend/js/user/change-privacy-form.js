const $ = require('jquery');
const _ = require('lodash');
const alert = require('../alert');

module.exports.deps = [
    '#change-privacy-form'
];

module.exports.init = () => {
    alert.init();

    $('#change-privacy-form').submit(evt => {
        evt.preventDefault();
        const val = $('input[name=publicProfile]:checked').val();

        const data = {
            publicProfile: _.isEqual(val, 'public'),
        };

        $.ajax(
            '/user/changePrivacy',
            {
                method: "POST",
                data: JSON.stringify(data),
                contentType: "application/json",
                success: (body, status, xhr) => {
                    if (_.isEqual(xhr.status, 200)) {
                        alert.display('Privacy setting changed!', false);
                        window.location.reload(true);
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
