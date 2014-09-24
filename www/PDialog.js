/*jslint browser: true, devel: true, node: true, sloppy: true*/
/*global require */
var exec = require("cordova/exec");

function PDialog() {}

PDialog.prototype.init = function (options) {
    options = options || {};
    exec(null, null, 'PDialog', 'init', options);
};
module.exports = new PDialog();