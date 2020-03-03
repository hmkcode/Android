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

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.CarExtender;
import android.support.v4.app.NotificationCompat.CarExtender.UnreadConversation;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.RemoteInput;

public class MyMessagingService extends Service {
    private static final String TAG = MyMessagingService.class.getSimpleName();

    private int getViewToInflate() {

        if (isPortrait(orientation)) {
            // check mode
            if (this.screenMode == AppConfig.SCREEN_MODE_TABLET) {
                // means it is tablet with portrait
                //            log("inflating portrait tablet");
                viewToInflate = R.layout.song_card_sw600;
            } else {
                // mobile with portrait
                //          log("inflating portrait mobile");
                viewToInflate = R.layout.song_card_normal;
            }
        } else {
            if (this.screenMode == AppConfig.SCREEN_MODE_TABLET) {
                // means it is tablet with landscape
                //        log("inflating landscape tablet");
                viewToInflate = R.layout.song_card_land_sw600;
            } else {
                // mobile with landscape
                //      log("inflating landscape mobile");
                viewToInflate = R.layout.song_card_normal_land;
            }

        }
        return viewToInflate;
    }

    private int getHeaderViewToInflate() {

        int _temp_header_viewID = -1;

        if (isPortrait(orientation)) {
            // check mode
            if (this.screenMode == AppConfig.SCREEN_MODE_TABLET) {
                // means it is tablet with portrait
                //    log("[H] inflating portrait tablet");
                _temp_header_viewID = R.layout.section_header_layout_sw600;
            } else {
                // mobile with portrait
                //  log("[H] inflating portrait mobile");
                _temp_header_viewID = R.layout.section_header_layout;
            }
        } else {
            if (this.screenMode == AppConfig.SCREEN_MODE_TABLET) {
                // means it is tablet with landscape
                //log("[H] inflating landscape tablet");
                _temp_header_viewID = R.layout.section_header_layout_land_sw600;
            } else {
                // mobile with landscape
                //log("[H] inflating landscape mobile");
                _temp_header_viewID = R.layout.section_header_layout_land;
            }

        }

        return _temp_header_viewID;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Typeface fontawesome = FontManager.getInstance(context).getTypeFace(FontManager.FONT_AWESOME);
        Typeface ralewayTfRegular = FontManager.getInstance(context).getTypeFace(FontManager.FONT_RALEWAY_REGULAR);
        Typeface ralewayTfBold = FontManager.getInstance(context).getTypeFace(FontManager.FONT_RALEWAY_BOLD);

        if (holder instanceof SongViewHolder) {

            // bind section data
            //  log("binding song " + position);
            Song song = songs.get(typeViewList.get(position).index);
            ((SongViewHolder) holder).title.setText(song.Title);
            ((SongViewHolder) holder).uploader.setText(song.UploadedBy);
            ((SongViewHolder) holder).views.setText(song.UserViews);
//            ((SongViewHolder) holder).popMenuBtn.setText("\uF142");
            ((SongViewHolder) holder).content_length.setText(song.TrackDuration);
            // loads thumbnail in async fashion
            if (connected()) Picasso.with(context)
                    .load(song.Thumbnail_url)
                    .into(((SongViewHolder) holder).thumbnail);

            // setting typeface to fonta
            ((SongViewHolder) holder).downloadBtn.setTypeface(fontawesome);
            ((SongViewHolder) holder).uploader_icon.setTypeface(fontawesome);
            ((SongViewHolder) holder).views_icon.setTypeface(fontawesome);
//            ((SongViewHolder) holder).popMenuBtn.setTypeface(fontawesome);
            //setting typeface to raleway
            ((SongViewHolder) holder).title.setTypeface(ralewayTfRegular);
            ((SongViewHolder) holder).content_length.setTypeface(ralewayTfRegular);
            ((SongViewHolder) holder).uploader.setTypeface(ralewayTfRegular);
            ((SongViewHolder) holder).views.setTypeface(ralewayTfRegular);

        } else {
            // binnd song data
            //log("binding header " + position);
            StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
            layoutParams.setFullSpan(true);
            String section_format = typeViewList.get(position).sectionTitle.substring(0,1).toUpperCase()+typeViewList.get(position).sectionTitle.substring(1);
            ((SectionTitleViewHolder) holder).sectionTitle.setText(section_format);
            ((SectionTitleViewHolder) holder).sectionTitle.setTypeface(ralewayTfRegular);
        }

    }

