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
    	super("Grafico do estado da Empreza");
		cont=getContentPane();
    	//cont.setLayout(new GridLayout(3,1,10,10));
    	
    	m=new DecimalFormat("###,###,###.00 Mts");
    	tabs=new JTabbedPane();
    	tabs.setForeground(new Color(180, 100, 255));
    	tabs.setFont(new Font("SansSerif",Font.BOLD,20));
    	
    	JPanel pane1=new JPanel();
    	pane1.setLayout(new GridLayout(1,4,10,10));
    	pane1.setBackground(Color.DARK_GRAY);
    	
    	JLabel ctDout=new JLabel("Quantidade Doutorado:\n"+Doutorado.ctDoutorado);
    	ctDout.setForeground(new Color(0, 255, 255));
    	ctDout.setBackground(new Color(180, 100, 255, 150));
    	ctDout.setFont(new Font("Dialog",Font.BOLD,15));
    	ctDout.setBorder(BorderFactory.createLineBorder(new Color(180, 100, 255, 120)));
    	pane1.add(ctDout,JLabel.CENTER);
    	
    	JLabel ctNor=new JLabel("Quantidade Normal:\n"+Normal.ctNormal);
    	ctNor.setForeground(new Color(0, 255, 255));
    	ctNor.setBackground(new Color(180, 100, 255, 150));
    	ctNor.setFont(new Font("Dialog",Font.BOLD,15));
    	ctNor.setBorder(BorderFactory.createLineBorder(new Color(180, 100, 255, 120)));
    	pane1.add(ctNor,JLabel.CENTER);
    	
    	JLabel ctRev=new JLabel("Quantidade Rvendedor:\n"+Revendedor.ctRevendor);
    	ctRev.setForeground(new Color(0, 255, 255));
    	ctRev.setBackground(new Color(180, 100, 255, 150));
    	ctRev.setFont(new Font("Dialog",Font.BOLD,15));
    	ctRev.setBorder(BorderFactory.createLineBorder(new Color(180, 100, 255, 120)));
    	pane1.add(ctRev,JLabel.CENTER);
    	
    	JLabel ctEst=new JLabel("Quantidade Estado:\n"+Estado.ctEstado);
    	ctEst.setForeground(new Color(0, 255, 255));
    	ctEst.setBackground(new Color(180, 100, 255, 150));
    	ctEst.setFont(new Font("Dialog",Font.BOLD,15));
    	ctEst.setBorder(BorderFactory.createLineBorder(new Color(180, 100, 255, 120)));
    	pane1.add(ctEst,JLabel.CENTER);
    	
    	//tabs.add("Quantidades",pane1);
    	//cont.add(tabs);
    	
    	JPanel pane2=new JPanel();
    	pane2.setLayout(new GridLayout(1,3,10,10));
    	pane2.setBackground(Color.DARK_GRAY);
    	
    	JLabel acTotal=new JLabel("Total Ganho: "+m.format(valorTotal));
    	acTotal.setForeground(new Color(57, 255, 20));
    	acTotal.setBackground(new Color(180, 100, 255, 150));
    	acTotal.setFont(new Font("Dialog",Font.BOLD,25));
    	acTotal.setBorder(BorderFactory.createLineBorder(new Color(180, 100, 255, 120)));
    	pane2.add(acTotal,JLabel.CENTER);
    	
    	JLabel acAdoneiro=new JLabel("Total Adoaneiro: "+m.format(adoanerio));
    	acAdoneiro.setForeground(new Color(57, 255, 20));
    	acAdoneiro.setBackground(new Color(180, 100, 255, 150));
    	acAdoneiro.setFont(new Font("Dialog",Font.BOLD,25));
    	acAdoneiro.setBorder(BorderFactory.createLineBorder(new Color(180, 100, 255, 120)));
    	pane2.add(acAdoneiro,JLabel.CENTER);
    	
    	//tabs.add("Valor total",pane2);
    	//cont.add(pane2);
    	
    	JPanel pane3=new JPanel();
    	pane3.setLayout(new GridLayout(1,2,10,10));
    	pane3.setBackground(Color.DARK_GRAY);
    	
    	if(margem<PREJUIZO)
    	{
	    	JLabel situacao=new JLabel("Empresa em Prejuiso: "+m.format((-1*margem)));
	    	situacao.setForeground(new Color(204, 0, 61));
	    	situacao.setBackground(new Color(180, 100, 255, 150));
	    	situacao.setFont(new Font("Dialog",Font.BOLD,25));
	    	situacao.setBorder(BorderFactory.createLineBorder(new Color(180, 100, 255, 120)));
	    	pane3.add(situacao);
    	}
    	else
    	{
    		JLabel situacao=new JLabel("Empresa em Lucro: "+m.format(margem));
	    	situacao.setForeground(new Color(57, 255, 20));
	    	situacao.setBackground(new Color(180, 100, 255, 150));
	    	situacao.setFont(new Font("Dialog",Font.BOLD,25));
	    	situacao.setBorder(BorderFactory.createLineBorder(new Color(180, 100, 255, 120)));
	    	pane3.add(situacao);
    	}
    	JPanel painelGrafico = gerarGraficoBarras(valorTotal, PREJUIZO);
    	painelGrafico.setBackground(Color.DARK_GRAY);
    	painelGrafico.setBorder(BorderFactory.createLineBorder(new Color(180, 100, 255, 120)));
    	pane3.add(painelGrafico);
    	
    	//tabs.add("Grafico",pane3);
    	//cont.add(pane3);
    	JPanel pagina1=new JPanel();
    	pagina1.setBackground(Color.DARK_GRAY);
    	pagina1.add(pane1);
    	pagina1.add(pane2);
    	pagina1.add(pane3);
    	pagina1.setLayout(new GridLayout(3,1,10,10));
    	
    	tabs.add("Consulta 1",pagina1);
    	
    	ImageIcon icon = new ImageIcon(ResourcePaths.imagem("bgImage.jpg"));
		JPanel pagina2 = new JPanel() 
		{
            protected void paintComponent(Graphics g) 
            {
                super.paintComponent(g);
                g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        //setContentPane(pagina2);
        
        cont= getContentPane();
        
        pagina2.setLayout(new GridLayout(2,1,10,10));

        // Título
        labelTitulo = new JLabel("Quantidade total de carros vendidos:", JLabel.CENTER);
        labelTitulo.setFont(new Font("Arial", Font.PLAIN, 50));
        labelTitulo.setForeground(new Color(204, 255, 51));
        pagina2.add(labelTitulo);

        // Quantidade
        labelQuantidade = new JLabel(totalCarrosVendidos+"", JLabel.CENTER);
        labelQuantidade.setBorder(BorderFactory.createLineBorder(new Color(28, 169, 201)));
        labelQuantidade.setFont(new Font("Arial", Font.PLAIN,50));
        labelQuantidade.setForeground(new Color(204, 255, 51));
        pagina2.add(labelQuantidade);
    	
        tabs.add("Consulta 2",pagina2);
    	
        // Painel com imagem de fundo
        ImageIcon icon3 = new ImageIcon(ResourcePaths.imagem("bgImage.jpg"));
        JPanel pagina3 = new JPanel() 
        {
            protected void paintComponent(Graphics g) 
            {
                super.paintComponent(g);
                g.drawImage(icon3.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        pagina3.setLayout(new GridLayout(2, 1, 0, 0));

        // JLabel com texto
        titulo = new JLabel("Estatistica de Venda", JLabel.CENTER);
        titulo.setForeground(new Color(28, 169, 201));
        titulo.setFont(new Font("Serif", Font.BOLD, 50));

        // Painel transparente com o título
        panelTitulo = new JPanel();
        panelTitulo.setOpaque(false); // Transparente
        panelTitulo.add(titulo);

        pagina3.add(panelTitulo,BorderLayout.NORTH);
        //setContentPane(pagina3);
        
        panelEst=new JPanel();
        panelEst.setLayout(new GridLayout(2,2,0,0));
        panelEst.setBackground(new Color(0,0,0,0));
        
        JPanel sub1=new JPanel();
        sub1.setLayout(new GridLayout(2,1));
        JLabel l1=new JLabel("Valor total Normal",JLabel.CENTER);
        l1.setFont(new Font("Serif", Font.BOLD, 30));
        l1.setForeground(new Color(204, 255, 51));
        sub1.add(l1);
        sub1.setBackground(new Color(0,0,0,0));
        JLabel acumN=new JLabel(m.format(acmN),JLabel.CENTER);
        acumN.setFont(new Font("Serif",Font.PLAIN,30));
        acumN.setForeground(new Color(204, 255, 51));
        sub1.add(acumN);
        sub1.setBorder(BorderFactory.createLineBorder(new Color(28, 169, 201)));
        
        JPanel sub2=new JPanel();
        sub2.setLayout(new GridLayout(2,1));
        JLabel l2=new JLabel("Valor total Doutorado",JLabel.CENTER);
        l2.setFont(new Font("Serif", Font.BOLD, 30));
        l2.setForeground(new Color(204, 255, 51));
        sub2.add(l2);
        sub2.setBackground(new Color(0,0,0,0));
        JLabel acumD=new JLabel(m.format(acmD),JLabel.CENTER);
        acumD.setFont(new Font("Serif",Font.PLAIN,30));
        acumD.setForeground(new Color(204, 255, 51));
        sub2.add(acumD);
        sub2.setBorder(BorderFactory.createLineBorder(new Color(28, 169, 201)));
        
        JPanel sub3=new JPanel();
        sub3.setLayout(new GridLayout(2,1));
        JLabel l3=new JLabel("Valor total Revendedor",JLabel.CENTER);
        l3.setForeground(new Color(204, 255, 51));
        l3.setFont(new Font("Serif", Font.BOLD, 30));
        sub3.add(l3);
        sub3.setBackground(new Color(0,0,0,0));
        JLabel acumR=new JLabel(m.format(acmR),JLabel.CENTER);
        acumR.setFont(new Font("Serif",Font.PLAIN,30));
        acumR.setForeground(new Color(204, 255, 51));
        sub3.add(acumR);
        sub3.setBorder(BorderFactory.createLineBorder(new Color(28, 169, 201)));
        
        JPanel sub4=new JPanel();
        sub4.setLayout(new GridLayout(2,1));
        JLabel l4=new JLabel("Valor total Estado",JLabel.CENTER);
        l4.setForeground(new Color(204, 255, 51));
        l4.setBackground(new Color(0,0,0,0));
        l4.setFont(new Font("Serif", Font.BOLD, 30));
        sub4.add(l4);
        sub4.setBackground(new Color(0,0,0,0));
        JLabel acumE=new JLabel(m.format(acmE),JLabel.CENTER);
        acumE.setFont(new Font("Serif",Font.PLAIN,30));
        acumE.setForeground(new Color(204, 255, 51));
        sub4.add(acumE);
        sub4.setBorder(BorderFactory.createLineBorder(new Color(28, 169, 201)));
        
        panelEst.add(sub1);
        panelEst.add(sub2);
        panelEst.add(sub3);
        panelEst.add(sub4);
        
        pagina3.add(panelEst);
        
        tabs.add("Consulta 3",pagina3);
    	
    	cont.add(tabs);
		
		repaint();
		revalidate();
		setSize(1100,800);
		cont.setBackground(Color.DARK_GRAY);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
    }
    public JPanel gerarGraficoBarras(double margem,final float PREJUIZO) 
    {
        return new JPanel() 
        {
            protected void paintComponent(Graphics g) 
            {
                super.paintComponent(g);

                int alturaMax = 150;
                double maior = Math.max(margem, PREJUIZO);
                int altura1 = (int) ((margem / maior) * alturaMax);
                int altura2 = (int) ((PREJUIZO / maior) * alturaMax);
                int base = getHeight() - 30;
                int largura = 60;

                // Barra verde
                g.setColor(new Color(34, 139, 34));
                g.fillRect(80, base - altura1, largura, altura1);
                g.setColor(Color.WHITE);
                g.drawString(String.format("%.2f", margem), 80, base - altura1 - 5);

                // Barra vermelha
                g.setColor(new Color(204, 0, 61));
                g.fillRect(180, base - altura2, largura, altura2);
                g.setColor(Color.WHITE);
                g.drawString(String.format("%.2f", PREJUIZO), 180, base - altura2 - 5);
            }

            /*public Dimension getPreferredSize() 
            {
                return new Dimension(320, 200);
            }*/
        };
    }

}
