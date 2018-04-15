const $ = require('jquery');
const _ = require('lodash');
const urlBuilder = require('../url-builder');

module.exports.deps = [
    '#login-btn'
];

module.exports.init = () => {
  
    $('#login-btn').on('click', () => {
      const emailSelector = $('#input-login-email');
      const pwdSelector = $('#input-login-pwd');
      if (_.isNil(emailSelector.val())) {
        console.log("Email is not typed");
      } else 
      if (_.isNil(pwdSelector.val())){
        console.log("Password is not typed");
      } else {
        const email = emailSelector.val();
        const pwd = pwdSelector.val();
        let data = {
          email, pwd
        };
  
  
        $.ajax(
          urlBuilder.buildLogin(),
          {
            method: "POST",
            data: data,
            contentType: "application/json",
            dataType: "application/json",
            success: res => {
              if (res.status == 204) {
                console.log('Login Success');
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