package com.makeryan.lib.mvp.presenter;//package com.xiaoxixi8.shiguangji.mvp.presenter;


import com.makeryan.lib.fragment.fragmentation.ISupport;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by MakerYan on 16/4/4 10:45.
 * Email: light.yan@qq.com
 */
public interface IBasePresenter<V extends ISupport> {

	/**
	 * Set or attach the view to this presenter
	 */
	void attachView(V view);

	/**
	 * Will be called if the view has been destroyed. Typically this method will be invoked from
	 * <code>Activity.detachView()</code> or <code>Fragment.onDestroyView()</code>
	 */
	void detachView(boolean retainInstance);

	void resume();

	void pause();

	void stop();

	void destroy();

	/**
	 * rx 异常, 回调方法
	 *
	 * @return
	 */
	Action1<Throwable> rxOnError();

	/**
	 * rx 完成, 回调方法
	 *
	 * @return
	 */
	Action0 rxOnCompleted();

	/**
	 * rx 异常, 回调方法
	 *
	 * @return
	 */
	Action1<Throwable> rxOnError(int tag);

	/**
	 * rx 完成, 回调方法
	 *
	 * @return
	 */
	Action0 rxOnCompleted(int tag);
}
