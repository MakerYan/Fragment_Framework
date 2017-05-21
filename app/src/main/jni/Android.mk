LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE := MyNdk
LOCAL_SRC_FILES := MyNdk.cpp

include $(BUILD_SHARED_LIBRARY)