require('bootstrap');

const $ = require('jquery');
window.jQuery = $;
window.jqeury = $;
window.$ = $;
const _ = require('lodash');
require('slick-carousel');
require('flip');

const components = [
  require('./base/nav-bar'),
  // require('./flip/jquery.flip'),
  require('./search/search-form'),
  require('./media/media-update-list'),
  require('./rating/rating-form-post-btn'),
  require('./user/login-form'),
  require('./user/registration-form'),
  require('./user/logout-btn'),
  // require('./slick/slick'),
  require('./base/carousel')
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

$(document).ready(function(){

	$(".loader_inner").delay(1300).fadeOut(500);
	$(".loader").delay(1300).fadeOut(500);

});
