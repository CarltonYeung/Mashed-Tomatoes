const $ = require('jquery');

module.exports.init = () => {
  $(window).scroll(function () {
    if ($(document).scrollTop() > 150) {
      $("#nav").css("position", "fixed");
      $("#nav").css("margin-top", "-17px");
      $("#content-container").css("padding-top", "500px");
      $("#menus").insertBefore("#search-bar");
      $("#menus").css("max-width", "45%");
      $("#menus").css("float", "left");
      $("#search-bar").removeClass("w-75");
      $("#nav").css("height", "80px");
      // $("#nav").addClass("shrink");
    } else {
      $("#nav").css("position", "static");
      $("#nav").css("margin-top", "0px");
      $("#content-container").css("margin-top", "0");
      $("#nav").css("z-index", "2");
      $("#menus").insertAfter("#login-btn");
      $("#menus").css("max-width", "100%");
      $("#search-bar").addClass("w-75");
      $("#nav").css("height", "");
      // $("#nav").delay(10000).animate({ opacity: 1 }, 700);​
      // $("#nav").removeClass("shrink");
    }
  });
};
/* 

Ethan's Legacy Code

window.onscroll = function() {
    var scrollTop = (window.pageYOffset !== undefined) ? window.pageYOffset : (document.documentElement || document.body.parentNode || document.body).scrollTop;
    if (scrollTop >= document.getElementById("popupstart").offsetTop) {
      document.getElementById("nav").style.position = "fixed";
      document.getElementById("popupstart").style.paddingTop = "0px";
      document.getElementById("nav").style.marginTop = "-17px";
      document.getElementById("contents-container").style.paddingTop = "133.33px";
    } else {
      document.getElementById("nav").style.position = "static";
      document.getElementById("popupstart").style.paddingTop = "0px";
      document.getElementById("nav").style.marginTop = "0px";
      document.getElementById("contents-container").style.paddingTop = "0px";
    }
};

*/