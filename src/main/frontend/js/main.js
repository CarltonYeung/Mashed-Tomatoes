require('./vendor');

const _ = require('lodash');

const components = [
    require('./base/nav-bar'),
    require('./search/search-form'),
    require('./media/media-update-list'),
    require('./media/media-box-cast'),
    require('./media/media-box-photo'),
    require('./media/movie-filter-btn'),
    require('./media/oscar-filter-btn'),
    require('./media/tv-filter-btn'),
    require('./rating/rating-form'),
    require('./rating/report-rating-btn'),
    require('./rating/review-filter-btn'),
    require('./user/login-form'),
    require('./user/registration-form'),
    require('./user/logout-btn'),
    require('./user/change-email-form'),
    require('./user/change-display-name-form'),
    require('./user/change-password-form'),
    require('./user/change-privacy-form'),
    require('./user/delete-user-btn'),
    require('./user/resend-email-form'),
    require('./user/forgot-password-form'),
    require('./user/follow-user-btn'),
    require('./user/delete-rating-btn'),
    require('./user/edit-rating-btn'),
    require('./user/critic-apply-form'),
    require('./user/report-user-form'),
    require('./user/delete-application-btn'),
    require('./user/critic-filter-btn'),
    require('./base/carousel')
];

const areDepsMet = deps => {
    const missingDeps = _.filter(deps, dep => {
        return _.isEqual($(dep).length, 0);
    });

    if (!_.isEmpty(missingDeps)) {
        return false;
    }

    return true;
};

$('#movie-section').hover(
    function() {
      $('#section-header-movie').addClass('heading-glow');
},
    function() {
      $('#section-header-movie').removeClass('heading-glow');
});

$('#tv-section').hover(
    function() {
      $('#section-header-tv').addClass('heading-glow');
},
    function() {
      $('#section-header-tv').removeClass('heading-glow');
});

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
