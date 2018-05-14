const $ = require('jquery');

module.exports.deps = [
    '#critic-filter-btn',
    '#critic-filter-select',
];

module.exports.init = () => {
    $('#critic-filter-btn').on('click', () => {
        let filter = $('[data-filter]').attr('data-filter');
        const page = $('[data-page]').attr('data-page');
        const filterSelect = $('#critic-filter-select > option:selected');
        if (!filterSelect.prop('disabled')) {
            filter = filterSelect.val();
        }

        window.location.href = `/critic?filter=${filter}&page=${page}`;
    });
};
