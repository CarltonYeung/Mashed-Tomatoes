'use strict';

const gulp = require('gulp');
const sass = require('gulp-sass');
const webpack = require('webpack-stream');

const srcSASSMain = 'sass/main.scss';
const srcSASSGlob = 'sass/**/*.scss';
const srcJSMain = 'js/main.js';
const srcJSGlob = 'js/**/*.js';
const resourceRoot = '../resources/static';
const resourceCSS = `${resourceRoot}/css`;
const resourceJS = `${resourceRoot}/js`;

gulp.task('sass', () => {
    gulp.src(srcSASSMain)
        .pipe(sass({outputStyle: 'compressed'})
            .on('error', sass.logError))
        .pipe(gulp.dest(resourceCSS));
});

gulp.task('js', () => {
    gulp.src(srcJSMain)
        .pipe(webpack({
            output: {
                filename: "bundle.js"
            }
        }))
        .pipe(gulp.dest(resourceJS));
});

gulp.task('build', ['sass', 'js']);

gulp.task('watch', () => {
    gulp.watch(srcSASSGlob, ['sass']);
    gulp.watch(srcJSGlob, ['js']);
});

