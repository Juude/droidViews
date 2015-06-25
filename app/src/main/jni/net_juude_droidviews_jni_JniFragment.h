#if !defined(__JNI_FRAGMENT__H_)
#define __JNI_FRAGMENT__H_
#include <jni.h>
#include <string.h>
#include  <sys/types.h>
#include  <stdio.h>
#include <android/log.h>
#define  LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG,"native",__VA_ARGS__)
#endif