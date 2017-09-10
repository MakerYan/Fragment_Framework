package com.makeryan.modules.container.mvp.presenter;

import com.makeryan.lib.databinding.FragmentContainerBinding;
import com.makeryan.lib.fragment.fragmentation.ISupport;
import com.makeryan.lib.fragment.fragmentation.SupportFragment;
import com.makeryan.lib.mvp.presenter.BasePresenter;


/**
 * Created by MakerYan on 2017/6/1 22:48.
 * Modify by MakerYan on 2017/6/1 22:48.
 * Email : light.yan@qq.com
 * project name : shanghaixuankai_android
 * package name : com.makeryan.modules.container.mvp.presenter
 */
public class ContainerPresenter
		extends BasePresenter {

	protected FragmentContainerBinding mBinding;

	public ContainerPresenter(ISupport iSupport) {

		super(iSupport);
	}

	@Override
	public void resume() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void stop() {

	}

	@Override
	public void destroy() {

		super.destroy();
	}

	@Override
	public void onSupportVisible(SupportFragment fragment) {

		super.onSupportVisible(fragment);
	}

	@Override
	public void onSupportInvisible(SupportFragment fragment) {

		super.onSupportInvisible(fragment);
	}

	public void init(FragmentContainerBinding binding) {

		mBinding = binding;
	}
}
