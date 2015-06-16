# gdx-facebook
libGDX extension providing cross-platform support for Facebook Graph API.

## Version
Current status i **beta**. (It is not recommended to use this library in production releases.)

Current snapshot: **0.2.0**

Current stable: **not yet existing**

##Supported Platforms
Android, iOS, Desktop

## That is how Facebook Graph API works.

Basically you send an HTTP request to Facebook and you will get a result which is nothing more than a JSON string. Before you can use the Graph API you need a so called "Acces Token" for a user allowing you to make requests on the users behalf. The Facebook SDKs for Android and iOS manage the signin process (getting an "Access Token") and caching of the "Access Token". For a desktop project there is no SDK one could use so its written by myself (and might be buggy). 

**Read more:**

Login: https://developers.facebook.com/docs/facebook-login/

Graph API: https://developers.facebook.com/docs/graph-api/using-graph-api/

## Setting up your Facebook App
Go to https://developers.facebook.com/apps/ and create a new app. 

**Android**

1. Add a new platform Android.
2. Enable Single Sign On
3. Add the key hashes of your debug and certificate. Read this to find out how to do that. https://developers.facebook.com/docs/android/getting-started/

**Desktop**
nothing to do.

**iOS**

1. Add a new platform iOS
2. Enable Single Sign On
3. Add the Bundle ID. Must be same as your robovm.properties->app.id value.

## Installation
**Core**

Add this to your libGDX build.gradle
```gradle
project(":core") {
	dependencies {
	    ...
	    compile "de.tomgrill.gdxfacebook:gdx-facebook-core:0.2.0-SNAPSHOT"
	    ...
	}
}
```

**Android**

Add this to your AndroidManifest.xml
```xml
<uses-permission android:name="android.permission.INTERNET" />

<application
	...
        <meta-data android:name="com.facebook.sdk.ApplicationId" android:value="@string/facebook_app_id" />
 		
 	<activity android:name="com.facebook.FacebookActivity"
          android:configChanges=
                 "keyboard|keyboardHidden|screenLayout|screenSize|orientation"
          android:theme="@android:style/Theme.Translucent.NoTitleBar"
          android:label="@string/app_name" />
	...
</application>
```
Add this to your /res/values/strings.xml and replace **0123456789** with your App ID.

```xml
<string name="facebook_app_id">0123456789</string>
```

Add this to your libGDX build.gradle
```gradle
project(":android") {
	dependencies {
	    ...
	    compile 'com.android.support:appcompat-v7:21.0.3'
	    compile ("com.facebook.android:facebook-android-sdk:4.1.0") {
		    exclude module: 'support-v4'
	    }
	    compile "de.tomgrill.gdxfacebook:gdx-facebook-android:0.2.0-SNAPSHOT"
	    ...
	}
}
```

**iOS**

Add this to your Info.plist.xml and replace **0123456789** (twice!, *fb*-prefix in 2nd part is correct!) and **YOUR_FACEBOOK_APP_NAM**E accordingly.
```xml
<key>FacebookAppID</key>
<string>0123456789</string>
<key>FacebookDisplayName</key>
<string>YOUR_FACEBOOK_APP_NAME</string>
<key>CFBundleURLTypes</key>
<array>
<dict>
<key>CFBundleURLSchemes</key>
<array>
    <string>fb0123456789</string>
</array>
</dict>
</array>
```
Add this to your robovm.xml
```xml
<forceLinkClasses>
    ....
    <pattern>de.tomgrill.gdxfacebook.ios.IOSFacebookAPI</pattern>
</forceLinkClasses>
```

Add this to your IOSLauncher

```java
@Override
public void didBecomeActive (UIApplication application) {
    super.didBecomeActive(application);
    // You need to add this line, otherwise Facebook will not work correctly!
    FBSDKAppEvents.activateApp();
}

@Override
public boolean openURL (UIApplication application, NSURL url, String sourceApplication, NSPropertyList annotation) {
    // You need to add this line, otherwise Facebook will not work correctly!
	return FBSDKApplicationDelegate.getSharedInstance().openURL(application, url, sourceApplication, annotation);	
}
@Override
public boolean didFinishLaunching(UIApplication application, UIApplicationLaunchOptions launchOptions) {
	boolean finished = super.didFinishLaunching(application, launchOptions);
	FBSDKApplicationDelegate.getSharedInstance().didFinishLaunching(application, launchOptions);
	return finished;
}
```

