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

  $('#index-carousel-tbo').hover(
    function() {
      $('#heading-top-box-office').addClass('heading-glow');
},
    function() {
      $('#heading-top-box-office').removeClass('heading-glow');
});


$('#index-carousel-trf').hover(
    function() {
      $('#heading-top-rated-films').addClass('heading-glow');
},
    function() {
      $('#heading-top-rated-films').removeClass('heading-glow');
});

$('#index-carousel-csf').hover(
    function() {
      $('#heading-coming-soon-films').addClass('heading-glow');
},
    function() {
      $('#heading-coming-soon-films').removeClass('heading-glow');
});


$('#index-carousel-tat').hover(
    function() {
      $('#heading-tv-airing-today').addClass('heading-glow');
},
    function() {
      $('#heading-tv-airing-today').removeClass('heading-glow');
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

