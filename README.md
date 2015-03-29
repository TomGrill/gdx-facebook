**Note: When you scrumble about this project you need to know that I am currently setting up the project for release. This is not ready to use yet!!**

# gdx-facebook
libGDX extension providing cross-platform support for Facebook Graph API.

## Version
Current status i **beta**. (It is not recommended to use this library in production releases.)

Current snapshot: **0.1.0**

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

## Installation
**Core**

Add this to your libGDX build.gradle
```
dependencies {
    ...
    compile "de.tomgrill.gdxfacebook:gdx-facebook-core:0.1.0-SNAPSHOT"
    ...
}
```

**Android**

Add this to your AndroidManifest.xml
```
<uses-permission android:name="android.permission.INTERNET" />

<application
	...
        <meta-data android:name="com.facebook.sdk.ApplicationId" android:value="@string/facebook_app_id" />
 		
 	<activity android:name="com.facebook.LoginActivity"
          android:theme="@android:style/Theme.Translucent.NoTitleBar"
          android:label="@string/app_name" />
	...
</application>
```
Add this to your /res/values/strings.xml and replace **0123456789** with your App ID.
```
<string name="facebook_app_id">0123456789</string>
```


Add this to your libGDX build.gradle
```
dependencies {
    ...
    compile 'com.android.support:appcompat-v7:21.0.+'
    compile ("com.facebook.android:facebook-android-sdk:3.23.1") {
	    exclude module: 'support-v4'
    }
    compile "de.tomgrill.gdxfacebook:gdx-facebook-android:0.1.0-SNAPSHOT"
    ...
}
```

**iOS**

Add this to your Info.plist.xml and replace **0123456789** (twice!, *fb*-prefix in 2nd part is correct!) and **YOUR_FACEBOOK_APP_NAM**E accordingly.
```
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
```
<forceLinkClasses>
    ....
    <pattern>de.tomgrill.gdxfacebook.ios.IOSFacebookAPI</pattern>
</forceLinkClasses>
```

Add this file https://github.com/TomGrill/gdx-facebook/blob/master/gdx-facebook-ios/libs/facebook-1.0.0-beta-03.jar to your libs folder under your libGDX ios subproject. Yes, I know this pretty huge :( 

Add this to your libGDX build.gradle
```
dependencies {
    ...
    compile "de.tomgrill.gdxfacebook:gdx-facebook-ios:0.1.0-SNAPSHOT"
    compile fileTree(dir: 'libs', include: '*.jar')
    ...
}
```

**Desktop**
```
dependencies {
    ...
    compile "de.tomgrill.gdxfacebook:gdx-facebook-desktop:0.1.0-SNAPSHOT"
    ...
}
```

## Usage
**Enable**
```
Gdx.app.setLogLevel(Application.LOG_DEBUG); // only if you want log output

FacebookConfig facebookConfig = new FacebookConfig();
facebookConfig.APP_ID = "0123456789"; // put your app ID here
facebookCOnfig.PERMISSIONS = "email,public_profile,user_friends"; // comma seperated list of permissions.

FacebookSystem facebookSystem = new FacebookSystem(facebookConfig);
FacebookAPI facebookAPI = facebookSystem.getFacebookAPI(); // returns null when current platform is not enabled/supported or anything else went wrong. I am looking for a better solution in the future since I do not like null returns :)
```

**Signin to Facebook**

This will start the signin process for the current platform. 
The first parameter enables the GUI signin when set true. 
When set false the login process will be done silently in the background. 

```
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
```

**Making a graph request**

This is a example for a "me" request. The Facebook Graph API allows a lot of requests. I did not cover all possibles. So there might be stuff that does not work yet.
```
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


