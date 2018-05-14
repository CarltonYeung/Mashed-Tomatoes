const $ = require('jquery');
const _ = require('lodash');
const alert = require('../alert');

module.exports.deps = [
  '[data-media-id]',
  '#rating-form'
];

module.exports.init = () => {
  const mediaId = $('[data-media-id]').attr('data-media-id');

  $('#rating-form').submit(evt => {
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
          `/api/media/${mediaId}/rate`,
          {
              method: "POST",
              data: JSON.stringify(data),
              contentType: "application/json",
              success: (body, status, xhr) => {
                  if (_.isEqual(xhr.status, 200)) {
                      alert.display('Review added', false);
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
      evt.preventDefault();
  });
};