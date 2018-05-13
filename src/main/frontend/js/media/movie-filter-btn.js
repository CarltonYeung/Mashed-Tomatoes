const $ = require('jquery');

module.exports.deps = [
    '#movie-filter-btn',
    '#movie-category-select',
    '#movie-sort-select',
    '#movie-genre-select'
];

module.exports.init = () => {
    $('#movie-filter-btn').on('click', () => {
        let category = $('[data-sort]').attr('data-category');
        let sort = $('[data-sort]').attr('data-sort');
        let genre = $('[data-genre]').attr('data-genre');
        const page = $('[data-page]').attr('data-page');
        const categorySelect = $('#movie-category-select > option:selected');
        const sortSelect = $('#movie-sort-select > option:selected');
        const genreSelect = $('#movie-genre-select > option:selected');
        if (!categorySelect.prop('disabled')) {
            category = categorySelect.val();
        }

        if (!sortSelect.prop('disabled')) {
            sort = sortSelect.val();
        }

        if (!genreSelect.prop('disabled')) {
            genre = genreSelect.val();
        }

        window.location.href = `/movie?page=${page}&sort=${sort}&genre=${genre}&category=${category}`;
    });
};

