package com.makeryan.lib.net;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.makeryan.lib.net.response.ProgressModel;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * Created by MakerYan on 2017/8/19 00:57.
 * Modify by MakerYan on 2017/8/19 00:57.
 * Email : light.yan@qq.com
 * project name : CNPC_Android
 * package name : com.makeryan.lib.net
 */
public class ProgressResponseBody
		extends ResponseBody {

	public static final int UPDATE = 0x01;

	public static final String TAG = ProgressResponseBody.class.getName();

	private ResponseBody responseBody;

	private ProgressListener mListener;

	private BufferedSource bufferedSource;

	private Handler myHandler;

	public ProgressResponseBody(ResponseBody body, ProgressListener listener) {

		responseBody = body;
		mListener = listener;
		if (myHandler == null) {
			myHandler = new MyHandler();
		}
	}

	class MyHandler
			extends Handler {

		public MyHandler() {

			super(Looper.getMainLooper());
		}

		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {
				case UPDATE:
					ProgressModel progressModel = (ProgressModel) msg.obj;
					if (mListener != null) {
						mListener.onProgress(
								progressModel.getProgress(),
								progressModel.getTotal(),
								progressModel.isDone()
											);
					}
					break;

			}
		}


	}

	@Override
	public MediaType contentType() {

		return responseBody.contentType();
	}

	@Override
	public long contentLength() {

		return responseBody.contentLength();
	}

	@Override
	public BufferedSource source() {

		if (bufferedSource == null) {
			bufferedSource = Okio.buffer(mySource(responseBody.source()));
		}
		return bufferedSource;
	}

	private Source mySource(Source source) {

		return new ForwardingSource(source) {

			long totalBytesRead = 0L;

			@Override
			public long read(Buffer sink, long byteCount)
					throws IOException {

				long bytesRead = super.read(
						sink,
						byteCount
										   );
				totalBytesRead += bytesRead != -1 ?
						bytesRead :
						0;

				Message msg = Message.obtain();
				msg.what = UPDATE;
				msg.obj = new ProgressModel(
						totalBytesRead,
						contentLength(),
						totalBytesRead == contentLength()
				);
				myHandler.sendMessage(msg);
				// KLog.d("currentBytes==" + totalBytesRead + "==contentLength==" + contentLength());
				return bytesRead;
			}
		};
	}
}