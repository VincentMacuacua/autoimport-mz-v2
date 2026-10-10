package autoimport;

import autoimport.ui.Login;
import autoimport.ui.Tema;
import autoimport.util.ResourcePaths;

import javax.swing.SwingUtilities;

public class App
{
	public static void main(String[] args)
	{
		ResourcePaths.prepararDados();
		SwingUtilities.invokeLater(() ->
		{
			Tema.instalar();
			new Login();
		});
	}
}
