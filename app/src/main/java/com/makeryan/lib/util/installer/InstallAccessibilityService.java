package com.makeryan.lib.util.installer;

import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.socks.library.KLog;

import java.util.HashMap;
import java.util.Map;

public class InstallAccessibilityService
		extends android.accessibilityservice.AccessibilityService {

	private Map<Integer, Boolean> handledMap = new HashMap<>();

	@Override
	public void onAccessibilityEvent(AccessibilityEvent event) {

		AccessibilityNodeInfo nodeInfo = event.getSource();
		if (nodeInfo != null) {
			int eventType = event.getEventType();
			if (eventType == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED || eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
				if (handledMap.get(event.getWindowId()) == null) {
					boolean handled = iterateNodesAndHandle(nodeInfo);
					if (handled) {
						handledMap.put(
								event.getWindowId(),
								true
									  );
					}
				}
			}
		}
	}

	private boolean iterateNodesAndHandle(AccessibilityNodeInfo nodeInfo) {

		if (nodeInfo != null) {
			int childCount = nodeInfo.getChildCount();
			if ("android.widget.Button".equals(nodeInfo.getClassName())) {
				String nodeContent = nodeInfo.getText()
											 .toString();
				KLog.d(
						"TAG",
						"content is " + nodeContent
					  );
				if ("安装".equals(nodeContent) || "完成".equals(nodeContent) || "确定".equals(nodeContent)) {
					nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
					return true;
				}
			} else if ("android.widget.ScrollView".equals(nodeInfo.getClassName())) {
				nodeInfo.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
			}
			for (int i = 0; i < childCount; i++) {
				AccessibilityNodeInfo childNodeInfo = nodeInfo.getChild(i);
				if (iterateNodesAndHandle(childNodeInfo)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void onInterrupt() {

	}
}