Add this to your libGDX build.gradle
```gradle
project(":ios") {
	dependencies {
	    ...
	    compile "de.tomgrill.gdxfacebook:gdx-facebook-ios:0.2.0-SNAPSHOT"
	    compile "org.robovm:robopods-facebook-ios:1.0-SNAPSHOT"
	    ...
	}
}
```

**Desktop**
```gradle
project(":desktop") {
	dependencies {
	    ...
	    compile "de.tomgrill.gdxfacebook:gdx-facebook-desktop:0.2.0-SNAPSHOT"
	    ...
	}
}
```

## Usage

**View the libGDX Facebook sample app**
https://github.com/TomGrill/gdx-facebook-app


**Enable**
```java
Gdx.app.setLogLevel(Application.LOG_DEBUG); // only if you want log output

FacebookConfig facebookConfig = new FacebookConfig();
facebookConfig.APP_ID = "0123456789"; // put your app ID here
facebookCOnfig.PERMISSIONS = "email,public_profile,user_friends"; // comma seperated list of permissions.

FacebookSystem facebookSystem = new FacebookSystem(facebookConfig);
FacebookAPI facebookAPI = facebookSystem.getFacebookAPI(); 
```

**Signin to Facebook**

This will start the signin process for the current platform. 
The first parameter enables the GUI signin when set true. 
When set false the login process will be done silently in the background. 

```java
if(facebookAPI.isLoaded()) {
	facebookAPI.signin(true, new ResponseListener() {
		@Override
		public void success() {
			Gdx.app.log(TAG, "Signin: User logged in successfully.");
		}
	
		@Override
		public void error(ResponseError responseError) {
			Gdx.app.log(TAG, "Signin: Error: " + responseError.getMessage() + "(Error Code: " + responseError.getCode() + ")");
		}
	
		@Override
		public void cancel() {
			Gdx.app.log(TAG, "Signin: Something canceled login.");
		}
	});
{
```

**Making a graph request**

This is a example for a "me" request. The Facebook Graph API allows a lot of requests. I did not cover all possibles. So there might be stuff that does not work yet.

```java
facebookAPI.newGraphRequest("me", "access_token=" + facebookAPI.getAccessToken(), new HttpResponseListener() {

	@Override
	public void handleHttpResponse(HttpResponse httpResponse) {
		if (httpResponse.getStatus().getStatusCode() == 200) {

			String result = httpResponse.getResultAsString();
			Gdx.app.log(TAG, "Request successfull: Responsebody: " + result);
			
			JsonValue root = new JsonReader().parse(result);

			Gdx.app.log(TAG, "Name: " + root.getString("name"));
			Gdx.app.log(TAG, "ID: " + root.getString("id"));

		} else {
			Gdx.app.log(TAG, "Request with error. Something went wrong with the access token.");
		}
	}

	@Override
	public void failed(Throwable t) {
		Gdx.app.log(TAG, "Request failed. Reason: " + t.getMessage());
	}

	@Override
	public void cancelled() {
		Gdx.app.log(TAG, "Request cancelled. Reason unknown.");
	}
});
```

##Release History

Release history for major milestones (available via Maven):

*Version 0.1.0: Initial Release
*Version 0.2.0: Upgrade to IOS Facebook SDK 4.x - uses robopods-facebook now https://github.com/robovm/robovm-robopods/tree/master/facebook

##Reporting Issues

Something not working quite as expected? Do you need a feature that has not been implemented yet? Check the issue tracker and add a new one if your problem is not already listed. Please try to provide a detailed description of your problem, including the steps to reproduce it.

##Contributing

Awesome! If you would like to contribute with a new feature or a bugfix, fork this repo and submit a pull request.

##License

The gdx-facebook project is licensed under the Apache 2 License, meaning you can use it free of charge, without strings attached in commercial and non-commercial projects. We love to get (non-mandatory) credit in case you release a game or app using gdx-facebook!
