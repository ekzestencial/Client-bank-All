/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.client.bank.utils;

import com.mycompany.client.bank.auth.SecretProvider;
import com.sun.org.apache.xml.internal.security.algorithms.MessageDigestAlgorithm;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.codec.digest.Crypt;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author artem
 */
public class CryptMessage {
   private static List<String> paramLst;
   public static List fromInternal(String SuperSecret, Object ... criptStrings){
   paramLst=new ArrayList<>();
   byte[] syperSecretBytes=null;
       try {
           syperSecretBytes = SuperSecret.getBytes("utf-8");
       } catch (UnsupportedEncodingException ex) {
           Logger.getLogger(CryptMessage.class.getName()).log(Level.SEVERE, null, ex);
       }
   int colvo = 0;
   for(Object str : criptStrings){
       try {
           if(str!=null){
           byte[] mybyte=str.toString().getBytes("utf-8");
           int i = 0;
           ByteTransform(syperSecretBytes, mybyte, i);
           paramLst.add(colvo++, new String(mybyte, "utf-8"));
           }
           else {paramLst.add(colvo++, null);}
       } catch (UnsupportedEncodingException ex) {
           Logger.getLogger(CryptMessage.class.getName()).log(Level.SEVERE, null, ex);
       }
   }
   return paramLst;
}
   public static Object toInternal(String SuperSecret, Object criptStrings){
       Class curClass=criptStrings.getClass();
   byte[] syperSecretBytes=null;
       try {
           syperSecretBytes = SuperSecret.getBytes("utf-8");
       } catch (UnsupportedEncodingException ex) {
           Logger.getLogger(CryptMessage.class.getName()).log(Level.SEVERE, null, ex);
       }
   for(Field field : curClass.getDeclaredFields()){
       try {
           String str=null;
           if(field.get(criptStrings)!=null)str = field.get(criptStrings).toString();
           if(str!=null){
           byte[] mybyte=str.getBytes("utf-8");
           int i = 0;
           ByteTransform(syperSecretBytes, mybyte, i);
           field.set(criptStrings, new String(mybyte, "utf-8"));
           }
       } catch (UnsupportedEncodingException | IllegalArgumentException | IllegalAccessException ex) {
           Logger.getLogger(CryptMessage.class.getName()).log(Level.SEVERE, null, ex);
       }
   }
   return criptStrings;
}
   private static void ByteTransform(byte[] syperSecretBytes, byte[] mybyte, int i){
           int j=0;
           for (byte b : mybyte){
           mybyte[j++] = (byte) (b ^ syperSecretBytes[i++]);
           if(i==syperSecretBytes.length)  i=0;
           }
   }
}
