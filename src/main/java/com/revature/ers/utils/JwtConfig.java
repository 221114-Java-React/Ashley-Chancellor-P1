package com.revature.ers.utils;

import com.sun.org.apache.xml.internal.security.algorithms.SignatureAlgorithm;

public class JwtConfig {
    private final int expiration = 60 * 60 * 1000;
    //private final SignatureAlgorithm sigAlg = SignatureAlgorithm.HS256;
}
