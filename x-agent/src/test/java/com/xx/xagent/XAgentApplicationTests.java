package com.xx.xagent;


import com.xx.xagentsdk.client.XClient;
import com.xx.xagentsdk.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@SpringBootTest
class XAgentApplicationTests {

    @Resource
    private XClient xClient;
    @Test
    void contextLoads() {
        String result1 = xClient.getNameByGet("xiong");
        String result2 = xClient.getNameByPost("xiongxi");
        User user = new User();
        user.setUsername("xi");
        String result3 = xClient.getUserNameByPost(user);
    }

}
