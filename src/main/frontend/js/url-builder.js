module.exports = {
  buildUpdateList: () => {
    return "/api/user/list";
  },
  buildCreateRating: (movieSlug) => {
    return "/api/movie/" + movieSlug + "/rating";
  },
}