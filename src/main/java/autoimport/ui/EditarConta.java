package autoimport.ui;
import autoimport.model.*;
import autoimport.util.*;
import autoimport.service.*;
import autoimport.persistence.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.*;
import java.util.*;


public class EditarConta extends JFrame
{
	private Container c;
	private GridBagLayout g;
	private GridBagConstraints grid;
	private JPanel panel;
	private JLabel nameL, contL, nome;
	private String comboTitles[];
	private JComboBox combo;
	private JTextField contacto,novaPassword;
	private JButton returnar, salvar,alterar;
	
	private Validacao val;
	private Pesquisa pes;
	private Alteracao alt;
	private Ficheiro file;
	
	
	public EditarConta(ArrayList<Usuario> a)
	{
		super("Recuperar password - AutoImport MZ");
		c=getContentPane();
		c.setLayout(new BorderLayout());
		c.add(Tema.cabecalho("Recuperar password", "Confirme o seu contacto para definir uma nova password"), BorderLayout.NORTH);
		
		g=new GridBagLayout();
		
		val=new Validacao();
		pes=new Pesquisa();
		alt=new Alteracao();
		file=new Ficheiro();
		
		panel=new JPanel();
		panel.setLayout(g);
		panel.setBorder(BorderFactory.createEmptyBorder(16,24,16,24));
		
		grid=new GridBagConstraints();
		grid.insets=new Insets(8,8,8,8);
		grid.anchor=GridBagConstraints.WEST;
		
		grid.gridx = 0;
		grid.gridy = 0;
		
		nameL = Tema.rotulo("Utilizador");
		nameL.setToolTipText("Seleciona o nome do utilizador");
		panel.add(nameL, grid);
		
		grid.gridx = 2;
		grid.gridy = 0;
		
		comboTitles =new String [a.size()];
		comboTitles=val.ternomes(a);
		
		combo = new JComboBox(comboTitles);
		grid.fill = GridBagConstraints.HORIZONTAL; 
		combo.setPreferredSize(new Dimension(250, combo.getPreferredSize().height));
		panel.add(combo, grid);
		
		grid.gridx = 0;
		grid.gridy = 1;
		
		contL = Tema.rotulo("Contacto");
		contL.setToolTipText("Introduz o seu numero de telefone");
		panel.add(contL,grid);
		
		grid.gridx = 2;
		grid.gridy = 1;
		
		contacto = new JTextField(20);
		contacto.putClientProperty("JTextField.placeholderText", "Ex.: 841234567");
		panel.add(contacto, grid);
		
		grid.gridx = 0;
		grid.gridy = 2;
		
		returnar = Tema.botaoSecundario(new JButton("Cancelar"));
		returnar.addActionListener
		(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent ev)
				{
					dispose();
				}
			}
		);
		panel.add(returnar,grid);
		
		grid.gridx = 2;
		grid.gridy = 2;
		
		salvar=Tema.botaoPrimario(new JButton("Verificar"));
		salvar.addActionListener
		(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent ev)
				{
					String numero;
					int poz,nomeUser;
					
					nomeUser=combo.getSelectedIndex();
					numero=contacto.getText();
					
					if(numero.length()<9 || numero.length()>10)
					{
						JOptionPane.showMessageDialog(null,"O numero de telefone tem que estar entre 9 a 10 elemento !","! Numero Invalido !",JOptionPane.WARNING_MESSAGE);
						contacto.setText("");
					}
					else
					{
						poz=pes.pesquisarNumeroTelefone(numero,a);
						if(poz==-1)
						{
							JOptionPane.showMessageDialog(null,"O numero de telefone nao exite","E R R O",JOptionPane.ERROR_MESSAGE);
							contacto.setText("");
						}
						else
						{
							if(nomeUser==poz)
							{
								contacto.setEditable(false);
								combo.setEditable(false);
								
								nome.setVisible(true);
								novaPassword.setVisible(true);
								alterar.setVisible(true);
								c.repaint();
								c.revalidate();
							}
						}
					}
				}
			}
		);
		panel.add(salvar,grid);
		
		grid.gridx = 0;
		grid.gridy = 3;
		
		nome=Tema.rotulo("Nova password");
		nome.setToolTipText("Introduz a nova password");
		nome.setVisible(false);
		panel.add(nome,grid);
		
		grid.gridx = 2;
		grid.gridy = 3;
		
		novaPassword=new JTextField();
		novaPassword.setVisible(false);
		panel.add(novaPassword,grid);
		
		grid.gridx = 1;
		grid.gridy = 4;
		
		alterar=Tema.botaoPrimario(new JButton("Alterar password"));
		alterar.setVisible(false);
		alterar.addActionListener
		(
			new ActionListener()
			{
				String pass,numero;
				int poz;
				public void actionPerformed(ActionEvent ev)
				{
					pass=novaPassword.getText();
					numero=contacto.getText();
					poz=pes.pesquisarNumeroTelefone(numero,a);
					
					alt.alterarPassword(a,pass,poz);
					file.actualizarFicheiro_users(a,pass,numero,poz);
					
					dispose();
					JOptionPane.showMessageDialog(null,"A password trocada com Sucesso por "+novaPassword.getText(),"Confirmacao",JOptionPane.WARNING_MESSAGE);
				}
			}
		);
		panel.add(alterar,grid);
		
		
		
		
		c.add(panel, BorderLayout.CENTER);
		setSize(620,400);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(true);
		
		
	}
	
}
