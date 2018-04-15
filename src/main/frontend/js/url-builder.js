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
  buildRegister:() => {
    return "/register.html";
  }
};