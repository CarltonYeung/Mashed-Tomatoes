require('bootstrap');

const $ = require('jquery');
const _ = require('lodash');

const components = [
  require('./base/nav-bar'),
  require('./media/media-update-list'),
  require('./rating/rating-form-post-btn'),
  require('./user/login-btn'),
  require('./user/register-btn'),
  requrie('./search/search-btn')
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