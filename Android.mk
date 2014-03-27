LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)
LOCAL_MODULE_TAGS := optional tests
LOCAL_SRC_FILES := $(call all-java-files-under, src)
 := ViewTest
LOCAL_CERTIFICATE := platform
include $(BUILD_PACKAGE) 