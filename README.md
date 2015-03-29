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
coming soon...

## Installation
Add this lines to your libGDX build.gradle

**Core**
```
dependencies {
    ...
    compile "de.tomgrill.gdxfacebook:gdx-facebook-core:0.1.0-SNAPSHOT"
    ...
}
```

**Android**
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
```
dependencies {
    ...
    compile "de.tomgrill.gdxfacebook:gdx-facebook-ios:0.1.0-SNAPSHOT"
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


