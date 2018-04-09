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
};

window.onload = () => {

  // init components if all deps are met or if no deps are given
  _.forEach(components, component => {
    if (_.has(component, 'deps')) {
      if (areDepsMet(component.deps) && _.has(component, 'init')) {
        component.init();
      }
    } else if (_.has(component, 'init')) {
        component.init();
    } 
  });
};