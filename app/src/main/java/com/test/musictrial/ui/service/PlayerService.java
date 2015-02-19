package com.test.musictrial.ui.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.test.musictrial.domain.ItunesItem;
import com.test.musictrial.domain.ServiceStatus;

import java.io.IOException;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by albertgf on 18/2/15.
 */
public class PlayerService extends Service implements MediaPlayer.OnCompletionListener,
        MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnSeekCompleteListener,
        MediaPlayer.OnInfoListener, MediaPlayer.OnBufferingUpdateListener {

    private MediaPlayer mediaPlayer = new MediaPlayer();
    private boolean isMediaPlayerStarted = false;

    private boolean isPausedInCall = false;
    private PhoneStateListener phoneStateListener;
    private TelephonyManager telephonyManager;

    // Intent intent;
    private final Handler handler = new Handler();


    private List<ItunesItem> mItemList;
    private int mListPosition = 0;

    // OnCreate
    @Override
    public void onCreate() {
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnErrorListener(this);
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnBufferingUpdateListener(this);
        mediaPlayer.setOnSeekCompleteListener(this);
        mediaPlayer.setOnInfoListener(this);
        mediaPlayer.reset();

        EventBus.getDefault().register(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        phoneStateListener = new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                switch (state) {
                    case TelephonyManager.CALL_STATE_OFFHOOK:
                    case TelephonyManager.CALL_STATE_RINGING:
                        if (mediaPlayer != null) {
                            pauseMedia();
                            isPausedInCall = true;
                        }
                        break;
                    case TelephonyManager.CALL_STATE_IDLE:
                        // Phone idle. Start playing.
                        if (mediaPlayer != null) {
                            if (isPausedInCall) {
                                isPausedInCall = false;
                                playMedia();
                            }
                        }
                        break;
                }
            }
        };
        telephonyManager.listen(phoneStateListener,
                PhoneStateListener.LISTEN_CALL_STATE);
        return START_STICKY;
    }

    public void onEvent(ServiceStatus status) {
        switch (status) {
            case PLAY:
                if (!isMediaPlayerStarted) {
                    if (!mediaPlayer.isPlaying()) {
                        try {
                            mediaPlayer.setDataSource(mItemList.get(mListPosition).getStreamUrl());
                            mediaPlayer.prepareAsync();
                            isMediaPlayerStarted = true;
                        } catch (IllegalArgumentException e) {
                            e.printStackTrace();
                        } catch (IllegalStateException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    if (!mediaPlayer.isPlaying()) {
                        playMedia();
                    } else {
                        pauseMedia();
                    }
                }
                break;
            case NEXT:
                nextMedia();
                break;
            case PREV:
                previousMedia();
                break;
            case PAUSE:
                if (mediaPlayer.isPlaying()) {
                    pauseMedia();
                }
                break;
        }
    }

    // --- onDestroy, stop media player and release. Also stop
    // phoneStateListener, notification, receivers...---
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("destroy player service", "destroy");
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
        }
        if (phoneStateListener != null) {
            telephonyManager.listen(phoneStateListener,
                    PhoneStateListener.LISTEN_NONE);
        }

        EventBus.getDefault().unregister(this);

    }

    @Override
    public void onBufferingUpdate(MediaPlayer arg0, int arg1) {
    }

    @Override
    public boolean onInfo(MediaPlayer arg0, int arg1, int arg2) {
        return false;
    }

    @Override
    public void onSeekComplete(MediaPlayer mp) {
        if (!mediaPlayer.isPlaying()) {
            playMedia();
        }
    }

    // ---Error processing ---
    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        switch (what) {
            case MediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK:
                Toast.makeText(this,
                        "MEDIA ERROR NOT VALID FOR PROGRESSIVE PLAYBACK " + extra,
                        Toast.LENGTH_SHORT).show();
                break;
            case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
                Toast.makeText(this, "MEDIA ERROR SERVER DIED " + extra,
                        Toast.LENGTH_SHORT).show();
                break;
            case MediaPlayer.MEDIA_ERROR_UNKNOWN:
                Toast.makeText(this, "MEDIA ERROR UNKNOWN " + extra,
                        Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer arg0) {
        //TODO send prepared
        playMedia();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        nextMedia();
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    public void playMedia() {
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    // Add for Telephony Manager
    public void pauseMedia() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    public void nextMedia() {
        try {
            nextSong();
            mediaPlayer.pause();
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.setDataSource(mItemList.get(mListPosition).getStreamUrl());
            // Prepare mediaplayer
            mediaPlayer.prepareAsync();
            isMediaPlayerStarted = true;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void previousMedia() {
        try {
            previousSong();
            mediaPlayer.pause();
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.setDataSource(mItemList.get(mListPosition).getStreamUrl());
            // Prepare mediaplayer
            mediaPlayer.prepareAsync();
            isMediaPlayerStarted = true;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void nextSong() {
        mListPosition = (mListPosition + 1) % mItemList.size();
    }

    public void previousSong() {
        int temp = ((mListPosition - 1) % mItemList.size());
        if (temp < 0) {
            mListPosition = temp + mItemList.size();
        } else {
            mListPosition = temp;
        }
    }
}
