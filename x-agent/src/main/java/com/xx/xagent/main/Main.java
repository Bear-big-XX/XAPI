package com.xx.xagent.main;

import com.xx.xagent.client.XClient;
import com.xx.xagent.model.User;

public class Main {

    public static void main(String[] args) {
        String accessKey = "123";
        String secretKey = "456";
        XClient xClient = new XClient(accessKey,secretKey);
        String result1 = xClient.getNameByGet("xiong");
        String result2 = xClient.getNameByPost("xiongxi");
        User user = new User();
        user.setUsername("xi");
        String result3 = xClient.getUserNameByPost(user);

    }


}
