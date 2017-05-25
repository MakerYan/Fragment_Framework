package com.makeryan.modules.main.ui.fragment;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.makeryan.lib.R;
import com.makeryan.lib.databinding.FragmentMainBinding;
import com.makeryan.lib.fragment.BaseFragment;
import com.makeryan.lib.fragment.fragmentation.SupportFragment;
import com.makeryan.lib.mvp.presenter.BasePresenter;
import com.makeryan.lib.pojo.TabBody;
import com.makeryan.lib.widget.tablayout.listener.ITabBody;
import com.makeryan.lib.widget.tablayout.listener.OnTabSelectListener;
import com.makeryan.modules.contacts.ui.fragment.ContactsFragment;
import com.makeryan.modules.main.mvp.presenter.MainPresenter;
import com.makeryan.modules.message.ui.fragment.MessageFragment;
import com.makeryan.modules.mine.ui.fragment.MineFragment;

import java.util.ArrayList;


/**
 * Created by MakerYan on 2017/5/15 21:01.
 * Email : light.yan@qq.com
 * Personal e-mail : light.yan@qq.com
 * project name : Fragment_Framework
 * package name : com.makeryan.lib
 */
public class MainFragment
		extends BaseFragment
		implements OnTabSelectListener {

	public static final String BEAN = "bean";

	public static final int MESSAGE = 0;

	public static final int CONTACTS = 1;

	public static final int MINE = 2;

	private String[] mTitles = {
			"消息",
			"通讯录",
			"我"
	};

	private int[] mIconUnselectIds = {
			R.mipmap.tab_speech_unselect,
			R.mipmap.tab_contact_unselect,
			R.mipmap.tab_me_unselect
	};

	private int[] mIconSelectIds = {
			R.mipmap.tab_speech_select,
			R.mipmap.tab_contact_select,
			R.mipmap.tab_me_select
	};

	private ArrayList<ITabBody> mTabBodys = new ArrayList<>();

	private SupportFragment[] mFragments = new SupportFragment[3];

	protected MainPresenter mPresenter;

	protected FragmentMainBinding mBinding;

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

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = super.onCreateView(
				inflater,
				container,
				savedInstanceState
									  );
		if (savedInstanceState == null) {
			mFragments[MESSAGE] = MessageFragment.newInstance();
			mFragments[CONTACTS] = ContactsFragment.newInstance();
			mFragments[MINE] = MineFragment.newInstance();

			loadMultipleRootFragment(
					R.id.fragment_container,
					CONTACTS,
					mFragments[MESSAGE],
					mFragments[CONTACTS],
					mFragments[MINE]
									);
		} else {
			// 这里库已经做了Fragment恢复,所以不需要额外的处理了, 不会出现重叠问题
			// 这里我们需要拿到mFragments的引用,也可以通过getChildFragmentManager.getFragments()自行进行判断查找(效率更高些),
			// 用下面的方法查找更方便些
			mFragments[MESSAGE] = findChildFragment(MessageFragment.class);
			mFragments[CONTACTS] = findChildFragment(ContactsFragment.class);
			mFragments[MINE] = findChildFragment(MineFragment.class);
		}
		return view;
	}

	/**
	 * @return 初始化并返回当前Presenter
	 */
	@Override
	public BasePresenter getPresenter() {

		return mPresenter == null ?
				mPresenter = new MainPresenter(this) :
				mPresenter;
	}

	/**
	 * @return 布局Id
	 */
	@Override
	protected int getLayoutResID() {

		return R.layout.fragment_main;
	}

	@Override
	protected void beforeSetContentView() {

		super.beforeSetContentView();
		for (int i = 0; i < mTitles.length; i++) {
			mTabBodys.add(new TabBody(
					mTitles[i],
					mIconSelectIds[i],
					mIconUnselectIds[i]
			));
		}
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
				mBinding = FragmentMainBinding.inflate(
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

		mBinding.commonTabLayout.setTabData(mTabBodys);
		mBinding.commonTabLayout.setOnTabSelectListener(this);
		mPresenter.init(mBinding);
	}

	@Override
	protected void onEnterAnimationEnd(Bundle savedInstanceState) {

		super.onEnterAnimationEnd(savedInstanceState);
		mBinding.commonTabLayout.setCurrentPager(CONTACTS);
	}

	@Override
	public void onTabSelect(int position, int prePosition) {

		showHideFragment(
				mFragments[position],
				mFragments[prePosition]
						);
	}

	@Override
	public void onTabReselect(int position) {

	}

	@Override
	public void pop() {

		getActivity().finish();
	}
}