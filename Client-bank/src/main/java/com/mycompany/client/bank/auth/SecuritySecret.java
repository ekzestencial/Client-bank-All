/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.client.bank.auth;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.Key;
import javax.crypto.KeyAgreement;
import javax.crypto.KeyAgreementSpi;
import javax.crypto.spec.DHParameterSpec;

/**
 *
 * @author artem
 */
public class SecuritySecret implements Serializable{
    private BigInteger P; 
    private BigInteger G; // 
    private String publicKey;
    private String PrivateKey;
    private BigInteger SuperSecretKey;

    public BigInteger getP() {
        return P;
    }

    public void setP(BigInteger P) {
        this.P = P;
    }

    public BigInteger getG() {
        return G;
    }

    public void setG(BigInteger G) {
        this.G = G;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        return PrivateKey;
    }

    public void setPrivateKey(String PrivateKey) {
        this.PrivateKey = PrivateKey;
    }

    public BigInteger getSuperSecretKey() {
        return SuperSecretKey;
    }

    public void setSuperSecretKey(BigInteger SuperSecretKey) {
        this.SuperSecretKey = SuperSecretKey;
    }
    
}
