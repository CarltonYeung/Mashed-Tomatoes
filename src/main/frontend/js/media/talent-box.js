const $ = require('jquery');
const _ = require('lodash');

module.exports.deps = [
  '.talent-box',
  '#talent-box-see-more'
];

module.exports.init = () => {
  $('#talent-box-see-more').on('click', () => {
    $('.talent-box').each((i, el) => {
      $(el).prop('hidden', false);
    });
    $('#talent-box-see-more').prop('hidden', true);
  });
};