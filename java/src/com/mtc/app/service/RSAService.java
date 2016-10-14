package com.mtc.app.service;

import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.mtc.common.util.RSAUtils;

/**
 * 
 * RSA非对称算法服务
 * 
 * @author lx
 * 
 */
@Service
public class RSAService {

	/**
	 * 公钥信息保存在页面，用于加密
	 */
	private String publicKeyExponent;
	/**
	 * 公钥信息保存在页面，用于加密
	 */
	private String publicKeyModulus;

	/**
	 * 解密私钥
	 */
	private RSAPrivateKey privateKey;
	
	
	
	public RSAPrivateKey getPrivateKey() {
		return privateKey;
	}

	public String getPublicKeyExponent() {
		return publicKeyExponent;
	}

	public String getPublicKeyModulus() {
		return publicKeyModulus;
	}
	
	public RSAService() throws NoSuchAlgorithmException{
		HashMap<String, Object> map;
		map = RSAUtils.getKeys();
		// 生成公钥和私钥
		RSAPublicKey publicKey = (RSAPublicKey) map.get("public");
		privateKey = (RSAPrivateKey) map.get("private");
		// System.out.println("privateKey:" + privateKey);
		// 私钥保存在session中，用于解密
		// session.setAttribute("privateKey", privateKey);
		// 公钥信息保存在页面，用于加密
		publicKeyExponent = publicKey.getPublicExponent().toString(
				16);
		publicKeyModulus = publicKey.getModulus().toString(16);

		//System.out.println("publicKeyExponent:" + publicKeyExponent);
		//System.out.println("publicKeyModulus:" + publicKeyModulus);
			
	}

	/**
	 * 
	 * 密文解密
	 * 
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public String decryptByPrivateKey(String code) throws Exception{
		String str = RSAUtils.decryptByPrivateKey(code, privateKey);
		return str;
	}
}
