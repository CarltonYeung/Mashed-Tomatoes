'use strict';

const gulp = require('gulp');
const gutil = require("gulp-util");
const fileinclude = require('gulp-file-include');
const htmlbeautify = require('gulp-html-beautify');
const sass = require('gulp-sass');
const webpack = require('webpack');
const jshint = require('gulp-jshint');
const image = require('gulp-image')

const srcHTMLRoot = 'templates/**/*.html'
const srcHTMLPartialsRoot = 'templates/partials'
const srcSASSMain = 'sass/main.scss';
const srcSASSGlob = 'sass/**/*.scss';
const srcJSGlob = 'js/**/*.js';
const srcImageGlob = 'img/*';
const distRoot = '../resources/static';
const distCSS = '../resources/static/css'
const distImage = '../resources/static/img';
const distHTML = '../resources/templates';
const port = process.env.GULP_PORT || 3000;
const isProduction = process.env.ENV == 'PROD';
const usingThymeleaf = process.env.USING_THYMELEAF == 'true';

gulp.task('html', () => {
  return gulp.src(srcHTMLRoot)
    .pipe(fileinclude({
      prefix: '@@',
      basepath: srcHTMLPartialsRoot,
      context: {
        usingThymeleaf: isProduction || usingThymeleaf
      }
    }))
    .pipe(htmlbeautify())
    .pipe(gulp.dest(distHTML))
});

gulp.task('sass', () => {
  return gulp.src(srcSASSMain)
    .pipe(sass({ outputStyle: 'compressed' })
      .on('error', sass.logError))
    .pipe(gulp.dest(distCSS));
});

gulp.task('js', done => {
  webpack(require('./webpack.config'), (err, stats) => {
    if (err) {
      throw new gutil.PluginError("js", err);
    } else {
      gutil.log("[webpack]", stats.toString());
    }
    done();
  });
});

gulp.task('lint', function () {
  return gulp.src(srcJSGlob)
    .pipe(jshint())
    .pipe(jshint.reporter('default'));
});

gulp.task('image', () => {
  if (isProduction) {
    return gulp.src(srcImageGlob)
      .pipe(image())
      .pipe(gulp.dest(distImage));
  } else {
    return gulp.src(srcImageGlob)
      .pipe(gulp.dest(distImage));
  }
});

gulp.task('watch', () => {
  gulp.watch(srcHTMLRoot, ['html']);
  gulp.watch(srcSASSGlob, ['sass']);
  gulp.watch(srcImageGlob, ['image']);
  gulp.watch(srcJSGlob, ['lint', 'js']);
});

if (isProduction) {
  gulp.task('build', ['html', 'sass', 'js', 'image']);
} else {
  gulp.task('build', ['html', 'sass', 'lint', 'js', 'image']);
}

gulp.task('serve', () => {
  const app = require('./app');
  app.listen(port, () => {
    console.log(`Server listening on port ${port}`);
  });
});

gulp.task('default', ['build', 'serve']);