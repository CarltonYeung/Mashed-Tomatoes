const $ = require('jquery');

module.exports.deps = [
  '#see-more-photo'
];

module.exports.init = () => {
  $('#see-more-photo').on('click', () => {
    $('.-photo').each((i, el) => {
      $(el).prop('hidden', false);
    });
    $('#see-more-photo').prop('hidden', true);
  });
};