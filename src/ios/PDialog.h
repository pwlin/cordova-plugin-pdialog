//
//  PDialog.h
//  Agenda da Cidade
//
//  Created by Orlando Leite on 7/13/16.
//
//

#import <Cordova/CDV.h>

@interface PDialog : CDVPlugin
{
	UITapGestureRecognizer *singleFingerTap;
	
    UIView *dialogContainer;
	UIView *oldDialogContainer;
	UILabel *titleLabel;
	UILabel *messageLabel;
	UIProgressView *progressBar;
	BOOL isCancellable;
}

- (void) init:(CDVInvokedUrlCommand*)command;
- (void) dismiss:(CDVInvokedUrlCommand*)command;
- (void) setProgress:(CDVInvokedUrlCommand*)command;
- (void) setTitle:(CDVInvokedUrlCommand*)command;
- (void) setMessage:(CDVInvokedUrlCommand*)command;
- (void) setMax:(CDVInvokedUrlCommand*)command;
- (void) setCancelable:(CDVInvokedUrlCommand*)command;

@end
