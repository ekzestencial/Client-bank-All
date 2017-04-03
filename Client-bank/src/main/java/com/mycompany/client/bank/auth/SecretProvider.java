/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.client.bank.auth;

import com.hazelcast.core.HazelcastInstance;
import com.mycompany.client.bank.utils.EntityIdGenerator;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.KeyAgreement;
import javax.crypto.spec.DHParameterSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author artem
 */
@Service
public class SecretProvider {
    @Autowired
    private HazelcastInstance instance;
    
    public void put(AuthUser aauth, SecuritySecret secret) {
        instance.getMap("secret-cacle").put(aauth, secret); //check equels
    }

    public SecuritySecret get(AuthUser aauth) {
        SecuritySecret secret=null;
        if(aauth!=null)secret = (SecuritySecret) instance.getMap("secret-cacle").get(aauth);
        return secret;
    }
    public SecuritySecret GenerateGandP(AuthUser aauth){
    if(get(aauth)!=null)instance.getMap("secret-cache").remove(aauth);
    SecuritySecret tmpSecur=new SecuritySecret();
    tmpSecur.setG(EntityIdGenerator.BigIntRandom(2));
    tmpSecur.setP(EntityIdGenerator.BigIntRandom(50));
            tmpSecur.setPrivateKey(EntityIdGenerator.BigIntRandom(5).toString());
            BigInteger publicKey = tmpSecur.getG().pow(Integer.valueOf(tmpSecur.getPrivateKey())).mod(tmpSecur.getP());
            tmpSecur.setPublicKey(publicKey.toString());
            put(aauth, tmpSecur);
            return tmpSecur;
    }
    
    public String GenerateNewSecret(AuthUser aauth, String PublicClienKey){
        if(get(aauth)==null)return null;
        SecuritySecret tmpS = get(aauth);
        tmpS.setSuperSecretKey(new BigInteger(PublicClienKey).pow(Integer.valueOf(tmpS.getPrivateKey())).mod(tmpS.getP()));
        System.out.println("Super Secret Key: " + tmpS.getSuperSecretKey());
        put(aauth, tmpS);
        return tmpS.getPublicKey();
    }
}
