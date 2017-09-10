package com.makeryan.modules.main.ui.fragment;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.makeryan.lib.fragment.BaseFragment;
import com.makeryan.lib.mvp.presenter.BasePresenter;


/**
 * Created by MakerYan on 2017/9/10 22:31.
 * Modify by MakerYan on 2017/9/10 22:31.
 * Email : light.yan@qq.com
 * project name : Fragment_Framework
 * package name : com.makeryan.modules.main.ui.fragment
 */
public class MainFragment
		extends BaseFragment {

	public static MainFragment newInstance() {

		return newInstance(null);
	}

	public static MainFragment newInstance(Bundle args) {

		MainFragment fragment = new MainFragment();
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

		return null;
	}

	/**
	 * @return 布局Id
	 */
	@Override
	protected int getLayoutResID() {

		return 0;
	}

	/**
	 * 初始化DataBinding
	 *
	 * @param inflater
	 * @param parent
	 */
	@Override
	protected ViewDataBinding initDataBinding(LayoutInflater inflater, ViewGroup parent) {

		return null;
	}

	/**
	 * 开始处理
	 */
	@Override
	protected void doAction() {

	}
}
