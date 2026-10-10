package autoimport.util;

import java.io.File;
import java.io.IOException;
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

	/**
	 * Pasta onde ficam os dados da aplicacao (utilizadores, clientes, PDF).
	 * Por omissao e ~/AutoImportMZ/data, para que a app encontre sempre os mesmos
	 * dados, seja lancada pelo executavel, pelo .jar ou pelo Maven.
	 * Pode ser alterada com -Dautoimport.dados=caminho.
	 */
	public static File pastaDados()
	{
		String definida = System.getProperty("autoimport.dados");
		if (definida != null && !definida.isBlank())
			return new File(definida);
		return new File(System.getProperty("user.home"), "AutoImportMZ" + File.separator + "data");
	}

	public static String dataFile(String nomeFicheiro)
	{
		return new File(pastaDados(), nomeFicheiro).getPath();
	}

	/** Cria a pasta de dados e os ficheiros vazios na primeira execucao. */
	public static void prepararDados()
	{
		File pasta = pastaDados();
		pasta.mkdirs();
		try
		{
			new File(pasta, "users.txt").createNewFile();
			new File(pasta, "Dados.txt").createNewFile();
		}
		catch (IOException e)
		{
			System.err.println("Nao foi possivel preparar a pasta de dados " + pasta + ": " + e.getMessage());
		}
	}
}
