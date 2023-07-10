package pe.gob.migraciones.sgv.videollamadas.security;

import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class KeysLoader {
	// PKCS#8 format
	private static final String PEM_PRIVATE_START = "-----BEGIN PRIVATE KEY-----";
	private static final String PEM_PRIVATE_END = "-----END PRIVATE KEY-----";
	
	// PKCS#8 format
	private static final String PEM_PUBLIC_START = "-----BEGIN PUBLIC KEY-----";
	private static final String PEM_PUBLIC_END = "-----END PUBLIC KEY-----";

	private KeysLoader() {
	}
	public static PrivateKey getPrivateKey(Class clazz, String pathResource) {
		PrivateKey output = null;

		try {
			InputStream inputStream = clazz.getResourceAsStream(pathResource);
			String dataFile = IOUtils.toString(inputStream,"UTF-8");

			dataFile= dataFile.replace(PEM_PRIVATE_START, "").replace(PEM_PRIVATE_END, "").replaceAll("\\r\\n", "").replaceAll("\\n", "");
			
			KeyFactory kf = KeyFactory.getInstance("RSA");
			PKCS8EncodedKeySpec keySpecPKCS8 = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(dataFile));
			output = kf.generatePrivate(keySpecPKCS8);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return output;
	}
	
	public static PublicKey getPublicKey(Class clazz, String pathResource) {
		PublicKey output = null;

		try {
			InputStream inputStream = clazz.getResourceAsStream(pathResource);
			String dataFile = IOUtils.toString(inputStream,"UTF-8");

			dataFile= dataFile.replace(PEM_PUBLIC_START, "").replace(PEM_PUBLIC_END, "").replaceAll("\\r\\n", "").replaceAll("\\n", "");
			
			KeyFactory kf = KeyFactory.getInstance("RSA");
			X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(Base64.getDecoder().decode(dataFile));
			output = kf.generatePublic(keySpecX509);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return output;
	}
}
