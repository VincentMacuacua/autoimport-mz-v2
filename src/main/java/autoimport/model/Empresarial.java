package autoimport.model;


public abstract class Empresarial extends Cliente
{
	protected int qtyViaturas;
	
	public Empresarial(String numeroTelefone,String nomeCliente,String dataCompra,String estadoCompra,String marcaViatura,String modeloViatura,int codeViatura,int cilindragem,int precoViatura,int qtyViaturas)
	{
		super(numeroTelefone,nomeCliente,dataCompra,estadoCompra,marcaViatura,modeloViatura,codeViatura,cilindragem,precoViatura);
		this.qtyViaturas=qtyViaturas;
	}
	public Empresarial()
	{
		this("","","","","","",0,0,0,0);
	}
	public void setQtyViaturas(int qtyViaturas)
	{
		this.qtyViaturas=qtyViaturas;
	}
	public int getQtyViaturas()
	{
		return qtyViaturas;
	}
	public String toString()
	{
		return super.toString()+"\tA quantidade de viaturas: "+qtyViaturas;
	}
}
