package autoimport.model;


public class Estado extends Empresarial
{
	private String nomeIGovernal,incluirManutencao;
	public static int ctEstado=0;
	
	public Estado(String numeroTelefone,String nomeCliente,String dataCompra,String estadoCompra,String marcaViatura,String modeloViatura,int codeViatura,int cilindragem,int precoViatura,int qtyViaturas,String nomeIGovernal,String incluirManutencao)
	{
		super(numeroTelefone,nomeCliente,dataCompra,estadoCompra,marcaViatura,modeloViatura,codeViatura,cilindragem,precoViatura,qtyViaturas);
		this.nomeIGovernal=nomeIGovernal;
		this.incluirManutencao=incluirManutencao;
		ctEstado++;
	}
	public Estado()
	{
		this("","","","","","",0,0,0,0,"","");
	}
	public void setNomeIGovernal(String nomeIGovernal)
	{
		this.nomeIGovernal=nomeIGovernal;
	}
	public String getNomeIGovernal()
	{
		return nomeIGovernal;
	}
	public void setIncluirManutencao(String incluirManutencao)
	{
		this.incluirManutencao=incluirManutencao;
	}
	public String getIncluirManutencao()
	{
		return incluirManutencao;
	}
	public String toString()
	{
		return super.toString()+"\t|Nome de Instituicao Govermental: "+nomeIGovernal+"\t|Conte Manutencao: "+incluirManutencao+"\t|";
	}
	public float valorFinal()
	{
		return precoViatura+qtyViaturas*valor_Tax_Fixa();
	}
}
