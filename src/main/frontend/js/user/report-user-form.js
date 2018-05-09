const $ = require('jquery');
const _ = require('lodash');
const alert = require('../alert');

module.exports.deps = [
    '#report-user-form',
    '#reportUserModal',
];

module.exports.init = () => {
    alert.init();

    $('#report-user-form').on('submit', evt => {
        evt.preventDefault();
        const userId = $('#report-user-id').val();
        const reason = $('#report-reason').val();

        let data = {
            userId,
            reason
        };

        $.ajax(
            `/user/report`,
            {
                method: "POST",
                data: JSON.stringify(data),
                contentType: "application/json",
                success: (body, status, xhr) => {
                    if (_.isEqual(xhr.status, 200)) {
                        alert.display('User reported!', false);
                        setTimeout(() => {
                            window.location.reload(true);
                        }, 1000);
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
