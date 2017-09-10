package com.jcodecraeer.xrecyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;

import com.makeryan.lib.util.adapter.CommonRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import com.makeryan.lib.R;


public class XRecyclerView
		extends RecyclerView {

	private static final float DRAG_RATE = 3;

	//下面的ItemViewType是保留值(ReservedItemViewType),如果用户的adapter与它们重复将会强制抛出异常。不过为了简化,我们检测到重复时对用户的提示是ItemViewType必须小于10000
	private static final int TYPE_REFRESH_HEADER = 10000;//设置一个很大的数字,尽可能避免和用户的adapter冲突

	private static final int TYPE_FOOTER = 10001;

	private static final int HEADER_INIT_INDEX = 10002;

	private static List<Integer> sHeaderTypes = new ArrayList<>();//每个header必须有不同的type,不然滚动的时候顺序会变化

	private final RecyclerView.AdapterDataObserver mDataObserver = new DataObserver();

	private boolean isLoadingData = false;

	private boolean isNoMore = false;

	private int mRefreshProgressStyle = ProgressStyle.SysProgress;

	private int mLoadingMoreProgressStyle = ProgressStyle.BallClipRotateMultiple;

	private ArrayList<View> mHeaderViews = new ArrayList<>();

	private WrapAdapter mWrapAdapter;

	private float mLastY = -1;

	private LoadingListener mLoadingListener;

	private ArrowRefreshHeader mRefreshHeader;

	private boolean pullRefreshEnabled = true;

	private boolean loadingMoreEnabled = true;

	private int mPageCount = 0;

	//adapter没有数据的时候显示,类似于listView的emptyView
	private View mEmptyView;

	private View mFootView;

	private AppBarStateChangeListener.State appbarState = AppBarStateChangeListener.State.EXPANDED;

	/**
	 * 是否处理事件?
	 */
	private boolean canScroll = true;

	private boolean headerVisible = true;

	public XRecyclerView(Context context) {

		this(
				context,
				null
			);
	}

	public XRecyclerView(Context context, AttributeSet attrs) {

		this(
				context,
				attrs,
				0
			);
	}

	public XRecyclerView(Context context, AttributeSet attrs, int defStyle) {

		super(
				context,
				attrs,
				defStyle
			 );
		init(
				context,
				attrs,
				defStyle
			);
	}

	private void init(Context context, AttributeSet attrs, int defStyle) {

		TypedArray a = context.obtainStyledAttributes(
				attrs,
				R.styleable.AVLoadingIndicatorView
													 );

		canScroll = a.getBoolean(
				R.styleable.AVLoadingIndicatorView_isScroll,
				true
								);
		headerVisible = a.getBoolean(
				R.styleable.AVLoadingIndicatorView_headerVisible,
				true
									);
		if (pullRefreshEnabled) {
			mRefreshHeader = new ArrowRefreshHeader(getContext());
			mRefreshHeader.setProgressStyle(mRefreshProgressStyle);
		}
		LoadingMoreFooter footView = new LoadingMoreFooter(getContext());
		footView.setProgressStyle(mLoadingMoreProgressStyle);
		mFootView = footView;
		mFootView.setVisibility(GONE);

		a.recycle();
	}

	public static List<Integer> getHeaderTypes() {

		return sHeaderTypes;
	}

	public static void setHeaderTypes(List<Integer> headerTypes) {

		sHeaderTypes = headerTypes;
	}

	public boolean isLoadingData() {

		return isLoadingData;
	}

	public void setLoadingData(boolean loadingData) {

		isLoadingData = loadingData;
	}

	public boolean isNoMore() {

		return isNoMore;
	}

	public int getRefreshProgressStyle() {

		return mRefreshProgressStyle;
	}

	public int getLoadingMoreProgressStyle() {

		return mLoadingMoreProgressStyle;
	}

	public ArrayList<View> getHeaderViews() {

		return mHeaderViews;
	}

	public void setHeaderViews(ArrayList<View> headerViews) {

		mHeaderViews = headerViews;
	}

	public WrapAdapter getWrapAdapter() {

		return mWrapAdapter;
	}

	public void setWrapAdapter(WrapAdapter wrapAdapter) {

		mWrapAdapter = wrapAdapter;
	}

	public float getLastY() {

		return mLastY;
	}

	public void setLastY(float lastY) {

		mLastY = lastY;
	}

	public LoadingListener getLoadingListener() {

		return mLoadingListener;
	}

	public ArrowRefreshHeader getRefreshHeader() {

		return mRefreshHeader;
	}

	public boolean isPullRefreshEnabled() {

		return pullRefreshEnabled;
	}

	public boolean isLoadingMoreEnabled() {

		return loadingMoreEnabled;
	}

	public int getPageCount() {

		return mPageCount;
	}

	public void setPageCount(int pageCount) {

		mPageCount = pageCount;
	}

	public View getFootView() {

		return mFootView;
	}

	public AppBarStateChangeListener.State getAppbarState() {

		return appbarState;
	}

	public void setAppbarState(AppBarStateChangeListener.State appbarState) {

		this.appbarState = appbarState;
	}

	public void addHeaderView(View view) {

		sHeaderTypes.add(HEADER_INIT_INDEX + mHeaderViews.size());
		mHeaderViews.add(view);
	}

	public void clearHeaderView() {

		sHeaderTypes.clear();
		mHeaderViews.clear();
		if (mWrapAdapter != null) {
			mWrapAdapter.notifyDataSetChanged();
		}
	}

	//根据header的ViewType判断是哪个header
	public View getHeaderViewByType(int itemType) {

		if (!isHeaderType(itemType)) {
			return null;
		}
		return mHeaderViews.get(itemType - HEADER_INIT_INDEX);
	}

	//判断一个type是否为HeaderType
	public boolean isHeaderType(int itemViewType) {

		return mHeaderViews.size() > 0 && sHeaderTypes.contains(itemViewType);
	}

	//判断是否是XRecyclerView保留的itemViewType
	public boolean isReservedItemViewType(int itemViewType) {

		if (itemViewType == TYPE_REFRESH_HEADER || itemViewType == TYPE_FOOTER || sHeaderTypes.contains(itemViewType)) {
			return true;
		} else {
			return false;
		}
	}

	public void setFootView(final View view) {

		mFootView = view;
	}

	public void loadMoreComplete() {

		isLoadingData = false;
		if (mFootView instanceof LoadingMoreFooter) {
			((LoadingMoreFooter) mFootView).setState(LoadingMoreFooter.STATE_COMPLETE);
		} else {
			mFootView.setVisibility(View.GONE);
		}
	}

	public void setNoMore(boolean noMore) {

		isLoadingData = false;
		isNoMore = noMore;
		if (mFootView instanceof LoadingMoreFooter) {
			((LoadingMoreFooter) mFootView).setState(isNoMore ?
															 LoadingMoreFooter.STATE_NOMORE :
															 LoadingMoreFooter.STATE_COMPLETE);
		} else {
			mFootView.setVisibility(View.GONE);
		}
	}

	public void reset() {

		setNoMore(false);
		loadMoreComplete();
		refreshComplete();
	}

	public void refreshComplete() {

		mRefreshHeader.refreshComplete();
		setNoMore(false);
	}

	public void setRefreshHeader(ArrowRefreshHeader refreshHeader) {

		mRefreshHeader = refreshHeader;
	}

	public void setPullRefreshEnabled(boolean enabled) {

		pullRefreshEnabled = enabled;
	}

	public void setLoadingMoreEnabled(boolean enabled) {

		loadingMoreEnabled = enabled;
		if (!enabled) {
			if (mFootView instanceof LoadingMoreFooter) {
				((LoadingMoreFooter) mFootView).setState(LoadingMoreFooter.STATE_COMPLETE);
			}
		}
	}

	public void setRefreshProgressStyle(int style) {

		mRefreshProgressStyle = style;
		if (mRefreshHeader != null) {
			mRefreshHeader.setProgressStyle(style);
		}
	}

	public void setLoadingMoreProgressStyle(int style) {

		mLoadingMoreProgressStyle = style;
		if (mFootView instanceof LoadingMoreFooter) {
			((LoadingMoreFooter) mFootView).setProgressStyle(style);
		}
	}

	public void setArrowImageView(int resId) {

		if (mRefreshHeader != null) {
			mRefreshHeader.setArrowImageView(resId);
		}
	}

	public View getEmptyView() {

		return mEmptyView;
	}

	public void setEmptyView(View emptyView) {

		this.mEmptyView = emptyView;
		mDataObserver.onChanged();
	}

	@Override
	public void setAdapter(Adapter adapter) {

		mWrapAdapter = new WrapAdapter(
				this,
				(CommonRecyclerViewAdapter) adapter
		);
		super.setAdapter(mWrapAdapter);
		adapter.registerAdapterDataObserver(mDataObserver);
		mDataObserver.onChanged();
	}

	@Override
	public void onScrollStateChanged(int state) {

		super.onScrollStateChanged(state);
		if (state == RecyclerView.SCROLL_STATE_IDLE && mLoadingListener != null && !isLoadingData && loadingMoreEnabled) {
			LayoutManager layoutManager = getLayoutManager();
			int           lastVisibleItemPosition;
			if (layoutManager instanceof GridLayoutManager) {
				lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
			} else if (layoutManager instanceof StaggeredGridLayoutManager) {
				int[] into = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
				((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(into);
				lastVisibleItemPosition = findMax(into);
			} else {
				lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
			}
			if (layoutManager.getChildCount() > 0 && lastVisibleItemPosition >= layoutManager.getItemCount() - 1 && layoutManager.getItemCount() > layoutManager.getChildCount() && !isNoMore && mRefreshHeader.getState() < ArrowRefreshHeader.STATE_REFRESHING) {
				isLoadingData = true;
				if (mFootView instanceof LoadingMoreFooter) {
					((LoadingMoreFooter) mFootView).setState(LoadingMoreFooter.STATE_LOADING);
				} else {
					mFootView.setVisibility(View.VISIBLE);
				}
				mLoadingListener.onLoadMore(
						this,
						mWrapAdapter.getAdapter()
										   );
			}
		}
	}

	public boolean isCanScroll() {

		return canScroll;
	}

	public void setCanScroll(boolean canScroll) {

		this.canScroll = canScroll;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent e) {

		return super.onInterceptTouchEvent(e);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {

		if (mLastY == -1) {
			mLastY = ev.getRawY();
		}
		switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN:
				mLastY = ev.getRawY();
				break;
			case MotionEvent.ACTION_MOVE:
				if (!canScroll) {
					return false;
				}
				final float deltaY = ev.getRawY() - mLastY;
				mLastY = ev.getRawY();
				if (isOnTop() && pullRefreshEnabled && appbarState == AppBarStateChangeListener.State.EXPANDED) {
					mRefreshHeader.onMove(deltaY / DRAG_RATE);
					if (mRefreshHeader.getVisibleHeight() > 0 && mRefreshHeader.getState() < ArrowRefreshHeader.STATE_REFRESHING) {
						return false;
					}
				}
				break;
			default:
				mLastY = -1; // reset
				if (isOnTop() && pullRefreshEnabled && appbarState == AppBarStateChangeListener.State.EXPANDED) {
					if (mRefreshHeader.releaseAction()) {
						if (mLoadingListener != null) {
							mLoadingListener.onRefresh(
									this,
									mWrapAdapter.getAdapter()
													  );
						}
					}
				}
				break;
		}
		return super.onTouchEvent(ev);
	}

	private int findMax(int[] lastPositions) {

		int max = lastPositions[0];
		for (int value : lastPositions) {
			if (value > max) {
				max = value;
			}
		}
		return max;
	}

	private boolean isOnTop() {

		if (mRefreshHeader.getParent() != null) {
			return true;
		} else {
			return false;
		}
	}

	public void setLoadingListener(LoadingListener listener) {

		mLoadingListener = listener;
	}

	public void setRefreshing(boolean refreshing) {

		if (refreshing && pullRefreshEnabled && mLoadingListener != null) {
			mRefreshHeader.setState(ArrowRefreshHeader.STATE_REFRESHING);
			mRefreshHeader.onMove(mRefreshHeader.getMeasuredHeight());
			mLoadingListener.onRefresh(
					this,
					mWrapAdapter.getAdapter()
									  );
		}
	}

	@Override
	protected void onAttachedToWindow() {

		super.onAttachedToWindow();
		//解决和CollapsingToolbarLayout冲突的问题
		AppBarLayout appBarLayout = null;
		ViewParent   p            = getParent();
		while (p != null) {
			if (p instanceof CoordinatorLayout) {
				break;
			}
			p = p.getParent();
		}
		if (p instanceof CoordinatorLayout) {
			CoordinatorLayout coordinatorLayout = (CoordinatorLayout) p;
			final int         childCount        = coordinatorLayout.getChildCount();
			for (int i = childCount - 1; i >= 0; i--) {
				final View child = coordinatorLayout.getChildAt(i);
				if (child instanceof AppBarLayout) {
					appBarLayout = (AppBarLayout) child;
					break;
				}
			}
			if (appBarLayout != null) {
				appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {

					@Override
					public void onStateChanged(AppBarLayout appBarLayout, State state, int verticalOffset) {

						appbarState = state;
					}
				});
			}
		}
	}

	public interface LoadingListener {

		void onRefresh(XRecyclerView recyclerView, CommonRecyclerViewAdapter adapter);

		void onLoadMore(XRecyclerView recyclerView, CommonRecyclerViewAdapter adapter);
	}

	public class DataObserver
			extends RecyclerView.AdapterDataObserver {

		@Override
		public void onChanged() {

			Adapter<?> adapter = getAdapter();
			if (adapter != null && mEmptyView != null) {
				int emptyCount = 0;
				if (pullRefreshEnabled) {
					emptyCount++;
				}
				if (loadingMoreEnabled) {
					emptyCount++;
				}
				if (adapter.getItemCount() == emptyCount) {
					mEmptyView.setVisibility(View.VISIBLE);
					XRecyclerView.this.setVisibility(View.GONE);
				} else {
					mEmptyView.setVisibility(View.GONE);
					XRecyclerView.this.setVisibility(View.VISIBLE);
				}
			}
			if (mWrapAdapter != null) {
				mWrapAdapter.notifyDataSetChanged();
			}
		}

		@Override
		public void onItemRangeInserted(int positionStart, int itemCount) {

			mWrapAdapter.notifyItemRangeInserted(
					positionStart,
					itemCount
												);
		}

		@Override
		public void onItemRangeChanged(int positionStart, int itemCount) {

			mWrapAdapter.notifyItemRangeChanged(
					positionStart,
					itemCount
											   );
		}

		@Override
		public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {

			mWrapAdapter.notifyItemRangeChanged(
					positionStart,
					itemCount,
					payload
											   );
		}

		@Override
		public void onItemRangeRemoved(int positionStart, int itemCount) {

			mWrapAdapter.notifyItemRangeRemoved(
					positionStart,
					itemCount
											   );
		}

		@Override
		public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {

			mWrapAdapter.notifyItemMoved(
					fromPosition,
					toPosition
										);
		}
	}
}