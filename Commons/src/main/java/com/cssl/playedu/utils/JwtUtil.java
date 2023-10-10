package com.cssl.playedu.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.cssl.playedu.exception.BusinessException;
import com.cssl.playedu.vo.ResultCode;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author : Tang
 * @CreateDate 2023/8/28 21:45
 */
public class JwtUtil {
    /**
     * 密钥
     */
    private static final String JWT_SECRET = "default_secret_key";

    /**
     * 过期时间
     * 单位为毫秒
     **/
    private static final long EXPIRATION = 24L * 60 * 60 * 1000;

    /**
     * 签名算法
     */
    private static final Algorithm ALGORITHM = Algorithm.HMAC256(JWT_SECRET);

    /**
     * 创建 JWT
     *
     * @param claims     主体（Payload）
     * @param expireDate 创建日期
     * @param algorithm  签名算法（default HMAC256）
     * @return header.payload.signature
     */
    public static String createToken(Map<String, Object> claims, Date expireDate, Algorithm algorithm) {
//        if (secretKey == null || secretKey.isEmpty()) {
//            secretKey = JWT_SECRET;
//        }
        System.out.println("JWT-Secret-Key: " + algorithm);
        Map<String, Object> headers = new HashMap<>();
        headers.put("alg", algorithm.getName());
        headers.put("typ", "JWT");
//        String token = Jwts.builder()
//                .setClaims(claims)
//                .setIssuedAt(new Date())
//                .setExpiration(expireDate)
//                .signWith(SignatureAlgorithm.HS256, JWT_SECRET.getBytes(StandardCharsets.UTF_8))
//                .compact();
        return JWT.create()
                .withHeader(headers)// 添加头部
                // Payload 负载消息
                .withPayload(claims)
                .withExpiresAt(expireDate) // 超时设置,设置过期的日期
                .withIssuedAt(new Date())  // 签发时间
                .sign(algorithm);// 设置签名
    }

    /**
     * 创建 JWT （param expireDate 创建日期，param algorithm 签名算法（default HMAC256））
     *
     * @param claim 主体（Payload）
     * @return header.payload.signature
     */
    public static String createToken(Map<String, Object> claim, String secretKey) {
        return createToken(claim, new Date(System.currentTimeMillis() + EXPIRATION), Algorithm.HMAC256(secretKey));
    }

    /**
     * 创建 JWT （param expireDate 创建日期）
     *
     * @param claim     主体（Payload）
     * @param algorithm 签名算法（default HMAC256）
     * @return header.payload.signature
     */
    public static String createToken(Map<String, Object> claim, Algorithm algorithm) {
        return createToken(claim, new Date(System.currentTimeMillis() + EXPIRATION), algorithm);
    }

    /**
     * 创建 JWT （param algorithm 签名算法（default HMAC256））
     *
     * @param claim      主体（Payload）
     * @param expireDate 创建日期
     * @return header.payload.signature
     */
    public static String createToken(Map<String, Object> claim, String secretKey, long expireDate) {
        expireDate = Math.max(expireDate, EXPIRATION);
        return createToken(claim, new Date(System.currentTimeMillis() + expireDate), Algorithm.HMAC256(secretKey));
    }

    /**
     * 校验token并解析token
     */
    public static Map<String, Claim> verifyToken(String token, String secretKey) {
        DecodedJWT jwt = null;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secretKey)).build();
            jwt = verifier.verify(token);

            //decodedJWT.getClaim("属性").asString()  获取负载中的属性值

        } catch (Exception e) {
            System.out.println("token解码异常：");
            e.printStackTrace();
            if (e instanceof TokenExpiredException) {
                throw new BusinessException(ResultCode.TOKEN_HAS_EXPIRED, "token已过期");
            } else {
                throw new BusinessException(ResultCode.TOKEN_PARSING_EXCEPTION, "无效 token");
            }
        }
        return jwt.getClaims();
    }
}
