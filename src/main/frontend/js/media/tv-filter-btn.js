const $ = require('jquery');

module.exports.deps = [
    '#tv-filter-btn',
    '#tv-sort-select',
    '#tv-genre-select'
];

module.exports.init = () => {
    $('#tv-filter-btn').on('click', () => {
        let sort = $('[data-sort]').attr('data-sort');
        let genre = $('[data-genre]').attr('data-genre');
        const page = $('[data-page]').attr('data-page');
        const selectSort = $('#tv-sort-select > option:selected');
        const genreSort = $('#tv-genre-select > option:selected');
        if (!selectSort.prop('disabled')) {
            sort = selectSort.val();
        }

        if (!genreSort.prop('disabled')) {
            genre = genreSort.val();
        }

        window.location.href = `/tv?page=${page}&sort=${sort}&genre=${genre}`;
    });
};
