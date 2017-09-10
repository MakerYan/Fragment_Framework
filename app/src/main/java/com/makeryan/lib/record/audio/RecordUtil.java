package com.makeryan.lib.record.audio;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;

public class RecordUtil {

	public static final String AUDIO_DIR = Environment.getExternalStorageDirectory()
													  .getAbsolutePath() + "/CNPC/audio";

	private String mName;

	private String mAudioPath; // 要播放的声音的路径

	private boolean recording = false;// 是否正在录音

	private boolean playing = false;// 是否正在播放

	public MediaRecorder mRecorder;

	public MediaPlayer mPlay;

	public boolean isRecording() {

		return recording;
	}

	public void setRecording(boolean recording) {

		this.recording = recording;
	}

	public boolean isPlaying() {

		return playing;
	}

	public void setPlaying(boolean playing) {

		this.playing = playing;
	}

	public String getmName() {

		return mName;
	}

	public void setmName(String mName) {

		this.mName = mName;
	}

	public String getmAudioPath() {

		return mAudioPath;
	}

	public void setmAudioPath(String mAudioPath) {

		this.mAudioPath = mAudioPath;
	}

	// 开始录音
	public void startRecord() {

		if (mRecorder != null) {
			return;
		}
		mRecorder = new MediaRecorder();
		mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		mRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
		mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
		mName = "audio_" + 1;
		mAudioPath = AUDIO_DIR + "/" + mName + ".amr";

		// 判断是否存在文件�?
		File folder = new File(AUDIO_DIR);
		if (!folder.exists()) {
			folder.mkdirs();
		}
		// 判断文件是否存在
		File file = new File(mAudioPath);
		if (file.exists()) {
			int count = 2;
			while (true) {
				mName = "audio_" + count;
				mAudioPath = AUDIO_DIR + "/" + mName + ".amr";
				file = new File(mAudioPath);
				if (file.exists()) {
					count++;
				} else {
					break;
				}
			}
		}
		mRecorder.setOutputFile(file.getAbsolutePath());
		recording = true;
		try {
			mRecorder.prepare();
			mRecorder.start();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 停止录音
	public String stopRecord() {

		if (mRecorder != null && recording) {
			//设置异常时不崩溃
			mRecorder.setOnErrorListener(null);
			try {
				mRecorder.stop();
			} catch (Exception e) {
				Log.d(
						"Cause",
						String.valueOf(e.getCause())
					 );
				Log.d(
						"Message",
						String.valueOf(e.getMessage())
					 );
			}
			mRecorder.release();
			mRecorder = null;
			recording = false;
		}

		return mAudioPath;
	}

	// 获取音量值，只是针对录音音量
	public int getVolume() {

		int volume = 0;
		// 录音
		if (mRecorder != null && recording) {
			volume = mRecorder.getMaxAmplitude() / 1;
			if (volume != 0)
			// volume = (int) (10 * Math.log(volume) / Math.log(10)) / 7;
			{
				volume = (int) (20 * Math.log10(volume));
			}
		}

		return volume;
	}

	// 开始播放
	public void startPlay(String path, boolean isLooping) {

		if (!path.equals("")) {
			mPlay = new MediaPlayer();
			try {
				mPlay.setDataSource(path);
				mPlay.prepare();
				mPlay.start();
				mPlay.setLooping(isLooping);
				playing = true;
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// 开始播放
	public void startPlay(String path, boolean isLooping, MediaPlayer.OnCompletionListener listener) {

		if (!path.equals("")) {
			mPlay = new MediaPlayer();
			try {
				mPlay.setDataSource(path);
				mPlay.prepare();
				mPlay.start();
				mPlay.setLooping(isLooping);
				mPlay.setOnCompletionListener(listener);
				playing = true;
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// 停止播放
	public void stopPlay() {

		if (mPlay != null && mPlay.isPlaying()) {
			mPlay.stop();
			mPlay.release();
			mPlay = null;
			playing = false;
		}
	}

}
