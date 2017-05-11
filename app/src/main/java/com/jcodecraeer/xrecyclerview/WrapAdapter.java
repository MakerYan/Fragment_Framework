package com.jcodecraeer.xrecyclerview;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.ViewGroup;

import com.makeryan.lib.util.adapter.CommonRecyclerViewAdapter;

/**
 * Created by MakerYan on 2017/4/22 15:49.
 * Email : light.yan@qq.com
 * Personal e-mail : light.yan@qq.com
 * project name : KongRongQi
 * package name : com.jcodecraeer.xrecyclerview
 */

public class WrapAdapter
		extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


	private CommonRecyclerViewAdapter adapter;


	//下面的ItemViewType是保留值(ReservedItemViewType),如果用户的adapter与它们重复将会强制抛出异常。不过为了简化,我们检测到重复时对用户的提示是ItemViewType必须小于10000
	private static final int TYPE_REFRESH_HEADER = 10000;//设置一个很大的数字,尽可能避免和用户的adapter冲突

	private static final int TYPE_FOOTER = 10001;

	private static final int HEADER_INIT_INDEX = 10002;

	private XRecyclerView xRecyclerView;

	public WrapAdapter(XRecyclerView xRecyclerView, CommonRecyclerViewAdapter adapter) {

		this.xRecyclerView = xRecyclerView;
		this.adapter = adapter;
	}

	public boolean isHeader(int position) {

		return position >= 1 && position < xRecyclerView.getHeaderViews()
														.size() + 1;
	}

	public boolean isFooter(int position) {

		if (xRecyclerView.isLoadingMoreEnabled()) {
			return position == getItemCount() - 1;
		} else {
			return false;
		}
	}

	public boolean isRefreshHeader(int position) {

		return position == 0;
	}

	public int getHeadersCount() {

		return xRecyclerView.getHeaderViews()
							.size();
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

		if (viewType == TYPE_REFRESH_HEADER) {
			return new SimpleViewHolder(xRecyclerView.getRefreshHeader());
		} else if (xRecyclerView.isHeaderType(viewType)) {
			return new SimpleViewHolder(xRecyclerView.getHeaderViewByType(viewType));
		} else if (viewType == TYPE_FOOTER) {
			return new SimpleViewHolder(xRecyclerView.getFootView());
		}
		return adapter.onCreateViewHolder(
				parent,
				viewType
										 );
	}

	@Override
	public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

		if (isHeader(position) || isRefreshHeader(position)) {
			return;
		}
		int adjPosition = position - (getHeadersCount() + 1);
		int adapterCount;
		if (adapter != null) {
			adapterCount = adapter.getItemCount();
			if (adjPosition < adapterCount) {
				adapter.onBindViewHolder(
						(SimpleViewHolder) holder,
						adjPosition
										);
				return;
			}
		}
	}

	@Override
	public int getItemCount() {

		if (xRecyclerView.isLoadingMoreEnabled()) {
			if (adapter != null) {
				return getHeadersCount() + adapter.getItemCount() + 2;
			} else {
				return getHeadersCount() + 2;
			}
		} else {
			if (adapter != null) {
				return getHeadersCount() + adapter.getItemCount() + 1;
			} else {
				return getHeadersCount() + 1;
			}
		}
	}

	@Override
	public int getItemViewType(int position) {

		int adjPosition = position - (getHeadersCount() + 1);
		if (xRecyclerView.isReservedItemViewType(adapter.getItemViewType(adjPosition))) {
			throw new IllegalStateException("XRecyclerView require itemViewType in adapter should be less than 10000 ");
		}
		if (isRefreshHeader(position)) {
			return TYPE_REFRESH_HEADER;
		}
		if (isHeader(position)) {
			position = position - 1;
			return xRecyclerView.getHeaderTypes()
								.get(position);
		}
		if (isFooter(position)) {
			return TYPE_FOOTER;
		}

		int adapterCount;
		if (adapter != null) {
			adapterCount = adapter.getItemCount();
			if (adjPosition < adapterCount) {
				return adapter.getItemViewType(adjPosition);
			}
		}
		return 0;
	}

	@Override
	public long getItemId(int position) {

		if (adapter != null && position >= getHeadersCount() + 1) {
			int adjPosition = position - (getHeadersCount() + 1);
			if (adjPosition < adapter.getItemCount()) {
				return adapter.getItemId(adjPosition);
			}
		}
		return -1;
	}

	@Override
	public void onAttachedToRecyclerView(RecyclerView recyclerView) {

		super.onAttachedToRecyclerView(recyclerView);
		RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
		if (manager instanceof GridLayoutManager) {
			final GridLayoutManager gridManager = ((GridLayoutManager) manager);
			gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {

				@Override
				public int getSpanSize(int position) {

					return (isHeader(position) || isFooter(position) || isRefreshHeader(position)) ?
							gridManager.getSpanCount() :
							1;
				}
			});
		}
		adapter.onAttachedToRecyclerView(recyclerView);
	}

	@Override
	public void onDetachedFromRecyclerView(RecyclerView recyclerView) {

		adapter.onDetachedFromRecyclerView(recyclerView);
	}

	@Override
	public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {

		super.onViewAttachedToWindow(holder);
		ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
		if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams && (isHeader(holder.getLayoutPosition()) || isRefreshHeader(holder.getLayoutPosition()) || isFooter(holder.getLayoutPosition()))) {
			StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
			p.setFullSpan(true);
		}
		adapter.onViewAttachedToWindow((SimpleViewHolder) holder);
	}

	@Override
	public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {

		adapter.onViewDetachedFromWindow((SimpleViewHolder) holder);
	}

	@Override
	public void onViewRecycled(RecyclerView.ViewHolder holder) {

		adapter.onViewRecycled(holder);
	}

	@Override
	public boolean onFailedToRecycleView(RecyclerView.ViewHolder holder) {

		return adapter.onFailedToRecycleView(holder);
	}

	@Override
	public void unregisterAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {

		adapter.unregisterAdapterDataObserver(observer);
	}

	@Override
	public void registerAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {

		adapter.registerAdapterDataObserver(observer);
	}

	public CommonRecyclerViewAdapter getAdapter() {

		return adapter;
	}
}
