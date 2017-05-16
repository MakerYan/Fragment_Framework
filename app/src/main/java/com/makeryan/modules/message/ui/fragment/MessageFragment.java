package com.makeryan.modules.message.ui.fragment;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.makeryan.lib.R;
import com.makeryan.lib.databinding.FragmentMessageBinding;
import com.makeryan.lib.fragment.BaseFragment;
import com.makeryan.modules.message.mvp.presenter.MessagePresenter;


/**
 * Created by MakerYan on 2017/5/16 10:07.
 * Email : light.yan@qq.com
 * Personal e-mail : light.yan@qq.com
 * project name : Fragment_Framework
 * package name : com.makeryan.modules.contacts.ui.fragment
 */
public class MessageFragment
		extends BaseFragment {

	protected MessagePresenter mPresenter;

	protected FragmentMessageBinding mBinding;

	public static MessageFragment newInstance() {

		return newInstance(null);
	}

	public static MessageFragment newInstance(Bundle args) {

		MessageFragment fragment = new MessageFragment();
		if (null != args) {
			fragment.setArguments(args);
		}
		return fragment;
	}

	/**
	 * @return 初始化并返回当前Presenter
	 */
	@Override
	protected MessagePresenter getPresenter() {

		return mPresenter == null ?
				mPresenter = new MessagePresenter(this) :
				mPresenter;
	}

	/**
	 * @return 布局Id
	 */
	@Override
	protected int getLayoutResID() {

		return R.layout.fragment_message;
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
				mBinding = FragmentMessageBinding.inflate(
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

	@Override
	public void pop() {

		getActivity().finish();
	}
}
