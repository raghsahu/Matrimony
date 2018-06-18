/**
 * Copyright 2016 Google Inc. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.samyotech.matrimony;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.samyotech.matrimony.interfaces.Consts;


public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = "MyFirebaseIIDService";
    String refreshedToken;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    @Override
    public void onTokenRefresh() {
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        try {
            refreshedToken = FirebaseInstanceId.getInstance().getToken();
            Log.d("Firbase id login", "Refreshed token: " + refreshedToken);
        } catch (Exception e) {
            e.printStackTrace();
        }
        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putString(Consts.FIREBASE_TOKEN, refreshedToken);
        editor.commit();


        sendRegistrationToServer(refreshedToken);
        SharedPreferences userDetails = MyFirebaseInstanceIDService.this.getSharedPreferences("MyPrefs", MODE_PRIVATE);
        Log.e(TAG, "my token: " + userDetails.getString(Consts.FIREBASE_TOKEN,""));


    }

    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.


    }
}
