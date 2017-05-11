package com.orhanobut.dialogplus;

import android.databinding.ViewDataBinding;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.makeryan.lib.R;

public class ViewDataBindingHolder
		implements Holder {

	private static final int INVALID = -1;

	private int backgroundResource;

	private ViewGroup headerContainer;

	private View headerView;

	private ViewGroup footerContainer;

	private View footerView;

	private View.OnKeyListener keyListener;

	protected ViewDataBinding mBinding;

	public ViewDataBindingHolder(ViewDataBinding binding) {

		mBinding = binding;
	}

	@Override
	public void addHeader(View view) {

		if (view == null) {
			return;
		}
		headerContainer.addView(view);
		headerView = view;
	}

	@Override
	public void addFooter(View view) {

		if (view == null) {
			return;
		}
		footerContainer.addView(view);
		footerView = view;
	}

	@Override
	public void setBackgroundResource(int colorResource) {

		this.backgroundResource = colorResource;
	}

	@Override
	public View getView(LayoutInflater inflater, ViewGroup parent) {

		View view = inflater.inflate(
				R.layout.dialog_view,
				parent,
				false
									);
		View outMostView = view.findViewById(R.id.dialogplus_outmost_container);
		outMostView.setBackgroundResource(backgroundResource);
		ViewGroup contentContainer = (ViewGroup) view.findViewById(R.id.dialogplus_view_container);
		contentContainer.setOnKeyListener(new View.OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {

				if (keyListener == null) {
					throw new NullPointerException("keyListener should not be null");
				}
				return keyListener.onKey(
						v,
						keyCode,
						event
										);
			}
		});
		addContent(
				inflater,
				parent,
				contentContainer
				  );
		headerContainer = (ViewGroup) view.findViewById(R.id.dialogplus_header_container);
		footerContainer = (ViewGroup) view.findViewById(R.id.dialogplus_footer_container);
		return view;
	}

	private void addContent(LayoutInflater inflater, ViewGroup parent, ViewGroup container) {

		View      root       = mBinding.getRoot();
		ViewGroup parentView = (ViewGroup) root.getParent();
		if (parentView != null) {
			parentView.removeView(root);
		}

		container.addView(root);
	}

	@Override
	public void setOnKeyListener(View.OnKeyListener keyListener) {

		this.keyListener = keyListener;
	}

	@Override
	public View getInflatedView() {

		return mBinding.getRoot();
	}

	@Override
	public View getHeader() {

		return headerView;
	}

	@Override
	public View getFooter() {

		return footerView;
	}

	/**
	 * @return
	 */
	public ViewDataBinding getViewDataBinding() {

		return mBinding;
	}
}
