const $ = require('jquery');
const _ = require('lodash');
const alert = require('../alert');

module.exports.deps = [
    '#report-rating-form',
    '#reportRatingModal',
    '.report-rating-btn'
];

module.exports.init = () => {
    alert.init();
    let ratingId = null;
    let mediaId = null;

    $('.report-rating-btn').on('click', (evt) => {
        ratingId = $(evt.currentTarget).attr('data-rating-id');
        mediaId = $(evt.currentTarget).attr('data-media-id');
        $('#reportRatingModal').modal('show');
    });

    $('#report-rating-form').submit(evt => {
        evt.preventDefault();
        let data = {
            reason: $('#report-reason').val()
        };

        $.ajax(
            `/api/media/${mediaId}/rate/report/${ratingId}`,
            {
                method: "POST",
                data: JSON.stringify(data),
                contentType: "application/json",
                success: (body, status, xhr) => {
                    if (_.isEqual(xhr.status, 200)) {
                        alert.display('Rating reported!', false);
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
