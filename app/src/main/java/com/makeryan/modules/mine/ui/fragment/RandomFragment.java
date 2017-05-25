package com.makeryan.modules.mine.ui.fragment;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.makeryan.lib.R;
import com.makeryan.lib.databinding.FragmentRandomBinding;
import com.makeryan.lib.fragment.BaseFragment;
import com.makeryan.lib.mvp.presenter.BasePresenter;
import com.makeryan.modules.mine.mvp.presenter.RandomPresenter;


/**
 * Created by MakerYan on 2017/5/16 15:04.
 * Email : light.yan@qq.com
 * Personal e-mail : light.yan@qq.com
 * project name : Fragment_Framework
 * package name : com.makeryan.modules.mine.ui.fragment
 */
public class RandomFragment
		extends BaseFragment {

	protected RandomPresenter mPresenter;

	protected FragmentRandomBinding mBinding;

	public static RandomFragment newInstance() {

		return newInstance(null);
	}

	public static RandomFragment newInstance(Bundle args) {

		RandomFragment fragment = new RandomFragment();
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
				mPresenter = new RandomPresenter(this) :
				mPresenter;
	}

	/**
	 * @return 布局Id
	 */
	@Override
	protected int getLayoutResID() {

		return R.layout.fragment_random;
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
				mBinding = FragmentRandomBinding.inflate(
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
