package cn.way.utils;

import android.annotation.SuppressLint;
import android.util.Base64;

import java.security.Key;
import java.security.MessageDigest;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class WStringUtil {

	public static String base64Encode(byte[] data) {
		return new String(Base64Coder.encode(data));
	}

	public static String hmacsha256(String data, String key) throws Exception {
		 byte[] keyData = key.getBytes("UTF-8");    
         //根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称     
         SecretKey secretKey = new SecretKeySpec(keyData, "HmacSHA256");     
         //生成一个指定 Mac 算法 的 Mac 对象     
         Mac mac = Mac.getInstance(secretKey.getAlgorithm());     
         //用给定密钥初始化 Mac 对象     
         mac.init(secretKey);                  
         //完成 Mac 操作      
         byte[] finalData = mac.doFinal(data.getBytes("UTF-8"));       
         return Base64.encodeToString(finalData, Base64.DEFAULT);  
	}

	private static final String KEY_MD5 = "MD5";
	private static final String KEY_SHA = "SHA";

	/**
	 * MAC算法可选以下多种算法
	 * 
	 * <pre>
	 * 
	 * HmacMD5  
	 * HmacSHA1  
	 * HmacSHA256  
	 * HmacSHA384  
	 * HmacSHA512
	 * </pre>
	 */

	/**
	 * MD5加密
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptMD5(byte[] data) throws Exception {

		MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
		md5.update(data);

		return md5.digest();

	}

	/**
	 * SHA加密
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptSHA(byte[] data) throws Exception {

		MessageDigest sha = MessageDigest.getInstance(KEY_SHA);
		sha.update(data);

		return sha.digest();

	}

	/**
	 * 初始化HMAC密钥
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressLint("TrulyRandom")
	public static String initMacKey() throws Exception {
		KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacMD5");
		SecretKey secretKey = keyGenerator.generateKey();
		return new String(Base64Coder.encode(secretKey.getEncoded()));
	}

	/**
	 * HMAC 加密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptHMAC(byte[] data, String key) throws Exception {
		SecretKey secretKey = new SecretKeySpec(Base64Coder.encodeString(key)
				.getBytes(), "HmacMD5");
		Mac mac = Mac.getInstance(secretKey.getAlgorithm());
		mac.init(secretKey);
		return mac.doFinal(data);
	}

	/**
	 * DES 算法 <br>
	 * 可替换为以下任意一种算法，同时key值的size相应改变。
	 * 
	 * <pre>
	 * DES                  key size must be equal to 56 
	 * DESede(TripleDES)    key size must be equal to 112 or 168 
	 * AES                  key size must be equal to 128, 192 or 256,but 192 and 256 bits may not be available 
	 * Blowfish             key size must be multiple of 8, and can only range from 32 to 448 (inclusive) 
	 * RC2                  key size must be between 40 and 1024 bits 
	 * RC4(ARCFOUR)         key size must be between 40 and 1024 bits
	 * </pre>
	 */
	public static final String ALGORITHM = "DES";

	/**
	 * DES 算法转换密钥<br>
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	private static Key toKey(byte[] key) throws Exception {
		SecretKey secretKey = null;
		if (ALGORITHM.equals("DES") || ALGORITHM.equals("DESede")) {
			DESKeySpec dks = new DESKeySpec(key);
			SecretKeyFactory keyFactory = SecretKeyFactory
					.getInstance(ALGORITHM);
			secretKey = keyFactory.generateSecret(dks);
		} else {
			// 当使用其他对称加密算法时，如AES、Blowfish等算法时，用下述代码替换上述三行代码
			secretKey = new SecretKeySpec(key, ALGORITHM);
		}
		return secretKey;
	}

	/**
	 * DES 算法解密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data, String key) throws Exception {
		Key k = toKey(Base64Coder.encodeString(key).getBytes());
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, k);
		return cipher.doFinal(data);
	}

	/**
	 * DES 算法加密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data, String key) throws Exception {
		Key k = toKey(Base64Coder.encodeString(key).getBytes());
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, k);
		return cipher.doFinal(data);
	}

	/**
	 * DES 算法生成密钥
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String initKey() throws Exception {
		return initKey(null);
	}

	/**
	 * DES 算法生成密钥
	 * 
	 * @param seed
	 * @return
	 * @throws Exception
	 */
	public static String initKey(String seed) throws Exception {
		SecureRandom secureRandom = null;
		if (seed != null) {
			secureRandom = new SecureRandom(Base64Coder.encodeString(seed)
					.getBytes());
		} else {
			secureRandom = new SecureRandom();
		}
		KeyGenerator kg = KeyGenerator.getInstance(ALGORITHM);
		kg.init(secureRandom);
		SecretKey secretKey = kg.generateKey();
		return base64Encode(secretKey.getEncoded());
	}
}
