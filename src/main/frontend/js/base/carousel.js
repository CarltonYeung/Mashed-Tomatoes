const $ = require('jquery');

// $("#card").flip();

$(document).ready(function(){
    $(".flip").flip({trigger: 'hover',axis: 'x'});
  });



$(document).ready(function(){
    $('.center').slick({
        centerMode: true,
        centerPadding: '60px',
        slidesToShow: 3,
        slideToScroll: 3,
        autoplay: true,
        autoplaySpeed: 3000,
      });
  });

  $(document).ready(function(){
    $('.center-2').slick({
        centerMode: true,
        centerPadding: '60px',
        slidesToShow: 5,
        slideToScroll: 5,
        autoplay: true,
        autoplaySpeed: 1900,
      });
  });

  $(document).ready(function(){
    $('#index-carousel-np').mouseover({
        centerMode: true,
        centerPadding: '60px',
        slidesToShow: 5,
        slideToScroll: 5,
        autoplay: true,
        autoplaySpeed: 1900,
      });
  });
  
  $('#index-carousel-np').hover(
      function() {
        $('#heading-now-playing').addClass('heading-glow');
  },
      function() {
        $('#heading-now-playing').removeClass('heading-glow');
  });

  $('#index-carousel-otw').hover(
    function() {
        $('#heading-opening-this-week').addClass('heading-glow');
  },
      function() {
        $('#heading-opening-this-week').removeClass('heading-glow');
  });

  $('#index-carousel-csm').hover(
    function() {
      $('#heading-coming-soon-movies').addClass('heading-glow');
  },
    function() {
      $('#heading-coming-soon-movies').removeClass('heading-glow');
  });

  $('#index-carousel-tbo').hover(
    function() {
      $('#heading-top-box-office').addClass('heading-glow');
  },
      function() {
        $('#heading-top-box-office').removeClass('heading-glow');
  });

  $('#index-carousel-trm').hover(
      function() {
        $('#heading-top-rated-movies').addClass('heading-glow');
  },
      function() {
        $('#heading-top-rated-movies').removeClass('heading-glow');
  });




  $('#index-carousel-at').hover(
      function() {
        $('#heading-airing-today').addClass('heading-glow');
  },
      function() {
        $('#heading-airing-today').removeClass('heading-glow');
  });

  $('#index-carousel-na').hover(
      function() {
        $('#heading-now-airing').addClass('heading-glow');
  },
      function() {
        $('#heading-now-airing').removeClass('heading-glow');
  });

  $('#index-carousel-trt').hover(
      function() {
        $('#heading-top-rated-tv').addClass('heading-glow');
  },
      function() {
        $('#heading-top-rated-tv').removeClass('heading-glow');
  });




//   $('.autoplay').slick({
//     slidesToShow: 3,
//     slidesToScroll: 1,
//     autoplay: true,
//     autoplaySpeed: 2000,
//   });

