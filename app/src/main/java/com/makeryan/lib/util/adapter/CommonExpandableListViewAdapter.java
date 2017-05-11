package com.makeryan.lib.util.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by MakerYan on 16/8/7 23:48.
 * Email : light.yan@qq.com
 * Personal e-mail : light.yan@qq.com
 * project name : dormapp-android
 * package name : com.huanxiao.dorm.utilty.adapter
 */
public abstract class CommonExpandableListViewAdapter<T1, T2, D1 extends ViewDataBinding, D2 extends ViewDataBinding>
		extends BaseExpandableListAdapter {

	protected int mGroupResId = 0;

	protected int mChildResId = 0;

	protected List<T1> mGroupData = new ArrayList<T1>();

	protected Map<String, List<T2>> mChildData = new HashMap<String, List<T2>>();

	public CommonExpandableListViewAdapter(int groupResId, int childResId, List<T1> groupData, Map<String, List<T2>> childData) {

		mGroupResId = groupResId;
		mChildResId = childResId;
		mGroupData.clear();
		mGroupData.addAll(groupData);
		mChildData = childData;
	}

	public List<T1> getGroupData() {

		return mGroupData;
	}

	public void setGroupData(List<T1> groupData) {

		mGroupData.clear();
		mGroupData.addAll(groupData);
	}

	public Map<String, List<T2>> getChildData() {

		return mChildData;
	}

	public void setChildData(Map<String, List<T2>> childData) {

		childData.clear();
		mChildData.putAll(childData);
	}

	@Override
	public int getGroupCount() {

		return mGroupData.size();
	}


	@Override
	public int getChildrenCount(int groupPosition) {

		T1 t1 = mGroupData.get(groupPosition);
		return mChildData.get(t1)
						 .size();
	}

	@Override
	public T1 getGroup(int groupPosition) {

		return mGroupData.get(groupPosition);
	}

	@Override
	public T2 getChild(int groupPosition, int childPosition) {

		T1 t1 = mGroupData.get(groupPosition);
		return mChildData.get(t1)
						 .get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {

		return 0;
	}


	@Override
	public long getChildId(int groupPosition, int childPosition) {

		return 0;
	}

	@Override
	public boolean hasStableIds() {

		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

		GroupHolder holder;
		if (convertView == null) {
			D1 binding = DataBindingUtil.inflate(
					LayoutInflater.from(parent.getContext()),
					mGroupResId,
					parent,
					false
												);
			convertView = binding.getRoot();
			holder = new GroupHolder(binding);
			convertView.setTag(holder);
		} else {
			holder = (GroupHolder) convertView.getTag();
		}
		T1 groupData = getGroup(groupPosition);
		D1 binding   = holder.getBinding();
		bindGroupData(
				binding,
				groupData,
				groupPosition,
				isExpanded,
				convertView,
				parent
					 );
		binding.executePendingBindings();
		return convertView;
	}

	public abstract void bindGroupData(D1 binding, T1 data, int groupPosition, boolean isExpanded, View convertView, ViewGroup parent);


	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

		ChildHolder holder;
		if (convertView == null) {
			D2 binding = DataBindingUtil.inflate(
					LayoutInflater.from(parent.getContext()),
					mChildResId,
					parent,
					false
												);
			convertView = binding.getRoot();
			holder = new ChildHolder(binding);
			convertView.setTag(holder);
		} else {
			holder = (ChildHolder) convertView.getTag();
		}
		T2 childData = getChild(
				groupPosition,
				childPosition
							   );
		D2 binding = holder.getBinding();
		bindChildData(
				binding,
				childData,
				groupPosition,
				childPosition,
				isLastChild,
				convertView,
				parent
					 );
		binding.executePendingBindings();
		return convertView;
	}

	public abstract void bindChildData(D2 binding, T2 childData, int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent);

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {

		return false;
	}


	public class GroupHolder {

		private D1 binding;

		public GroupHolder() {

		}

		public GroupHolder(D1 binding) {

			this.binding = binding;
		}

		public D1 getBinding() {

			return this.binding;
		}

		public void setBinding(D1 binding) {

			this.binding = binding;
		}
	}


	public class ChildHolder {

		private D2 binding;

		public ChildHolder() {

		}

		public ChildHolder(D2 binding) {

			this.binding = binding;
		}

		public D2 getBinding() {

			return this.binding;
		}

		public void setBinding(D2 binding) {

			this.binding = binding;
		}
	}
}
