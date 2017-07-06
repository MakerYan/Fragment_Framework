package com.makeryan.modules.contacts.mvp.presenter;

import android.app.Activity;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.jcodecraeer.xrecyclerview.SimpleViewHolder;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.makeryan.lib.BR;
import com.makeryan.lib.databinding.FragmentContactsBinding;
import com.makeryan.lib.event.EventBean;
import com.makeryan.lib.event.EventType;
import com.makeryan.lib.fragment.fragmentation.ISupport;
import com.makeryan.lib.mvp.presenter.BasePresenter;
import com.makeryan.lib.photopicker.PhotoPicker;
import com.makeryan.lib.util.adapter.CommonRecyclerViewAdapter;
import com.makeryan.lib.widget.DividerGridItemDecoration;
import com.makeryan.modules.contacts.adapters.ContactsAdapter;

import java.util.ArrayList;


/**
 * Created by MakerYan on 2017/5/16 10:07.
 * Email : light.yan@qq.com
 * Personal e-mail : light.yan@qq.com
 * project name : Fragment_Framework
 * package name : com.makeryan.modules.contacts.mvp.presenter
 */
public class ContactsPresenter
		extends BasePresenter
		implements CommonRecyclerViewAdapter.OnRecyclerViewItemClickListener<String> {

	protected FragmentContactsBinding mBinding;

	protected ContactsAdapter mAdapter;

	protected ArrayList<String> mPhotos = new ArrayList<>();

	public ContactsPresenter(ISupport iSupport) {

		super(iSupport);
	}

	@Override
	public void resume() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void stop() {

	}

	public void init(FragmentContactsBinding binding) {

		mBinding = binding;
		binding.setVariable(
				BR.listener,
				this
						   );
		Activity activity = getActivity();
		binding.xRV.setLayoutManager(new GridLayoutManager(
				activity,
				4
		));
		binding.xRV.addItemDecoration(new DividerGridItemDecoration(activity));
		mAdapter = new ContactsAdapter(
				this,
				this
		);
		mAdapter.setFcButtonStart(false);
		binding.xRV.setAdapter(mAdapter);
		binding.xRV.setLoadingListener(this);
	}

	@Override
	public void onLoadMore(XRecyclerView recyclerView, CommonRecyclerViewAdapter adapter) {

		recyclerView.loadMoreComplete();
	}

	@Override
	public void onRefresh(XRecyclerView recyclerView, CommonRecyclerViewAdapter adapter) {

		recyclerView.refreshComplete();
	}

	@Override
	public void onClick(View v) {

		PhotoPicker.builder()
				   .setSelected(mPhotos)
				   .setGridColumnCount(4)
				   .setShowGif(true)
				   .setPreviewEnabled(true)
				   .setShowCamera(true)
				   .start(getView());
	}

	/**
	 * item 点击事件
	 *
	 * @param view
	 * 		点击的哪个View
	 * @param adapter
	 * 		这个Item属于哪个Adapter
	 * @param holder
	 * 		{@link SimpleViewHolder ( View )}
	 * @param position
	 * 		点击的哪个位置
	 * @param data
	 */
	@Override
	public void onItemClick(View view, CommonRecyclerViewAdapter adapter, SimpleViewHolder holder, int position, String data) {

		ArrayList dataList = adapter.getDataList();
	}

	/**
	 * item 长按点击事件
	 *
	 * @param view
	 * 		点击的哪个View
	 * @param adapter
	 * 		这个Item属于哪个Adapter
	 * @param holder
	 * 		{@link SimpleViewHolder ( View )}
	 * @param position
	 * 		点击的哪个位置
	 * @param data
	 */
	@Override
	public void onItemLongClick(View view, CommonRecyclerViewAdapter adapter, SimpleViewHolder holder, int position, String data) {

	}

	@Override
	public void onEventBus(EventBean event) {

		if (event.getTag() == EventType.PHOTO_PICKER) {
			ArrayList<String> selectedPhotos = (ArrayList<String>) event.getData();
			mAdapter.setDataList(selectedPhotos);
		}
	}
}
