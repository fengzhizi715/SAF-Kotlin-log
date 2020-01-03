//
// Created by tony on 2020-01-03.
//

#include "utility.h"

static JavaVM *		__logback_jvm = NULL;
static jclass		__logback_jclass_logger;
static jmethodID	__logback_jmethod_logWrite;