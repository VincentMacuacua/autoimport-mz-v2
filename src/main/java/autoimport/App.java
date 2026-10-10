package autoimport;

import autoimport.ui.Login;
import autoimport.ui.Tema;

import javax.swing.SwingUtilities;

public class App
{
	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(() ->
		{
			Tema.instalar();
			new Login();
		});
	}
}
