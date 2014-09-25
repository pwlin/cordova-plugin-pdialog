var exec = require("cordova/exec");

function PDialog() {};

/**
 * Initializing the progress dialog and set various parameters
 * @param options
 * These are the valid options:
 * 
 * "theme": can be one of the following:
 * "TRADITIONAL", "DEVICE_DARK", "DEVICE_LIGHT" (default), "HOLO_DARK", "HOLO_LIGHT"
 * 
 * "progressStyle": can be one of the following:
 * "SPINNER" (default), "HORIZONTAL"
 *  
 *  "cancelable" : true (default) or false
 *  
 *  "title": title of the progress dialog (defaults to empty)
 *  
 *  "message": contents of the progress dialog (defaults to empty)
 *  
 *  @example
 *  cordova.plugins.pDialog.init({
 *  	theme : 'HOLO_DARK',
 *  	progressStyle: 'SPINNER',
 *  	cancelable : false,
 *  	title: 'Please Wait...',
 *  	message: 'Contacting server ...',
 *  });
 *     
 */
PDialog.prototype.init = function(options) {
	options = options || {};
	exec(null, null, "PDialog", "init", [options]);
	return this;
};

/**
 * Show the progress dialog
 */
PDialog.prototype.show = function() {
    exec(null, null, "PDialog", "show", []);
    return this;
};


/**
 * Dismiss the progress dialog
 */
PDialog.prototype.dismiss = function() {
    exec(null, null, "PDialog", "dismiss", []);
    return this;
};

/**
 * Set the value of the progress bar
 * @param {Int} value
 */
PDialog.prototype.setProgress = function(value) {
    exec(null, null, "PDialog", "setProgress", [value]);
    return this;
};

/**
 * Set the value of the progress bar style
 * @param {String} style
 * It can be one of the following:
 * "SPINNER" (default), "HORIZONTAL"
 */
PDialog.prototype.setProgressStyle = function(style) {
    exec(null, null, "PDialog", "setProgressStyle", [style]);
    return this;
};

/**
 * Set the title of the progress dialog
 * @param {String} title
 */
PDialog.prototype.setTitle = function(title) {
    exec(null, null, "PDialog", "setTitle", [title]);
    return this;
};

/**
 * Set the message of the progress dialog
 * @param {String} message
 */
PDialog.prototype.setMessage = function(message) {
    exec(null, null, "PDialog", "setMessage", [message]);
    return this;
};

/**
 * Set whether the progress dialog is calncelable or not
 * @param {Boolean} flag
 */
PDialog.prototype.setCancelable = function(flag) {
    exec(null, null, "PDialog", "setCancelable", [flag]);
    return this;
};

module.exports = new PDialog();
