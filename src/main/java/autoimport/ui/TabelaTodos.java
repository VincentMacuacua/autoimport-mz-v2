package autoimport.ui;
import autoimport.model.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class TabelaTodos extends JFrame
{
	private Container cont;
	private String[] titulosDoutorado = {"Nome do cliente", "Numero do cliente","Data de compra","Estado de compra","Marca da viatura","Modelo da viatura","Codigo da viatura","Cilindragem","Preco da viatura","Tipo da viatura","Data de conclusao do doutoramento"};
	private String[] titulosNormal = {"Nome do cliente", "Numero do cliente","Data de compra","Estado de compra","Marca da viatura","Modelo da viatura","Codigo da viatura","Cilindragem","Preco da viatura","Tipo da vitura","Esteve no estrangeiro", "Numero de anos fora"};
	private String[] titulosRevendedor = {"Nome do cliente", "Numero do cliente","Data de compra","Estado de compra","Marca da viatura","Modelo da viatura","Codigo da viatura","Cilindragem","Preco da viatura","Quantidade de viaturas","Nome Comercial"};
	private String[] titulosEstado = {"Nome do cliente", "Numero do cliente","Data de compra","Estado de compra","Marca da viatura","Modelo da viatura","Codigo da viatura","Cilindragem","Preco da viatura","Quantidade de viaturas","Nome da instituicao governamental","Deseja incluir a manutencao"};
	private JTable tabelaD, tabelaN, tabelaR, tabelaE;
	private String[][] arrayD, arrayN, arrayR, arrayE;
	private JScrollPane scrollD, scrollN, scrollR, scrollE;
	private JPanel panelTitulo;
	//Adicionando popup
	private JPopupMenu popup;
	private JMenuItem ordenar, sair;


	public TabelaTodos(ArrayList<Cliente> lista)
	{
		super("Todos os clientes - AutoImport MZ");

		cont = getContentPane();
		cont.setLayout(new BorderLayout());
		cont.setBackground(Tema.FUNDO);

		panelTitulo = Tema.cabecalho("Dados de todos os clientes", "Clique com o botao direito numa tabela para ordenar por cilindragem");
		cont.add(panelTitulo, BorderLayout.NORTH);

		JPanel corpo = new JPanel(new GridLayout(4,1,0,12));
		corpo.setBackground(Tema.FUNDO);
		corpo.setBorder(BorderFactory.createEmptyBorder(16,20,20,20));
		cont.add(corpo, BorderLayout.CENTER);

		arrayD = tabelaDoutorado(lista, lista.size());
		tabelaD = Tema.tabela(arrayD, titulosDoutorado);
		scrollD = Tema.rolagem(tabelaD);
		corpo.add(seccaoTabela("Particular · Doutorado", scrollD));

		arrayN = tabelaNormal(lista, lista.size());
		tabelaN = Tema.tabela(arrayN,titulosNormal);
		scrollN = Tema.rolagem(tabelaN);
		corpo.add(seccaoTabela("Particular · Normal", scrollN));

		arrayR = tabelaRevendedor(lista, lista.size());
		tabelaR = Tema.tabela(arrayR,titulosRevendedor);
		scrollR = Tema.rolagem(tabelaR);
		corpo.add(seccaoTabela("Empresarial · Revendedor", scrollR));

		arrayE = tabelaEstado(lista, lista.size());
		tabelaE = Tema.tabela(arrayE,titulosEstado);
		scrollE = Tema.rolagem(tabelaE);
		corpo.add(seccaoTabela("Empresarial · Estado", scrollE));

		popup = new JPopupMenu();
		//Os JMenuItems
		ordenar = new JMenuItem("Ordenar por cilindragem");
		ordenar.setToolTipText("Ordenar os dados dos clientes pela cilindragem");
		sair = new JMenuItem("Voltar");
		sair.setToolTipText("Voltar Para Menu");

		popup.add(ordenar);
		popup.addSeparator();
		popup.add(sair);

		// O menu de contexto fica disponivel em toda a janela, incluindo dentro das tabelas
		for (JComponent c : new JComponent[]{(JComponent) cont, corpo, tabelaD, tabelaN, tabelaR, tabelaE, scrollD, scrollN, scrollR, scrollE})
			c.setComponentPopupMenu(popup);

		ordenar.addActionListener
		(
		    new ActionListener()
		    {
		        public void actionPerformed(ActionEvent a)
		        {
		            bubbleSorte(lista);
		            arrayD = tabelaDoutorado(lista, lista.size());
		            tabelaD.setModel(Tema.modeloLeitura(arrayD, titulosDoutorado));
		            arrayN = tabelaNormal(lista, lista.size());
		            tabelaN.setModel(Tema.modeloLeitura(arrayN, titulosNormal));
		            arrayR = tabelaRevendedor(lista, lista.size());
		            tabelaR.setModel(Tema.modeloLeitura(arrayR, titulosRevendedor));
		            arrayE = tabelaEstado(lista, lista.size());
		            tabelaE.setModel(Tema.modeloLeitura(arrayE, titulosEstado));
		        }
		    }
		);
		sair.addActionListener
		(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent a)
				{
					dispose();
				}
			}
		);

		setSize(1200,800);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	private JPanel seccaoTabela(String titulo, JScrollPane scroll)
	{
		JPanel p = new JPanel(new BorderLayout(0,6));
		p.setOpaque(false);
		p.add(Tema.rotulo(titulo), BorderLayout.NORTH);
		p.add(scroll, BorderLayout.CENTER);
		return p;
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
