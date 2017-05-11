package com.orhanobut.dialogplus;

import android.databinding.ViewDataBinding;
import android.view.View;


public interface OnItemClickListener {

	void onItemClick(DialogPlus dialog, Object item, View view, int position);

	void onItemClick(DialogPlus dialog, Object item, View view, int position, ViewDataBinding binding);
}
