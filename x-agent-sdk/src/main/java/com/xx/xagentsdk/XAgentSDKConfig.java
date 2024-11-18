package com.xx.xagentsdk;

import com.xx.xagentsdk.client.XClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
// 将该类标记为配置类

@ConfigurationProperties("x.agent")
// 能够读取application.yml的配置信息，并且将读取的配置信息设置为此处的属性

@Data
@ComponentScan
public class XAgentSDKConfig {

    private String accessKey;

    private String secretKey;

    // 创建一个名为xClient的Bean
    @Bean
    public XClient xClient(){
        return new XClient(accessKey,secretKey);
    }
}
