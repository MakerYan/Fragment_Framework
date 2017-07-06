package com.makeryan.modules.mine.ui.fragment;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.makeryan.lib.fragment.BaseFragment;
import com.makeryan.lib.mvp.presenter.BasePresenter;
import com.makeryan.modules.mine.mvp.presenter.MinePresenter;
import com.makeryan.lib.R;
import com.makeryan.lib.databinding.FragmentMineBinding;


/**
 * 我的
 * Created by MakerYan on 2017/6/1 22:46.
 * Modify by MakerYan on 2017/6/1 22:46.
 * Email : light.yan@qq.com
 * project name : shanghaixuankai_android
 * package name : com.makeryan.modules.user.ui.fragment
 */
public class MineFragment
		extends BaseFragment {

	protected MinePresenter mPresenter;

	protected FragmentMineBinding mBinding;

	public static MineFragment newInstance() {

		return newInstance(null);
	}

	public static MineFragment newInstance(Bundle args) {

		MineFragment fragment = new MineFragment();
		if (null != args) {
			fragment.setArguments(args);
		}
		return fragment;
	}

	/**
	 * @return 初始化并返回当前Presenter
	 */
	@Override
	public BasePresenter getPresenter() {

		return mPresenter == null ?
				mPresenter = new MinePresenter(this) :
				mPresenter;
	}

	/**
	 * @return 布局Id
	 */
	@Override
	protected int getLayoutResID() {

		return R.layout.fragment_mine;
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
				mBinding = FragmentMineBinding.inflate(
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
