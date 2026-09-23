package autoimport.persistence;

import autoimport.model.*;
import autoimport.util.PasswordUtil;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Ficheiro
{
	public Ficheiro()
	{

	}
	public void actualizarFicheiro_users(ArrayList<Usuario> a,String novaPass,String numero,int poz)
	{
		try (BufferedWriter b = new BufferedWriter(new FileWriter("data/users.txt")))
		{
			for(int i=0;i<a.size();i++)
			{
				Usuario u=a.get(i);
				if(u.getNumeroTelefone().equals(numero))
				{
					b.write(u.getNome()+";"+PasswordUtil.hash(novaPass)+";"+u.getNumeroTelefone());
					b.newLine();
				}
				else
				{
					b.write(u.getNome()+";"+u.getPin()+";"+u.getNumeroTelefone());
					b.newLine();
				}
			}
		}
		catch(IOException io)
		{
			JOptionPane.showMessageDialog(null,io.getMessage(),"ERRO",JOptionPane.ERROR_MESSAGE);
		}
	}
}
