package org.ibd.redisrepository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RedisConfigFile {

    private String address;
    private String port;
    private String username;
    private String password;
}
