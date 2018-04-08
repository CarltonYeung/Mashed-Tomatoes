require('bootstrap');

const _ = require('lodash');

const components = [
  require('./components/nav-bar.js'),
  require('./components/rating-form-post-btn.js'),
  require('./components/media-update-list')
];

window.onload = () => {
  _.forEach(components, component => {
    _.has(component, 'init') && component.init();
  });
};