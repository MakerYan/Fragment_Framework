package com.makeryan.modules.message.ui.fragment;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.makeryan.lib.R;
import com.makeryan.lib.databinding.FragmentSiblingBinding;
import com.makeryan.lib.fragment.BaseFragment;
import com.makeryan.lib.mvp.presenter.BasePresenter;
import com.makeryan.modules.main.ui.fragment.MainFragment;
import com.makeryan.modules.message.mvp.presenter.SiblingPresenter;
import com.makeryan.modules.message.vo.MessageVO;


/**
 * Created by MakerYan on 2017/5/16 12:10.
 * Email : light.yan@qq.com
 * Personal e-mail : light.yan@qq.com
 * project name : Fragment_Framework
 * package name : com.makeryan.modules.message.ui.fragment
 */
public class SiblingFragment
		extends BaseFragment {

	protected SiblingPresenter mPresenter;

	protected FragmentSiblingBinding mBinding;

	public static SiblingFragment newInstance(@Nullable MessageVO params) {

		SiblingFragment fragment = new SiblingFragment();
		Bundle          bundle   = new Bundle();
		bundle.putSerializable(
				MainFragment.BEAN,
				params
							  );
		fragment.setArguments(bundle);
		return fragment;
	}

	/**
	 * @return 初始化并返回当前Presenter
	 */
	@Override
	public BasePresenter getPresenter() {

		return mPresenter == null ?
				mPresenter = new SiblingPresenter(this) :
				mPresenter;
	}

	/**
	 * @return 布局Id
	 */
	@Override
	protected int getLayoutResID() {

		return R.layout.fragment_sibling;
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
				mBinding = FragmentSiblingBinding.inflate(
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
