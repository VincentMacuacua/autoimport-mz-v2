package autoimport.model;

import java.io.*;

public abstract class Cliente implements Serializable
{
	protected String numeroTelefone,nomeCliente,dataCompra,estadoCompra,marcaViatura,modeloViatura;
	protected int codeViatura,cilindragem,precoViatura;
	
	public Cliente(String numeroTelefone,String nomeCliente,String dataCompra,String estadoCompra,String marcaViatura,String modeloViatura,int codeViatura,int cilindragem,int precoViatura)
	{
		this.numeroTelefone=numeroTelefone;
		this.nomeCliente=nomeCliente;
		this.dataCompra=dataCompra;
		this.estadoCompra=estadoCompra;
		this.marcaViatura=marcaViatura;
		this.modeloViatura=modeloViatura;
		this.codeViatura=codeViatura;
		this.cilindragem=cilindragem;
		this.precoViatura=precoViatura;
		
	}
	public Cliente()
	{
		this("","","","","","",0,0,0);
	}
	public void setNumeroTelefone(String numeroTelefone)
	{
		this.numeroTelefone=numeroTelefone;
	}
	public String getNumeroTelefone()
	{
		return numeroTelefone;
	}
	public void setNomeCliente(String nomeCliente)
	{
		this.nomeCliente=nomeCliente;
	}
	public String getNomeCliente()
	{
		return nomeCliente;
	}
	public void setDataCompra(String dataCompra)
	{
		this.dataCompra=dataCompra;
	}
	public String getDataCompra() 
	{
		return dataCompra;
	}
	public void setEstadoCompra(String estadoCompra)
	{
		this.estadoCompra=estadoCompra;
	}
	public String getEstadoCompra()
	{
		return estadoCompra;
	}
	public void setMarcaViatura(String marcaViatura)
	{
		this.marcaViatura=marcaViatura;
	}
	public String getMarcaViatura()
	{
		return marcaViatura;
	}
	public void setModeloViatura(String modeloViatura)
	{
		this.modeloViatura=modeloViatura;
	}
	public String getModeloViatura()
	{
		return modeloViatura;
	}
	public void setCodeViatura(int codeViatura)
	{
		this.codeViatura=codeViatura;
	}
	public int getCodeViatura()
	{
		return codeViatura;
	}
	public void setCilindragem(int cilindragem)
	{
		this.cilindragem=cilindragem;
	}
	public int getCilindragem()
	{
		return cilindragem;
	}
	public void setPrecoViatura(int precoViatura)
	{
		this.precoViatura=precoViatura;
	}
	public int getPrecoViatura()
	{
		return precoViatura;
	}
	public String toString()
	{
		return "| Numero Telefone: "+numeroTelefone+"\t|Nome do(a) Clinete: "+nomeCliente+"\t|Data da compra: "+dataCompra+"\t|Estado da compra: "+estadoCompra+"\t|Marca da Viatura: "+marcaViatura+"\t|Modelo da Viatura: "+modeloViatura+"\t|Codigo da viatura"+codeViatura+"\t|Cilidagems: "+cilindragem+"\t|Valor da Viatura: "+precoViatura+" |";
	}
	public float direitosAdoaneiro()
	{
		final float TAX15=15/100f;
		return (precoViatura*TAX15);
	}
	public float direitosAdoaneiroTotal()
	{
		return precoViatura+direitosAdoaneiro();
	}
	public abstract float valorFinal();
	
	public float valor_Tax_Fixa()
	{
		final int TAXADOANEIRO10=10000,TAXINSPENSAO5=5000;
		return TAXADOANEIRO10+TAXINSPENSAO5;
	}
}
