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

package com.samyotech.smmatrimony;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.samyotech.smmatrimony.activity.dashboard.Dashboard;


public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {


        Log.e(TAG, "From: " + remoteMessage.getFrom());
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Message data payload: " + remoteMessage.getData());
            Log.e("varun", "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }


        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            sendNotification(remoteMessage.getNotification().getBody());
        }

    }


    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, Dashboard.class);
        intent.putExtra("screenName", "Notifiy");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
        bigTextStyle.setBigContentTitle("BehindBars");
        bigTextStyle.bigText(messageBody);


        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        notificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
       /* notificationBuilder.setContentTitle("BehindBars");
        notificationBuilder.setContentText(messageBody);*/
        notificationBuilder.setStyle(bigTextStyle);
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setSound(defaultSoundUri);
        notificationBuilder.setContentIntent(pendingIntent);

        if (!(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)) {
            //notificationBuilder.setColor(Color.WHITE)
            notificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
            notificationBuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        }

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0
/* ID of notification */
                , notificationBuilder.build());
    }


//e7q2YZRtQEA:APA91bGua6Fg6xP33iq-e0s7OEPDI_Rc19V7pYt_LR7u32OFwUuDlHCwP_sx1YVTUPywQGp4OlfPrD45QJThrH9Do2y1jgIq1yUputQLNATAah7IxHAIN9rMITHrMZI0zTi7yyyiXFWH

}