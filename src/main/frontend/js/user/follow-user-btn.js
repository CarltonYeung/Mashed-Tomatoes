const $ = require('jquery');
const _ = require('lodash');
const ko = require('knockout');
const alert = require('../alert');

const userId = $('[data-user-id]').attr('data-user-id');
const isFollowing = _.isEqual($('[data-user-id]').attr('data-user-is-following'), 'true');

const updateIsFollowing = (isFollowing) => {
    const url = isFollowing ? '/user/unfollow' : '/user/follow';
    $.ajax(
        url,
        {
            method: "POST",
            data: JSON.stringify({
                id: userId,
            }),
            contentType: "application/json",
            success: (body, status, xhr) => {
                if (_.isEqual(xhr.status, 200)) {
                    if (isFollowing) {
                        console.log('User unfollowed');
                    } else {
                        console.log('User followed');
                    }
                    window.location.reload(true);
                }
            },
            error: (xhr, status, err) => {
                if (!_.isEqual(xhr.status, 500)) {
                    alert.display(xhr.responseText, true);
                } else if (_.isEqual(xhr.status, 500)) {
                    alert.display("Something's wrong with our server. Please try again later", true);
                }
            }
        });
};

class ViewModel {
    constructor(isFollowing) {
        this.isFollowing = ko.observable(isFollowing);

        this.followCSS = ko.computed(() => {
            if (this.isFollowing()) {
                return 'btn-danger';
            }
            return 'btn-primary';
        });

        this.followText = ko.computed(() => {
            if (this.isFollowing()) {
                return "Unfollow";
            }
            return "Follow";
        });
    }

    onFollow() {
        updateIsFollowing(this.isFollowing());
        this.isFollowing(!this.isFollowing());
    }
}

module.exports.deps = [
    '#follow-user-btn-row',
    '#follow-user-btn',
    '[data-user-id]',
    '[data-user-is-following]'
];

module.exports.init = () => {
    let vm = new ViewModel(isFollowing);
    ko.applyBindings(vm, document.getElementById('follow-user-btn-row'));
};

