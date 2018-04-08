require('bootstrap');

const _ = require('lodash');

const components = [
  require('./components/nav-bar.js')
];

window.onload = () => {
  _.forEach(components, component => {
    component.init();
  });
};