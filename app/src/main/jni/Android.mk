LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE := Mk
LOCAL_SRC_FILES := Mk.cpp

include $(BUILD_SHARED_LIBRARY)