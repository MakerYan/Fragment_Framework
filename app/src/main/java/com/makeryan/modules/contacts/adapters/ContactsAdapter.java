package com.makeryan.modules.contacts.adapters;

import android.databinding.ViewDataBinding;
import android.view.View;

import com.jcodecraeer.xrecyclerview.SimpleViewHolder;
import com.makeryan.lib.BR;
import com.makeryan.lib.R;
import com.makeryan.lib.util.adapter.CommonRecyclerViewAdapter;


/**
 * Created by MakerYan on 2017/5/16 13:25.
 * Email : light.yan@qq.com
 * Personal e-mail : light.yan@qq.com
 * project name : Fragment_Framework
 * package name : com.makeryan.modules.contacts.adapters
 */
public class ContactsAdapter
		extends CommonRecyclerViewAdapter<String, ViewDataBinding> {

	public ContactsAdapter(OnRecyclerViewItemClickListener itemClickListener, View.OnClickListener functionClickListener) {

		super(itemClickListener,
			  functionClickListener);
	}

	/**
	 * @return item layout resource id
	 */
	@Override
	public int getLayoutResID() {

		return R.layout.listitem_contacts;
	}

	@Override
	public int getFunctionLayoutResId() {

		return R.layout.listitem_add;
	}

	/**
	 * 绑定数据
	 *
	 * @param position
	 * @param holder
	 * @param binding
	 * @param data
	 */
	@Override
	public void bindData(int position, SimpleViewHolder holder, ViewDataBinding binding, String data) {

		binding.setVariable(
				BR.listener,
				mItemClickListener
						   );
		binding.setVariable(
				BR.position,
				position
						   );
		binding.setVariable(
				BR.holder,
				holder
						   );
		binding.setVariable(
				BR.adapter,
				this
						   );
		binding.setVariable(
				BR.item,
				data
						   );
	}

	@Override
	public void bindFunctionData(int position, SimpleViewHolder holder, ViewDataBinding binding) {

		binding.setVariable(
				BR.listener,
				mFunctionClickListener
						   );
	}
}
