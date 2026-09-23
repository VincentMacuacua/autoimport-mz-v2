package autoimport.ui;
import autoimport.util.*;
import autoimport.model.*;
import autoimport.service.*;
import autoimport.persistence.*;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.swing.*;


public class Menu extends JFrame
{
	private JMenuBar menuBar;
	private JMenu hom, uti, fic, reg, rel, ext, aju, visualicao,tema;
	private JMenuItem ler, das, sair, log, userDados, abrir, gua, adi, pro, edi, rem, calQ, calT, calD, ver, verE;
	private JMenuItem tabT, tabC, exp, sobre, manual;
	private JMenuItem vid, mus;
	private JLabel welcome, beFor;
	private ImageIcon temaIcon,imagemAtual,manualIcon,vidIcon,altIcon,verIcon,verEIcon,adiIcon,proIcon,ediIcon,remIcon,tabTIcon,tabCIcon,musicIcon,pdfIcon,userIcon,logIcon,lerIcon,exitIcon,qtyIcon,acumIcon,dasIcon;
	private Container cont;
	private JPopupMenu popup;
	private JRadioButton tema1,tema2,tema3,tema4;
	private ButtonGroup grTema;
	private JPanel panel ;

	private ArrayList<Cliente> cliente;
	private Visualizar vis;
	private Ficheiro fi;
	private Calculos cal;
	private Pesquisa pes;
	private ExportarComprasPDF pdf;
	private Remover r;

	public Menu(ArrayList<Usuario> user,String passValidar)
	{
		super("Menu");
		menuBar = new JMenuBar();

		imagemAtual = new ImageIcon(ResourcePaths.imagem("rose_lilaz_escuro.jpeg"));
		panel = new JPanel()
		{
		    protected void paintComponent(Graphics g)
		    {
		        super.paintComponent(g);
	            g.drawImage(imagemAtual.getImage(), 0, 0, getWidth(), getHeight(), this);
		    }
		};
        setContentPane(panel);

		cont = getContentPane();
		cont.setLayout(new BorderLayout());

		// definir icon da janela
		ImageIcon programaIcon=new ImageIcon(ResourcePaths.imagem("programaIcon1.jpeg"));
		setIconImage(programaIcon.getImage());

		setJMenuBar(menuBar);
		cliente=new ArrayList<Cliente>();
		vis=new Visualizar();
		fi=new Ficheiro();
		cal=new Calculos();
		pes=new Pesquisa();
		pdf=new ExportarComprasPDF();
		r = new Remover();

		buildHomeMenu();
		buildUtilizadorMenu(user,passValidar);
		buildRegistoMenu();
		buildRelatorioMenu();
		buildAjudaMenu();
		buildExtraMenu();
		buildWelcomeLabels();
		buildTemaMenu();
		buildPopupMenu();

		panel.repaint();
		panel.validate();
		repaint();
		revalidate();
		setSize(1000, 700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		setVisible(true);
		cont.revalidate();
		cont.repaint();
	}

	private void buildHomeMenu()
	{
		ler =new JMenuItem("Ler Ficheiro de Texto");
		ler.setMnemonic('l');
		lerIcon=new ImageIcon(ResourcePaths.imagem("images.png"));
		ler.setIcon(lerIcon);
		ler.addActionListener
		(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent ev)
				{
					lerFicheiroTexto();
					visualicao.setEnabled(true);
					das.setEnabled(true);
					reg.setEnabled(true);
					rel.setEnabled(true);
					ler.setEnabled(false);
				}
			}
		);

