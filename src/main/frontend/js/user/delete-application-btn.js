const $ = require('jquery');
const _ = require('lodash');
const alert = require('../alert');

module.exports.deps = [
    '.delete-application-btn'
];

module.exports.init = () => {
    alert.init();
    $('.delete-application-btn').on('click', (evt) => {
        const applicantId = $(evt.currentTarget).attr('data-applicant-id');
        const isApproved = _.isEqual($(evt.currentTarget).attr('data-approved'), 'true');

        $.ajax(
            `/user/criticApplication/${applicantId}`,
            {
                method: "DELETE",
                contentType: 'application/json',
                data: JSON.stringify({
                    approved: isApproved,
                }),
                success: (body, status, xhr) => {
                    if (_.isEqual(xhr.status, 200)) {
                        alert.display('Critic application processed!', false);
                        setTimeout(() => {
                            window.location.reload(true);
                        }, 1000);
                    }
                },
                error: (xhr, status, err) => {
                    if (xhr.status !== 500) {
                        alert.display(xhr.responseText, true);
                    } else if (xhr.status === 500) {
                        alert.display("Something's wrong with our server. Please try again later", true);
                    }
                }
            });
    });
};
