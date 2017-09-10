package com.makeryan.modules.container.ui.fragment;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.makeryan.lib.R;
import com.makeryan.lib.databinding.FragmentContainerBinding;
import com.makeryan.lib.fragment.BaseFragment;
import com.makeryan.lib.mvp.presenter.BasePresenter;
import com.makeryan.modules.container.mvp.presenter.ContainerPresenter;
import com.makeryan.modules.main.ui.fragment.MainFragment;

/**
 * Created by MakerYan on 2017/6/1 22:39.
 * Modify by MakerYan on 2017/6/1 22:39.
 * Email : light.yan@qq.com
 * project name : shanghaixuankai_android
 * package name : com.makeryan.modules.main
 */
public class ContainerFragment
		extends BaseFragment {

	protected FragmentContainerBinding mBinding;

	protected ContainerPresenter       mPresenter;

	protected MainFragment mFragment;

	public static ContainerFragment newInstance() {

		return newInstance(null);
	}

	public static ContainerFragment newInstance(Bundle args) {

		ContainerFragment fragment = new ContainerFragment();
		if (null != args) {
			fragment.setArguments(args);
		}
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = super.onCreateView(
				inflater,
				container,
				savedInstanceState
									  );

		if (savedInstanceState == null) {
			mFragment = MainFragment.newInstance();
		} else {
			// 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题
			// 这里我们需要拿到mFragments的引用,也可以通过getChildFragmentManager.getFragments()自行进行判断查找(效率更高些),
			// 用下面的方法查找更方便些
			mFragment = findChildFragment(MainFragment.class);
		}
		loadRootFragment(
				R.id.flContainer,
				mFragment
						);
		return view;
	}

	/**
	 * @return 初始化并返回当前Presenter
	 */
	@Override
	public BasePresenter getPresenter() {

		return mPresenter == null ?
				mPresenter = new ContainerPresenter(this) :
				mPresenter;
	}

	/**
	 * @return 布局Id
	 */
	@Override
	protected int getLayoutResID() {

		return R.layout.fragment_container;
	}

	/**
	 * 初始化DataBinding
	 *
	 * @param inflater
	 * @param parent
	 */
	@Override
	protected ViewDataBinding initDataBinding(LayoutInflater inflater, ViewGroup parent) {

		return mBinding == null ?
				mBinding = FragmentContainerBinding.inflate(
						inflater,
						parent,
						false
														   ) :
				mBinding;
	}

	/**
	 * 开始处理
	 */
	@Override
	protected void doAction() {

		mPresenter.init(mBinding);
	}
}
