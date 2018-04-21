require('bootstrap');

const $ = require('jquery');
const _ = require('lodash');

const components = [
  require('./base/nav-bar'),
  require('./search/search-form'),
  require('./media/media-update-list'),
  require('./media/media-box-cast'),
  require('./media/media-box-photo'),
  require('./rating/rating-form-post-btn'),
  require('./user/login-form'),
  require('./user/registration-form'),
  require('./user/logout-btn'),
];

const areDepsMet = deps => {
  const missingDeps = _.filter(deps, dep => {
    return $(dep).length == 0;
  });

  if (!_.isEmpty(missingDeps)) {
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