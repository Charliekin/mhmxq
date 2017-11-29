package com.mhm.xq.constacts

import com.mhm.xq.BuildConfig

class Actions {
    companion object {
        private final val ACTION_PREFIX: String = BuildConfig.APPLICATION_ID
        public final val LOGIN: String = ACTION_PREFIX + ".LOGIN"
    }
}