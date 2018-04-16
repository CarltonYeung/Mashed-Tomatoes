const _ = require('lodash');

module.exports = {
  buildUpdateList: () => {
    return "/api/user/list";
  },
  buildCreateRating: (movieSlug) => {
    return "/api/movie/" + movieSlug + "/rating";
  },
  buildLogin:() => {
    return "/login";
  },
  buildLogout:() => {
    return "/logout";
  },
  buildRegister:() => {
    return "/register";
  },
  buildSearch:(expr) => {
    if (_.isNil(expr) || _.isEmpty(expr)) {
      return "/search";
    }

    return `/search?expr=${expr}`;
  }
};