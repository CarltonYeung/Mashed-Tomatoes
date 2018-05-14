const $ = require('jquery');
const _ = require('lodash');
const alert = require('../alert');

module.exports.deps = [
    '#delete-user-btn'
];

module.exports.init = () => {
    alert.init();
    const userId = $('[data-user-id]').attr('data-user-id');
    $('#delete-user-btn').on('click', () => {
        $.ajax(
            `/user/${userId}`,
            {
                method: "DELETE",
                success: (body, status, xhr) => {
                    if (_.isEqual(xhr.status, 200)) {
                        alert.display('User deleted!', false);
                        window.location.href = "/";
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
