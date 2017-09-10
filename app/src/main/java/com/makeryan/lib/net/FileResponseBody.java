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
 * 扩展OkHttp的请求体，实现上传时的进度提示
 * Created by MakerYan on 2017/8/16 22:47.
 * Modify by MakerYan on 2017/8/16 22:47.
 * Email : light.yan@qq.com
 * project name : CNPC_Android
 * package name : com.makeryan.lib.net
 */
public class FileResponseBody<T>
		extends ResponseBody {

	public static final int UPDATE = 0x01;

	public static final String TAG = FileResponseBody.class.getName();

	private ResponseBody responseBody;

	private ProgressListener mListener;

	private BufferedSource bufferedSource;

	private Handler myHandler;

	public FileResponseBody(ResponseBody body, ProgressListener listener) {

		responseBody = body;
		mListener = listener;
		if (myHandler == null) {
			myHandler = new MyHandler();
		}
	}

	/**
	 * 将进度放到主线程中显示
	 */
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
					//接口返回
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
				//发送消息到主线程，ProgressModel为自定义实体类
				Message msg = Message.obtain();
				msg.what = UPDATE;
				msg.obj = new ProgressModel(
						totalBytesRead,
						contentLength(),
						totalBytesRead == contentLength()
				);
				myHandler.sendMessage(msg);

				return bytesRead;
			}
		};
	}
}
