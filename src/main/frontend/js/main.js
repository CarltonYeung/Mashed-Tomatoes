require('bootstrap');

const _ = require('lodash');

const components = [
  require('./components/nav-bar.js'),
  require('./components/rating-form-post-btn.js')
];

window.onload = () => {
  _.forEach(components, component => {
    component.init();
    if (process.env.USE_MOCK_AJAX && _.has(component, 'mockInit')) {
      component.mockInit();
    }
  });
};