package PokalaAWSUtils;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import com.softwareag.util.IDataMap;
// --- <<IS-END-IMPORTS>> ---

public final class util

{
	// ---( internal utility methods )---

	final static util _instance = new util();

	static util _newInstance() { return new util(); }

	static util _cast(Object o) { return (util)o; }

	// ---( server methods )---




	public static final void SHA256Hash (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(SHA256Hash)>> ---
		// @sigtype java 3.5
		// [i] field:0:required data
		// [o] field:0:required hash
		IDataMap pipe = new IDataMap(pipeline);
		try {
			pipe.put("hash", getSHA256Hash(pipe.getAsString("data")));
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			throw new ServiceException(e);
		}
			
		// --- <<IS-END>> ---

                
	}



	public static final void getSignatureKey (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getSignatureKey)>> ---
		// @sigtype java 3.5
		// [i] field:0:required key
		// [i] field:0:required dateStamp
		// [i] field:0:required regionName
		// [i] field:0:required serviceName
		// [o] object:0:required kSigning
		IDataMap pipe = new IDataMap(pipeline);
		String key = pipe.getAsString("key");
		String dateStamp = pipe.getAsString("dateStamp");
		String regionName = pipe.getAsString("regionName");
		String serviceName = pipe.getAsString("serviceName");
		try{
			byte[] kSecret = ("AWS4" + key).getBytes("UTF8");
		    byte[] kDate = HmacSHA256(dateStamp, kSecret);
		    byte[] kRegion = HmacSHA256(regionName, kDate);
		    byte[] kService = HmacSHA256(serviceName, kRegion);
		    byte[] kSigning = HmacSHA256("aws4_request", kService);
		    pipe.put("kSigning", kSigning);
		}catch(Exception e){
			throw new ServiceException(e); 
		}			
		// --- <<IS-END>> ---

                
	}



	public static final void hmacSHA256 (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(hmacSHA256)>> ---
		// @sigtype java 3.5
		// [i] field:0:required data
		// [i] object:0:required key
		// [o] field:0:required signedDataHex
		IDataMap pipe = new IDataMap(pipeline);
		String data = pipe.getAsString("data");
		byte[] key = (byte[])pipe.get("key");
		try{			
			pipe.put("signedDataHex", bytesToHex(HmacSHA256(data, key)));
		}catch(Exception e)
		{
			throw new ServiceException(e);
		}
		
			
		// --- <<IS-END>> ---

                
	}



	public static final void joinWithNewLines (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(joinWithNewLines)>> ---
		// @sigtype java 3.5
		// [i] field:1:required str
		// [o] field:0:required outputStr
		IDataMap pipe = new IDataMap(pipeline);
		String[] str = pipe.getAsStringArray("str");
		String outputStr = "";
		for(int i=0; i< str.length; i++){
			if(i != str.length-1){
				outputStr = outputStr + str[i] + "\n"; 
			}else{
				outputStr = outputStr + str[i];
			}
		}
		pipe.put("outputStr", outputStr);
			
		// --- <<IS-END>> ---

                
	}

	// --- <<IS-START-SHARED>> ---
	private static String getSHA256Hash(String data) throws NoSuchAlgorithmException, UnsupportedEncodingException {		        
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
	    byte[] hash = digest.digest(data.getBytes("UTF-8"));
	    return bytesToHex(hash);
	}
	
	public static byte[] HmacSHA256(String data, byte[] key) throws Exception {
	    String algorithm="HmacSHA256";
	    Mac mac = Mac.getInstance(algorithm);
	    mac.init(new SecretKeySpec(key, algorithm));
	    return mac.doFinal(data.getBytes("UTF8"));
	}
	
	public static String bytesToHex(byte[] hash) {
	   return DatatypeConverter.printHexBinary(hash).toLowerCase();	
	}
	// --- <<IS-END-SHARED>> ---
}

