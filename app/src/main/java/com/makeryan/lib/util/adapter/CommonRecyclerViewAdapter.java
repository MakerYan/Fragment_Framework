package com.makeryan.lib.util.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableBoolean;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcodecraeer.xrecyclerview.SimpleViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by MakerYan on 16/8/7 23:14.
 * Email : light.yan@qq.com
 * Personal e-mail : light.yan@qq.com
 * project name : dormapp-android
 * package name : com.huanxiao.dorm.utilty.adapter
 */
public abstract class CommonRecyclerViewAdapter<T, D extends ViewDataBinding>
		extends RecyclerView.Adapter<SimpleViewHolder> {


	public static final int GROUP_TYPE = 801;

	public static final int CHILD_TYPE = 802;

	protected OnRecyclerViewItemClickListener<T> mItemClickListener;

	protected int mResId = 0;

	protected List<T> mDataList = new ArrayList<T>();

	protected ObservableBoolean mFunctionButtonVisibility = new ObservableBoolean(true);

	/**
	 * item 最大数量
	 */
	protected int mMaxCount = Integer.MAX_VALUE;

	public CommonRecyclerViewAdapter() {

	}

	public CommonRecyclerViewAdapter(OnRecyclerViewItemClickListener itemClickListener) {

		this.mItemClickListener = itemClickListener;
	}

	public void addItem(T item) {

		mDataList.add(item);
		notifyDataSetChanged();
	}

	public void addItem(int position, T item) {

		mDataList.add(
				position,
				item
					 );
		notifyDataSetChanged();
	}

	public void addDataList(List<T> dataList) {

		if (dataList != null) {
			mDataList.addAll(dataList);
			notifyDataSetChanged();
		}
	}

	public void addDataList(int position, List<T> dataList) {

		if (dataList != null && !dataList.isEmpty()) {
			if (mMaxCount == mDataList.size()) { // 如果集合达到了最大限制,则移除功能键
				mDataList.remove(null);
			}
			mDataList.addAll(
					position,
					dataList
							);
			notifyDataSetChanged();
		}
	}

	public List<T> getDataList() {

		return mDataList;
	}


	public void setDataList(T... array) {

		addDataList(Arrays.asList(array));
	}

	public void setDataList(List<T> dataList) {

		mDataList.clear();
		addDataList(dataList);
	}


	/**
	 * 是否已有此条数据
	 *
	 * @param t
	 *
	 * @return
	 */
	public boolean contains(T t) {

		if (mDataList == null || mDataList.size() <= 0) {
			return false;
		}
		return mDataList.contains(t);
	}

	/**
	 * 批量移除数据
	 *
	 * @param t
	 */
	public void removeItem(T t) {

		mDataList.remove(t);
		notifyDataSetChanged();
	}

	/**
	 * 批量移除数据
	 *
	 * @param list
	 */
	public void removeItems(List<T> list) {

		for (T t : list) {
			mDataList.remove(t);
		}
		notifyDataSetChanged();
	}

	public void removeItems(int position) {

		mDataList.remove(position);
		notifyDataSetChanged();
	}

	public int getMaxCount() {

		return mMaxCount;
	}

	public void setMaxCount(int maxCount) {

		mMaxCount = maxCount;
		notifyDataSetChanged();
	}

	public boolean maxCountVisibility() {

		return mDataList.size() <= mMaxCount;
	}

	public void clear() {

		mDataList.clear();
		notifyDataSetChanged();
	}

	public void replace(T item, int position) {

		mDataList.remove(position);
		mDataList.add(
				position,
				item
					 );
		notifyDataSetChanged();
	}

	protected void setRecursiveClick(View view) {
		// TODO: 2017/4/15

	}

	@Override
	public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

		Context context     = parent.getContext();
		int     layoutResID = getLayoutResID();
		int layoutId = mResId != 0 ?
				mResId :
				layoutResID;
		View convertView = LayoutInflater.from(context)
										 .inflate(
												 layoutId,
												 parent,
												 false
												 );
		D binding = DataBindingUtil.bind(convertView);
		SimpleViewHolder<T, D> holder = new SimpleViewHolder<T, D>(
				binding.getRoot(),
				binding
		);
		setRecursiveClick(binding.getRoot());
		return holder;
	}

	@Override
	public void onBindViewHolder(SimpleViewHolder holder, int position) {

		D binding = (D) holder.getBinding();
		holder.itemView.setTag(position);
		T data = getItem(position);
		holder.setDataObj(data);
		processingViewHolder(
				holder,
				binding,
				position,
				data
							);
		bindData(
				position,
				holder,
				binding,
				data
				);
		binding.executePendingBindings();
	}

	public OnRecyclerViewItemClickListener<T> getItemClickListener() {

		return mItemClickListener;
	}

	public void setItemClickListener(OnRecyclerViewItemClickListener<T> itemClickListener) {

		mItemClickListener = itemClickListener;
	}

	public T getItem(int position) {

		return mDataList.get(position);
	}

	@Override
	public int getItemCount() {

		if (mDataList == null) {
			return 0;
		}

		if (mDataList.size() > mMaxCount) {
			return mMaxCount;
		} else {
			return mDataList.size();
		}
	}

	/**
	 * 返回布局ID
	 *
	 * @return
	 */
	public abstract int getLayoutResID();

	/**
	 * 在bindData前执行
	 * 用途: RecyclerView 嵌套时处理数据
	 *
	 * @param holder
	 * @param binding
	 * @param position
	 * @param data
	 */
	public void processingViewHolder(SimpleViewHolder holder, D binding, int position, T data) {

	}

	/**
	 * 绑定数据
	 *
	 * @param position
	 * @param holder
	 * @param binding
	 * @param data
	 */
	public abstract void bindData(int position, SimpleViewHolder holder, D binding, T data);


	@Override
	public int getItemViewType(int position) {

		return super.getItemViewType(position);
		//		return getItemType(position);
	}

	@Override
	public void onAttachedToRecyclerView(RecyclerView recyclerView) {

		super.onAttachedToRecyclerView(recyclerView);
	}

	@Override
	public void onDetachedFromRecyclerView(RecyclerView recyclerView) {

		super.onDetachedFromRecyclerView(recyclerView);
	}

	/**
	 * 当Item出现时调用此方法
	 */
	@Override
	public void onViewAttachedToWindow(SimpleViewHolder holder) {

		super.onViewAttachedToWindow(holder);
	}

	/**
	 * 当Item被回收时调用此方法
	 */
	@Override
	public void onViewDetachedFromWindow(SimpleViewHolder holder) {

		super.onViewDetachedFromWindow(holder);
	}

	public interface OnRecyclerViewItemClickListener<T> {

		/**
		 * item 点击事件
		 *
		 * @param view
		 * 		点击的哪个View
		 * @param adapter
		 * 		这个Item属于哪个Adapter
		 * @param holder
		 * 		{@link com.jcodecraeer.xrecyclerview.SimpleViewHolder(View)}
		 * @param position
		 * 		点击的哪个位置
		 * @param data
		 * 		数据对象
		 */
		void onItemClick(View view, CommonRecyclerViewAdapter adapter, SimpleViewHolder holder, int position, T data);
	}
}
