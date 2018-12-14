package com.zhongkouwei.web.prc;

import com.zhongkouwei.user.common.model.ResultSub;
import org.springframework.util.Assert;

public class RpcUtil {

    public static void resultHandler(ResultSub resultSub) {
        if (resultSub != null && resultSub.getStatus() != 200) {
            Assert.isTrue(false, resultSub.getMessage());
        }
    }

}
