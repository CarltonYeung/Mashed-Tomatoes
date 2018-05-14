const $ = require('jquery');
const _ = require('lodash');
const alert = require('../alert');

module.exports.deps = [
    '.delete-rating-btn'
];

module.exports.init = () => {
    alert.init();
    $('.delete-rating-btn').on('click', (evt) => {
        const ratingId = $(evt.currentTarget).attr('data-rating-id');
        const mediaId = $(evt.currentTarget).attr('data-media-id');
        $.ajax(
            `/api/media/${mediaId}/rate/delete/${ratingId}`,
            {
                method: "DELETE",
                success: (body, status, xhr) => {
                    if (_.isEqual(xhr.status, 200)) {
                        alert.display('Rating deleted!', false);
                        window.location.reload(true);
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
