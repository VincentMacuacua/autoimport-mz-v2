package autoimport.model;


public class Usuario 
{
	private String nome,pin,numeroTelefone;
	
	public Usuario(String nome,String pin,String numeroTelefone)
	{
		this.nome=nome;
		this.pin=pin;
		this.numeroTelefone=numeroTelefone;
	}
	public Usuario()
	{
		this("","","");
	}
	public String getNome() {return nome;}
	public String getPin() {return pin;}
	public String getNumeroTelefone() {return numeroTelefone;}
	
	public void setNome(String nome) {this.nome=nome;}
	public void setPin(String pin) {this.pin=pin;}
	public void setNumeroTelefone(String numeroTelefone) {this.numeroTelefone=numeroTelefone;}
	
}
