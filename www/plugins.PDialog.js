var exec = require("cordova/exec");

function PDialog() {};

PDialog.prototype.init = function(options) {
	options = options || {};
	exec(null, null, "PDialog", "init", [options]);
};

PDialog.prototype.show = function(options) {
    options = options || {};
    exec(null, null, "PDialog", "show", [options]);
};

PDialog.prototype.dismiss = function(options) {
    options = options || {};
    exec(null, null, "PDialog", "dismiss", [options]);
};

PDialog.prototype.setProgress = function(options) {
    options = options || {};
    exec(null, null, "PDialog", "setProgress", [options]);
};

PDialog.prototype.setTitle = function(options) {
    options = options || {};
    exec(null, null, "PDialog", "setTitle", [options]);
};

PDialog.prototype.setMessage = function(options) {
    options = options || {};
    exec(null, null, "PDialog", "setMessage", [options]);
};

module.exports = new PDialog();
