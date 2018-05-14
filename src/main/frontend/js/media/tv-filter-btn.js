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
        const sortSelect = $('#tv-sort-select > option:selected');
        const genreSelect = $('#tv-genre-select > option:selected');
        if (!sortSelect.prop('disabled')) {
            sort = sortSelect.val();
        }

        if (!genreSelect.prop('disabled')) {
            genre = genreSelect.val();
        }

        window.location.href = `/tv?page=${page}&sort=${sort}&genre=${genre}`;
    });
};
