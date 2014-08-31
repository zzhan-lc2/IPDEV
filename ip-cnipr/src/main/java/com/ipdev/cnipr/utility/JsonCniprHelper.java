package com.ipdev.cnipr.utility;

import com.ipdev.common.utility.json.GsonJsonHelper;

public class JsonCniprHelper extends GsonJsonHelper {

    public JsonCniprHelper() {
        this.setDateFormat("yyyy.MM.dd")
            .setEnableHtmlEsacping(false)
            .setSerializeNulls(true);
    }
}
