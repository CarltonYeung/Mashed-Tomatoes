const path = require('path');
const express = require('express');

var app = module.exports = exports = express();

const distStatic = '../resources/static';
const distTemplate = '../resources/templates';

app.use(require('connect-livereload')())

app.use(express.static(path.join(__dirname, distTemplate)));
app.use(express.static(path.join(__dirname, distStatic)));