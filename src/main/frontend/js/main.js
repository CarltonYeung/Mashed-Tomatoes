require('bootstrap');

const $ = require('jquery');
const _ = require('lodash');

const components = [
  require('./components/nav-bar.js'),
  require('./components/rating-form-post-btn.js'),
  require('./components/media-update-list')
];

const areDepsMet = deps => {
  const missingDeps = _.filter(deps, dep => {
    return $(dep).length == 0;
  });

  if (!_.isEmpty(missingDeps)) {
    console.log('Missing deps');
    console.log(missingDeps);
    return false;
  }

  return true;
}

window.onload = () => {
  _.forEach(components, component => {
    _.has(component, 'deps') &&
      areDepsMet(component.deps) &&
      _.has(component, 'init') &&
      component.init();
  });
};