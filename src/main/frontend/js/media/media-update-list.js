const $ = require('jquery');
const _ = require('lodash');
const ko = require('knockout');
const alert = require('../alert');

const mediaId = $('[data-media-id]').attr('data-media-id');

const updateList = (isWantToSee, adding) => {
  $.ajax(
    "/userMediaLists",
    {
      method: "POST",
      data: JSON.stringify({
        id: mediaId,
        add:  adding,
        list: isWantToSee ? "WTS" : "NI"
      }),
      contentType: "application/json",
        success: (body, status, xhr) => {
            if (_.isEqual(xhr.status, 200)) {
                console.log('List updated');
            }
        },
        error: (xhr, status, err) => {
            if (xhr.status != 500) {
                alert.display(xhr.responseText, true);
            } else if (xhr.status ==  500) {
                alert.display("Something's wrong with our server. Please try again later", true);
            }
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

  onNI() {
    if (this.inNI()) {
      this.inNI(false);
      updateList(false, false);
    } else {
        this.inNI(true);
        if (this.inWTS()) {
            this.inWTS(false);
        }
        updateList(false, true);
    }
  }

  onWTS() {
    if (this.inWTS()) {
        this.inWTS(false);
        updateList(true, false);
    } else {
        this.inWTS(true);
        if (this.inNI()) {
            this.inNI(false);
        }
        updateList(true, true);
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