		das = new JMenuItem("Dashboard"); //Uma janela que mostra graficos
		das.setEnabled(false);
		das.setMnemonic('D');
		dasIcon=new ImageIcon(ResourcePaths.imagem("dashIcon.png"));
		das.setIcon(dasIcon);
		das.addActionListener
		(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent ev)
				{
					float adoanerio,acmN,acmD,acmE,acmR;
					double valorTotal,margem;
					final int PREJUIZO=1900000;
					int totalCarrosVendidos;

					totalCarrosVendidos=cal.calcular_Quantidade(cliente);
					acmN=cal.acumulador_Normal(cliente);
					acmD=cal.acumulador_Doutorado(cliente);
					acmE=cal.acumulador_Estado(cliente);
					acmR=cal.acumulador_Revendedor(cliente);
					adoanerio=cal.acumuladorAdoaneiro(cliente);
					valorTotal=cal.acumuladorGeral(cliente);
					margem=cal.calculo_margem(valorTotal);

					new Dash(acmN,acmD,acmE,acmR,totalCarrosVendidos,adoanerio,valorTotal,margem, PREJUIZO);
				}
			}
		);

		visualicao = new JMenu("Visualizar Estatisticas");
		visualicao.setEnabled(false);
		visualicao.setMnemonic('V');
		//Insite JMenu
		calQ = new JMenuItem("Calcular Quantidade de tipo de cliente");
		qtyIcon=new ImageIcon(ResourcePaths.imagem("quantidade.png"));
		calQ.setIcon(qtyIcon);
		calQ.addActionListener
		(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent ev)
				{
					vis.visualizar_CT(cliente);
				}
			}
		);

		calT = new JMenuItem("Calcular valor Total Pago a Empresa");
		qtyIcon=new ImageIcon(ResourcePaths.imagem("quantidade.png"));
		calT.setMnemonic('c');
		calT.setIcon(qtyIcon);
		calT.addActionListener
		(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent ev)
				{
					double valorTotal;

					valorTotal=cal.acumuladorGeral(cliente);
					vis.visualizar_AcumuladorGeral(valorTotal);
				}
			}
		);

		calD = new JMenuItem("Calcular Direitos Aduaneiros");
		calD.setMnemonic('D');
		acumIcon=new ImageIcon(ResourcePaths.imagem("valortotal.jpg"));
		calD.setIcon(acumIcon);
		calD.addActionListener
		(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent ev)
				{
					float valorAdoaneiro;

					valorAdoaneiro=cal.acumuladorAdoaneiro(cliente);
					vis.visualizar_AcumuladorAdoaneiro(valorAdoaneiro);
				}
			}
		);

		visualicao.add(calQ);
		visualicao.add(calT);
		visualicao.add(calD);

		sair = new JMenuItem("Sair");
		sair.setMnemonic('S');
		exitIcon=new ImageIcon(ResourcePaths.imagem("exit.png"));
		sair.setIcon(exitIcon);
		sair.addActionListener
		(
			new ActionListener()
			{
				int opcao;
				public void actionPerformed(ActionEvent ev)
				{
					opcao = JOptionPane.showConfirmDialog(null, "Deseja realmente sair ?","Confirmacao para sair do programa",JOptionPane.YES_NO_OPTION);
					if (opcao == JOptionPane.YES_OPTION)
					{
						String texto =
				                "     |======================================|\n" +
				                "     |==============    ===     ============|\n" +
				                "     |=============      =       ===========|\n" +
				                "     |=============              ===========|\n" +
				                "     |==============           =============|\n" +
				                "     |================       ===============|\n" +
				                "     |======   =========   ========   ======|\n" +
				                "     |======= =========== ========== =======|\n" +
				                "     |======================================|\n" +
				                "     |*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-|\n" +
				                "     | Obrigado por usar o nosso programa ! |\n" +
				                "     |*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-|\n" +
				                "     |======================================|\n" +
				                "     |==============    ===     ============|\n" +
				                "     |=============      =       ===========|\n" +
				                "     |=============              ===========|\n" +
				                "     |==============           =============|\n" +
				                "     |================       ===============|\n" +
				                "     |======   =========   ========   ======|\n" +
				                "     |======= =========== ========== =======|\n" +
				                "     |======================================|";

				        JTextArea area = new JTextArea(texto);
				        area.setEditable(false);
				        area.setFont(new Font("Monospaced",Font.PLAIN, 12));
				        area.setForeground(new Color(180, 100, 255));
				        area.setBackground(Color.DARK_GRAY);

				        JOptionPane.showMessageDialog(null,area,"Ate breve!",JOptionPane.PLAIN_MESSAGE);
						System.exit(0);
					}
				}
			}
		);

		//Insite JMenu Home
		hom = new JMenu("Home");
		hom.add(ler);
		hom.add(das);
		hom.add(visualicao);
		hom.add(sair);
		hom.setMnemonic('H');
		menuBar.add(hom);
	}

	private void buildUtilizadorMenu(ArrayList<Usuario> user, String passValidar)
	{
		//Second JMenu
		log = new JMenuItem("Logout");
		logIcon=new ImageIcon(ResourcePaths.imagem("logout.jpg"));
		log.setIcon(logIcon);
		log.addActionListener
		(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent ev)
				{
					new Login();
					dispose();
				}
			}
		);
		userDados = new JMenuItem("Dados do utilizador");
		userIcon=new ImageIcon(ResourcePaths.imagem("user.jpg"));
		userDados.setIcon(userIcon);
		userDados.addActionListener
		(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent ev)
				{
					int poz=pes.posicaoPass(user,passValidar);
					if(poz==-1)
						JOptionPane.showMessageDialog(null,"O usuario nao exite");
					else
					{
						vis.visualizarUser(user,poz);
					}
				}
			}
		);
		uti = new JMenu("Utilizador");
		uti.setMnemonic('U');
		uti.add(log);
		uti.add(userDados);
		menuBar.add(uti);
	}

	private void buildRegistoMenu()
	{
		//Third JMenu
		adi = new JMenuItem("Adicionar Cliente");
		adiIcon=new ImageIcon(ResourcePaths.imagem("add.png"));
		adi.setIcon(adiIcon);
		adi.setMnemonic('A');
		adi.addActionListener
		(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent ev)
				{
					new NovoClinete(cliente);
				}
			}
		);
		pro = new JMenuItem("Procurar Cliente");
		proIcon=new ImageIcon(ResourcePaths.imagem("pesquisa.png"));
		pro.setIcon(proIcon);
		pro.addActionListener
		(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent ev)
				{
					new PesquisaGUI(cliente);
				}
			}
		);
		edi = new JMenuItem("Editar estado da compra");
		ediIcon=new ImageIcon(ResourcePaths.imagem("editar.png"));
		edi.setIcon(ediIcon);
		edi.addActionListener
		(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent ev)
				{
					new Editar_Cliente(cliente);
				}
			}
		);
		rem = new JMenuItem("Remover Cliente");
		remIcon=new ImageIcon(ResourcePaths.imagem("remover.png"));
		rem.setIcon(remIcon);
		rem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ev)
			{
				new RemoverGUI(cliente);
			}
		});
		reg = new JMenu("Registar");
		reg.setMnemonic('R');
		reg.add(adi);
		reg.add(pro);
		reg.add(edi);
		reg.add(rem);
		reg.setEnabled(false);

		menuBar.add(reg);
	}

	private void buildRelatorioMenu()
	{
		tabT = new JMenuItem("Tabela de todos os clientes");
		tabTIcon=new ImageIcon(ResourcePaths.imagem("tabelaT.png"));
		tabT.setIcon(tabTIcon);
		tabT.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent a)
					{
						new TabelaTodos(cliente);
					}
				}
			);
		tabC = new JMenuItem("Tabela de clientes por tipo");
		tabCIcon=new ImageIcon(ResourcePaths.imagem("tabelaC.png"));
		tabC.setMnemonic('t');
		tabC.setIcon(tabCIcon);
		tabC.addActionListener
		(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent a)
				{
					JOptionPane.showMessageDialog(null, "Selecione o tipo de cliente que desejas ver os seus dados");
					new TabelaPorTipo(cliente);
				}
			}
		);
		exp = new JMenuItem("Exporar Compras(PDF)");
		pdfIcon=new ImageIcon(ResourcePaths.imagem("pdf.jpg"));
		exp.setMnemonic('x');
		exp.setIcon(pdfIcon);
		exp.addActionListener
		(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent ev)
				{
					String nomePDF="data/Balaco.pdf";
					pdf.exportarParaPDF(cliente,nomePDF);
				}
			}

		);

		rel = new JMenu("Relatorio");
		rel.add(tabT);
		rel.add(tabC);
		rel.add(exp);
		rel.setEnabled(false);
		menuBar.add(rel);
	}

	private void buildAjudaMenu()
	{
		manual = new JMenuItem("Manual de Utilizador");
		manualIcon=new ImageIcon(ResourcePaths.imagem("Manual.png"));
		manual.setIcon(manualIcon);
		manual.addActionListener
		(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent ev)
				{
					new Ajuda();
				}
			}
		);
		aju = new JMenu("Ajuda");
		aju.setMnemonic('A');
		aju.add(manual);
		menuBar.add(aju);
	}

	private void buildExtraMenu()
	{
		ext = new JMenu("Extra");
		vid = new JMenuItem("Reproduzir Video");
		vidIcon=new ImageIcon(ResourcePaths.imagem("videoIcon.png"));
		vid.setIcon(vidIcon);
		vid.addActionListener
		(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent ev)
				{
					new SeletorVideoMov();
				}
			}
		);
		mus = new JMenuItem("Reproduzir Musica");
		musicIcon=new ImageIcon(ResourcePaths.imagem("musicIcon.png"));
		mus.setIcon(musicIcon);
		mus.addActionListener
		(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent ev)
				{
					new BONUX_Musica();
				}
			}
		);
		ext.add(vid);
		ext.add(mus);
		//menuBar.add(ext);
	}

	private void buildWelcomeLabels()
	{
		welcome = new JLabel("Welcome To",JLabel.CENTER);
		welcome.setFont(new Font("Serif", Font.BOLD, 70));
		welcome.setForeground(new Color(255, 255, 102));
		cont.add(welcome, BorderLayout.NORTH);

		beFor = new JLabel("AutoImport MZ",JLabel.CENTER);
		beFor.setFont(new Font("Serif", Font.BOLD, 70));
		beFor.setForeground(new Color(255, 255, 102));
		cont.add(beFor, BorderLayout.SOUTH);
	}

	private void buildTemaMenu()
	{
		// Criar radio buttons para right click
        tema1 = new JRadioButton("Tema Lilas escuro", true);
        tema2 = new JRadioButton("Tema Vermelho Ford");
        tema3 = new JRadioButton("Tema Azul SkyLine");
        tema4 = new JRadioButton("Tema Lilaz Labo");

        grTema = new ButtonGroup();
        grTema.add(tema1);
        grTema.add(tema2);
        grTema.add(tema3);
        grTema.add(tema4);

        //listener de click
        tema4.addItemListener
        (
        	new ItemListener()
        	{
	            public void itemStateChanged(ItemEvent e)
	            {
	                mudarTema(ResourcePaths.imagem("premium_photo-1687153733088-9fc19cbc59bf.jpeg"));
	            }
        	}
        );

        tema1.addItemListener
        (
        	new ItemListener()
        	{
	            public void itemStateChanged(ItemEvent e)
	            {
	                mudarTema(ResourcePaths.imagem("rose_lilaz_escuro.jpeg"));
	            }
        	}
        );

        tema2.addItemListener
        (
        	new ItemListener()
        	{
	            public void itemStateChanged(ItemEvent e)
	            {
	                mudarTema(ResourcePaths.imagem("car-pc-desktop-4k.jpg"));
	            }
        	}
        );

        tema3.addItemListener
        (
        	new ItemListener()
        	{
        		public void itemStateChanged(ItemEvent e)
	            {
	                mudarTema(ResourcePaths.imagem("racing-car-night-speed-desktop-wallpaper-preview.jpg"));
	            }
        	}
        );

		tema=new JMenu("Tema");
		tema.add(tema1);
		tema.add(tema2);
		tema.add(tema3);
		tema.add(tema4);
		temaIcon=new ImageIcon(ResourcePaths.imagem("temaIcon.png"));
		tema.setIcon(temaIcon);
	}

	private void buildPopupMenu()
	{
		cont.addMouseListener
		(
			new MouseAdapter()
			{
				public void mouseReleased(MouseEvent e)
				{
					processMouseEvent(e);
				}
				public void processMouseEvent(MouseEvent e)
				{
					if(e.isPopupTrigger())
						popup.show(e.getComponent(),e.getX(),e.getY());
				}
			}
		);
		cont.addMouseListener
		(
		    new MouseAdapter()
		    {
		        public void mousePressed(MouseEvent e)
		        {
		            showPopup(e);
		        }

		        public void mouseReleased(MouseEvent e)
		        {
		            showPopup(e);
		        }

		        private void showPopup(MouseEvent e)
		        {
		            if (e.isPopupTrigger())
		            {
		                popup.show(e.getComponent(), e.getX(), e.getY());
		            }
		        }
		    }
		);

		popup = new JPopupMenu();

		popup.add(sair);
		popup.addSeparator();
		popup.add(vid);
		popup.addSeparator();
		popup.add(mus);
		popup.addSeparator();
        // Transparente para não cobrir imagem
        popup.setOpaque(false);
        popup.add(tema);
	}

	private void mudarTema(java.net.URL caminhoImagem)
	{
        imagemAtual = new ImageIcon(caminhoImagem);
        panel.repaint(); // Atualiza o fundo
    }
	public void lerFicheiroTexto()
	{
		StringTokenizer st;
		String linha,numeroTelefone,nomeCliente,dataCompra,estadoCompra,marcaViatura,modeloViatura,dataCDoutoramento,nomeIGovernal,incluirManutencao,nomeComercial,tipoViatura,estrangeiro;
		int qtyViaturas,numeroAnosForaPais,codeViatura,cilindragem,precoViatura;
		char c;

		try
		{
			FileReader fr=new FileReader("data/Dados.txt");
			BufferedReader br=new BufferedReader(fr);

			linha=br.readLine();
			while(linha!=null)
			{
				st=new StringTokenizer(linha,";");

				numeroTelefone=st.nextToken();
				nomeCliente=st.nextToken();
				dataCompra=st.nextToken();
				estadoCompra=st.nextToken();
				marcaViatura=st.nextToken();
				modeloViatura=st.nextToken();
				codeViatura=Integer.parseInt(st.nextToken());
				cilindragem=Integer.parseInt(st.nextToken());
				precoViatura=Integer.parseInt(st.nextToken());

				c=(st.nextToken()).charAt(0);
				switch(c)
				{
					case 'p': case 'P':
						tipoViatura=st.nextToken();
						c=(st.nextToken()).charAt(0);
						switch(c)
						{
							case 'd': case 'D':
								dataCDoutoramento=st.nextToken();
								doutorado(numeroTelefone,nomeCliente,dataCompra,estadoCompra,marcaViatura,modeloViatura,codeViatura,cilindragem,precoViatura,tipoViatura,dataCDoutoramento);
								break;
							case 'n': case 'N':
								estrangeiro=st.nextToken();
								numeroAnosForaPais=Integer.parseInt(st.nextToken());
								normal(numeroTelefone,nomeCliente,dataCompra,estadoCompra,marcaViatura,modeloViatura,codeViatura,cilindragem,precoViatura,tipoViatura,estrangeiro,numeroAnosForaPais);
								break;
						}
						break;
					case 'e': case 'E':
						qtyViaturas=Integer.parseInt(st.nextToken());
						c=(st.nextToken()).charAt(0);
						switch(c)
						{
							case 'r': case 'R':
								nomeComercial=st.nextToken();
								revendedor(numeroTelefone,nomeCliente,dataCompra,estadoCompra,marcaViatura,modeloViatura,codeViatura,cilindragem,precoViatura,qtyViaturas,nomeComercial);
								break;
							case 'e': case 'E':
								nomeIGovernal=st.nextToken();
								incluirManutencao=st.nextToken();
								estado(numeroTelefone,nomeCliente,dataCompra,estadoCompra,marcaViatura,modeloViatura,codeViatura,cilindragem,precoViatura,qtyViaturas,nomeIGovernal,incluirManutencao);
								break;
						}
						break;
				}
				linha=br.readLine();
			}
			cliente.trimToSize();
			br.close();

		}
		catch(FileNotFoundException fn)
		{
			JOptionPane.showMessageDialog(null,"|======================|\n"+"| Nao contra o ficheiro de Dados.txt |\n"+"|======================|\n","FILE NOT FOUND",JOptionPane.ERROR_MESSAGE);
		}
		catch(NumberFormatException nf)
		{
			JOptionPane.showMessageDialog(null,nf.getMessage());
		}
		catch(IOException io)
		{
			JOptionPane.showMessageDialog(null,io.getMessage());
		}
		JOptionPane.showMessageDialog(null, "|==========================|\n"+"| Ficheiro de Dados.txt lido com Sucesso! |\n"+"|==========================|\n", "Info", JOptionPane.INFORMATION_MESSAGE);

    }
	public void adaptRemoverCli()
	{
		r.remover(cliente);
	}
	public void revendedor(String numeroTelefone,String nomeCliente,String dataCompra,String estadoCompra,String marcaViatura,String modeloViatura,int codeViatura,int cilindragem,int precoViatura,int qtyViaturas,String nomeComercial)
	{
		Revendedor r=new Revendedor();

		r.setNumeroTelefone(numeroTelefone);
		r.setNomeCliente(nomeCliente);
		r.setDataCompra(dataCompra);
		r.setEstadoCompra(estadoCompra);
		r.setMarcaViatura(marcaViatura);
		r.setModeloViatura(modeloViatura);
		r.setCodeViatura(codeViatura);
		r.setCilindragem(cilindragem);
		r.setPrecoViatura(precoViatura);
		r.setQtyViaturas(qtyViaturas);
		r.setNomeComercial(nomeComercial);

		cliente.add(r);
	}
	public void estado(String numeroTelefone,String nomeCliente,String dataCompra,String estadoCompra,String marcaViatura,String modeloViatura,int codeViatura,int cilindragem,int precoViatura,int qtyViaturas,String nomeIGovernal,String incluirManutencao)
	{
		Estado e=new Estado();

		e.setNumeroTelefone(numeroTelefone);
		e.setNomeCliente(nomeCliente);
		e.setDataCompra(dataCompra);
		e.setEstadoCompra(estadoCompra);
		e.setMarcaViatura(marcaViatura);
		e.setModeloViatura(modeloViatura);
		e.setCodeViatura(codeViatura);
		e.setCilindragem(cilindragem);
		e.setPrecoViatura(precoViatura);
		e.setQtyViaturas(qtyViaturas);
		e.setNomeIGovernal(nomeIGovernal);
		e.setIncluirManutencao(incluirManutencao);

		cliente.add(e);
	}
	public void normal(String numeroTelefone,String nomeCliente,String dataCompra,String estadoCompra,String marcaViatura,String modeloViatura,int codeViatura,int cilindragem,int precoViatura,String tipoViatura,String estrangeiro,int numeroAnosForaPais)
	{
		Normal n=new Normal();

		n.setNumeroTelefone(numeroTelefone);
		n.setNomeCliente(nomeCliente);
		n.setDataCompra(dataCompra);
		n.setEstadoCompra(estadoCompra);
		n.setMarcaViatura(marcaViatura);
		n.setModeloViatura(modeloViatura);
		n.setCodeViatura(codeViatura);
		n.setCilindragem(cilindragem);
		n.setPrecoViatura(precoViatura);
		n.setTipoViatura(tipoViatura);
		n.setEstrangeiro(estrangeiro);
		n.setNumeroAnosForaPais(numeroAnosForaPais);

		cliente.add(n);
	}
	public void doutorado(String numeroTelefone,String nomeCliente,String dataCompra,String estadoCompra,String marcaViatura,String modeloViatura,int codeViatura,int cilindragem,int precoViatura,String tipoViatura,String dataCDoutoramento)
	{
		Doutorado d=new Doutorado();

		d.setNumeroTelefone(numeroTelefone);
		d.setNomeCliente(nomeCliente);
		d.setDataCompra(dataCompra);
		d.setEstadoCompra(estadoCompra);
		d.setMarcaViatura(marcaViatura);
		d.setModeloViatura(modeloViatura);
		d.setCodeViatura(codeViatura);
		d.setCilindragem(cilindragem);
		d.setPrecoViatura(precoViatura);
		d.setTipoViatura(tipoViatura);
		d.setDataCDoutoramento(dataCDoutoramento);

		cliente.add(d);
	}
}
