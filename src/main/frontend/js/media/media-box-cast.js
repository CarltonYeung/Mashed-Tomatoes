const $ = require('jquery');

module.exports.deps = [
  '#see-more-cast',
];

module.exports.init = () => {
  $('#see-more-cast').on('click', () => {
    $('.-cast').each((i, el) => {
      $(el).prop('hidden', false);
    });
    $('#see-more-cast').prop('hidden', true);
  });
};