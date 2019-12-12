package com.me.ui.sample;

import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * @author kylingo
 * @since 2019/01/04 14:33
 */
public class SampleApplication extends TinkerApplication {
    public SampleApplication() {
        super(
                //tinkerFlags, which types is supported
                //dex only, library only, all support
                ShareConstants.TINKER_ENABLE_ALL,
                // This is passed as a string so the shell application does not
                // have a binary dependency on your ApplicationLifeCycle class.
                "com.me.ui.sample.SampleApplicationLike");
    }
}
