const $ = require('jquery');
const _ = require('lodash');
const urlBuilder = require('../url-builder');

module.exports.deps = [
  '[data-media-slug]',
  '#rating-form-post-btn',
  '#rating-form-comment-box',
  'input[name=rating-form-star-rating]'
];

module.exports.init = () => {
  const movieSlug = $('[data-media-slug]').attr('data-media-slug');

  $('#rating-form-post-btn').on('click', () => {
    const commentBoxSelector = $('#rating-form-comment-box');
    const checkedStarSelector = $('input[name=rating-form-star-rating]:checked');
    if (_.isNil(checkedStarSelector.val())) {
      console.log("No rating picked");
    } else {
      const comment = commentBoxSelector.val();
      const rating = parseInt(checkedStarSelector.val());
      let data = {
        rating
      };

      if (!_.isEmpty(comment)) {
        data.comment = comment;
      }

      $.ajax(
        urlBuilder.buildCreateRating(movieSlug),
        {
          method: "POST",
          data: data,
          contentType: "application/json",
          dataType: "application/json",
          success: res => {
            if (res.status == 204) {
              console.log('Review added');
            }
          },
          error: res => {
            console.error(res.status);
          }
      });
    }
  });
};