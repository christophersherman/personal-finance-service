package com.sherm.pfinance.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class TransactionHasher {

  public static String GenerateUniqueHash(String[] record) {
    String recordString = Arrays.toString(record);
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      byte[] hashBytes =
          md.digest(recordString.getBytes(StandardCharsets.UTF_8));
      StringBuilder hashString = new StringBuilder();
      for (byte b : hashBytes) {
        hashString.append(String.format("%02x", b));
      }
      return hashString.toString();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
      return null;
    }
  }
}
