package io.github.pwlin.cordova.plugins.pdialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.util.Log;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

public class PDialog extends CordovaPlugin {

	private ProgressDialog pDialogObj = null;

	/**
	 * Executes the request and returns a boolean.
	 * 
	 * @param action
	 *            The action to execute.
	 * @param args
	 *            JSONArry of arguments for the plugin.
	 * @param callbackContext
	 *            The callback context used when calling back into JavaScript.
	 * @return boolean.
	 */
	public boolean execute(final String action, final JSONArray args, CallbackContext callbackContext) throws JSONException {
		cordova.getActivity().runOnUiThread(new Runnable() {
			public void run() {
				if ("init".equals(action)) {
					try {
						PDialog.this.init(args);
					} catch (JSONException e) {
						//e.printStackTrace();
					}
				} else if ("show".equals(action)) {
					PDialog.this.show();
				} else if ("dismiss".equals(action)) {
					PDialog.this.dismiss();
				} else if ("setProgress".equals(action)) {
					try {
						PDialog.this.setProgress(args.getInt(0));
					} catch (JSONException e) {
						//e.printStackTrace();
					}
				} else if ("setProgressStyle".equals(action)) {
					try {
						PDialog.this.setProgressStyle(args.getString(0));
					} catch (JSONException e) {
						//e.printStackTrace();
					}
				} else if ("setTitle".equals(action)) {
					try {
						PDialog.this.setTitle(args.getString(0));
					} catch (JSONException e) {
						//e.printStackTrace();
					}
				} else if ("setMessage".equals(action)) {
					try {
						PDialog.this.setMessage(args.getString(0));
					} catch (JSONException e) {
						//e.printStackTrace();
					}
				} else if ("setCancelable".equals(action)) {
					try {
						PDialog.this.setCancelable(args.getBoolean(0));
					} catch (JSONException e) {
						//e.printStackTrace();
					}
				} else {
					Log.e("CordovaLog", "PDialog: invalid action: " + action);
				}
			}
		});
		return true;
	}
	
	/**
	 * Initializing the progress dialog and set various parameters
	 * @param args
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
	 * @throws JSONException
	 */
	private void init(JSONArray args) throws JSONException {
		JSONObject argsObj = new JSONObject(args.getString(0));
		
		int theme = ProgressDialog.THEME_DEVICE_DEFAULT_LIGHT;
		if (argsObj.has("theme")) {
			String themeArg = argsObj.getString("theme");
			if ("TRADITIONAL".equals(themeArg)) {
				theme = ProgressDialog.THEME_TRADITIONAL;
			} else if ("DEVICE_DARK".equals(themeArg)) {
				theme = ProgressDialog.THEME_DEVICE_DEFAULT_DARK;
			} if ("HOLO_DARK".equals(themeArg)) {
				theme = ProgressDialog.THEME_HOLO_DARK;
			} if ("HOLO_LIGHT".equals(themeArg)) {
				theme = ProgressDialog.THEME_HOLO_LIGHT;
			}
		}
		
		int style = ProgressDialog.STYLE_SPINNER;
		if (argsObj.has("progressStyle")) {
			if ("HORIZONTAL".equals(argsObj.getString("progressStyle"))) {
				style = ProgressDialog.STYLE_HORIZONTAL;
			}
		}
		
		boolean cancelable = true;
		if (argsObj.has("cancelable")) {
			if (argsObj.getBoolean("cancelable") == false) {
				cancelable = false;
			}
		}
		
		String title = "";
		if (argsObj.has("title")) {
			title = argsObj.getString("title");
		}
		
		String message = "";
		if (argsObj.has("message")) {
			message = argsObj.getString("message");
		}
		
		PDialog.this.pDialogObj = null;
		PDialog.this.pDialogObj = new ProgressDialog(cordova.getActivity(), theme);
		PDialog.this.pDialogObj.setProgressStyle(style);
		PDialog.this.pDialogObj.setCancelable(cancelable);
		PDialog.this.pDialogObj.setCanceledOnTouchOutside(cancelable);
		PDialog.this.pDialogObj.setTitle(title);
		PDialog.this.pDialogObj.setMessage(message);
	}
	
	/**
	 * Show the progress dialog
	 */
	private void show() {
		PDialog.this.pDialogObj.show();
	}
	
	/**
	 * Dismiss the progress dialog	
	 */
	private void dismiss() {
		PDialog.this.pDialogObj.dismiss();
		PDialog.this.pDialogObj = null;
	}
	
	/**
	 * Set the value of the progress bar
	 * @param value
	 */
	private void setProgress(int value) {
		PDialog.this.pDialogObj.setProgress(value);
	}
	
	/**
	 * Set the value of the progress bar style
	 * @param style
	 * It can be one of the following:
	 * "SPINNER" (default), "HORIZONTAL"
	 */
	private void setProgressStyle(String style) {
		Log.v("CordovaLog", style);
		int progressStyle = ProgressDialog.STYLE_SPINNER;
		if ("HORIZONTAL".equals(style)) {
			progressStyle = ProgressDialog.STYLE_HORIZONTAL;
		}
		PDialog.this.pDialogObj.setProgressStyle(progressStyle);
	}
	
	/**
	 * Set the title of the progress dialog
	 * @param title
	 */
	private void setTitle(CharSequence title) {
		PDialog.this.pDialogObj.setTitle(title);
	}
	
	/**
	 * Set the message of the progress dialog
	 * @param message
	 */
	private void setMessage(CharSequence message) {
		PDialog.this.pDialogObj.setMessage(message);
	}
	
	/**
	 * Set whether the progress dialog is calncelable or not
	 * @param flag
	 */
	private void setCancelable(boolean flag) {
		PDialog.this.pDialogObj.setCancelable(flag);
		PDialog.this.pDialogObj.setCanceledOnTouchOutside(flag);
	}
	
}
