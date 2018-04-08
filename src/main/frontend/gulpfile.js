'use strict';

const gulp = require('gulp');
const html = require('gulp-html-beautify');
const sass = require('gulp-sass');
const jshint = require('gulp-jshint');
const image = require('gulp-image')
const fileinclude = require('gulp-file-include');
const webpack = require('webpack-stream');

const srcSASSMain = 'sass/main.scss';
const srcSASSGlob = 'sass/**/*.scss';
const srcJSMain = 'js/main.js';
const srcJSGlob = 'js/**/*.js';
const srcImageGlob = 'img/*';
const distRoot = '../resources/static';
const distCSS = '../resources/static/css'
const distImage = '../resources/static/img';
const distJS = '../resources/static/js';
const port = process.env.GULP_PORT || 3000;

gulp.task('sass', () => {
  return gulp.src(srcSASSMain)
    .pipe(sass({ outputStyle: 'compressed' })
      .on('error', sass.logError))
    .pipe(gulp.dest(distCSS));
});

gulp.task('js', () => {
  return gulp.src(srcJSMain)
    .pipe(webpack({
      output: {
        filename: "bundle.js"
      },
      devtool: 'source-map',
    }))
    .pipe(gulp.dest(distJS));
});

gulp.task('lint', function () {
  return gulp.src(srcJSGlob)
    .pipe(jshint())
    .pipe(jshint.reporter('default'));
});

gulp.task('image', () => {
  return gulp.src(srcImageGlob)
    .pipe(image())
    .pipe(gulp.dest(distImage));
});

gulp.task('watch', () => {
  gulp.watch(srcSASSGlob, ['sass']);
  gulp.watch(srcImageGlob, ['image']);
  gulp.watch(srcJSGlob, ['lint', 'js']);
});

gulp.task('build', ['sass', 'js', 'image']);

gulp.task('default', ['build']);

gulp.task('serve', () => {
   const app = require('./app');
   app.listen(port, () => {
      console.log(`Server listening on port ${port}`);
   });
});


