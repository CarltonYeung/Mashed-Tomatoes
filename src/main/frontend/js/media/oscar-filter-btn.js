const $ = require('jquery');

module.exports.deps = [
    '#oscar-filter-btn',
    '#oscar-year-select',
];

module.exports.init = () => {
    $('#oscar-filter-btn').on('click', () => {
        let year = $('[data-year]').attr('data-year');
        const yearSelect = $('#oscar-year-select > option:selected');
        if (!yearSelect.prop('disabled')) {
            year = yearSelect.val();
        }

        window.location.href = `/movie/academy-award?year=${year}`;
    });
};
