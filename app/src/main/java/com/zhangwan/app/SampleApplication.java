package com.zhangwan.app;

import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;

public class SampleApplication extends TinkerApplication {
    public SampleApplication() {
        super(ShareConstants.TINKER_ENABLE_ALL, "com.zhangwan.app.MyApplication",
                "com.tencent.tinker.loader.TinkerLoader", false);
    }
}