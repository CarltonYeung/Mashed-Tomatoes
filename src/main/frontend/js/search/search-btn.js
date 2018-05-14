const $ = require('jquery');
const _ = require('lodash');
const urlBuilder = require('../url-builder');

module.exports.deps = [
    '#search-btn'
];

module.exports.init = () => {
  
    $('#search-btn').on('click', () => {

      const searchSelector = $('#input-search');
      if (_.isNil(searchSelector.val())) {
        console.log("Search bar is not typed");
      } else {
        const expr = searchSelector.val();
        let data = {
          expr
        };
        
        $.ajax(
          urlBuilder.buildSearch(),
          {
            method: "GET",
            data: data,
            contentType: "application/json",
            dataType: "application/json",
            success: res => {
              if (res.status === 204) {
                window.location = '/';
              }
            },
            error: res => {
              console.error(res.status);
            }
        });
      }
    });
  };
