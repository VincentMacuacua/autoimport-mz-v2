package autoimport.ui;

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
	private JPanel panel;
	private JLabel label;
	
	
	public Ajuda()
	{
		super("Manual do utilizador - AutoImport MZ");

		cont = getContentPane();
		cont.setLayout(new BorderLayout());
		cont.setBackground(Tema.FUNDO);
		cont.add(Tema.cabecalho("Manual do utilizador", "Guia rápido das áreas do menu do AutoImport MZ"), BorderLayout.NORTH);

		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBackground(Tema.FUNDO);
		panel.setBorder(BorderFactory.createEmptyBorder(16, 20, 20, 20));

		panel.add(entrada("Sobre o sistema",
			"O AutoImport MZ é um sistema de gestão desenvolvido para facilitar o registo, controlo e análise de vendas de veículos, com uma interface intuitiva e funcional."));
		panel.add(entrada("Home",
			"Carregar os dados, abrir o dashboard, consultar estatísticas e sair do programa."));
		panel.add(entrada("Utilizador",
			"Ver os dados do utilizador com sessão iniciada e terminar a sessão."));
		panel.add(entrada("Registar",
			"Adicionar, procurar, editar o estado da compra e remover clientes."));
		panel.add(entrada("Relatório",
			"Tabelas de clientes (todos ou por tipo) e exportação das compras para PDF."));
		panel.add(entrada("Ajuda",
			"Este manual, com orientações sobre o uso do sistema."));
		panel.add(entrada("Extra",
			"Funcionalidades adicionais: reproduzir música ou vídeo (botão direito no ecrã inicial)."));

		label = Tema.textoSuave("Desenvolvido por Vicente Macuácua");
		label.setAlignmentX(Component.LEFT_ALIGNMENT);
		label.setBorder(BorderFactory.createEmptyBorder(8, 4, 0, 0));
		panel.add(label);

		JScrollPane scroll = new JScrollPane(panel);
		scroll.setBorder(BorderFactory.createEmptyBorder());
		scroll.getVerticalScrollBar().setUnitIncrement(16);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		cont.add(scroll, BorderLayout.CENTER);

		setSize(820,720);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	/** Um cartao do manual: titulo e descricao de uma area do sistema. */
	private JPanel entrada(String titulo, String descricao)
	{
		JPanel cartao = Tema.cartao(new BorderLayout(20, 0));
		cartao.setAlignmentX(Component.LEFT_ALIGNMENT);
		cartao.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

		JPanel texto = new JPanel();
		texto.setOpaque(false);
		texto.setLayout(new BoxLayout(texto, BoxLayout.Y_AXIS));
		JLabel t = Tema.rotulo(titulo);
		t.setFont(Tema.fonte(Font.BOLD, 15f));
		JLabel d = new JLabel("<html><div style='width:500px'>" + descricao + "</div></html>");
		d.setForeground(Tema.TEXTO_SUAVE);
		d.setBorder(BorderFactory.createEmptyBorder(6, 0, 0, 0));
		texto.add(t);
		texto.add(d);
		cartao.add(texto, BorderLayout.CENTER);

		JPanel envolvente = new JPanel(new BorderLayout());
		envolvente.setOpaque(false);
		envolvente.setAlignmentX(Component.LEFT_ALIGNMENT);
		envolvente.setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));
		envolvente.add(cartao);
		envolvente.setMaximumSize(new Dimension(Integer.MAX_VALUE, envolvente.getPreferredSize().height));
		return envolvente;
	}
}
