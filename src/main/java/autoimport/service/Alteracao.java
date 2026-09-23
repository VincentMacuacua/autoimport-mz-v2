package autoimport.service;
import autoimport.model.*;
import autoimport.util.PasswordUtil;

import java.util.ArrayList;

public class Alteracao
{

	public Alteracao()
	{

	}
	public void alterarPassword(ArrayList<Usuario> a,String pass,int poz)
	{
		for(int i=0;i<a.size();i++)
		{
			if(i==poz)
				a.get(i).setPin(PasswordUtil.hash(pass));
		}
	}
}
