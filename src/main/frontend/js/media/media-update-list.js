const $ = require('jquery');
const _ = require('lodash');
const ko = require('knockout');
const urlBuilder = require('../url-builder');

const movieId = $('[data-media-id]').attr('data-media-id');

const updateList = (isWantToSee) => {
  $.ajax(
    urlBuilder.buildUpdateList(),
    {
      method: "PATCH",
      data: {
        movieId: movieId,
        isWantToSee: isWantToSee
      },
      contentType: "application/json",
      success: res => {
        if (res.status == 204) {
          console.log('List updated');
        }
      },
      error: res => {
        console.error(res.status);
      }
    });
};

class ViewModel {
  constructor(inNI, inWTS) {
    this.inNI = ko.observable(inNI);
    this.inWTS = ko.observable(inWTS);

    this.ntCSS = ko.computed(() => {
      if (this.inNI()) {
        return '-ni';
      }
      return '';
    });

    this.wtsCSS = ko.computed(() => {
      if (this.inWTS()) {
        return '-wts';
      }
      return '';
    });
  }

  onAddToNI() {
    if (!this.inNI()) {
      this.inNI(true);
      updateList(false);
      if (this.inWTS()) {
        this.inWTS(false);
      }
    }
  }

  onAddToWTS() {
    if (!this.inWTS()) {
      this.inWTS(true);
      updateList(true);
      if (this.inNI()) {
        this.inNI(false);
      }
    }
  }
}

module.exports.deps = [
  '#media-update-list',
  '[data-media-id]'
];

module.exports.init = () => {
  const inNI = $('.-ni').length > 0;
  const inWTS = $('.-wts').length > 0;
  let vm = new ViewModel(inNI, inWTS);
  ko.applyBindings(vm, document.getElementById('media-update-list'));
};