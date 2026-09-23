package autoimport.service;
import autoimport.model.*;
import autoimport.util.PasswordUtil;

import java.util.ArrayList;

public class Validacao
{
	public Validacao()
	{

	}
	public String [] ternomes(ArrayList<Usuario> list)
	{
		String [] s=new String[list.size()];
		Usuario u;

		for(int i=0;i<list.size();i++)
		{
			u=list.get(i);
			s[i]=u.getNome();
		}

		return s;
	}
	public String terPass(ArrayList<Usuario> list,String pass)
	{
		Usuario usuario;

		for(int i=0;i<list.size();i++)
		{
			usuario=list.get(i);
			if(PasswordUtil.confere(pass,usuario.getPin()))
				return usuario.getPin();
		}
		return "";
	}

}
