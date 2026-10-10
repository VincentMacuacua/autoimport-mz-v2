package autoimport.ui;
import autoimport.util.*;
import autoimport.model.*;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

public class Dash extends JFrame 
{
	private DecimalFormat m;
	private JTabbedPane tabs;
	private JLabel labelTitulo,labelQuantidade,titulo;
    private Container cont;
    private JPanel panelEst, panelTitulo;
    private JScrollPane src1,src2,src3,src4;
    private JTable tab1,tab2,tab3,tab4;
    private String [][] dados1,dados2,dados3,dados4;

    public Dash(float acmN,float acmD,float acmE,float acmR,int totalCarrosVendidos,float adoanerio,double valorTotal,double margem, final int PREJUIZO) 
    {
    	super("Dashboard - AutoImport MZ");
		cont=getContentPane();
		cont.setLayout(new BorderLayout());
		cont.setBackground(Tema.FUNDO);
    	
    	m=new DecimalFormat("###,###,##0.00 Mts");
    	tabs=new JTabbedPane();
    	tabs.setFont(Tema.fonte(Font.BOLD, 14f));
    	
    	// ---------- Pagina 1: Visao geral ----------
    	JPanel pane1=new JPanel(new GridLayout(1,4,16,16));
    	pane1.setOpaque(false);
    	pane1.add(Tema.cartaoIndicador("Clientes Doutorado", Doutorado.ctDoutorado+"", Tema.TEXTO));
    	pane1.add(Tema.cartaoIndicador("Clientes Normal", Normal.ctNormal+"", Tema.TEXTO));
    	pane1.add(Tema.cartaoIndicador("Clientes Revendedor", Revendedor.ctRevendor+"", Tema.TEXTO));
    	pane1.add(Tema.cartaoIndicador("Clientes Estado", Estado.ctEstado+"", Tema.TEXTO));
    	
    	JPanel pane2=new JPanel(new GridLayout(1,2,16,16));
    	pane2.setOpaque(false);
    	pane2.add(Tema.cartaoIndicador("Total faturado", m.format(valorTotal), Tema.PRIMARIA));
    	pane2.add(Tema.cartaoIndicador("Total em direitos aduaneiros", m.format(adoanerio), Tema.PRIMARIA));
    	
    	JPanel pane3=new JPanel(new GridLayout(1,2,16,16));
    	pane3.setOpaque(false);
    	if(margem<PREJUIZO)
    		pane3.add(Tema.cartaoIndicador("Situacao: empresa em prejuizo", m.format((-1*margem)), Tema.PERIGO));
    	else
    		pane3.add(Tema.cartaoIndicador("Situacao: empresa em lucro", m.format(margem), Tema.SUCESSO));
    	
    	JPanel painelGrafico = gerarGraficoBarras(valorTotal, PREJUIZO);
    	pane3.add(painelGrafico);
    	
    	JPanel pagina1=new JPanel();
    	pagina1.setLayout(new BoxLayout(pagina1, BoxLayout.Y_AXIS));
    	pagina1.setBackground(Tema.FUNDO);
    	pagina1.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
    	pagina1.add(pane1);
    	pagina1.add(Box.createVerticalStrut(16));
    	pagina1.add(pane2);
    	pagina1.add(Box.createVerticalStrut(16));
    	pagina1.add(pane3);
    	
    	tabs.add("Visao geral",pagina1);
    	
    	// ---------- Pagina 2: Viaturas vendidas ----------
		JPanel pagina2 = new JPanel(new GridBagLayout());
		pagina2.setBackground(Tema.FUNDO);

		JPanel cartaoVendas = Tema.cartao(new GridLayout(2,1,0,8));
		cartaoVendas.setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createLineBorder(Tema.BORDA),
			BorderFactory.createEmptyBorder(32, 64, 32, 64)));
        labelTitulo = new JLabel("Total de viaturas vendidas", JLabel.CENTER);
        labelTitulo.setFont(Tema.fonte(Font.BOLD, 16f));
        labelTitulo.setForeground(Tema.TEXTO_SUAVE);
        cartaoVendas.add(labelTitulo);

        labelQuantidade = new JLabel(totalCarrosVendidos+"", JLabel.CENTER);
        labelQuantidade.setFont(Tema.fonte(Font.BOLD, 64f));
        labelQuantidade.setForeground(Tema.MARINHO);
        cartaoVendas.add(labelQuantidade);
        pagina2.add(cartaoVendas);
    	
        tabs.add("Viaturas vendidas",pagina2);
    	
    	// ---------- Pagina 3: Receita por tipo de cliente ----------
        JPanel pagina3 = new JPanel(new BorderLayout(0,16));
        pagina3.setBackground(Tema.FUNDO);
        pagina3.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        titulo = new JLabel("Receita por tipo de cliente");
        titulo.setForeground(Tema.TEXTO);
        titulo.setFont(Tema.fonte(Font.BOLD, 18f));

        panelTitulo = new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
        panelTitulo.setOpaque(false);
        panelTitulo.add(titulo);
        pagina3.add(panelTitulo,BorderLayout.NORTH);
        
        panelEst=new JPanel(new GridLayout(2,2,16,16));
        panelEst.setOpaque(false);
        panelEst.add(Tema.cartaoIndicador("Normal", m.format(acmN), Tema.TEXTO));
        panelEst.add(Tema.cartaoIndicador("Doutorado", m.format(acmD), Tema.TEXTO));
        panelEst.add(Tema.cartaoIndicador("Revendedor", m.format(acmR), Tema.TEXTO));
        panelEst.add(Tema.cartaoIndicador("Estado", m.format(acmE), Tema.TEXTO));
        pagina3.add(panelEst, BorderLayout.CENTER);
        
        tabs.add("Receita por tipo",pagina3);
    	
        cont.add(Tema.cabecalho("Dashboard", "Indicadores de vendas e situacao financeira da empresa"), BorderLayout.NORTH);
    	cont.add(tabs, BorderLayout.CENTER);
		
		setSize(1100,720);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
    }
    public JPanel gerarGraficoBarras(double margem,final float PREJUIZO) 
    {
        JPanel p = new JPanel() 
        {
            protected void paintComponent(Graphics g) 
            {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

                Insets in = getInsets();
                int topo = in.top + 28;
                int base = getHeight() - in.bottom - 24;
                int alturaMax = Math.max(20, base - topo - 20);
                double maior = Math.max(Math.max(margem, PREJUIZO), 1);
                int altura1 = (int) ((margem / maior) * alturaMax);
                int altura2 = (int) ((PREJUIZO / maior) * alturaMax);
                int largura = 56;
                int x1 = in.left + 40, x2 = x1 + largura + 48;

                g2.setFont(Tema.fonte(Font.BOLD, 11f));
                g2.setColor(Tema.TEXTO_SUAVE);
                g2.drawString("TOTAL FATURADO VS. LIMITE DE PREJUIZO", in.left, in.top + 12);

                g2.setColor(Tema.BORDA);
                g2.drawLine(in.left, base, getWidth() - in.right, base);

                g2.setColor(Tema.PRIMARIA);
                g2.fillRoundRect(x1, base - altura1, largura, altura1, 6, 6);
                g2.setColor(new Color(0xCBD5E1));
                g2.fillRoundRect(x2, base - altura2, largura, altura2, 6, 6);

                g2.setFont(Tema.fonte(Font.PLAIN, 11f));
                g2.setColor(Tema.TEXTO);
                g2.drawString(m.format(margem), x1, base - altura1 - 6);
                g2.drawString(m.format(PREJUIZO), x2, base - altura2 - 6);
                g2.setColor(Tema.TEXTO_SUAVE);
                g2.drawString("Faturado", x1, base + 16);
                g2.drawString("Limite", x2, base + 16);
                g2.dispose();
            }
        };
        p.setBackground(Tema.SUPERFICIE);
        p.setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createLineBorder(Tema.BORDA),
			BorderFactory.createEmptyBorder(16, 20, 12, 20)));
        p.setPreferredSize(new Dimension(320, 220));
        return p;
    }

}
