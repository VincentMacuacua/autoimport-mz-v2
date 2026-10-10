package autoimport.ui;
import autoimport.model.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class TabelaPorTipo extends JFrame implements ItemListener
{
	private Container cont;
	private String[] titulosDoutorado = {"Nome do cliente", "Numero do cliente","Data de compra","Estado de compra","Marca da viatura","Modelo da viatura","Codigo da viatura","Cilindragem","Preco da viatura","Tipo da viatura","Data de conclusao do doutoramento"};
	private String[] titulosNormal = {"Nome do cliente", "Numero do cliente","Data de compra","Estado de compra","Marca da viatura","Modelo da viatura","Codigo da viatura","Cilindragem","Preco da viatura","Tipo da vitura","Esteve no estrangeiro", "Numero de anos fora"};
	private String[] titulosRevendedor = {"Nome do cliente", "Numero do cliente","Data de compra","Estado de compra","Marca da viatura","Modelo da viatura","Codigo da viatura","Cilindragem","Preco da viatura","Quantidade de viaturas","Nome Comercial"};
	private String[] titulosEstado = {"Nome do cliente", "Numero do cliente","Data de compra","Estado de compra","Marca da viatura","Modelo da viatura","Codigo da viatura","Cilindragem","Preco da viatura","Quantidade de viaturas","Nome da instituicao governamental","Deseja incluir a manutencao"};
	private JTable tabelaD, tabelaN, tabelaR, tabelaE;
	private String[][] arrayD, arrayN, arrayR, arrayE;
	private JScrollPane scrollD, scrollN, scrollR, scrollE;
	private JPanel panelRadio, panelD, panelN, panelR, panelE, panelGeral;
	private ButtonGroup buttonGroup;
	private JRadioButton d,e,n,r;
	private CardLayout card;
	
	public TabelaPorTipo(ArrayList<Cliente> lista)
	{
		super("Clientes por tipo - AutoImport MZ");

        card = new CardLayout();
		cont = getContentPane();
		cont.setLayout(new BorderLayout());
		cont.setBackground(Tema.FUNDO);
		panelGeral = new JPanel();  //O painel que vai conter todas as tabelas
		panelGeral.setLayout(card);
		panelGeral.setOpaque(false);
		panelGeral.setBorder(BorderFactory.createEmptyBorder(0,20,20,20));
		//Inicializacao dos JPanels
        panelD = new JPanel(new BorderLayout());
        panelN = new JPanel(new BorderLayout());
        panelE = new JPanel(new BorderLayout());
        panelR = new JPanel(new BorderLayout());
        //Inicializacao dos RadioButtons
        panelRadio = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panelRadio.setOpaque(false);
        panelRadio.setBorder(BorderFactory.createEmptyBorder(16,20,12,20));
        buttonGroup = new ButtonGroup();

        n = new JRadioButton("Normal",false);
        e = new JRadioButton("Estado",false);
        d = new JRadioButton("Doutorado",true);
        r = new JRadioButton("Revendedor",false);

        // Os botoes aparecem como um seletor segmentado (sem o circulo do radio)
        for (JRadioButton b : new JRadioButton[]{d, n, e, r})
        {
        	b.setIcon(new ImageIcon(new java.awt.image.BufferedImage(1, 1, java.awt.image.BufferedImage.TYPE_INT_ARGB)));
        	b.setFont(Tema.fonte(Font.BOLD, 13f));
        	b.setFocusPainted(false);
        	b.setOpaque(true);
        	b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        	buttonGroup.add(b);
        	panelRadio.add(b);
        	panelRadio.add(Box.createHorizontalStrut(8));
        }

		arrayD = tabelaDoutorado(lista, lista.size());
		tabelaD = Tema.tabela(arrayD, titulosDoutorado);
		scrollD = Tema.rolagem(tabelaD);
		panelD.add(scrollD, BorderLayout.CENTER);
		panelGeral.add(panelD,"doutor");

		arrayN = tabelaNormal(lista, lista.size());
		tabelaN = Tema.tabela(arrayN,titulosNormal);
		scrollN = Tema.rolagem(tabelaN);
		panelN.add(scrollN, BorderLayout.CENTER);
		panelGeral.add(panelN,"normal");

		arrayR = tabelaRevendedor(lista, lista.size());
		tabelaR = Tema.tabela(arrayR,titulosRevendedor);
		scrollR = Tema.rolagem(tabelaR);
		panelR.add(scrollR, BorderLayout.CENTER);
		panelGeral.add(panelR,"revendedor");

		arrayE = tabelaEstado(lista, lista.size());
		tabelaE = Tema.tabela(arrayE,titulosEstado);
		scrollE = Tema.rolagem(tabelaE);
		panelE.add(scrollE, BorderLayout.CENTER);
		panelGeral.add(panelE, "estado");

		JPanel topo = new JPanel(new BorderLayout());
		topo.setOpaque(false);
		topo.add(Tema.cabecalho("Clientes por tipo", "Escolha o tipo de cliente para ver os respetivos dados"), BorderLayout.NORTH);
		topo.add(panelRadio, BorderLayout.SOUTH);
		cont.add(topo, BorderLayout.NORTH);
		cont.add(panelGeral, BorderLayout.CENTER);

		//Eventos dos redioButtons
		r.addItemListener(this);
		e.addItemListener(this);
		n.addItemListener(this);
		d.addItemListener(this);
		atualizarSeletor();
		setSize(1200,800);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	private void atualizarSeletor()
	{
		for (JRadioButton b : new JRadioButton[]{d, n, e, r})
		{
			boolean ativo = b.isSelected();
			b.setBackground(ativo ? Tema.PRIMARIA : Tema.SUPERFICIE);
			b.setForeground(ativo ? Tema.TEXTO_INVERSO : Tema.TEXTO);
			b.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(ativo ? Tema.PRIMARIA : Tema.BORDA),
				BorderFactory.createEmptyBorder(6, 14, 6, 18)));
		}
	}
	//Para Eventos
	public void itemStateChanged(ItemEvent a)
	{
		atualizarSeletor();
		if(r.isSelected())
		{
			card.show(panelGeral, "revendedor");
		}
		if(d.isSelected())
		{
			card.show(panelGeral, "doutor");
		}
		if(e.isSelected())
		{
			card.show(panelGeral, "estado");
		}
		if(n.isSelected())
		{
			card.show(panelGeral, "normal");
		}
	}
	public String[][] tabelaDoutorado(ArrayList<Cliente> lista, int linhas)
	{
		String[][] array = new String[Doutorado.ctDoutorado][titulosDoutorado.length];
		int cont=0;
		Cliente cliente;
		Doutorado doutor;
		for(int i=0; i<linhas;i++)
		{
//			"Nome do cliente", "Numero do cliente","Data de compra","Estado de compra","Marca da viatura","Modelo da viatura","Codigo da viatura",
//			"Cilindragem","Preco da viatura","Tpo da viatura","Data de conclusao do doutoramento"
			cliente = (Cliente) lista.get(i);
			if(cliente instanceof Doutorado)
			{
				doutor = (Doutorado) cliente;
				array[cont][0] = doutor.getNomeCliente();
				array[cont][1] = doutor.getNumeroTelefone();
				array[cont][2] = doutor.getDataCompra();
				array[cont][3] = doutor.getEstadoCompra();
				array[cont][4] = doutor.getMarcaViatura();
				array[cont][5] = doutor.getModeloViatura();
				array[cont][6] = String.valueOf(doutor.getCodeViatura());
				array[cont][7] = String.valueOf(doutor.getCilindragem());
				array[cont][8] = String.valueOf(doutor.getPrecoViatura());
				array[cont][9] = doutor.getTipoViatura();
				array[cont][10] = doutor.getDataCDoutoramento();
				cont++;
			}
		}
		return array;
	}
	
	//Tabela de Normal
	public String[][] tabelaNormal(ArrayList<Cliente> lista, int linhas)
	{
		String[][] array = new String[Normal.ctNormal][titulosNormal.length];
		int cont=0;
		Cliente cliente;
		Normal normal;
		for(int i=0; i<linhas;i++)
		{
//			{"Nome do cliente", "Numero do cliente","Data de compra","Estado de compra","Marca da viatura"
//			,"Modelo da viatura","Codigo da viatura","Cilindragem","Preco da viatura","Tipo da vitura",
//			"Esteve no estrangeiro", "Numero de anos fora"};
			cliente = (Cliente) lista.get(i);
			if(cliente instanceof Normal)
			{
				normal = (Normal) cliente;
				array[cont][0] = normal.getNomeCliente();
				array[cont][1] = normal.getNumeroTelefone();
				array[cont][2] = normal.getDataCompra();
				array[cont][3] = normal.getEstadoCompra();
				array[cont][4] = normal.getMarcaViatura();
				array[cont][5] = normal.getModeloViatura();
				array[cont][6] = String.valueOf(normal.getCodeViatura());
				array[cont][7] = String.valueOf(normal.getCilindragem());
				array[cont][8] = String.valueOf(normal.getPrecoViatura());
				array[cont][9] = normal.getTipoViatura();
				array[cont][10] = normal.getEstrangeiro();
				array[cont][11] = String.valueOf(normal.getNumeroAnosForaPais());
				cont++;
			}
		}
		return array;
	}
	
	public String[][] tabelaRevendedor(ArrayList<Cliente> lista, int linhas)
	{
		String[][] array = new String[Revendedor.ctRevendor][titulosRevendedor.length];
		int cont=0;
		Cliente cliente;
		Revendedor rev;
		for(int i=0; i<linhas;i++)
		{
//			{"Nome do cliente", "Numero do cliente","Data de compra","Estado de compra","Marca da viatura"
//			,"Modelo da viatura","Codigo da viatura","Cilindragem","Preco da viatura","Quantidade de viaturas"
//			,"Nome Comercial"};
			cliente = (Cliente) lista.get(i);
			if(cliente instanceof Revendedor)
			{
				rev = (Revendedor) cliente;
				array[cont][0] = rev.getNomeCliente();
				array[cont][1] = rev.getNumeroTelefone();
				array[cont][2] = rev.getDataCompra();
				array[cont][3] = rev.getEstadoCompra();
				array[cont][4] = rev.getMarcaViatura();
				array[cont][5] = rev.getModeloViatura();
				array[cont][6] = String.valueOf(rev.getCodeViatura());
				array[cont][7] = String.valueOf(rev.getCilindragem());
				array[cont][8] = String.valueOf(rev.getPrecoViatura());
				array[cont][9] = String.valueOf(rev.getQtyViaturas());
				array[cont][10] = rev.getNomeComercial();
				cont++;
			}
		}
		return array;
	}
	
	public String[][] tabelaEstado(ArrayList<Cliente> lista, int linhas)
	{
		String[][] array = new String[Estado.ctEstado][titulosEstado.length];
		int cont=0;
		Cliente cliente;
		Estado estado;
		for(int i=0; i<linhas;i++)
		{
//			{"Nome do cliente", "Numero do cliente","Data de compra","Estado de compra","Marca da viatura"
//			,"Modelo da viatura","Codigo da viatura","Cilindragem","Preco da viatura","Quantidade de viaturas"
//			,"Nome da instituicao governamental","Deseja incluir a manutencao"};
			cliente = (Cliente) lista.get(i);
			if(cliente instanceof Estado)
			{
				estado = (Estado) cliente;
				array[cont][0] = estado.getNomeCliente();
				array[cont][1] = estado.getNumeroTelefone();
				array[cont][2] = estado.getDataCompra();
				array[cont][3] = estado.getEstadoCompra();
				array[cont][4] = estado.getMarcaViatura();
				array[cont][5] = estado.getModeloViatura();
				array[cont][6] = String.valueOf(estado.getCodeViatura());
				array[cont][7] = String.valueOf(estado.getCilindragem());
				array[cont][8] = String.valueOf(estado.getPrecoViatura());
				array[cont][9] = String.valueOf(estado.getQtyViaturas());
				array[cont][10] = estado.getNomeIGovernal();
				array[cont][11] = estado.getIncluirManutencao();
				cont++;
			}
		}
		return array;
	}
	public void bubbleSorte(ArrayList<Cliente> lista)
	{
		Cliente cliente1, cliente2;
		for(int j=0; j<lista.size()-1; j++)
		{
			for(int i=0; i<lista.size()-1-j; i++)
			{
				cliente1 = (Cliente)lista.get(i);
				cliente2 = (Cliente)lista.get(i+1);
				if(cliente1.getCilindragem() > cliente2.getCilindragem())
					troca(lista, i, i+1);
			}
		}
	}
	public void troca(ArrayList<Cliente> lista, int menor, int maior)
	{
		Cliente aux = (Cliente) lista.get(menor);
		lista.set(menor, lista.get(maior));
		lista.set(maior, aux);
	}
}
