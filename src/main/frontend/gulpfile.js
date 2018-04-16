'use strict';

const gulp = require('gulp');
const htmlbeautify = require('gulp-html-beautify');
const sass = require('gulp-sass');
const jshint = require('gulp-jshint');
const fileinclude = require('gulp-file-include');
const webpack = require('webpack-stream');
const fs = require('fs');

const srcHTMLGlob = 'templates/**/*.html'
const srcHTMLPartialsRoot = 'templates/partials';
const srcSASSMain = 'sass/main.scss';
const srcSASSGlob = 'sass/**/*.scss';
const srcJSMain = 'js/main.js';
const srcJSGlob = 'js/**/*.js';
const srcImageGlob = 'img/**/*.{png,jpg,jpeg}';
const resourceRoot = '../resources';
const resourceHTML = `${resourceRoot}/templates`;
const resourceStaticRoot = `${resourceRoot}/static`;
const resourceCSS = `${resourceStaticRoot}/css`;
const resourceImage = `${resourceStaticRoot}/img`;
const resourceJS = `${resourceStaticRoot}/js`;
const targetRoot = '../../../target/classes';
const targetHTML = `${targetRoot}/templates`;
const targetStaticRoot = `${targetRoot}/static`;
const targetCSS = `${targetStaticRoot}/css`;
const targetImage = `${targetStaticRoot}/img`;
const targetJS = `${targetStaticRoot}/js`;
const port = process.env.GULP_PORT || 3000;
const usingThymeleaf = process.env.USING_THYMELEAF == 'true';

gulp.task('html', () => {
  let stream = gulp.src(srcHTMLGlob)
    .pipe(fileinclude({
      prefix: '@@',
      basepath: srcHTMLPartialsRoot
    }))
    .pipe(htmlbeautify())
    .pipe(gulp.dest(resourceHTML));
  
  if (fs.existsSync(targetHTML)) {
    stream.pipe(gulp.dest(targetHTML));
  }
});

gulp.task('sass', () => {
  let stream = gulp.src(srcSASSMain)
    .pipe(sass({ outputStyle: 'compressed' })
      .on('error', sass.logError))
    .pipe(gulp.dest(resourceCSS));

  if (fs.existsSync(targetCSS)) {
    stream.pipe(gulp.dest(targetCSS));
  }
});

gulp.task('js', () => {
  let stream = gulp.src(srcJSMain)
    .pipe(webpack({
      output: {
        filename: "bundle.js"
      },
      devtool: 'source-map',
    }))
    .pipe(gulp.dest(resourceJS));

  if (fs.existsSync(targetJS)) {
    stream.pipe(gulp.dest(targetJS));
  }
});

gulp.task('lint', function () {
  return gulp.src(srcJSGlob)
    .pipe(jshint())
    .pipe(jshint.reporter('default'));
});

gulp.task('image', () => {
  let stream = gulp.src(srcImageGlob)
      .pipe(gulp.dest(resourceImage));
  
  if (fs.existsSync(targetImage)) {
    stream.pipe(gulp.dest(targetImage));
  }
});

gulp.task('watch', () => {
  gulp.watch(srcHTMLGlob, ['html']);
  gulp.watch(srcSASSGlob, ['sass']);
  gulp.watch(srcImageGlob, ['image']);
  gulp.watch(srcJSGlob, ['lint', 'js']);
});

gulp.task('build', ['html', 'sass', 'lint', 'js', 'image']);

gulp.task('serve', () => {
  const app = require('./app');
  app.listen(port, () => {
    console.log(`Server listening on port ${port}`);
  });
});

gulp.task('default', ['build', 'serve']);