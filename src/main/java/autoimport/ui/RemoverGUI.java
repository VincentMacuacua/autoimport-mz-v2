package autoimport.ui;
import autoimport.util.*;
import autoimport.model.*;
import autoimport.service.*;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

public class RemoverGUI extends JFrame 
{
	private Container cont;
	private JLabel introNumero;
	private JTextField numeroT;
	private JButton enter;
	private ImageIcon removeIcon;
	
	private Pesquisa pes;
	
	public RemoverGUI(ArrayList<Cliente> a)
	{
		super("Remover Cliente");
		cont=getContentPane();
		//cont.setLayout(new GridLayout(2,1,5,5));
		
		pes=new Pesquisa();
		
		JPanel panePrin=new JPanel();
		panePrin.setLayout(new FlowLayout());
		
		introNumero=new JLabel("Introduz numero de telefone");
		numeroT=new JTextField(20);
		
		panePrin.add(introNumero);
		panePrin.add(numeroT);
		panePrin.setBackground(new Color(28, 169, 201));
		
		cont.add(panePrin,BorderLayout.NORTH);
		
		enter=new JButton("Remover");
		removeIcon=new ImageIcon(ResourcePaths.imagem("remover1.png"));
		enter.setIcon(removeIcon);
		enter.setForeground(Color.red);
		enter.addActionListener
		(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent ev)
				{
					Cliente pai;
					Doutorado dou;
					Normal nor;
					Revendedor rev;
					Estado est;
					String numero=numeroT.getText();
					
					if(numero.equals(""))
					{
						JOptionPane.showMessageDialog(null, "Por favor, preencha o numero de telefone!", "Erro", JOptionPane.ERROR_MESSAGE);
						return;
					}
					if (numero.length() < 9 || numero.length() > 10)
				    {
						JOptionPane.showMessageDialog(null, "O número de telefone deve ter entre 9 e 10 dígitos.", "Erro", JOptionPane.ERROR_MESSAGE);
					    numeroT.setText("");
					    return;
				    }
					String sub=numero.substring(0, 2);
	                if (!(sub.equals("82") || sub.equals("83") || sub.equals("84") || sub.equals("85") || sub.equals("86") || sub.equals("87"))) 
	                {
	                	JOptionPane.showMessageDialog(null, "O número de telefone deve começar com: 82, 83, 84, 85, 86 ou 87.", "Erro", JOptionPane.WARNING_MESSAGE);
	                    numeroT.setText("");
	                    return;
	                }
	                int poz=pes.pesquisarNumeroTelefone_CLIENTE(numero,a);
	                if(poz==-1)
	                	JOptionPane.showMessageDialog(null,"Nao existe esse cliente","ERRO",JOptionPane.ERROR_MESSAGE);
	                else
	                {
	                	try 
	                	{
	                	    FileWriter fr = new FileWriter("data/Dados.txt");
	                	    BufferedWriter bw = new BufferedWriter(fr);

	                	    for (int i = 0; i < a.size(); i++) 
	                	    {
	                	        if (i == poz) 
	                	        	continue; // pula o cliente a remover

	                	        pai = (Cliente) a.get(i);

	                	        if (pai instanceof Doutorado) 
	                	        {
	                	            dou = (Doutorado) pai;
	                	            bw.write(dou.getNumeroTelefone() + ";" + dou.getNomeCliente() + ";" + dou.getDataCompra() + ";" +dou.getEstadoCompra() + ";" + dou.getMarcaViatura() + ";" + dou.getModeloViatura() + ";" +dou.getCodeViatura() + ";" + dou.getCilindragem() + ";" + dou.getPrecoViatura() + ";" +'p' + ";" + dou.getTipoViatura() + ";" + 'd' + ";" + dou.getDataCDoutoramento());
	                	            bw.newLine();
	                	        }
	                	        else if (pai instanceof Normal) 
	                	        {
	                	            nor = (Normal) pai;
	                	            bw.write(nor.getNumeroTelefone() + ";" + nor.getNomeCliente() + ";" + nor.getDataCompra() + ";" +
	                	                     nor.getEstadoCompra() + ";" + nor.getMarcaViatura() + ";" + nor.getModeloViatura() + ";" +
	                	                     nor.getCodeViatura() + ";" + nor.getCilindragem() + ";" + nor.getPrecoViatura() + ";" +
	                	                     'p' + ";" + nor.getTipoViatura() + ";" + 'n' + ";" + nor.getEstrangeiro() + ";" + nor.getNumeroAnosForaPais());
	                	            bw.newLine();
	                	        }
	                	        else if (pai instanceof Revendedor) 
	                	        {
	                	            rev = (Revendedor) pai;
	                	            bw.write(rev.getNumeroTelefone() + ";" + rev.getNomeCliente() + ";" + rev.getDataCompra() + ";" +rev.getEstadoCompra() + ";" + rev.getMarcaViatura() + ";" + rev.getModeloViatura() + ";" +rev.getCodeViatura() + ";" + rev.getCilindragem() + ";" + rev.getPrecoViatura() + ";" +'e' + ";" + rev.getQtyViaturas() + ";" + 'r' + ";" + rev.getNomeComercial());
	                	            bw.newLine();
	                	        }
	                	        else if (pai instanceof Estado) 
	                	        {
	                	            est = (Estado) pai;
	                	            bw.write(est.getNumeroTelefone() + ";" + est.getNomeCliente() + ";" + est.getDataCompra() + ";" +est.getEstadoCompra() + ";" + est.getMarcaViatura() + ";" + est.getModeloViatura() + ";" +est.getCodeViatura() + ";" + est.getCilindragem() + ";" + est.getPrecoViatura() + ";" +'e' + ";" + est.getQtyViaturas() + ";" + 'e' + ";" + est.getNomeIGovernal() + ";" + est.getIncluirManutencao());
	                	            bw.newLine();
	                	        }
		                	    
	                	    }

	                	    bw.close();

	                	    // Remove o cliente da lista e decrementa o contador
	                	    	                	    
	                	    Cliente paiR=(Cliente)
            	    		a.remove(poz);
	                	    a.trimToSize();
	                	    if (paiR instanceof Doutorado) 
	                	    	Doutorado.ctDoutorado--;
	                	    else if (paiR instanceof Normal) 
	                	    	Normal.ctNormal--;
	                	    else if (paiR instanceof Revendedor) 
	                	    	Revendedor.ctRevendor--;
	                	    else if (paiR instanceof Estado) 
	                	    	Estado.ctEstado--;
	                	} 
	                	catch (IOException io) 
	                	{
	                	    JOptionPane.showMessageDialog(null,"Erro ao escrever no arquivo: " + io.getMessage());
	                	}
	                }
	                
	                JOptionPane.showMessageDialog(null,"O Cliente removido com Sucesso !");
				}
			}
		);
		
		cont.add(enter,BorderLayout.SOUTH);
		
		cont.setBackground(new Color(255, 100, 0));
		
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(500,200);
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
