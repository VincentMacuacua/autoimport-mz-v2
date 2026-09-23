package autoimport.ui;
import autoimport.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*; //Para as bordas
import java.io.*;
import java.util.*;


public class Ajuda extends JFrame
{
	private Container cont;
	private JPanel panel, panelInstrucoes;
	private TitledBorder title, titleImagens;
	private JLabel label;
	//Labels Identificadores com imagens
	private JLabel l1, l2, l3, l4, l5, l6, l7, l8;
	//Imagens
	private Icon i1, i2, i3, i4, i5, i6, i7,i8;
	
	
	public Ajuda()
	{
		super("Manual de usuario");
		ImageIcon icon = new ImageIcon(ResourcePaths.imagem("rose_lilaz_escuro.jpeg"));
		
		JPanel panel = new JPanel()
		{
            protected void paintComponent(Graphics g) 
            {
                super.paintComponent(g);
                g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
		
		
		setContentPane(panel);
		cont = getContentPane();
		cont.setLayout(new BorderLayout());
		//Panel Imagens
		panel = new JPanel();
		panel.setLayout(new GridLayout(9,1));
		//Panel Instrucoes
		panelInstrucoes = new JPanel();
		panelInstrucoes.setLayout(new GridLayout(9,1));
		
		
		panel.setBackground(new Color(0, 128, 128,150));
//		panel.setBackground(Color.white);
		panel.setPreferredSize(new Dimension(350,400));
		
		//BackGroud Para a parte das Instrucoes
		panelInstrucoes.setBackground(new Color(0, 128, 128,60));
		panelInstrucoes.setPreferredSize(new Dimension(350,400));
		
		//Adicionando TitleBorder
		title = BorderFactory.createTitledBorder("Descrição");
		title.setTitleJustification(TitledBorder.CENTER);
		title.setTitleColor(new Color(0, 255, 204));
		title.setTitleFont(new Font("Serif", Font.BOLD, 20));
		//Titulo para Imagens
		titleImagens = BorderFactory.createTitledBorder("Imagens");
		titleImagens.setTitleJustification(TitledBorder.CENTER);
		titleImagens.setTitleColor(new Color(0, 255, 204));
		titleImagens.setTitleFont(new Font("Serif", Font.BOLD, 20));
		panel.setBorder(title);
		panelInstrucoes.setBorder(titleImagens);
		
		
		
		//Dentro da janela das imagens
		l1 = new JLabel("1: Bara de Menu ------------->");
		l2 = new JLabel("2: Item Home:   ---------------------------------------------------------------->");
		l3 = new JLabel("3: Item Utilizador ----------------------------------------------------------->");
		l4 = new JLabel("4: Item Registar--------------------------------------------------------------->");
		l5 = new JLabel("5: Item Operacoes ---------------------------------------------------------->");
		l6 = new JLabel("6: Item Relatorio ------------------------------------------------------------>");
		l7 = new JLabel("7: Item Ajuda ------------------------------------------------------------------->");
		l8 = new JLabel("8: Item Extra -------------------------------------------------------------------->");
		JLabel lFinal = new JLabel("BeForwad MZ");
		//Aumentando o tamanho da letra na nos Lanels
		l1.setFont(new Font("Serif", Font.PLAIN, 30));
		l2.setFont(new Font("Serif", Font.PLAIN, 30));
		l3.setFont(new Font("Serif", Font.PLAIN, 30));
		l4.setFont(new Font("Serif", Font.PLAIN, 30));
		l5.setFont(new Font("Serif", Font.PLAIN, 30));
		l6.setFont(new Font("Serif", Font.PLAIN, 30));
		l7.setFont(new Font("Serif", Font.PLAIN, 30));
		l8.setFont(new Font("Serif", Font.PLAIN, 30));
		lFinal.setFont(new Font("Serif", Font.PLAIN, 30));
		lFinal.setHorizontalAlignment(SwingConstants.CENTER);

		//cor das escritas nos Labels
		l1.setForeground(Color.white);
		l2.setForeground(Color.white);
		l3.setForeground(Color.white);
		l4.setForeground(Color.white);
		l5.setForeground(Color.white);
		l6.setForeground(Color.white);
		l7.setForeground(Color.white);
		l8.setForeground(Color.white);
		lFinal.setForeground(Color.white);
		
		//Criacao dos Icons
		i1 = new ImageIcon(ResourcePaths.imagem("bara.png"));
		i2 = new ImageIcon(ResourcePaths.imagem("Home.png"));
		i3 = new ImageIcon(ResourcePaths.imagem("Utilizador.png"));
		i4 = new ImageIcon(ResourcePaths.imagem("Registar.png"));
		i5 = new ImageIcon(ResourcePaths.imagem("Operacoes.png"));
		i6 = new ImageIcon(ResourcePaths.imagem("Relatorio.png"));
		i7 = new ImageIcon(ResourcePaths.imagem("Ajuda.png"));
		i8 = new ImageIcon(ResourcePaths.imagem("Extra.png"));
		//Colocando imagens em caga Label
		l1.setIcon(i1);
		l2.setIcon(i2);
		l3.setIcon(i3);
		l4.setIcon(i4);
		l5.setIcon(i5);
		l6.setIcon(i6);
		l7.setIcon(i7);
		l8.setIcon(i8);
		
		//Adicionando os labels no JPanel das imagens
//		panel.add(panel);
//		panelInstrucoes.add(l1);
		
		//Dentro do JPanel
		panelInstrucoes.add(l1);
		panelInstrucoes.add(l2);
		panelInstrucoes.add(l3);
		panelInstrucoes.add(l4);
		panelInstrucoes.add(l5);
		panelInstrucoes.add(l6);
		panelInstrucoes.add(l7);
		panelInstrucoes.add(l8);
		panelInstrucoes.add(lFinal);

		
		//Para panel de informacoes(Panel)
		JLabel lb1 = new JLabel("<html>\"AutoImport MZ é um sistema de gestão desenvolvido para facilitar o processo de registo, controlo e análise de vendas de veículos, oferecendo ao utilizador uma interface intuitiva e funcional.\"</html>");
		JLabel lb2 = new JLabel("<html>\"Home:Leva o utilizador de volta à tela inicial, onde pode visualizar atalhos e informações gerais do sistema.\"<html>");	
		JLabel lb3 = new JLabel("<html>\"Utilizador:\r\n"
				+ "Permite a gestão de contas de acesso, incluindo o registo de novos utilizadores, edição de dados e definição de permissões.\"<html>");	
		JLabel lb4 = new JLabel("<html>\"Registar:\r\n"
				+ "Esta opção permite adicionar novos registos ao sistema, como veículos, clientes ou fornecedores.\"<html>");	
		JLabel lb5 = new JLabel("<html>\"Operações:\r\n"
				+ "Área destinada à execução das transações, como a venda e compra de viaturas, bem como a emissão de comprovativos\"<html>");	
		JLabel lb6 = new JLabel("<html>\"Relatório:\r\n"
				+ "Gera relatórios detalhados sobre vendas, veículos disponíveis, movimentações e desempenho do negócio.\"<html>");	
		JLabel lb7 = new JLabel("<html>\"Ajuda:\r\n"
				+ "Disponibiliza orientações sobre o uso do sistema, incluindo contacto para suporte técnico\"<html>");	
		JLabel lb8 = new JLabel("<html>\"Extra:\r\n"
				+ "Contém funcionalidades adicionais, Calculadora, Tocar musica ou video\"<html>");	
		JLabel lbFinal = new JLabel("<html>\"Desenvolvido por: Vicente Macuácua\"<html>");
		
		
		lb1.setForeground(Color.white);
		lb2.setForeground(Color.white);
		lb3.setForeground(Color.white);
		lb4.setForeground(Color.white);
		lb5.setForeground(Color.white);
		lb6.setForeground(Color.white);
		lb7.setForeground(Color.white);
		lb8.setForeground(Color.white);
		lbFinal.setForeground(Color.white);

		panel.add(lb1);
		panel.add(lb2);
		panel.add(lb3);
		panel.add(lb4);
		panel.add(lb5);
		panel.add(lb6);
		panel.add(lb7);
		panel.add(lb8);
		panel.add(lbFinal);

		
		cont.add(panel, BorderLayout.EAST);
		cont.add(panelInstrucoes, BorderLayout.CENTER);
		cont.repaint();
		cont.revalidate();
		
		setSize(1300,750);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
		setResizable(false);
	}
}







