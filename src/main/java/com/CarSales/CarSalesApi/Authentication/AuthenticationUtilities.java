package com.CarSales.CarSalesApi.Authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.Calendar;
import java.util.Map;
import com.auth0.jwt.exceptions.JWTVerificationException;

public class AuthenticationUtilities {
    public static String generateJWT(String secretKey, String userId) {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 60);

        String jwt = JWT.create()
                .withHeader(Map.of("alg", "HS256"))
                .withClaim("sub", userId)
                .withClaim("iss", "rev_car_sales")
                .withClaim("exp", calendar.getTime())
                .sign(Algorithm.HMAC256(secretKey));

        return jwt;
    }
    public static DecodedJWT validateJWT(String jwt, String secretKey) throws JWTVerificationException {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        return JWT.require(algorithm).build().verify(jwt);
    }
}
