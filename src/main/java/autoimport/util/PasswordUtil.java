package autoimport.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class PasswordUtil
{
	private PasswordUtil() {}

	public static String hash(String senhaEmTexto)
	{
		try
		{
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] bytes = digest.digest(senhaEmTexto.getBytes("UTF-8"));
			StringBuilder sb = new StringBuilder();
			for (byte b : bytes)
			{
				sb.append(String.format("%02x", b));
			}
			return sb.toString();
		}
		catch (NoSuchAlgorithmException | java.io.UnsupportedEncodingException e)
		{
			throw new RuntimeException(e);
		}
	}

	public static boolean confere(String senhaEmTexto, String hashGuardado)
	{
		if (hashGuardado == null || hashGuardado.length() != 64)
		{
			return senhaEmTexto.equals(hashGuardado);
		}
		return hash(senhaEmTexto).equals(hashGuardado);
	}
}
