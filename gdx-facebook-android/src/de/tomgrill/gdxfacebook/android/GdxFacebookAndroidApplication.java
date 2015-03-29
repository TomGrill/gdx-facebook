package de.tomgrill.gdxfacebook.android;

import android.content.Intent;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;

public class GdxFacebookAndroidApplication extends AndroidApplication {

	private UiLifecycleHelper fbUiLifecycleHelper;

	@Override
	protected void onCreate(Bundle state) {
		super.onCreate(state);
		fbUiLifecycleHelper = new UiLifecycleHelper(this, new Session.StatusCallback() {
			@Override
			public void call(Session session, SessionState state, Exception exception) {
			}
		});
		fbUiLifecycleHelper.onCreate(state);
	}

	@Override
	protected void onSaveInstanceState(Bundle state) {
		super.onSaveInstanceState(state);
		fbUiLifecycleHelper.onSaveInstanceState(state);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		fbUiLifecycleHelper.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onResume() {
		super.onResume();
		fbUiLifecycleHelper.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		fbUiLifecycleHelper.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		fbUiLifecycleHelper.onDestroy();
	}

}
