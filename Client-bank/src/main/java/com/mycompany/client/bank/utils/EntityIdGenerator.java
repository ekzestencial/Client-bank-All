/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.client.bank.utils;

import java.math.BigInteger;
import java.util.Random;
import java.util.UUID;
public class EntityIdGenerator {
        public static Long random(){
        Long l = UUID.randomUUID().getLeastSignificantBits();
        if(l<0){
            l=-l;
        }
        return l;
    }
        public static BigInteger BigIntRandom(int size){
            return BigInteger.probablePrime(size, new Random(System.nanoTime()));
        }
}
