package com.makeryan.lib.util.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
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

	public static final int FC_TYPE = 802;

	public static final int CHILD_TYPE = 803;

	protected OnRecyclerViewItemClickListener<T> mItemClickListener;

	protected ArrayList<T> mDataList = new ArrayList<T>();

	/**
	 * item 最大数量
	 */
	protected int mMaxCount = Integer.MAX_VALUE;

	protected boolean mIsFcButton = false;

	protected boolean mIsFcButtonStart = false;

	protected int mSelectedPosition = -1;

	protected View.OnClickListener mFunctionClickListener;

	public boolean isFcButton() {

		return mIsFcButton;
	}

	public void setFcButton(boolean fcButton) {

		mIsFcButton = fcButton;
	}

	public boolean isFcButtonStart() {

		return mIsFcButtonStart;
	}

	public void setFcButtonStart(boolean fcButtonStart) {

		mIsFcButtonStart = fcButtonStart;
	}

	public CommonRecyclerViewAdapter() {

	}

	public CommonRecyclerViewAdapter(OnRecyclerViewItemClickListener itemClickListener) {

		this.mItemClickListener = itemClickListener;
	}

	public CommonRecyclerViewAdapter(OnRecyclerViewItemClickListener itemClickListener, boolean hasFcButton) {

		this.mItemClickListener = itemClickListener;
		this.mIsFcButton = hasFcButton;
	}

	public CommonRecyclerViewAdapter(OnRecyclerViewItemClickListener itemClickListener, boolean hasFcButton, int maxCount) {

		this.mItemClickListener = itemClickListener;
		this.mIsFcButton = hasFcButton;
		this.mMaxCount = maxCount;
	}

	public CommonRecyclerViewAdapter(OnRecyclerViewItemClickListener itemClickListener, View.OnClickListener functionClickListener) {

		this.mItemClickListener = itemClickListener;
		this.mFunctionClickListener = functionClickListener;
	}

	public CommonRecyclerViewAdapter(OnRecyclerViewItemClickListener itemClickListener, View.OnClickListener functionClickListener, boolean hasFcButton) {

		this.mItemClickListener = itemClickListener;
		this.mFunctionClickListener = functionClickListener;
		this.mIsFcButton = hasFcButton;
	}

	public CommonRecyclerViewAdapter(OnRecyclerViewItemClickListener itemClickListener, View.OnClickListener functionClickListener, boolean hasFcButton, int maxCount) {

		this.mItemClickListener = itemClickListener;
		this.mFunctionClickListener = functionClickListener;
		this.mIsFcButton = hasFcButton;
		this.mMaxCount = maxCount;
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

	public ArrayList<T> getDataList() {

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

		SimpleViewHolder<T, D> holder  = null;
		Context                context = parent.getContext();
		if (viewType == FC_TYPE) {
			int layoutResID = getFunctionLayoutResId();
			View convertView = LayoutInflater.from(context)
											 .inflate(
													 layoutResID,
													 parent,
													 false
													 );
			D binding = DataBindingUtil.bind(convertView);
			holder = new SimpleViewHolder<T, D>(
					binding.getRoot(),
					binding
			);
			setRecursiveClick(binding.getRoot());
		} else if (viewType == CHILD_TYPE) {

			int layoutResID = getLayoutResID();
			View convertView = LayoutInflater.from(context)
											 .inflate(
													 layoutResID,
													 parent,
													 false
													 );
			D binding = DataBindingUtil.bind(convertView);
			holder = new SimpleViewHolder<T, D>(
					binding.getRoot(),
					binding
			);
			setRecursiveClick(binding.getRoot());
		} else if (viewType == GROUP_TYPE) {
			int layoutResID = getLayoutResID();
			View convertView = LayoutInflater.from(context)
											 .inflate(
													 layoutResID,
													 parent,
													 false
													 );
			D binding = DataBindingUtil.bind(convertView);
			holder = new SimpleViewHolder<T, D>(
					binding.getRoot(),
					binding
			);
			setRecursiveClick(binding.getRoot());
		}
		return holder;
	}

	@Override
	public void onBindViewHolder(SimpleViewHolder holder, int position) {

		D   binding      = (D) holder.getBinding();
		int itemViewType = getItemViewType(position);
		if (itemViewType == FC_TYPE) {
			bindFunctionData(
					position,
					holder,
					binding
							);
		} else if (itemViewType == GROUP_TYPE) {
			T data;
			if (isFcButton() && isFcButtonStart()) {
				holder.itemView.setTag(position - 1);
				data = getItem(position - 1);
			} else {
				data = getItem(position);
				holder.itemView.setTag(position);
			}
			holder.setDataObj(data);
			bindGroupData(
					position,
					holder,
					binding,
					data
						 );
		} else if (itemViewType == CHILD_TYPE) {
			T data;
			if (isFcButton() && isFcButtonStart()) {
				holder.itemView.setTag(position - 1);
				data = getItem(position - 1);
			} else {
				holder.itemView.setTag(position);
				data = getItem(position);
			}
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
		}
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
			if (mIsFcButton) {
				return 1;
			} else {
				return 0;
			}
		}

		if (mIsFcButton) {

			int count = mDataList.size() + 1;
			if (count > mMaxCount) {
				count = mMaxCount;
			}
			return count;
		}

		if (mDataList.size() > mMaxCount) {
			return mMaxCount;
		} else {
			return mDataList.size();
		}
	}

	@Override
	public int getItemViewType(int position) {

		if (mDataList == null) {
			if (mIsFcButton) {
				return FC_TYPE;
			} else {
				return CHILD_TYPE;
			}
		}

		if (mIsFcButton) {
			if (mIsFcButtonStart) {
				return position == 0 ?
						FC_TYPE :
						CHILD_TYPE;
			} else {
				return (position == mDataList.size() && position != mMaxCount) ?
						FC_TYPE :
						CHILD_TYPE;
			}

		}
		return CHILD_TYPE;
	}

	/**
	 * 设置选中Item
	 *
	 * @param position
	 */
	public void setSelected(int position) {

		this.mSelectedPosition = position;
		notifyDataSetChanged();
	}

	public boolean isSelected(int position) {

		return mSelectedPosition == position;
	}

	/**
	 * @return Group layout resource id
	 */
	public int getGroupLayoutResId() {

		return 0;
	}

	/**
	 * @return item layout resource id
	 */
	public abstract int getLayoutResID();

	/**
	 * @return function layout resource id
	 */
	public int getFunctionLayoutResId() {

		return 0;
	}

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

	/**
	 * bind group data
	 *
	 * @param position
	 * @param holder
	 * @param binding
	 * @param data
	 */
	public void bindGroupData(int position, SimpleViewHolder holder, D binding, T data) {

	}

	/**
	 * bind function data
	 *
	 * @param position
	 * @param holder
	 * @param binding
	 */
	public void bindFunctionData(int position, SimpleViewHolder holder, D binding) {

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
