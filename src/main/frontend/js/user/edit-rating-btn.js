const $ = require('jquery');
const _ = require('lodash');
const alert = require('../alert');

module.exports.deps = [
    '#edit-rating-form',
    '#editRatingModal',
    '.edit-rating-btn'
];

module.exports.init = () => {
    alert.init();
    let ratingId = null;
    let mediaId = null;

    $('.edit-rating-btn').on('click', (evt) => {
        ratingId = $(evt.currentTarget).attr('data-rating-id');
        mediaId = $(evt.currentTarget).attr('data-media-id');
        const score = $(evt.currentTarget).attr('data-rating-score');
        const review = $(evt.currentTarget).attr('data-rating-review');
        $(`input[name=rating][value=${score}]`).prop('checked', true);
        $(`textarea[name=comment]`).val(review);
        $('#editRatingModal').modal('show');
    });

    $('#edit-rating-form').submit(evt => {
        evt.preventDefault();
        const commentBoxSelector = $('#rating-form-comment-box');
        const checkedStarSelector = $('input[name=rating]:checked');
        if (_.isEqual(checkedStarSelector.length, 0)) {
            alert.display('Please select a rating', true);
            return;
        }

        const comment = commentBoxSelector.val();
        const rating = parseInt(checkedStarSelector.val());
        let data = {
            rating,
        };

        if (!_.isEmpty(comment)) {
            data.review = comment;
        }

        $.ajax(
            `/api/media/${mediaId}/rate/update/${ratingId}`,
            {
                method: "PATCH",
                data: JSON.stringify(data),
                contentType: "application/json",
                success: (body, status, xhr) => {
                    if (_.isEqual(xhr.status, 200)) {
                        alert.display('Rating updated!', false);
                        window.location.reload(true);
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
