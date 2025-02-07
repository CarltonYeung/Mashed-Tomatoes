const $ = require('jquery');

module.exports.init = () => {
  if ($(document).height() < 1500) {
    return;
  }

  $(window).scroll(function () {
    if ($(document).scrollTop() > 175) {
      $("#nav").css("position", "fixed");
      $("#nav").css("margin-top", "-17px");
      $("#popupstart").css("padding-top", "210px");
      $("#menus").insertBefore("#search-bar");
      $("#menus").css("max-width", "45%"); 
      $("#menus").css("float", "left");
      $("#menus").css("margin-right", "-100px");
      $("#search-bar").removeClass("w-75");
      $("#search-bar").addClass("w-45");
      $("#search-bar").css("margin-right", "-110px");
      $("#nav").css("height", "80px");
      $("#login-btn").removeClass("ml-70");
      // $("#nav").addClass("shrink");
    } else {
      $("#nav").css("position", "static");
      $("#nav").css("margin-top", "0px");
      $("#popupstart").css("padding-top", "0");
      $("#nav").css("z-index", "999");
      $("#menus").insertAfter("#login-btn");
      $("#menus").css("max-width", "100%");
      $("#menus").css("margin-right", "0px");
      $("#search-bar").removeClass("w-45");
      $("#search-bar").addClass("w-75");
      $("#search-bar").css("margin-right", "0");
      $("#nav").css("height", "");
      $("#login-btn").addClass("ml-70");
      // $("#nav").delay(10000).animate({ opacity: 1 }, 700);​
      // $("#nav").removeClass("shrink");
    }
  });
};

  $("#dropdown-movies").hover(
    function() {
     
      $("#dropdown-movies").addClass("show");
      $('#movies-navbarDropdownMenuLink').attr("aria-expanded", "true");
      $("#movies-sub-menu").addClass("show");
  },
  function() {
     
    $("#dropdown-movies").removeClass("show");
    $('#movies-navbarDropdownMenuLink').attr("aria-expanded", "false");
    $("#movies-sub-menu").removeClass("show");
  });


  $("#dropdown-tv-shows").hover(
    function() {
     
      $("#dropdown-tv-shows").addClass("show");
      $('#tv-shows-navbarDropdownMenuLink').attr("aria-expanded", "true");
      $("#tv-shows-sub-menu").addClass("show");
  },
  function() {
     
    $("#dropdown-tv-shows").removeClass("show");
    $('#tv-shows-navbarDropdownMenuLink').attr("aria-expanded", "false");
    $("#tv-shows-sub-menu").removeClass("show");
  });

  $("#dropdown-critics").hover(
    function() {
     
      $("#dropdown-critics").addClass("show");
      $('#critics-navbarDropdownMenuLink').attr("aria-expanded", "true");
      $("#critics-sub-menu").addClass("show");
  },
  function() {
     
    $("#dropdown-critics").removeClass("show");
    $('#critics-navbarDropdownMenuLink').attr("aria-expanded", "false");
    $("#critics-sub-menu").removeClass("show");
  });


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