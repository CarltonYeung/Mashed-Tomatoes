const $ = require('jquery');

module.exports.deps = [
    '#review-filter-btn',
    '#review-filter-select',
];

module.exports.init = () => {
    $('#review-filter-btn').on('click', () => {
        let filter = $('[data-filter]').attr('data-filter');
        const page = $('[data-page]').attr('data-page');
        const filterSelect = $('#review-filter-select > option:selected');
        if (!filterSelect.prop('disabled')) {
            filter = filterSelect.val();
        }

        window.location.href = `/review?filter=${filter}&page=${page}`;
    });
};
