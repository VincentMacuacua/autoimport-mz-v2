package autoimport.service;
import autoimport.model.*;
import autoimport.util.PasswordUtil;

import java.util.*;

public class Pesquisa
{
	public Pesquisa()
	{

	}
	public int posicaoPass(ArrayList<Usuario> user,String passValidar)
	{
		for(int i=0;i<user.size();i++)
		{
			Usuario u=user.get(i);
			if(PasswordUtil.confere(passValidar,u.getPin()))
				return i;
		}
		return -1;
	}
	public int pesquisa_Code(ArrayList<Cliente> a,String campoTexto)
	{
		Cliente pai;
		int code=Integer.parseInt(campoTexto);

		for(int i=0;i<a.size();i++)
		{
			pai=a.get(i);
			if(pai.getCodeViatura()==code)
				return i;
		}
		return -1;
	}
	public int pesquisa_Nome(ArrayList<Cliente> a,String campoTexto)
	{
		Cliente pai;

		for(int i=0;i<a.size();i++)
		{
			pai=a.get(i);
			if(pai.getNomeCliente().equalsIgnoreCase(campoTexto))
				return i;
		}
		return -1;
	}
	public int pesquisa(ArrayList<Cliente> a,int code,String nome)
	{
		Cliente pai;

		for(int i=0;i<a.size();i++)
		{
			pai=a.get(i);
			if(pai.getNomeCliente().equalsIgnoreCase(nome) && pai.getCodeViatura()==code)
				return i;
		}
		return -1;
	}
	public int pesquisarNumeroTelefone_CLIENTE(String numeroTelefone,ArrayList<Cliente> a)
	{
		Cliente u;

		for(int i=0;i<a.size();i++)
		{
			u=a.get(i);
			if(u.getNumeroTelefone().equals(numeroTelefone))
				return i;
		}
		return -1;
	}
	public int pesquisarNumeroTelefone(String numeroTelefone,ArrayList<Usuario> a)
	{
		Usuario u;

		for(int i=0;i<a.size();i++)
		{
			u=a.get(i);
			if(u.getNumeroTelefone().equals(numeroTelefone))
				return i;
		}
		return -1;
	}

}
