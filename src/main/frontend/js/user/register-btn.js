const $ = require('jquery');
const _ = require('lodash');
const urlBuilder = require('../url-builder');

module.exports.deps = [
    '#register-btn'
];

module.exports.init = () => {
  
    $('#register-btn').on('click',function(e){
      e.preventDefault();
      const fnameSelector = $('#input-register-fname');
      const lnameSelector = $('#input-register-lname');
      const emailSelector = $('#input-register-email');
      const pwdSelector = $('#input-register-pwd');
      if (_.isNil(emailSelector.val())) {
        console.log("Email is not typed");
      } else 
      if (_.isNil(fnameSelector.val())){
        console.log("First name is not typed");
      } else 
      if (_.isNil(lnameSelector.val())){
        console.log("Last name is not typed");
      } else 
      if (_.isNil(pwdSelector.val())){
        console.log("Password is not typed");
      } else {
        const displayName = fnameSelector.val() + " " + lnameSelector.val();
        const email = emailSelector.val();
        const password = pwdSelector.val();
        let data = {
          displayName, email, password
        };
        
        $.ajax(
          urlBuilder.buildRegister(),
          {
            method: "POST",
            data: data,
            contentType: "application/json",
            dataType: "application/json",
            success: res => {
              if (res.status == 204) {
                console.log('Register Success');
                // window.location = '/';
              }
            },
            error: res => {
              console.error(res.status);
            }
        });
      }
    });
  };
