package autoimport.service;
import autoimport.util.ResourcePaths;
import autoimport.model.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Remover
{
	public void remover(ArrayList<Cliente> a)
	{
		Cliente pai;
		Doutorado dou;
		Normal nor;
		Revendedor rev;
		Estado est;
		
		for(int i=0;i<a.size();i++)
		{
			pai=(Cliente)a.get(i);
			if(pai.getEstadoCompra().equalsIgnoreCase("entrega concluida"))
			{
				try
				{
					FileWriter fr=new FileWriter(ResourcePaths.dataFile("Cliente_de_Entregue_concluida.txt"),true);
					BufferedWriter bw=new BufferedWriter(fr);
					
					if(pai instanceof Doutorado)
					{
						dou=(Doutorado)pai;
						bw.write(dou.getNumeroTelefone()+";"+dou.getNomeCliente()+";"+dou.getDataCompra()+";"+dou.getEstadoCompra()+";"+dou.getMarcaViatura()+";"+dou.getModeloViatura()+";"+dou.getCodeViatura()+";"+dou.getCilindragem()+";"+dou.getPrecoViatura()+";"+'p'+";"+dou.getTipoViatura()+";"+'d'+";"+dou.getDataCDoutoramento());
						bw.newLine();
					}
					else if(pai instanceof Normal)
					{
						nor=(Normal)pai;
						bw.write(nor.getNumeroTelefone()+";"+nor.getNomeCliente()+";"+nor.getDataCompra()+";"+nor.getEstadoCompra()+";"+nor.getMarcaViatura()+";"+nor.getModeloViatura()+";"+nor.getCodeViatura()+";"+nor.getCilindragem()+";"+nor.getPrecoViatura()+";"+'p'+";"+nor.getTipoViatura()+";"+'n'+";"+nor.getEstrangeiro()+";"+nor.getNumeroAnosForaPais());
						bw.newLine();
					}
					else if(pai instanceof Revendedor)
					{
						rev=(Revendedor)pai;
						bw.write(rev.getNumeroTelefone()+";"+rev.getNomeCliente()+";"+rev.getDataCompra()+";"+rev.getEstadoCompra()+";"+rev.getMarcaViatura()+";"+rev.getModeloViatura()+";"+rev.getCodeViatura()+";"+rev.getCilindragem()+";"+rev.getPrecoViatura()+";"+'e'+";"+rev.getQtyViaturas()+";"+'r'+";"+rev.getNomeComercial());
						bw.newLine();
					}
					else if(pai instanceof Estado)
					{
						est=(Estado)pai;
						bw.write(est.getNumeroTelefone()+";"+est.getNomeCliente()+";"+est.getDataCompra()+";"+est.getEstadoCompra()+";"+est.getMarcaViatura()+";"+est.getModeloViatura()+";"+est.getCodeViatura()+";"+est.getCilindragem()+";"+est.getPrecoViatura()+";"+'e'+";"+est.getQtyViaturas()+";"+'e'+";"+est.getNomeIGovernal()+";"+est.getIncluirManutencao());
						bw.newLine();
					}
				
					a.remove(i);
					a.trimToSize();
					i--;
					
					if(pai instanceof Doutorado)
						Doutorado.ctDoutorado--;
					else if(pai instanceof Normal)
						Normal.ctNormal--;
					else if(pai instanceof Revendedor)
						Revendedor.ctRevendor--;
					else if(pai instanceof Estado)
						Estado.ctEstado--;
				bw.close();
				}
				catch(IOException io)
				{
					System.out.println(io.getMessage());
				}
			}
		}
		try {
		    FileWriter fr = new FileWriter(ResourcePaths.dataFile("Dados.txt"));
		    BufferedWriter bw = new BufferedWriter(fr);

		    for (int i = 0; i < a.size(); i++) 
		    {
		        pai=(Cliente)a.get(i);
		        if (!pai.getEstadoCompra().equalsIgnoreCase("entrega concluida")) 
		        {
		        	if(pai instanceof Doutorado)
					{
						dou=(Doutorado)pai;
						bw.write(dou.getNumeroTelefone()+";"+dou.getNomeCliente()+";"+dou.getDataCompra()+";"+dou.getEstadoCompra()+";"+dou.getMarcaViatura()+";"+dou.getModeloViatura()+";"+dou.getCodeViatura()+";"+dou.getCilindragem()+";"+dou.getPrecoViatura()+";"+'p'+";"+dou.getTipoViatura()+";"+'d'+";"+dou.getDataCDoutoramento());
						bw.newLine();
					}
					else if(pai instanceof Normal)
					{
						nor=(Normal)pai;
						bw.write(nor.getNumeroTelefone()+";"+nor.getNomeCliente()+";"+nor.getDataCompra()+";"+nor.getEstadoCompra()+";"+nor.getMarcaViatura()+";"+nor.getModeloViatura()+";"+nor.getCodeViatura()+";"+nor.getCilindragem()+";"+nor.getPrecoViatura()+";"+'p'+";"+nor.getTipoViatura()+";"+'n'+";"+nor.getEstrangeiro()+";"+nor.getNumeroAnosForaPais());
						bw.newLine();
					}
					else if(pai instanceof Revendedor)
					{
						rev=(Revendedor)pai;
						bw.write(rev.getNumeroTelefone()+";"+rev.getNomeCliente()+";"+rev.getDataCompra()+";"+rev.getEstadoCompra()+";"+rev.getMarcaViatura()+";"+rev.getModeloViatura()+";"+rev.getCodeViatura()+";"+rev.getCilindragem()+";"+rev.getPrecoViatura()+";"+'e'+";"+rev.getQtyViaturas()+";"+'r'+";"+rev.getNomeComercial());
						bw.newLine();
					}
					else if(pai instanceof Estado)
					{
						est=(Estado)pai;
						bw.write(est.getNumeroTelefone()+";"+est.getNomeCliente()+";"+est.getDataCompra()+";"+est.getEstadoCompra()+";"+est.getMarcaViatura()+";"+est.getModeloViatura()+";"+est.getCodeViatura()+";"+est.getCilindragem()+";"+est.getPrecoViatura()+";"+'e'+";"+est.getQtyViaturas()+";"+'e'+";"+est.getNomeIGovernal()+";"+est.getIncluirManutencao());
						bw.newLine();
					}
		        }
		    }

		    bw.close();
		} catch (IOException io) {
		    System.out.println(io.getMessage());
		}

		;
		JOptionPane.showMessageDialog(null, "Remocao do estado de entregue concluida!");		
	}
}
