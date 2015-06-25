#include "net_juude_droidviews_jni_JniFragment.h"



void panic(char* s)
{
  char str[80];
  strcpy (str,"panic ");
  strcat (str, s);
  LOGD("paic %s", str);
  //__android_log_print(ANDROID_LOG_DEBUG,"native", str);
}


int init_watchdog()
{

	pid_t pid = fork();
	//std::string monitor_dir = "/data/data/net.juude.droidviews";

	if(pid < 0)
	{
		panic("fork error");
	}
	else if(pid == 0)
	{
		panic("in child process");
		// child_process(monitor_dir);
		// start_monitor(monitor_dir);
		// poll_monitor(monitor_dir);
		// epoll_monitor(monitor_dir);
		//select_monitor(monitor_dir);
		main(0, 0);
		return 0;
	}
	else
	{
//		stringstream s;
//		s <<  "in parent process child id is ";
//		s << pid;
//		loge(s.str().c_str());
		//waitpid(-1,NULL,0);
		return pid;
	}
}

void
Java_net_juude_droidviews_jni_JniFragment_forkChildProcess(JNIEnv* env, jobject thiz) {
	init_watchdog();
}


