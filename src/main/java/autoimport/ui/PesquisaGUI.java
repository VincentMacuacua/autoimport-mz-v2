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
        super("Procurar cliente - AutoImport MZ");

        pes=new Pesquisa();

        cont = getContentPane();
        cont.setLayout(new BorderLayout());

        panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 12, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(16, 20, 16, 20));

        // Tabela de resultados: criada uma vez e atualizada a cada pesquisa
        tab = Tema.tabela(new String[0][cabeca.length], cabeca);
        scr = Tema.rolagem(tab);
        JPanel resultados = new JPanel(new BorderLayout(0, 6));
        resultados.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
        resultados.add(Tema.rotulo("Resultado"), BorderLayout.NORTH);
        resultados.add(scr, BorderLayout.CENTER);

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

        btnPesquisar = Tema.botaoPrimario(new JButton("Pesquisar"));
        getRootPane().setDefaultButton(btnPesquisar);
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
	                {
	                	tab.setModel(Tema.modeloLeitura(new String[0][cabeca.length], cabeca));
	                	JOptionPane.showMessageDialog(PesquisaGUI.this,"Nao existe esse cliente","Sem resultados",JOptionPane.INFORMATION_MESSAGE);
	                }
	                else
	                {
	                	dados=preencher(a,poz);
		                tab.setModel(Tema.modeloLeitura(dados, cabeca));
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

        JPanel topo = new JPanel(new BorderLayout());
        topo.add(Tema.cabecalho("Procurar cliente", "Pesquise por código da viatura ou pelo nome do cliente"), BorderLayout.NORTH);
        topo.add(panel, BorderLayout.CENTER);
        cont.add(topo, BorderLayout.NORTH);
        cont.add(resultados, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 480);
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
