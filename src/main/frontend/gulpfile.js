'use strict';

const gulp = require('gulp');
const html = require('gulp-html-beautify');
const sass = require('gulp-sass');
const fileinclude = require('gulp-file-include');
const webserver = require('gulp-webserver');

const srcHTMLGlob = 'src/*.html';
const srcHTMLPartialsRoot = 'src/partials';
const srcSASSMain = 'sass/main.scss';
const srcSASSGlob = 'sass/**/*.scss';
const distRoot = 'dist/'
const distCSS = '../resources/static/css'
 
gulp.task('html', () => {
  return gulp.src(srcHTMLGlob)
    .pipe(fileinclude({
      prefix: '@@',
      basepath: srcHTMLPartialsRoot
    }))
    .pipe(html())
    .pipe(gulp.dest(distRoot))
});

gulp.task('sass', () => {
  return gulp.src(srcSASSMain)
   .pipe(sass().on('error', sass.logError))
   .pipe(gulp.dest(distCSS));
});

gulp.task('watch', () => {
  gulp.watch(srcHTMLGlob, ['html'])
  gulp.watch(srcSASSGlob, ['sass'])
});

gulp.task('default', ['html', 'sass']);

gulp.task('serve', ['html', 'sass'], () => {
  gulp.src('dist')
    .pipe(webserver({
      livereload: true,
      open: true
    }));
});