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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;

//import android.util.Log;

import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

public class PDialog extends CordovaPlugin {

	private ProgressDialog pDialogObj = null;

    /**
     * Executes the request and returns PluginResult.
     *
     * @param action            The action to execute.
     * @param args              JSONArray of arguments for the plugin.
     * @param callbackContext   The callback context used when calling back into JavaScript.
     * @return                  True when the action was valid, false otherwise.
     */
	public boolean execute(final String action, final JSONArray args, CallbackContext callbackContext) throws JSONException {
		/*
    	 * Don't run any of these if the current activity is finishing
    	 * in order to avoid android.view.WindowManager$BadTokenException
    	 * crashing the app. Just return true here since false should only
    	 * be returned in the event of an invalid action.
    	 */
    	if(this.cordova.getActivity().isFinishing()) {
    		return true;
    	}
    	
    	if (action.equals("init")) {
    		this.init(args.getString(0));
    	}
    	else if (action.equals("dismiss")) {
    		this.dismiss();
    	}
    	else if (action.equals("setProgress")) {
    		this.setProgress(args.getInt(0));
    	}
    	else if (action.equals("setTitle")) {
    		this.setTitle(args.getString(0));
    	}
    	else if (action.equals("setMessage")) {
    		this.setMessage(args.getString(0));
    	}
    	else if (action.equals("setCancelable")) {
    		this.setCancelable(args.getBoolean(0));
    	}
    	
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
	private synchronized void init(final String args) throws JSONException {
		if (this.pDialogObj != null) {
            this.pDialogObj.dismiss();
            this.pDialogObj = null;
        }
		final CordovaInterface cordova = this.cordova;
		Runnable runnable = new Runnable() {
            public void run() {
		
				JSONObject argsObj = null;
				try {
					argsObj = new JSONObject(args);
				} catch (JSONException e) {
					//e.printStackTrace();
				}
				
				int theme = 5; //ProgressDialog.THEME_DEVICE_DEFAULT_LIGHT
				if (argsObj.has("theme")) {
					String themeArg = null;
					try {
						themeArg = argsObj.getString("theme");
					} catch (JSONException e) {
						//e.printStackTrace();
					}
					if ("TRADITIONAL".equals(themeArg)) {
						theme = 1; //ProgressDialog.THEME_TRADITIONAL
					} else if ("DEVICE_DARK".equals(themeArg)) {
						theme = 4; //ProgressDialog.THEME_DEVICE_DEFAULT_DARK
					} if ("HOLO_DARK".equals(themeArg)) {
						theme = 2; //ProgressDialog.THEME_HOLO_DARK
					} if ("HOLO_LIGHT".equals(themeArg)) {
						theme = 3; //ProgressDialog.THEME_HOLO_LIGHT
					}
				}
				
				int style = ProgressDialog.STYLE_SPINNER;
				if (argsObj.has("progressStyle")) {
					try {
						if ("HORIZONTAL".equals(argsObj.getString("progressStyle"))) {
							style = ProgressDialog.STYLE_HORIZONTAL;
						}
					} catch (JSONException e) {
						//e.printStackTrace();
					}
				}
				
				boolean cancelable = true;
				if (argsObj.has("cancelable")) {
					try {
						if (argsObj.getBoolean("cancelable") == false) {
							cancelable = false;
						}
					} catch (JSONException e) {
						//e.printStackTrace();
					}
				}
				
				String title = "";
				if (argsObj.has("title")) {
					try {
						title = argsObj.getString("title");
					} catch (JSONException e) {
						//e.printStackTrace();
					}
				}
				
				String message = "";
				if (argsObj.has("message")) {
					try {
						message = argsObj.getString("message");
					} catch (JSONException e) {
						//e.printStackTrace();
					}
				}
				
				PDialog.this.pDialogObj = new ProgressDialog(cordova.getActivity(), theme);
				PDialog.this.pDialogObj.setProgressStyle(style);
				PDialog.this.pDialogObj.setCancelable(cancelable);
				PDialog.this.pDialogObj.setCanceledOnTouchOutside(cancelable);
				PDialog.this.pDialogObj.setTitle(title);
				PDialog.this.pDialogObj.setMessage(message);
				PDialog.this.pDialogObj.show();
            };
		};
		this.cordova.getActivity().runOnUiThread(runnable);
	}
	
	/**
	 * Dismiss the progress dialog	
	 */
	private synchronized void dismiss() {
		if (this.pDialogObj != null) {
            this.pDialogObj.dismiss();
        }
	}
	
	/**
	 * Set the value of the progress bar when progress style is "HORIZONTAL"
	 * @param value
	 */
	private void setProgress(final int value) {
		if (this.pDialogObj != null) {
			Runnable runnable = new Runnable() {
	            public void run() {
	            	PDialog.this.pDialogObj.setProgress(value);
	            };
			};
			this.cordova.getActivity().runOnUiThread(runnable);
		}
	}
	
	/**
	 * Set the title of the progress dialog
	 * @param title
	 */
	private synchronized void setTitle(final CharSequence title) {
		if (this.pDialogObj != null) {
			Runnable runnable = new Runnable() {
	            public void run() {
	            	PDialog.this.pDialogObj.setTitle(title);
	            };
			};
			this.cordova.getActivity().runOnUiThread(runnable);
		}
	}
	
	/**
	 * Set the message of the progress dialog
	 * @param message
	 */
	private synchronized void setMessage(final CharSequence message) {
		if (this.pDialogObj != null) {
			Runnable runnable = new Runnable() {
	            public void run() {
	            	PDialog.this.pDialogObj.setMessage(message);
	            };
			};
			this.cordova.getActivity().runOnUiThread(runnable);
		}
	}
	
	/**
	 * Set whether the progress dialog is calncelable or not
	 * @param flag
	 */
	private synchronized void setCancelable(final boolean flag) {
		if (this.pDialogObj != null) {
			Runnable runnable = new Runnable() {
	            public void run() {
	            	PDialog.this.pDialogObj.setCancelable(flag);
	        		PDialog.this.pDialogObj.setCanceledOnTouchOutside(flag);
	            };
			};
			this.cordova.getActivity().runOnUiThread(runnable);
		}
	}
	
}
