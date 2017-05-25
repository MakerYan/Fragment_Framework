package com.makeryan.modules.main.ui.fragment;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.makeryan.lib.BR;
import com.makeryan.lib.R;
import com.makeryan.lib.databinding.FragmentSecondBinding;
import com.makeryan.lib.fragment.BaseFragment;
import com.makeryan.lib.mvp.presenter.BasePresenter;

import java.util.Random;


/**
 * Created by MakerYan on 2017/5/15 22:27.
 * Email : light.yan@qq.com
 * Personal e-mail : light.yan@qq.com
 * project name : Fragment_Framework
 * package name : com.makeryan.modules.main.ui.fragment
 */
public class SecondFragment
		extends BaseFragment {

	public static final String COUNT = "count";

	protected FragmentSecondBinding mBinding;

	public static SecondFragment newInstance(long count) {

		SecondFragment fragment = new SecondFragment();
		Bundle         bundle   = new Bundle();
		bundle.putLong(
				COUNT,
				count
					  );
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

		return R.layout.fragment_second;
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
				mBinding = FragmentSecondBinding.inflate(
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

		mBinding.setVariable(
				BR.count,
				mExtras.getLong(COUNT)
							);
		mBinding.textView.setOnClickListener(v -> {

			start(SecondFragment.newInstance(new Random(100).nextInt()));
		});
	}
}
