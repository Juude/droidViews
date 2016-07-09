/*
 * 
 * Copyright (c) 2015, alipay.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.euler.andfix;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.euler.test.A;

import net.juude.andfix.R;

import java.io.File;
import java.net.URISyntaxException;

/**
 * sample activity
 * 
 * @author luohou
 * @author sanping.li@alipay.com
 * 
 */
public class MainActivity extends Activity {
	private static final String TAG = "euler";
	private static final int FILE_SELECT_CODE = 1000;
	private static final int REQUEST_PERMISSION_STORAGE = 9999;
	private Button mButtonAddPatch;
	private Button mButtonA;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.e(TAG, A.a("good-fix"));
		Log.e(TAG, "" + new A().b("s1", "s2"));
		Log.e(TAG, "" + new A().getI());
		setContentView(R.layout.activity_main);
		mButtonAddPatch = (Button) findViewById(R.id.button_add_patch);
		mButtonAddPatch.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				checkShowFileChooser();
			}
		});
		mButtonA = (Button) findViewById(R.id.button_A);
		mButtonA.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				A.a("hehe");
			}
		});
	}

	private void checkShowFileChooser() {
		if (Build.VERSION.SDK_INT >= 23) {
			switch (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)) {
				case PackageManager.PERMISSION_DENIED:
					if (!shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
						requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION_STORAGE);
					} else {
						Toast.makeText(this, "denied", Toast.LENGTH_LONG).show();
					}
					break;
				case PackageManager.PERMISSION_GRANTED:
					showFileChooser();
					break;
			}
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == REQUEST_PERMISSION_STORAGE) {
			if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				showFileChooser();
			}
		}
	}

	private void showFileChooser() {

		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("*/*");
		intent.addCategory(Intent.CATEGORY_OPENABLE);

		try {
			startActivityForResult(
					Intent.createChooser(intent, "Select a File to Upload"),
					FILE_SELECT_CODE);
		} catch (android.content.ActivityNotFoundException ex) {
			// Potentially direct the user to the Market with a Dialog
			Toast.makeText(this, "Please install a File Manager.",
					Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == FILE_SELECT_CODE && resultCode == RESULT_OK) {
			Uri uri = data.getData();
			Log.d(TAG, "File Uri: " + uri.toString());
			// Get the path
			String path = null;
			try {
				path = getPath(this, uri);
				Log.d(TAG, "File Path: " + path);
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}
	}

	public static String getPath(Context context, Uri uri) throws URISyntaxException {
		File f = new File(uri.toString());
		return f.getAbsolutePath();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		android.os.Process.killProcess(android.os.Process.myPid());
	}

}
