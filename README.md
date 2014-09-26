pDialog - A Native Android Progress Dialog Plugin for Cordova
======================

![pDialog1](http://i.imgur.com/LmAZa2d.png)


Requirements
-------------
- Android 4 or higher (**Make sure that you have at least `android:minSdkVersion="14"` in your AndroidManifest.xml**)
- Cordova 3.0 or higher

Installation
-------------
    cordova plugin add https://github.com/pwlin/cordova-plugin-pdialog
    
Simple Usage
-------------

    cordova.plugins.pDialog.init({
        theme : 'HOLO_DARK',
        progressStyle : 'SPINNER',
        cancelable : true,
        title : 'Please Wait...',
        message : 'Contacting server ...'
    });

show:

    cordova.plugins.pDialog.show();

dismiss:

    cordova.plugins.pDialog.dismiss();


Functions
----------

###.init(_arguments_)

Initialize the progress dialog and set various parameters.
These are the valid options:

`theme`: can be one of the following:
`TRADITIONAL`, `DEVICE_DARK`, `DEVICE_LIGHT` (default), `HOLO_DARK`, `HOLO_LIGHT`


`progressStyle`: can be one of the following:
`SPINNER` (default), `HORIZONTAL`

`cancelable`: `true` (default) or `false`

`title`: title of the progress dialog (defaults to empty)

`message`: contents of the progress dialog (defaults to empty)

    cordova.plugins.pDialog.init({
        theme : 'HOLO_DARK',
        progressStyle : 'SPINNER',
        cancelable : true,
        title : 'Please Wait...',
        message : 'Contacting server ...'
    });

###.show()

Show the progress dialog.

    cordova.plugins.pDialog.show();
    
###.dismiss()

Dismiss the progress dialog.

    cordova.plugins.pDialog.dismiss();

###.setProgress(_int_)

Set the value of the progress bar when progressStyle is `HORIZONTAL`.
    
    cordova.plugins.pDialog.init({progressStyle : 'HORIZONTAL', title: 'Please Wait...', message : 'Connecting to server...'});
    cordova.plugins.pDialog.setProgress(25);
    
![pDialog2](http://i.imgur.com/7k2docz.png)


###.setTitle(_title_)

Set the title of the progress dialog.
    
    cordova.plugins.pDialog.setTitle('Please Wait...');
    
###.setMessage(_message_)

Set the message of the progress dialog

    cordova.plugins.pDialog.setMessage('Connecting to server...');   
    
###.setCancelable(_boolean_)

Set whether the progress dialog is calncelable or not (defaults to `true`)

    cordova.plugins.pDialog.setCancelable(false); // The user can not cancel the progress dialog  
    

Chaining The Functions
-----------------------

It is also possible to chain the functions instead of calling them one by one.

    cordova.plugins.pDialog.init({
        theme: 'HOLO_DARK',
        title: 'Please Wait...',
        message : 'Connecting to server...',
        progressStyle: 'HORIZONTAL'
    })
    .show()
    .setProgress(25);


    