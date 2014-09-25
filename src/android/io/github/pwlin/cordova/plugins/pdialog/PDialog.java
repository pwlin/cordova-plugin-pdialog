package io.github.pwlin.cordova.plugins.pdialog;

import java.io.File;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;
import org.apache.cordova.CordovaResourceApi;

//@SuppressWarnings("unused")
public class PDialog extends CordovaPlugin {

	private ProgressDialog pDialogObj;

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
					PDialog.this.setProgress();
				} else {
					Log.e("CordovaLog", "PDialog: invalid action: " + action);
				}
			}
		});
		return true;
	}

	private void init(JSONArray args) throws JSONException {
		JSONObject argsObj = new JSONObject(args.getString(0));
		if (argsObj.has("param3")) {
			Log.v("CordovaLog", "param3 exists.");
			Log.v("CordovaLog", argsObj.getString("param3"));
		} else {
			Log.v("CordovaLog", "param3 does not exists.");
		}
		Log.v("CordovaLog", "PDialog: init function");
		PDialog.this.pDialogObj = new ProgressDialog(cordova.getActivity(), ProgressDialog.THEME_DEVICE_DEFAULT_DARK);
		//PDialog.this.pDialogObj.setTitle("");
		PDialog.this.pDialogObj.show();
	}

	private void show() {
		PDialog.this.pDialogObj.show();
	}

	private void dismiss() {
		PDialog.this.pDialogObj.dismiss();
	}

	private void setProgress() {

	}
	
	private void setTitle(CharSequence title) {
		PDialog.this.pDialogObj.setTitle(title);
	}
	
	private void setMessage(CharSequence message) {
		PDialog.this.pDialogObj.setMessage(message);
	}
	
}
