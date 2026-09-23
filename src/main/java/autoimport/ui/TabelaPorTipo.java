package autoimport.ui;
import autoimport.util.*;
import autoimport.model.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
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
	private JLabel labelGeral;
	private JPanel panelRadio, panelD, panelN, panelR, panelE, panelGeral;
	private ButtonGroup buttonGroup;
	private JRadioButton d,e,n,r;
	private CardLayout card;
	
	public TabelaPorTipo(ArrayList<Cliente> lista)
	{
		super("Todos os dados");
		
		ImageIcon icon = new ImageIcon(ResourcePaths.imagem("DataImagesTitle1.jpg"));
		JPanel panel = new JPanel() 
		{
            protected void paintComponent(Graphics g) 
            {
                super.paintComponent(g);
                g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        setContentPane(panel);
        card = new CardLayout();
		cont = getContentPane();
		cont.setLayout(new BorderLayout());
		panelGeral = new JPanel();  //O painel que vai conter todas as tabelas
		panelGeral.setLayout(card);
		cont.setBackground(Color.LIGHT_GRAY);
		//Inicializacao dos JPanels
        panelD = new JPanel();
        panelN = new JPanel();
        panelE = new JPanel();
        panelR = new JPanel();
        //Inicializacao dos RadioButtons
        panelRadio = new JPanel();
        buttonGroup = new ButtonGroup();
        
        n = new JRadioButton("Normal",false);
        e = new JRadioButton("Estado",false);
        d = new JRadioButton("Doutorado",true);
        r = new JRadioButton("Revendedor",false);
        
        buttonGroup.add(n);
        buttonGroup.add(e);
        buttonGroup.add(d);
        buttonGroup.add(r);
        
        panelRadio.add(d);
        panelRadio.add(n);
        panelRadio.add(e);
        panelRadio.add(r);

		
		panelD.setLayout(new BorderLayout());
//		cont.add(panelR,"titulo");
		arrayD = tabelaDoutorado(lista, lista.size());
		tabelaD = new JTable(arrayD, titulosDoutorado);
		scrollD = new JScrollPane(tabelaD);
		tabelaD.setEnabled(false);
		panelD.add(panelRadio, BorderLayout.NORTH);
		panelD.add(scrollD, BorderLayout.CENTER);
		panelGeral.add(panelD,"doutor");
		
//		System.out.println(titulosNormal.length);
		panelN.setLayout(new BorderLayout());
		arrayN = tabelaNormal(lista, lista.size());
		tabelaN = new JTable(arrayN,titulosNormal);
		scrollN = new JScrollPane(tabelaN);
		tabelaN.setEnabled(false);
		panelN.add(panelRadio, BorderLayout.NORTH);
		panelN.add(scrollN);
		panelGeral.add(panelN,"normal");
		
		panelR.setLayout(new BorderLayout());
		arrayR = tabelaRevendedor(lista, lista.size());
		tabelaR = new JTable(arrayR,titulosRevendedor);
		scrollR = new JScrollPane(tabelaR);
		tabelaR.setEnabled(false);
		panelR.add(panelRadio, BorderLayout.NORTH);
		panelR.add(scrollR, BorderLayout.CENTER);
		panelGeral.add(panelR,"revendedor");
		
		panelE.setLayout(new BorderLayout());
		arrayE = tabelaEstado(lista, lista.size());
		tabelaE = new JTable(arrayE,titulosEstado);
		scrollE = new JScrollPane(tabelaE);
		tabelaE.setEnabled(false);
		panelD.add(panelRadio, BorderLayout.NORTH);
		panelE.add(scrollE, BorderLayout.CENTER);
		panelGeral.add(panelE, "estado");

		
		cont.add(panelRadio, BorderLayout.NORTH);
		cont.add(panelGeral, BorderLayout.CENTER);
		
		
		//Eventos dos redioButtons
		r.addItemListener(this);
		e.addItemListener(this);
		n.addItemListener(this);
		d.addItemListener(this);
		setSize(1200,800);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	//Para Eventos
	public void itemStateChanged(ItemEvent a)
	{
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
