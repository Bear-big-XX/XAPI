package com.xx.xagentsdk.utils;

import cn.hutool.crypto.digest.DigestUtil;

public class SignUtils {

    /**
     * 生成签名
     * @param body
     * @param secretKey
     * @return
     */
    public static String genSign(String body, String secretKey){
        // 加密内容
        String content = body + "." + secretKey;
        // 使用SHA256加密算法
        String sha256 = DigestUtil.sha256Hex(content);
        return sha256;
    }
}
