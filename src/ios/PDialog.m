/*
 The MIT License (MIT)
 
 Copyright (c) 2016 Orlando Leite - orlleite gmail
 
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

#import <QuartzCore/QuartzCore.h>

#import "PDialog.h"

#define DIALOG_WIDTH 180.0f
#define DIALOG_PADDING 8.0f
#define DIALOG_HEIGHT 90.0f
#define ANIMATION_TIME 0.3f

@implementation PDialog

- (void) init:(CDVInvokedUrlCommand*)command
{
	bool animated = YES;
	
	if( dialogContainer )
	{
		[dialogContainer removeFromSuperview];
		animated = NO;
	}
	
	if( oldDialogContainer )
	{
		[oldDialogContainer removeFromSuperview];
		oldDialogContainer = nil;
	}
	
	NSDictionary *args = [command.arguments objectAtIndex:0];
	
	UIView *root = self.webView.superview;
	
	dialogContainer = [[UIView alloc] initWithFrame:root.bounds];
	[root addSubview:dialogContainer];
	
	[dialogContainer addSubview:[self background:root.bounds]];
	
	if( [args[@"progressStyle"] isEqualToString:@"SPINNER"] )
	{
		[dialogContainer addSubview:[self spinnerDialog:root.bounds title:args[@"title"] message:args[@"message"]]];
	}
	else if( [args[@"progressStyle"] isEqualToString:@"HORIZONTAL"] )
	{
		[dialogContainer addSubview:[self horizontalDialog:root.bounds title:args[@"title"] message:args[@"message"]]];
	}
	
	if( animated )
	{
		dialogContainer.alpha = 0.0f;
		[UIView animateWithDuration:ANIMATION_TIME animations:^
		 {
			 dialogContainer.alpha = 1.0f;
		 }];
	}
	
	if( !singleFingerTap )
	{
		singleFingerTap = [[UITapGestureRecognizer alloc] initWithTarget:self
																  action:@selector(handleSingleTap:)];
	}
	
	[root addGestureRecognizer:singleFingerTap];
	
	CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@""];
	[self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)handleSingleTap:(UITapGestureRecognizer *)recognizer
{
	if( isCancellable ) [self dismiss];
}

- (UIView *) background:(CGRect) rect
{
	UIView *background = [[UIView alloc] initWithFrame:rect];
	background.autoresizingMask = UIViewAutoresizingFlexibleTopMargin | UIViewAutoresizingFlexibleBottomMargin
	| UIViewAutoresizingFlexibleLeftMargin | UIViewAutoresizingFlexibleRightMargin;
	
	background.backgroundColor = [UIColor blackColor];
	background.alpha = 0.5f;
	
	return background;
}

- (UIView *) spinnerDialog:(CGRect) rect title:(NSString *) title message:(NSString *) message
{
	UIView *dialog = [[UIView alloc] initWithFrame:CGRectMake(
															  (rect.size.width - DIALOG_WIDTH) * 0.5f,
															  (rect.size.height - DIALOG_HEIGHT) * 0.5f,
															  DIALOG_WIDTH, DIALOG_HEIGHT)];
	
	dialog.backgroundColor = [UIColor colorWithRed:0.0f green:0.0f blue:0.0f alpha:0.8f];
	dialog.layer.cornerRadius = 5.0f;
	dialog.layer.masksToBounds = YES;
	
	CGFloat height = 40.0f;
	UIActivityIndicatorView *actIndicator = [[UIActivityIndicatorView alloc] initWithActivityIndicatorStyle:UIActivityIndicatorViewStyleWhite];
	actIndicator.center = CGPointMake( DIALOG_WIDTH * 0.5f, 30.0f );
	actIndicator.alpha = 1.0f;
	[actIndicator startAnimating];
	//	initWithFrame:CGRectMake( ( DIALOG_WIDTH - 20.0f ) * 0.5f, 20.0f, 20.0f, 20.0f)];
	[dialog addSubview:actIndicator];
	
	height += 10.0f;
	
	UILabel *label = [self createLabel:title size:15.0f y:height];
	titleLabel = label;
	[dialog addSubview:label];
	height += label.frame.size.height + DIALOG_PADDING * 0.5f;
	
	label = [self createLabel:message size:13.0f y:height];
	messageLabel = label;
	[dialog addSubview:label];
	height += label.frame.size.height + DIALOG_PADDING + 10.0f;
	
	CGRect frame = dialog.frame;
	frame.size.height = height;
	frame.origin.y = (rect.size.height - height) * 0.5f;
	dialog.frame = frame;
	
	return dialog;
}

- (UIView *) horizontalDialog:(CGRect)rect title:(NSString *)title message:(NSString *)message
{
	UIView *dialog = [[UIView alloc] initWithFrame:CGRectMake(
															  (rect.size.width - DIALOG_WIDTH) * 0.5f,
															  (rect.size.height - DIALOG_HEIGHT) * 0.5f,
															  DIALOG_WIDTH, DIALOG_HEIGHT)];
	
	dialog.backgroundColor = [UIColor colorWithRed:0.0f green:0.0f blue:0.0f alpha:0.8f];
	dialog.layer.cornerRadius = 5.0f;
	dialog.layer.masksToBounds = YES;
	
	CGFloat height = 10.0f;
	
	UILabel *label = [self createLabel:title size:15.0f y:height];
	titleLabel = label;
	[dialog addSubview:label];
	height += label.frame.size.height + DIALOG_PADDING * 0.5f;
	
	label = [self createLabel:message size:13.0f y:height];
	messageLabel = label;
	[dialog addSubview:label];
	height += label.frame.size.height + DIALOG_PADDING;
	
	progressBar = [[UIProgressView alloc] initWithProgressViewStyle:UIProgressViewStyleDefault];
	[progressBar setFrame:CGRectMake( DIALOG_PADDING, height, DIALOG_WIDTH - DIALOG_PADDING * 2.0f, 2.0f)];
	[progressBar setProgress:0.0f];
	progressBar.trackTintColor = [UIColor grayColor];
	progressBar.progressTintColor = [UIColor whiteColor];
	[dialog addSubview:progressBar];
	
	height += 10.0f + DIALOG_PADDING;
	
	CGRect frame = dialog.frame;
	frame.size.height = height;
	frame.origin.y = (rect.size.height - height) * 0.5f;
	dialog.frame = frame;
	
	return dialog;
}

- (UILabel *) createLabel:(NSString *) text size:(CGFloat)fontSize y:(CGFloat)y
{
	UILabel *label = [[UILabel alloc] initWithFrame:CGRectMake(DIALOG_PADDING, y, DIALOG_WIDTH - DIALOG_PADDING * 2.0f, 10.0f)];
	label.adjustsFontSizeToFitWidth = NO;
	label.numberOfLines = 0;
	label.textAlignment = NSTextAlignmentCenter;
	label.backgroundColor = [UIColor clearColor];
	label.textColor = [UIColor whiteColor];
	
	label.font = [UIFont systemFontOfSize:fontSize];
	label.text = text;
	
	[label sizeToFit];
	
	CGRect frame = label.frame;
	frame.size.width = DIALOG_WIDTH - DIALOG_PADDING * 2.0f;
	label.frame = frame;
	
	return label;
}

- (void) dismiss:(CDVInvokedUrlCommand*)command
{
	[self dismiss];
	CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@""];
	[self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void) dismiss
{
	if( dialogContainer )
	{
		oldDialogContainer = dialogContainer;
		dialogContainer = titleLabel = messageLabel = progressBar = nil;
		
		[UIView animateWithDuration:ANIMATION_TIME
						 animations:^
		 {
			 oldDialogContainer.alpha = 0.0f;
		 }
						 completion:^(BOOL finished)
		 {
			 if( oldDialogContainer )
				 [oldDialogContainer removeFromSuperview];
		 }];
	}
}

- (void) setProgress:(CDVInvokedUrlCommand*)command
{
	if( progressBar )
	{
		CGFloat value = ((CGFloat) [[command.arguments objectAtIndex:0] integerValue])  / 100.0f;
		[progressBar setProgress:value animated:YES];
	}
	
	CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@""];
	[self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void) setTitle:(CDVInvokedUrlCommand*)command
{
	if( titleLabel )
	{
		titleLabel.text = [command.arguments objectAtIndex:0];
	}
	
	CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@""];
	[self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void) setMessage:(CDVInvokedUrlCommand*)command
{
	if( messageLabel )
	{
		messageLabel.text = [command.arguments objectAtIndex:0];
	}
	
	CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@""];
	[self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void) setMax:(CDVInvokedUrlCommand*)command
{
	CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@""];
	[self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void) setCancelable:(CDVInvokedUrlCommand*)command
{
	isCancellable = [[command.arguments objectAtIndex:0] boolValue];
	
	CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@""];
	[self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

@end
