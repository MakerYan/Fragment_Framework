package com.makeryan.modules.contacts.ui.fragment;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.makeryan.lib.R;
import com.makeryan.lib.databinding.FragmentContactsBinding;
import com.makeryan.lib.fragment.BaseFragment;
import com.makeryan.lib.mvp.presenter.BasePresenter;
import com.makeryan.modules.contacts.mvp.presenter.ContactsPresenter;


/**
 * Created by MakerYan on 2017/5/16 10:07.
 * Email : light.yan@qq.com
 * Personal e-mail : light.yan@qq.com
 * project name : Fragment_Framework
 * package name : com.makeryan.modules.contacts.ui.fragment
 */
public class ContactsFragment
		extends BaseFragment {

	protected ContactsPresenter mPresenter;

	protected FragmentContactsBinding mBinding;

	public static ContactsFragment newInstance() {

		return newInstance(null);
	}

	public static ContactsFragment newInstance(Bundle args) {

		ContactsFragment fragment = new ContactsFragment();
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
				mPresenter = new ContactsPresenter(this) :
				mPresenter;
	}

	/**
	 * @return 布局Id
	 */
	@Override
	protected int getLayoutResID() {

		return R.layout.fragment_contacts;
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
				mBinding = FragmentContactsBinding.inflate(
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
