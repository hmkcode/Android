/*
 * Copyright (C) 2014 The Android Open Source Project
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

package simple.tierscrollview;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

public class MessageReadReceiver extends BroadcastReceiver {
    private static final String TAG = MessageReadReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (MyMessagingService.READ_ACTION.equals(intent.getAction())) {
            int conversationId = intent.getIntExtra(MyMessagingService.CONVERSATION_ID, -1);
            if (conversationId != -1) {
                Log.d(TAG, "Conversation " + conversationId + " was read");
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
                notificationManager.cancel(conversationId);
            }
        }
    }
}
