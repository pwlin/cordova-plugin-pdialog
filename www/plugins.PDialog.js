var exec = require("cordova/exec");

function PDialog() {};

PDialog.prototype.init = function(options) {
	options = options || {};
	exec(null, null, "PDialog", "init", [options]);
};

module.exports = new PDialog();
