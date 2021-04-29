package com.laboratory.demo.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import java.util.Calendar;
import java.util.Date;

public class JwtUtils {
    public static String createToken(String id, String identity) {
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.HOUR,24);
        Date expiresDate = nowTime.getTime();
        return JWT.create().withAudience("feichai")  // 签发对象
                .withIssuedAt(new Date())  // 发行时间
                .withExpiresAt(expiresDate) //有效时间
                .withClaim("id", id)
                .withClaim("identity", identity)
                .sign(Algorithm.HMAC256(id + identity + "token"));
    }

    public static Claim getClaimByName(String token, String name) {
        return JWT.decode(token).getClaim(name);
    }

    public static String updateToken(String token) {
        String id = getClaimByName(token, "id").asString();
        String identity = getClaimByName(token, "identity").asString();
        return createToken(id, identity);
    }

    public static boolean checkToken(String token) {
        Date expires;
        try{
            expires =  JWT.decode(token).getExpiresAt();
        }catch (JWTDecodeException e) {
            return false;
        }
        Date nowTime = new Date();
        if(expires.before(nowTime)) {
            return false;
        }
        String id = getClaimByName(token, "id").asString();
        String identity = getClaimByName(token, "identity").asString();
        String signature = JWT.decode(token).getSignature();
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(id + identity + "token")).build();
            if(!verifier.verify(token).getSignature().equals(signature)) {
                return false;
            }
        }catch (Exception e) {
            return false;
        }
        return true;
    }
}

