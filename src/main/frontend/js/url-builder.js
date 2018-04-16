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
  }
};