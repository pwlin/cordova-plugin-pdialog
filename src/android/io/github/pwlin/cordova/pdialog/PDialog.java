package io.github.pwlin.cordova.pdialog;

import java.io.File;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Intent;
import android.net.Uri;
//import android.util.Log;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;
import org.apache.cordova.CordovaResourceApi;

public class PDialog extends CordovaPlugin {

    /**
     * Executes the request and returns a boolean.
     *
     * @param action
     * The action to execute.
     * @param args
     * JSONArry of arguments for the plugin.
     * @param callbackContext
     * The callback context used when calling back into JavaScript.
     * @return boolean.
     */
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        System.out.println("test1");
		if (action.equals("init")) {
			System.out.println("test2");
        } 
		return true;
    }
    
}