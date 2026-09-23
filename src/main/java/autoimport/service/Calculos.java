package autoimport.service;
import autoimport.model.*;

import java.util.ArrayList;

public class Calculos
{
	public Calculos()
	{

	}
	public int calcular_Quantidade(ArrayList<Cliente> a)
	{
		int ct=0,ctEst=0,ctRev=0;
		Cliente pai;
		Estado est;
		Revendedor rev;

		for(int i=0;i<a.size();i++)
		{
			pai=a.get(i);
			if(pai instanceof Estado)
			{
				est=(Estado)pai;
				ctEst+=est.getQtyViaturas();
			}
			else if(pai instanceof Revendedor)
			{
				rev=(Revendedor)pai;
				ctRev+=rev.getQtyViaturas();
			}
		}
		ctEst*=Estado.ctEstado;
		ctRev*=Revendedor.ctRevendor;
		ct=Normal.ctNormal+Doutorado.ctDoutorado+ctEst+ctRev;

		return ct;
	}
	public double calculo_margem(double valorTotal)
	{
		return valorTotal-1900000;
	}
	public float acumulador_Normal(ArrayList<Cliente> a)
	{
		float acumulador=0;
		Cliente pai;

		for(int i=0;i<a.size();i++)
		{
			pai=a.get(i);
			if(pai instanceof Normal)
			{
				acumulador+=pai.valorFinal();
			}
		}
		return acumulador;
	}
	public float acumulador_Revendedor(ArrayList<Cliente> a)
	{
		float acumulador=0;
		Cliente pai;

		for(int i=0;i<a.size();i++)
		{
			pai=a.get(i);
			if(pai instanceof Revendedor)
			{
				acumulador+=pai.valorFinal();
			}
		}
		return acumulador;
	}
	public float acumulador_Estado(ArrayList<Cliente> a)
	{
		float acumulador=0;
		Cliente pai;

		for(int i=0;i<a.size();i++)
		{
			pai=a.get(i);
			if(pai instanceof Estado)
			{
				acumulador+=pai.valorFinal();
			}
		}
		return acumulador;
	}
	public float acumulador_Doutorado(ArrayList<Cliente> a)
	{
		float acumulador=0;
		Cliente pai;

		for(int i=0;i<a.size();i++)
		{
			pai=a.get(i);
			if(pai instanceof Doutorado)
			{
				acumulador+=pai.valorFinal();
			}
		}
		return acumulador;
	}
	public float acumuladorAdoaneiro(ArrayList<Cliente> a)
	{
		float acumulador=0;

		for(int i=0;i<a.size();i++)
		{
			acumulador+=a.get(i).direitosAdoaneiro();
		}

		return acumulador;
	}
	public double acumuladorGeral(ArrayList<Cliente> a)
	{
		double acumulador=0;

		for(int i=0;i<a.size();i++)
		{
			acumulador+=a.get(i).valorFinal();
		}
		return acumulador;
	}
}
