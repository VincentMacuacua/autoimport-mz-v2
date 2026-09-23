package autoimport.ui;
import autoimport.model.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.time.Year;
import java.util.ArrayList;
import java.util.Random;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class NovoClinete extends JFrame implements ItemListener
{
    private JPanel painelPrincipal;
    private JScrollPane scrollPane;
    private String estadoCompra, estrangeiro, manutencao;
    private JRadioButton ep,et,ec, nor, dou, rev, est; //Pra Estado de preocesso: exemplo ep-significa "Em processo"
    private JRadioButton emp,par;
    private String tipo, tipoViatura;
    private JRadioButton nao2, sim2, nao1, sim1, pessoal, profiss;
    private JComboBox anos, diaFinal, mesFinal, quantViaturasCombo, anoFinal;
    private JTextField nomeComercial;
    // Componentes que podem precisar ser acessados para eventos
    private JComboBox diaCombo, mesCombo, anoCombo;

    // Adicionar referências aos campos para validação
    private JTextField nomeField, telefoneField, precoField, cilindragemField, codField, nomeIns;
    private JComboBox marcaCombo, modeloCombo;

    public NovoClinete(ArrayList<Cliente> array)
    {
        super("Novo Cliente");

        painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        buildDadosClientePanel();
        buildDadosCompraPanel();
        buildInformacoesExtrasPanel();
        buildOutrasInformacoesPanel();
        buildDataConclusaoPanel();
        buildBotoesPanel(array);

        scrollPane = new JScrollPane(painelPrincipal);
        add(scrollPane, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 700);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void buildDadosClientePanel()
    {
        // ---------- Painel: Dados do Cliente ----------
        JPanel painelDadosCliente = new JPanel(new GridLayout(3, 2, 10, 10));
        painelDadosCliente.setBorder(BorderFactory.createTitledBorder("Dados do Cliente"));

        painelDadosCliente.add(new JLabel("Nome: "));
        nomeField = new JTextField(20);
        painelDadosCliente.add(nomeField);

        painelDadosCliente.add(new JLabel("Telefone: "));
        telefoneField = new JFormattedTextField();
        painelDadosCliente.add(telefoneField);

        painelDadosCliente.add(new JLabel("Tipo de Cliente:"));
        JPanel tipoClientePanel = new JPanel(new GridLayout(2, 3));
        ButtonGroup grupoTipo = new ButtonGroup();
        ButtonGroup grupoTipo1 = new ButtonGroup();
        par = new JRadioButton("Particular",false);
        emp= new JRadioButton("Empresarial",false);
        dou = new JRadioButton("Doutorado",false);
        rev = new JRadioButton("Revendedor",false);
        nor = new JRadioButton("Normal",false);
        est = new JRadioButton("Estado",false);
        dou.addItemListener(this);
        nor.addItemListener(this);
        rev.addItemListener(this);
        est.addItemListener(this);
        dou.setEnabled(false);
        rev.setEnabled(false);
        nor.setEnabled(false);
        est.setEnabled(false);
        grupoTipo.add(par);
        grupoTipo.add(emp);
        grupoTipo1.add(dou);
        grupoTipo1.add(rev);
        grupoTipo1.add(nor);
        grupoTipo1.add(est);
        tipoClientePanel.add(par);
        tipoClientePanel.add(emp);
        tipoClientePanel.add(est);
        tipoClientePanel.add(dou);
        tipoClientePanel.add(nor);
        tipoClientePanel.add(rev);
        painelDadosCliente.add(tipoClientePanel);

        par.addItemListener
        (
    		new ItemListener()
        	{
    			public void itemStateChanged(ItemEvent a)
    			{
    				nor.setEnabled(true);
    	            dou.setEnabled(true);
    	            est.setEnabled(false);
    	            rev.setEnabled(false);
    			}
        	}
		);
        emp.addItemListener
        (
    		new ItemListener()
        	{
    			public void itemStateChanged(ItemEvent a)
    			{
    				nomeComercial.setEditable(true);
    				est.setEnabled(true);
		            rev.setEnabled(true);
		            nor.setEnabled(false);
		            dou.setEnabled(false);
    			}

    		}
		);

        painelPrincipal.add(painelDadosCliente);
    }

    private void buildDadosCompraPanel()
    {
        // ---------- Painel: Dados da Compra ----------
        JPanel painelDadosCompra = new JPanel(new GridLayout(8, 2, 10, 10));
        painelDadosCompra.setBorder(BorderFactory.createTitledBorder("Dados da Compra"));

        painelDadosCompra.add(new JLabel("Data da Compra:"));
        JPanel painelData = new JPanel(new FlowLayout(FlowLayout.LEFT));

        String[] meses = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        mesCombo = new JComboBox(meses);

        String[] dias = new String[31];
        for (int i = 1; i <= 31; i++)
        {
            dias[i - 1] = String.valueOf(i);
        }
        diaCombo = new JComboBox(dias);
        painelData.add(diaCombo);

        painelData.add(mesCombo);

        anoCombo = new JComboBox();
        for (int i = Year.now().getValue(); i <= 2035; i++)
        {
            anoCombo.addItem(String.valueOf(i));
        }
        painelData.add(anoCombo);
        painelDadosCompra.add(painelData);

        painelDadosCompra.add(new JLabel("Marca:"));
        marcaCombo = new JComboBox(new String[]{"Toyota", "Honda", "Ford","Mercedes","BMW","Audi","Mazda","Dodge","VW","Jeep"});
        painelDadosCompra.add(marcaCombo);
        painelDadosCompra.add(new JLabel("Modelo:"));
        modeloCombo = new JComboBox(new String[]{"Modelo A", "Modelo B","Modelo C","Modelo D","Modelo E","Modelo F"});
        painelDadosCompra.add(modeloCombo);

        painelDadosCompra.add(new JLabel("Estado da Compra:"));
        JPanel estadoCompraPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ButtonGroup grupoEstado = new ButtonGroup();

        //Podemos mudar a forma da adicao
        ep = new JRadioButton("Em Processo");
        et = new JRadioButton("Em Transito");
        ec = new JRadioButton("Estado Concluida");
        ep.addItemListener(this);
        et.addItemListener(this);
        ec.addItemListener(this);
        grupoEstado.add(ep);
        grupoEstado.add(et);
        grupoEstado.add(ec);

        estadoCompraPanel.add(ep);
        estadoCompraPanel.add(et);
        estadoCompraPanel.add(ec);

        painelDadosCompra.add(estadoCompraPanel);
        painelDadosCompra.add(new JLabel("Preço: "));
        precoField = new JTextField(20);
        painelDadosCompra.add(precoField);
        painelDadosCompra.add(new JLabel("Cilindagem: "));
        cilindragemField = new JTextField(20);
        painelDadosCompra.add(cilindragemField);
        painelDadosCompra.add(new JLabel("Codigo da viatura"));

        // Gera 10000-99999
        Random r = new Random();
        int rand = 10000 + r.nextInt(90000);
        codField = new JTextField(String.valueOf(rand));
        codField.setEditable(false);

        painelDadosCompra.add(codField);
        painelDadosCompra.add(new JLabel("Tipo de viatura"));
        JPanel tipoV = new JPanel();
        tipoV.setLayout(new FlowLayout(FlowLayout.LEFT));
        ButtonGroup gt = new ButtonGroup();
        pessoal = new JRadioButton("Pessoal");
        profiss = new JRadioButton("Profissional");

        pessoal.addItemListener(this);
        profiss.addItemListener(this);
        gt.add(pessoal);
        gt.add(profiss);
        tipoV.add(pessoal);
        tipoV.add(profiss);
        pessoal.setEnabled(false);
        profiss.setEnabled(false);

        painelDadosCompra.add(tipoV);
        painelPrincipal.add(painelDadosCompra);
    }

    private void buildInformacoesExtrasPanel()
    {
        // ---------- Painel: Informações Extras ----------
        JPanel painelExtras = new JPanel(new GridLayout(3, 2, 10, 10));
        painelExtras.setBorder(BorderFactory.createTitledBorder("Informações Extras"));

        painelExtras.add(new JLabel("E estrangeiro?"));
        JPanel painelSimNao1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        sim1 = new JRadioButton("Sim");
        nao1 = new JRadioButton("Nao");
        sim1.addItemListener(this);
        nao1.addItemListener(this);
        ButtonGroup grupo1 = new ButtonGroup();
        grupo1.add(sim1);
        grupo1.add(nao1);
        painelSimNao1.add(sim1);
        painelSimNao1.add(nao1);
        painelExtras.add(painelSimNao1);

        painelExtras.add(new JLabel("Tempo fora do pais (anos):"));
        anos = new JComboBox(new String[]{"0", "1", "2", "3", "4", "5+"});
        anos.setEnabled(false);
        painelExtras.add(anos);

        painelExtras.add(new JLabel("Incluir manutenção?"));
        JPanel painelSimNao2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        sim2 = new JRadioButton("Sim");
        nao2 = new JRadioButton("Não");
        sim2.addItemListener(this);
        nao2.addItemListener(this);
        sim2.setEnabled(false);
        nao2.setEnabled(false);
        ButtonGroup grupo2 = new ButtonGroup();
        grupo2.add(sim2);
        grupo2.add(nao2);
        painelSimNao2.add(sim2);
        painelSimNao2.add(nao2);
        painelExtras.add(painelSimNao2);

        painelPrincipal.add(painelExtras);
    }

    private void buildOutrasInformacoesPanel()
    {
        // ---------- Painel: Outras Informações ----------
        JPanel outrasInfo = new JPanel(new GridLayout(3, 2, 10, 10));
        outrasInfo.setBorder(BorderFactory.createTitledBorder("Outras Informações"));
        outrasInfo.add(new JLabel("Qtd. Viaturas Desejadas:"));
        quantViaturasCombo = new JComboBox(new String[]{"1", "2", "3", "4", "5"});
        quantViaturasCombo.setEditable(false);
        outrasInfo.add(quantViaturasCombo);
        quantViaturasCombo.setEditable(false);
        outrasInfo.add(new JLabel("Nome Comercial:"));
        nomeComercial = new JTextField(20);
        nomeComercial.setEnabled(true);
        outrasInfo.add(nomeComercial);
        outrasInfo.add(new JLabel("Nome da Instituição:"));
        nomeIns = new JTextField(20);
        outrasInfo.add(nomeIns);
        painelPrincipal.add(outrasInfo);
    }

    private void buildDataConclusaoPanel()
    {
        // ---------- Painel: Data Conclusão ----------
        JPanel painelConclusao = new JPanel(new GridLayout(1, 2));
        painelConclusao.setBorder(BorderFactory.createTitledBorder("Data de Conclusão"));
        painelConclusao.add(new JLabel("Data:"));

        JPanel dataConclusao = new JPanel(new FlowLayout(FlowLayout.LEFT));
        String[] diasFinal = new String[31];
        for (int i = 1; i <= 31; i++)
        {
            diasFinal[i - 1] = String.valueOf(i);
        }
        String[] mesesFinal = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        diaFinal = new JComboBox(diasFinal);
        mesFinal = new JComboBox(mesesFinal);
        anoFinal = new JComboBox();

        anoFinal.setEnabled(false);
        diaFinal.setEnabled(false);
        mesFinal.setEnabled(false);
        dou.addItemListener
        (
        	new ItemListener()
        	{
        		public void itemStateChanged(ItemEvent a)
        		{
        			anoFinal.setEnabled(true);
        	        diaFinal.setEnabled(true);
        	        mesFinal.setEnabled(true);
        		}
        	}
        );
        for (int i = Year.now().getValue(); i >= 2010; i--)
        {
            anoFinal.addItem(String.valueOf(i));
        }
        dataConclusao.add(mesFinal);
        dataConclusao.add(diaFinal);
        dataConclusao.add(anoFinal);
        painelConclusao.add(dataConclusao);

        painelPrincipal.add(painelConclusao);
    }

    private void buildBotoesPanel(ArrayList<Cliente> array)
    {
        // ---------- Painel: Botões ----------
        JPanel botoesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton gravar = new JButton("Gravar");
        JButton voltar = new JButton("Voltar");
        gravar.setPreferredSize(new Dimension(120, 40));
        voltar.setPreferredSize(new Dimension(120, 40));
        voltar.addActionListener
        (
    		new ActionListener()
			{
				public void actionPerformed(ActionEvent a)
				{
					dispose();
				}
			}
		);
        gravar.addActionListener
        (
        	new ActionListener()
        	{
        		public void actionPerformed(ActionEvent a)
        		{
        			// VALIDACOES OBRIGATORIAS
        			if(nomeField.getText().trim().isEmpty())
        			{
        				JOptionPane.showMessageDialog(null, "Por favor, preencha o campo Nome!");
        				return;
        			}
        			else if(telefoneField.getText().trim().isEmpty())
        			{
        				JOptionPane.showMessageDialog(null, "Por favor, preencha o campo Telefone!");
        				return;
        			}
        			else if (telefoneField.getText().length()<9 || telefoneField.getText().length()>10)
        			{
        				JOptionPane.showMessageDialog(null, "Por favor, preencha o campo Telefone!");
        				return;
        			}
        			else if(!par.isSelected() && !emp.isSelected())
        			{
        				JOptionPane.showMessageDialog(null, "Por favor, selecione o tipo de cliente (Particular ou Empresarial)!");
        				return;
        			}
        			else if(par.isSelected() && !nor.isSelected() && !dou.isSelected())
        			{
        				JOptionPane.showMessageDialog(null, "Por favor, selecione o subtipo para cliente Particular (Normal ou Doutorado)!");
        				return;
        			}
        			else if(emp.isSelected() && !est.isSelected() && !rev.isSelected())
        			{
        				JOptionPane.showMessageDialog(null, "Por favor, selecione o subtipo para cliente Empresarial (Estado ou Revendedor)!");
        				return;
        			}
        			else if(!ep.isSelected() && !et.isSelected() && !ec.isSelected())
        			{
        				JOptionPane.showMessageDialog(null, "Por favor, selecione o Estado da Compra!");
        				return;
        			}
        			else if(precoField.getText().trim().isEmpty())
        			{
        				JOptionPane.showMessageDialog(null, "Por favor, preencha o campo Preco!");
        				return;
        			}
        			else if(cilindragemField.getText().trim().isEmpty())
        			{
        				JOptionPane.showMessageDialog(null, "Por favor, preencha o campo Cilindagem!");
        				return;
        			}
        			else if(codField.getText().trim().isEmpty())
        			{
        				JOptionPane.showMessageDialog(null, "Por favor, preencha o campo Codigo da Viatura!");
        				return;
        			}

        			// VALIDAÇÕES ESPECÍFICAS POR TIPO DE CLIENTE
        			else if((nor.isSelected() || dou.isSelected()) && !pessoal.isSelected() && !profiss.isSelected())
        			{
        				JOptionPane.showMessageDialog(null, "Por favor, selecione o Tipo de Viatura (Pessoal ou Profissional)!");
        				return;
        			}
        			else if(nor.isSelected() && !sim1.isSelected() && !nao1.isSelected())
        			{
        				JOptionPane.showMessageDialog(null, "Por favor, informe se e estrangeiro!");
        				return;
        			}
        			else if(est.isSelected() && !sim2.isSelected() && !nao2.isSelected())
        			{
        				JOptionPane.showMessageDialog(null, "Por favor, informe se deseja incluir manutencao!");
        				return;
        			}
        			else if(rev.isSelected() && nomeComercial.getText().trim().isEmpty())
        			{
        				JOptionPane.showMessageDialog(null, "Por favor, preencha o Nome Comercial para cliente Revendedor!");
        				return;
        			}
        			else if(est.isSelected() && nomeIns.getText().trim().isEmpty())
        			{
        				JOptionPane.showMessageDialog(null, "Por favor, preencha o Nome da Instituição para cliente do Estado!");
        				return;
        			}

        			String nome, nomeCome="",nomeInstituicao, dataConclusao,numero, dataCompra, marca,modelo;
	        			int anosFora, preco,numAnos,cil,codViatura, numViaturas;
	        			nome = nomeField.getText().trim();
	        			numero = telefoneField.getText().trim();
	        			dataCompra = (diaCombo.getSelectedIndex()+1)+"/"+(mesCombo.getSelectedIndex()+1)+"/"+anoCombo.getSelectedItem();
	        			marca = marcaCombo.getSelectedItem()+"";
	        			modelo = modeloCombo.getSelectedItem()+"";
	        			codViatura =Integer.parseInt(codField.getText());
	        			preco = Integer.parseInt(precoField.getText());
	        			cil = Integer.parseInt(cilindragemField.getText());
	        			numViaturas = Integer.parseInt(String.valueOf(quantViaturasCombo.getSelectedItem()));

	        			nomeCome = nomeComercial.getText();
	        			nomeInstituicao = nomeIns.getText();

	        			if(tipo.equals("Normal"))
	        			{
		        			anosFora = Integer.parseInt((anos.getSelectedItem()+""));
							normal(array, numero,nome,dataCompra,estadoCompra,marca,modelo,codViatura,cil,preco,tipoViatura,estrangeiro,anosFora);
	        			}
	        			else if(tipo.equals("Doutorado"))
	        			{
		        			dataConclusao = diaFinal.getSelectedItem()+"/"+mesFinal.getSelectedItem()+"/"+anoFinal.getSelectedItem();
		        			doutorado(array, numero,nome,dataCompra,estadoCompra,marca,modelo,codViatura,cil,preco,tipoViatura,dataConclusao);
	        			}
	        			else if(tipo.equals("Revendedor"))
	        			{
		        			revendedor(array, numero,nome,dataCompra,estadoCompra,marca,modelo,codViatura,cil,preco,numViaturas, nomeCome);
	        			}
	        			else if(tipo.equals("Estado"))
	        			{
		        			estado(array, numero,nome,dataCompra,estadoCompra,marca,modelo,codViatura,cil,preco,numViaturas, nomeInstituicao, manutencao);
	        			}

	        			// Gravar no arquivo após adicionar ao ArrayList
	        			gravarNoArquivo(array,nomeCome,manutencao,tipoViatura,estrangeiro);

	        			JOptionPane.showMessageDialog(null, "Adição feita com sucesso e dados gravados no arquivo!");
        		}
        	}
        );
        botoesPanel.add(gravar);
        botoesPanel.add(voltar);
        painelPrincipal.add(botoesPanel);
    }

    // Método para gravar no arquivo
    private void gravarNoArquivo(ArrayList<Cliente> array,String nomeCome,String manutencao,String tipoViatura,String estrangeiro)
    {
        try
        {
            FileWriter fw = new FileWriter("data/Dados.txt", true); // true para append (manter dados existentes)
            BufferedWriter bw = new BufferedWriter(fw);

            // Pega o último elemento adicionado ao array (o mais recente)
            Cliente pai = array.get(array.size()-1);
            Doutorado douto;
    		Normal normal;
    		Revendedor revend;
    		Estado estado;

            // Escreve os dados baseado no tipo do objeto

            if(pai instanceof Doutorado)
			{
				douto=(Doutorado)pai;
				bw.write(douto.getNumeroTelefone()+";"+douto.getNomeCliente()+";"+douto.getDataCompra()+";"+douto.getEstadoCompra()+";"+douto.getMarcaViatura()+";"+douto.getModeloViatura()+";"+douto.getCodeViatura()+";"+douto.getCilindragem()+";"+douto.getPrecoViatura()+";"+'p'+";"+douto.getTipoViatura()+";"+'d'+";"+douto.getDataCDoutoramento());
				bw.newLine();
			}
			else if(pai instanceof Normal)
			{
				normal=(Normal)pai;
				bw.write(normal.getNumeroTelefone()+";"+normal.getNomeCliente()+";"+normal.getDataCompra()+";"+normal.getEstadoCompra()+";"+normal.getMarcaViatura()+";"+normal.getModeloViatura()+";"+normal.getCodeViatura()+";"+normal.getCilindragem()+";"+normal.getPrecoViatura()+";"+'p'+";"+normal.getTipoViatura()+";"+'n'+";"+normal.getEstrangeiro()+";"+normal.getNumeroAnosForaPais());
				bw.newLine();
			}
			else if(pai instanceof Revendedor)
			{
				revend=(Revendedor)pai;
				bw.write(revend.getNumeroTelefone()+";"+revend.getNomeCliente()+";"+revend.getDataCompra()+";"+revend.getEstadoCompra()+";"+revend.getMarcaViatura()+";"+revend.getModeloViatura()+";"+revend.getCodeViatura()+";"+revend.getCilindragem()+";"+revend.getPrecoViatura()+";"+'e'+";"+revend.getQtyViaturas()+";"+'r'+";"+revend.getNomeComercial());
				bw.newLine();
			}
			else if(pai instanceof Estado)
			{
				estado=(Estado)pai;
				bw.write(estado.getNumeroTelefone()+";"+estado.getNomeCliente()+";"+estado.getDataCompra()+";"+estado.getEstadoCompra()+";"+estado.getMarcaViatura()+";"+estado.getModeloViatura()+";"+estado.getCodeViatura()+";"+estado.getCilindragem()+";"+estado.getPrecoViatura()+";"+'e'+";"+estado.getQtyViaturas()+";"+'e'+";"+estado.getNomeIGovernal()+";"+estado.getIncluirManutencao());
				bw.newLine();
			}
            bw.close();
        }
        catch (IOException e)
        {
            JOptionPane.showMessageDialog(null, "Erro ao gravar no arquivo: " + e.getMessage());
        }
    }

    public void itemStateChanged(ItemEvent a)
    {
    	// Captura estado da compra
    	if(ep.isSelected())
    		estadoCompra = ep.getText();
    	if(et.isSelected())
    		estadoCompra = et.getText();
    	if(ec.isSelected())
    		estadoCompra = ec.getText();

    	// Captura tipo de cliente
    	if(nor.isSelected())
    	{
    		tipo = nor.getText();
    		pessoal.setEnabled(true);
    		profiss.setEnabled(true);
    		anos.setEnabled(true);
    	}
    	if(est.isSelected())
    	{
    		quantViaturasCombo.setEditable(true);

    		tipo = est.getText();
    		nomeComercial.setEditable(true);
    		sim2.setEnabled(true);
    		nao2.setEnabled(true);
    	}
    	if(dou.isSelected())
    	{
    		tipo = dou.getText();
    		pessoal.setEnabled(true);
    		profiss.setEnabled(true);
    	}
    	if(rev.isSelected())
    	{
    		tipo = rev.getText();
    		quantViaturasCombo.setEnabled(true);
    		nomeComercial.setEditable(true);
    	}

    	//Captura se é estrangeiro
    	if(sim1.isSelected())
    		estrangeiro = "Sim";
    	if(nao1.isSelected())
    		estrangeiro = "Nao";

    	//Captura se quer manutenção
    	if(sim2.isSelected())
    		manutencao = "Sim";
    	if(nao2.isSelected())
    		manutencao = "Nao";

    	//Captura tipo de viatura
    	if(pessoal.isSelected())
    		tipoViatura = "Pessoal";
    	if(profiss.isSelected())
    		tipoViatura = "Profissional";
    }

    public void normal(ArrayList<Cliente> array, String numeroTelefone,String nomeCliente,String dataCompra,String estadoCompra,String marcaViatura,String modeloViatura,int codeViatura,int cilindragem,int precoViatura,String tipoViatura,String estrangeiro,int numeroAnosForaPais)
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

		array.add(n);
	}
    public void revendedor(ArrayList<Cliente> array, String numeroTelefone,String nomeCliente,String dataCompra,String estadoCompra,String marcaViatura,String modeloViatura,int codeViatura,int cilindragem,int precoViatura,int qtyViaturas,String nomeComercial)
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

		array.add(r);
	}
    public void estado(ArrayList<Cliente> array, String numeroTelefone,String nomeCliente,String dataCompra,String estadoCompra,String marcaViatura,String modeloViatura,int codeViatura,int cilindragem,int precoViatura,int qtyViaturas,String nomeIGovernal,String incluirManutencao)
	{
		Estado e = new Estado();

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

		array.add(e);
	}
    public void doutorado(ArrayList<Cliente> array, String numeroTelefone,String nomeCliente,String dataCompra,String estadoCompra,String marcaViatura,String modeloViatura,int codeViatura,int cilindragem,int precoViatura,String tipoViatura,String dataCDoutoramento)
	{
		Doutorado d = new Doutorado();

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

		array.add(d);
	}
}
