package com.fana.utils;


import cn.hutool.core.util.ObjectUtil;
import com.fana.config.Status;
import com.fana.entry.pojo.TbUser;
import com.fana.entry.pojo.TbWebUser;
import com.fana.exception.CustomException;
import com.fana.mapper.TbWebUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;


import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class TokenManager {
    @Resource
    private RedisTemplate<Object, Object> redisTemplate;
    private final String SALE = "liuxiaoxidalianaxi";
    private final String prefix = "_wwmfanaweb";

    @Resource
    TbWebUserMapper webUserMapper;
    /**
     * 生成token
     * @param userId 用户ID
     * @return Token
     * */
    public String createToken(int userId){
        UUID uuid = UUID.randomUUID();  // 根据机器和时间生成唯一字符
        String token = userId + "_" + uuid.toString().replace("-","").toUpperCase();  // token = userId_uuid(去-)
        String key = userId +prefix;
        redisTemplate.opsForValue().set(key, token,
                30, TimeUnit.DAYS);
        return token;
    }

    /**
     * 获取token中用户Id
     * @param token
     * @return
     */
    public Integer getUserId(String token){
        String[] s = token.split("_");
        String str = s[0];
        int i = Integer.parseInt(str);
        return i;
    }
    /**
     * 检查token
     * @param token
     * @return true更新token;false令牌不存在
     * */
    public Boolean checkToken(String token){
        if(token == null || token.equals("")){  // 空token 返回false
//            throw new CustomException(Status.TOKEN_ERROR.code,Status.TOKEN_ERROR.message);
            log.info("token is null");
            throw new CustomException(Status.TOKEN_ERROR.code,"token is null");

        }
        String[] arr1 = token.split("_");   // 分解token
        if(arr1.length != 2){   // 格式不对返回false
            throw new CustomException(Status.TOKEN_ERROR.code,Status.TOKEN_ERROR.message);
        }
        try {
            String key = arr1[0] + prefix;
            Object o = redisTemplate.opsForValue().get(key);// 读取服务器token
            if(ObjectUtil.isEmpty(o)){
                log.info("redis token is null");
                throw new CustomException(Status.TOKEN_ERROR.code,Status.TOKEN_ERROR.message);
            }
            if(!token.equals(o.toString())){ // 服务器token 过期 或 与用户token 不相等返回false
                log.info("token不匹配");
                throw new CustomException(Status.TOKEN_ERROR.code,Status.TOKEN_ERROR.message);
            }
            TbWebUser tbWebUser = webUserMapper.selectById(arr1[0]);
            if(ObjectUtil.isEmpty(tbWebUser)){
                log.info("db user 不存在");
                throw new CustomException(Status.TOKEN_ERROR.code,"User does not exist");
            }

            redisTemplate.opsForValue().set(key, token, // 更新token时长
                    30, TimeUnit.DAYS);
            return true;
        }catch (Exception e){
            System.out.println(e);
        }
        throw new CustomException(Status.TOKEN_ERROR.code,Status.TOKEN_ERROR.message);
    }

    public static void main(String[] args) {
        UUID uuid = UUID.randomUUID();  // 根据机器和时间生成唯一字符
        String auth = MD5Util.md5("liuxiaoxidalianaxi" + uuid.toString()).toUpperCase();
        String token = 1 + "_" + auth;
        System.out.println( token );
    }
}
