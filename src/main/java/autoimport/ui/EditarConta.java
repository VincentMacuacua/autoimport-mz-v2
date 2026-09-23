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
	private TitledBorder title;
	private JLabel nameL, contL, nome;
	private String comboTitles[];
	private JComboBox combo;
	private JTextField contacto,novaPassword;
	private ImageIcon loginImage, contImagem;
	private JButton returnar, salvar,alterar;
	
	private Validacao val;
	private Pesquisa pes;
	private Alteracao alt;
	private Ficheiro file;
	
	
	public EditarConta(ArrayList<Usuario> a)
	{
		super("Alterar a password");
		ImageIcon icon = new ImageIcon(ResourcePaths.imagem("rose_lilaz_escuro.jpeg"));
		
		panel = new JPanel()
		{
            protected void paintComponent(Graphics g) 
            {
                super.paintComponent(g);
                g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
		setContentPane(panel);
		c=getContentPane();
		
		g=new GridBagLayout();
		c.setLayout(g);
		
		val=new Validacao();
		pes=new Pesquisa();
		alt=new Alteracao();
		file=new Ficheiro();
		
		panel=new JPanel();
		panel.setLayout(g);
		
		grid=new GridBagConstraints();
		grid.insets=new Insets(10,10,10,10);
		
		panel.setBackground(new Color(0, 128, 128,150));
		panel.setPreferredSize(new Dimension(350,400));
		
		title=BorderFactory.createTitledBorder("Alteracao da Password");
		title.setTitleJustification(TitledBorder.CENTER);
		title.setTitleColor(new Color(0, 255, 204));
		title.setTitleFont(new Font("Serif", Font.BOLD, 20));
		panel.setBorder(title);
		
		grid.gridx = 0;
		grid.gridy = 0;
		
		nameL = new JLabel("Nome:          ");
		nameL.setToolTipText("Seleciona o nome aulado");
		loginImage = new ImageIcon(ResourcePaths.imagem("login.jpg"));
		nameL.setIcon(loginImage);
		nameL.setForeground(Color.white);
		nameL.setBorder(BorderFactory.createLineBorder(new Color(0, 255, 204)));
		nameL.setFont(new Font("Dialog", Font.BOLD, 20));
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
		
		contL = new JLabel("Contacto: ");
		contL.setToolTipText("Introduz o seu numero de telefone");
		contImagem = new ImageIcon(ResourcePaths.imagem("passImage.jpg"));
		contL.setForeground(Color.white);
		contL.setIcon(contImagem);
		contL.setBorder(BorderFactory.createLineBorder(new Color(0, 255, 204)));
		contL.setFont(new Font("Dialog", Font.BOLD, 20));
		panel.add(contL,grid);
		
		grid.gridx = 2;
		grid.gridy = 1;
		
		contacto = new JTextField("",300);
		panel.add(contacto, grid);
		
		grid.gridx = 0;
		grid.gridy = 2;
		
		returnar = new JButton("Retornar");
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
		
		salvar=new JButton("Verificar");
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
		
		nome=new JLabel("Nova Password:");
		nome.setToolTipText("Introduz a nova password");
		nome.setForeground(Color.white);
		nome.setBorder(BorderFactory.createLineBorder(new Color(0,255,204)));
		nome.setFont(new Font("Dialog",Font.BOLD,20));
		nome.setVisible(false);
		panel.add(nome,grid);
		
		grid.gridx = 2;
		grid.gridy = 3;
		
		novaPassword=new JTextField();
		novaPassword.setBorder(BorderFactory.createLineBorder(new Color(0,255,204)));
		novaPassword.setVisible(false);
		panel.add(novaPassword,grid);
		
		grid.gridx = 1;
		grid.gridy = 4;
		
		alterar=new JButton("Alterar Passaword");
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
		
		
		
		
		c.add(panel);
		setSize(700,400);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(true);
		
		
	}
	
}
