package com.fana.utils;


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
    private final String prefix = "fanaweb_";

    /**
     * 生成token
     * @param userId 用户ID
     * @return Token
     * */
    public String createToken(int userId){
        UUID uuid = UUID.randomUUID();  // 根据机器和时间生成唯一字符
        String auth = MD5Util.md5(SALE + uuid.toString()).toUpperCase();
        String token = userId + "_" + uuid.toString().replace("-","");  // token = userId_uuid(去-)
        String key = userId + "_fana";
        redisTemplate.opsForValue().set(key, token,
                1, TimeUnit.DAYS);
        return token;
    }

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
//        if(token == null || token.equals("")){  // 空token 返回false
////            return false;
//            log("token为空无法分解");
//            throw new CustomException(Status.TOKEN_NULL.code,Status.TOKEN_NULL.message);
//        }
//        String[] arr1 = token.split("_");   // 分解token
//        if(arr1.length != 2){   // 格式不对返回false
//            throw new CustomException(Status.TOKEN_ERROR.code,Status.TOKEN_ERROR.message);
//        }
////        try {
//        String key = arr1[0] + "_dhamecha88";
//        Object o = redisTemplate.opsForValue().get(key);// 读取服务器token
//        if(o == null){
//            throw new CustomException(Status.TOKEN_PAST.code,Status.TOKEN_PAST.message);
//        }
//        if(!token.equals(o.toString())){ // 服务器token 过期 或 与用户token 不相等返回false
//
//                throw new CustomException(Status.TOKEN_PAST.code,Status.TOKEN_PAST.message);
//        }
//        List<TbBusinessUser> list = businessUserMapper.selectList(new QueryWrapper<TbBusinessUser>().lambda().eq(TbBusinessUser::getUserId, arr1[0]).eq(TbBusinessUser::getIsDelete, 0));
//        if(CollectionUtil.isEmpty(list)){
//            throw new CustomException(Status.USER_ERROR.code,Status.USER_ERROR.message);
//        }
//
//        List<Integer> collect = list.stream().map(a -> a.getBusinessId()).collect(Collectors.toList());
//
//        LambdaQueryWrapper<TbBusiness> in = new QueryWrapper<TbBusiness>().lambda()
//                .in(TbBusiness::getId, collect).eq(TbBusiness::getStatus,1).eq(TbBusiness::getIsDelete,0);
//
//        List<TbBusiness> businessList = businessMapper.selectList(in);
//
//        if(CollectionUtil.isEmpty(businessList)){
//            throw new CustomException(Status.BUSINESS_NOT_EXIST.code,Status.BUSINESS_NOT_EXIST.message);
//        }
//        redisTemplate.opsForValue().set(key, token, // 更新token时长
//                    30, TimeUnit.DAYS);
            return true;
//        }catch (Exception e){
//            System.out.println(e);
//        }
//        return false;
    }

    /**
     * 注销token
     * @param token
     * @return true成功;false失败
     * */
    public Boolean clearToken(String token){
//        if(token == null || token.equals("")){  // 空token 返回false
//            throw new CustomException(Status.TOKEN_NULL.code,Status.TOKEN_NULL.message);
//        }
//        String[] arr1 = token.split("_");   // 分解token
//        if(arr1.length != 2){   // 格式不对返回false
//            throw new CustomException(Status.TOKEN_ERROR.code,Status.TOKEN_ERROR.message);
//        }
//        try {
//            String key = arr1[0] + "_dhamecha88";
//            Object o = redisTemplate.opsForValue().get(key);// 读取服务器token
//            if(o == null){
//                throw new CustomException(Status.TOKEN_PAST.code,Status.TOKEN_PAST.message);
//            }
//            if(! token.equals(o.toString())){ // 服务器token 过期 或 与用户token 不相等返回false
//                throw new CustomException(Status.TOKEN_PAST.code,Status.TOKEN_PAST.message);
//            }
//            redisTemplate.delete(key);
//            return true;
//        }catch (Exception e){
//            System.out.println(e);
//        }
        return false;
    }

    public static void main(String[] args) {
        UUID uuid = UUID.randomUUID();  // 根据机器和时间生成唯一字符
        String auth = MD5Util.md5("liuxiaoxidalianaxi" + uuid.toString()).toUpperCase();
        String token = 1 + "_" + auth;
        System.out.println( token );
    }
}
