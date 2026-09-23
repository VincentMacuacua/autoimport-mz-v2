package autoimport.ui;
import autoimport.util.*;
import autoimport.model.*;
import autoimport.service.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class PesquisaGUI extends JFrame 
{
    private Container cont;
    private JComboBox combo;
    private JLabel lblCriterio, lblCodigo, lblNome;
    private JPanel panel;
    private JTextField txtCodigo, txtNome;
    private JTable tab;
    private JScrollPane scr;
    private JButton btnPesquisar;

    private String[] comboItem = {"Codigo", "Nome"};
    private String[] cabeca = {"Numero Telefone","Nome Cliente","Data Compra","Estado Compra","Marca Viatura","Modelo Viatura","Codigo Viatura","Cilidragem","Preco Viatura"};
    private String[][] dados;
    private Pesquisa pes;

    public PesquisaGUI(ArrayList<Cliente> a) 
    {
        super("Procurar cliente");
        
        ImageIcon icon = new ImageIcon(ResourcePaths.imagem("bgImage.jpg"));
		JPanel panelw = new JPanel() 
		{
            protected void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        setContentPane(panelw);
        
		cont = getContentPane();
		cont.setLayout(new BorderLayout());
        
        pes=new Pesquisa();

        cont = getContentPane();
        cont.setLayout(new BorderLayout());

        panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        lblCriterio = new JLabel("Escolhe criterio:");
        combo = new JComboBox(comboItem);
        combo.addItemListener
        (
    		new ItemListener() 
		    {
		        public void itemStateChanged(ItemEvent ev) 
		        {
		            if (ev.getStateChange() == ItemEvent.SELECTED) 
		            {
		                String itemSelecionado = ev.getItem().toString();
		                boolean isCodigo = itemSelecionado.equals("Codigo");
		                lblCodigo.setEnabled(isCodigo);
		                txtCodigo.setEnabled(isCodigo);
		                lblNome.setEnabled(!isCodigo);
		                txtNome.setEnabled(!isCodigo);
		            }
		        }
		    }	
		);

        lblCodigo = new JLabel("Codigo:");
        lblCodigo.setEnabled(false);
        txtCodigo = new JTextField();
        txtCodigo.setEnabled(false);

        lblNome = new JLabel("Nome:");
        lblNome.setEnabled(false);
        txtNome = new JTextField();
        txtNome.setEnabled(false);

        btnPesquisar = new JButton("Pesquisar");
        btnPesquisar.addActionListener
        (
        	new ActionListener() 
        	{
	            public void actionPerformed(ActionEvent e) 
	            {
	                String criterio = combo.getSelectedItem().toString();
	                String campoTexto="";
	                int poz;
	                
	                panel.repaint();
	                panel.revalidate();
	                
	                
	                if (criterio.equals("Codigo")) 
	                {
	                    campoTexto = txtCodigo.getText();
	                    poz=pes.pesquisa_Code(a,campoTexto);
	                } 
	                else 
	                {
	                    campoTexto = txtNome.getText();
	                    poz=pes.pesquisa_Nome(a,campoTexto);
	                }
	                if(campoTexto==null)
	                	JOptionPane.showMessageDialog(null,"Introduz na caixa de texto","ERRO",JOptionPane.ERROR_MESSAGE);
	                if(poz==-1)
	                	JOptionPane.showMessageDialog(null,"Nao existe esse cliente","ERRO",JOptionPane.ERROR_MESSAGE);
	                else
	                {
	                	dados=preencher(a,poz);
		                tab=new JTable(dados,cabeca);
		                tab.setForeground(Color.RED);
		                tab.setEnabled(false);
		                scr=new JScrollPane(null);
		                scr=new JScrollPane(tab);
		                scr.setBackground(Color.BLUE);
		                scr.setForeground(Color.BLUE);
		                cont.add(scr, BorderLayout.CENTER);
		                
		                panel.repaint();
		                panel.revalidate();
		                
	                }
	            }
        	}
        );

        panel.add(lblCriterio);
        panel.add(combo);
        panel.add(lblCodigo);
        panel.add(txtCodigo);
        panel.add(lblNome);
        panel.add(txtNome);
        panel.add(new JLabel());
        panel.add(btnPesquisar);
        panel.repaint();
        panel.revalidate();

        cont.add(panel, BorderLayout.NORTH);
        
        cont.repaint();
        cont.revalidate();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    public String [][] preencher(ArrayList<Cliente> a,int poz)
    {
    	String [][] s=new String [1][cabeca.length];
    	Cliente pai;
    	
    		pai=(Cliente)a.get(poz);
    		s[0][0]=pai.getNumeroTelefone();
    		s[0][1]=pai.getNomeCliente();
    		s[0][2]=pai.getDataCompra();
    		s[0][3]=pai.getEstadoCompra();
    		s[0][4]=pai.getMarcaViatura();
    		s[0][5]=pai.getModeloViatura();
    		s[0][6]=pai.getCodeViatura()+"";
    		s[0][7]=pai.getCilindragem()+"";
    		s[0][8]=pai.getPrecoViatura()+"";
    	
    	
    	return s;
    }
}
