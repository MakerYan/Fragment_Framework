package com.orhanobut.dialogplus;

import android.databinding.ViewDataBinding;
import android.view.View;

public interface OnClickListener {

	void onClick(DialogPlus dialog, View view);

	void onClick(DialogPlus dialog, View view, ViewDataBinding binding);
}
