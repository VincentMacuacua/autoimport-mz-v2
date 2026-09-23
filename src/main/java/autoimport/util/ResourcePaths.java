package autoimport.util;

import java.net.URL;

public final class ResourcePaths
{
	private ResourcePaths() {}

	public static URL imagem(String nomeFicheiro)
	{
		return ResourcePaths.class.getResource("/imagens/" + nomeFicheiro);
	}

	public static URL musica(String nomeFicheiro)
	{
		return ResourcePaths.class.getResource("/musica/" + nomeFicheiro);
	}

	public static URL video(String nomeFicheiro)
	{
		return ResourcePaths.class.getResource("/video/" + nomeFicheiro);
	}

	public static String dataFile(String nomeFicheiro)
	{
		return "data/" + nomeFicheiro;
	}
}
