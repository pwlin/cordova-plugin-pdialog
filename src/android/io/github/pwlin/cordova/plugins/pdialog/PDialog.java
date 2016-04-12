/*
The MIT License (MIT)

Copyright (c) 2014 pwlin - pwlin05@gmail.com

Permission is hereby granted, free of charge, to any person obtaining a copy of
this software and associated documentation files (the "Software"), to deal in
the Software without restriction, including without limitation the rights to
use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
the Software, and to permit persons to whom the Software is furnished to do so,
subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package io.github.pwlin.cordova.plugins.pdialog;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;

//import android.util.Log;

import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

public class PDialog extends CordovaPlugin {

	// private static String LOG_TAG = "CordovaLog";
	private static ProgressDialog pDialogObj = null;

	/**
	 * Executes the request and returns PluginResult.
	 *
	 * @param action The action to execute.
	 * @param rawArgs The exec() arguments in JSON form.
	 * @param callbackContext The callback context used when calling back into JavaScript.
	 * @return Whether the action was valid.
	 */
	@Override
	public boolean execute(String action, String rawArgs, CallbackContext callbackContext) {
		/*
		 * Don't run any of these if the current activity is finishing in order
		 * to avoid android.view.WindowManager$BadTokenException crashing the
		 * app.
		 */
		if (this.cordova.getActivity().isFinishing()) {
			return true;
		}
		if (action.equals("init")) {
			this.init(rawArgs);
		} else if (action.equals("dismiss")) {
			this.dismiss();
		} else if (action.equals("setProgress")) {
			this.setProgress(rawArgs);
		} else if (action.equals("setTitle")) {
			this.setTitle(rawArgs);
		} else if (action.equals("setMessage")) {
			this.setMessage(rawArgs);
		} else if (action.equals("setCancelable")) {
			this.setCancelable(rawArgs);
		} else if (action.equals("setProgressNumberFormat")) {
            this.setProgressNumberFormat(rawArgs);
        }
		return true;
	}

	/**
	 * Initializing the progress dialog and set various parameters
	 *
	 * @param rawArgs
	 * @see https://github.com/pwlin/cordova-plugin-pdialog/blob/master/README.md
	 */
	private void init(final String rawArgs) {
		final CordovaInterface cordova = this.cordova;
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				if (PDialog.pDialogObj != null) {
					PDialog.pDialogObj.dismiss();
					PDialog.pDialogObj = null;
				}
				JSONObject argsObj = null;
				try {
					argsObj = new JSONObject(rawArgs);
				} catch (JSONException e) {
					// e.printStackTrace();
				}

				int theme = 5; // ProgressDialog.THEME_DEVICE_DEFAULT_LIGHT
				if (argsObj.has("theme")) {
					String themeArg = null;
					try {
						themeArg = argsObj.getString("theme");
					} catch (JSONException e) {
						// e.printStackTrace();
					}
					if ("TRADITIONAL".equals(themeArg)) {
						theme = 1; // ProgressDialog.THEME_TRADITIONAL
					} else if ("DEVICE_DARK".equals(themeArg)) {
						theme = 4; // ProgressDialog.THEME_DEVICE_DEFAULT_DARK
					}
					if ("HOLO_DARK".equals(themeArg)) {
						theme = 2; // ProgressDialog.THEME_HOLO_DARK
					}
					if ("HOLO_LIGHT".equals(themeArg)) {
						theme = 3; // ProgressDialog.THEME_HOLO_LIGHT
					}
				}

				int style = ProgressDialog.STYLE_SPINNER;
				if (argsObj.has("progressStyle")) {
					try {
						if ("HORIZONTAL".equals(argsObj.getString("progressStyle"))) {
							style = ProgressDialog.STYLE_HORIZONTAL;
						}
					} catch (JSONException e) {
						// e.printStackTrace();
					}
				}

				boolean cancelable = true;
				if (argsObj.has("cancelable")) {
					try {
						if (argsObj.getBoolean("cancelable") == false) {
							cancelable = false;
						}
					} catch (JSONException e) {
						// e.printStackTrace();
					}
				}

				String title = "";
				if (argsObj.has("title")) {
					try {
						title = argsObj.getString("title");
					} catch (JSONException e) {
						// e.printStackTrace();
					}
				}

				String message = "";
				if (argsObj.has("message")) {
					try {
						message = argsObj.getString("message");
					} catch (JSONException e) {
						// e.printStackTrace();
					}
				}

				String format = "";
				if (argsObj.has("format")) {
					try {
					    format = argsObj.getString("format");
					} catch (JSONException e) {
						// e.printStackTrace();
					}
				}

				PDialog.pDialogObj = new ProgressDialog(cordova.getActivity(), theme);
				PDialog.pDialogObj.setProgressStyle(style);
				PDialog.pDialogObj.setCancelable(cancelable);
				PDialog.pDialogObj.setCanceledOnTouchOutside(cancelable);
				PDialog.pDialogObj.setTitle(title);
				PDialog.pDialogObj.setMessage(message);
				PDialog.pDialogObj.setProgressNumberFormat(format);
				PDialog.pDialogObj.show();
			};
		};
		this.cordova.getActivity().runOnUiThread(runnable);
	}

	/**
	 * Dismiss the progress dialog
	 */
	private void dismiss() {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				PDialog.pDialogObj.dismiss();
			};
		};
		this.cordova.getActivity().runOnUiThread(runnable);
	}

	/**
	 * Set the value of the progress bar when progress style is "HORIZONTAL"
	 *
	 * @param rawArgs
	 */
	private void setProgress(final String rawArgs) {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				int value = Integer.parseInt(rawArgs);
				PDialog.pDialogObj.setProgress(value);
			};
		};
		this.cordova.getActivity().runOnUiThread(runnable);
	}

	/**
	 * Set the title of the progress dialog
	 *
	 * @param rawArgs
	 */
	private void setTitle(final String title) {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				PDialog.pDialogObj.setTitle(title.replaceAll("^\"|\"$", ""));
			};
		};
		this.cordova.getActivity().runOnUiThread(runnable);
	}

	/**
	 * Set the message of the progress dialog
	 *
	 * @param rawArgs
	 */
	private void setMessage(final String message) {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				PDialog.pDialogObj.setMessage(message.replaceAll("^\"|\"$", ""));
			};
		};
		this.cordova.getActivity().runOnUiThread(runnable);
	}

	/**
	 * Set the progress max of the progress dialog
	 *
	 * @param rawArgs
	 */
	private void setMax(final String max) {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				PDialog.pDialogObj.setMax(Integer.parseInt(max));
			};
		};
		this.cordova.getActivity().runOnUiThread(runnable);
	}

	/**
	 * Set whether the progress dialog is calncelable or not
	 *
	 * @param rawArgs
	 */
	private void setCancelable(final String rawArgs) {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				boolean flag = Boolean.parseBoolean(rawArgs);
				PDialog.pDialogObj.setCancelable(flag);
				PDialog.pDialogObj.setCanceledOnTouchOutside(flag);
			};
		};
		this.cordova.getActivity().runOnUiThread(runnable);
	}

    /**
	 * Change the format of the small text showing current and maximum units of progress.
	 *
	 * @param rawArgs
	 */
	private void setProgressNumberFormat(final String format) {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				PDialog.pDialogObj.setProgressNumberFormat(format);
			};
		};
		this.cordova.getActivity().runOnUiThread(runnable);
	}
}