    @Override
    public int getItemCount() {
        return typeViewList.size();
    }

    @Override
    public int getItemViewType(int position) {
        //log("view type at"+position+" = "+typeViewList.get(position).viewType);
        return typeViewList.get(position).viewType;
    }

    public void setOnTaskAddListener(TaskAddListener listener) {
        this.taskAddListener = listener;
    }

    public void setOnStreamingSourceAvailable(OnStreamingSourceAvailableListener listener) {
        this.streamingSourceAvailableListener = listener;
    }

    public void addDownloadTask(final String video_id, final String file_name) {

        if (this.taskAddListener != null)
            this.taskAddListener.onTaskTapped();

        TaskHandler
                .getInstance(context)
                .addTask(file_name, video_id);

        if (this.taskAddListener != null)
            this.taskAddListener.onTaskAddedToQueue(file_name);
    }

    public void setScreenMode(int mode) {
        this.screenMode = mode;
    }

    private boolean connected() {
        return ConnectivityUtils.getInstance(context).isConnectedToNet();
    }

    public interface OnStreamingSourceAvailableListener {
        void onPrepared(String uri);
        void optioned();
    }

    public static final String READ_ACTION =
            "simple.tierscrollview.ACTION_MESSAGE_READ";
    public static final String REPLY_ACTION =
            "simple.tierscrollview.ACTION_MESSAGE_REPLY";
    public static final String CONVERSATION_ID = "conversation_id";
    public static final String EXTRA_VOICE_REPLY = "extra_voice_reply";

    private NotificationManagerCompat mNotificationManager;

    private final Messenger mMessenger = new Messenger(new IncomingHandler());

    /**
     * Handler of incoming messages from clients.
     */
    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            sendNotification(1, "This is a sample message", "John Doe",
                    System.currentTimeMillis());
        }
    }

    @Override
    public void onCreate() {
        mNotificationManager = NotificationManagerCompat.from(getApplicationContext());
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    private Intent createIntent(int conversationId, String action) {
        return new Intent()
                .addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES)
                .setAction(action)
                .putExtra(CONVERSATION_ID, conversationId);
    }

    private void sendNotification(int conversationId, String message,
                                  String participant, long timestamp) {
        // A pending Intent for reads
        PendingIntent readPendingIntent = PendingIntent.getBroadcast(getApplicationContext(),
                conversationId,
                createIntent(conversationId, READ_ACTION),
                PendingIntent.FLAG_UPDATE_CURRENT);

        // Build a RemoteInput for receiving voice input in a Car Notification
        RemoteInput remoteInput = new RemoteInput.Builder(EXTRA_VOICE_REPLY)
                .setLabel("Reply by voice")
                .build();

        // Building a Pending Intent for the reply action to trigger
        PendingIntent replyIntent = PendingIntent.getBroadcast(getApplicationContext(),
                conversationId,
                createIntent(conversationId, REPLY_ACTION),
                PendingIntent.FLAG_UPDATE_CURRENT);

        // Create the UnreadConversation and populate it with the participant name,
        // read and reply intents.
        UnreadConversation.Builder unreadConvBuilder =
                new UnreadConversation.Builder(participant)
                        .setLatestTimestamp(timestamp)
                        .setReadPendingIntent(readPendingIntent)
                        .setReplyAction(replyIntent, remoteInput);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext())
                // Set the application notification icon:
                //.setSmallIcon(R.drawable.notification_icon)

                // Set the large icon, for example a picture of the other recipient of the message
                //.setLargeIcon(personBitmap)

                .setContentText(message)
                .setWhen(timestamp)
                .setContentTitle(participant)
                .setContentIntent(readPendingIntent)
                .extend(new CarExtender()
                        .setUnreadConversation(unreadConvBuilder.build()));

        mNotificationManager.notify(conversationId, builder.build());
    }
}
