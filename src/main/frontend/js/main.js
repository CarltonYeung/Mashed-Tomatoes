require('bootstrap');

const $ = require('jquery');
window.jQuery = $;
window.jqeury = $;
window.$ = $;
require('slick-carousel');
require('flip');

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
    require('./user/change-email-form'),
    require('./base/carousel')
];

const areDepsMet = deps => {
    const missingDeps = _.filter(deps, dep => {
        return $(dep).length == 0;
    });

    if (!_.isEmpty(missingDeps)) {
        console.log(missingDeps);
        return false;
    }

    return true;
};

$(document).ready(function () {
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

    $(".loader_inner").delay(1300).fadeOut(500);
    $(".loader").delay(1300).fadeOut(500);
});
