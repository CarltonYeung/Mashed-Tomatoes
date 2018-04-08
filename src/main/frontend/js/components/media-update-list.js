const $ = require('jquery');
const _ = require('lodash');
const ko = require('knockout');

class ViewModel {
  constructor(inNI, inWTS) {
    this.inNI = ko.observable(inNI);
    this.inWTS = ko.observable(inWTS);

    this.ntCSS = ko.computed(() => {
      if (this.inNI()) {
        return '-ni';
      }
      return '';
    })

    this.wtsCSS = ko.computed(() => {
      if (this.inWTS()) {
        return '-wts';
      }
      return '';
    });
  }

  onAddToNI() {
    console.log(this.inNI);
    if (!this.inNI()) {
      this.inNI(true);
      if (this.inWTS()) {
        this.inWTS(false);
      }
    }
  }

  onAddToWTS() {
    if (!this.inWTS()) {
      this.inWTS(true);
      if (this.inNI()) {
        this.inNI(false);
      }
    }
  }
}


module.exports.init = () => {
  const inNI = $('.-ni').length > 0;
  const inWTS = $('.-wts').length > 0;
  let vm = new ViewModel(inNI, inWTS);
  ko.applyBindings(vm, document.getElementById('media-update-list'));
};