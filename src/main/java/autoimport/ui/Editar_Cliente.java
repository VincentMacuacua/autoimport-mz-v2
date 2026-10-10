package autoimport.ui;
import autoimport.model.*;
import autoimport.service.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Editar_Cliente extends JFrame 
{

    private JTextField campoCodigo;
    private JRadioButton rbProcesso, rbConcluida, rbTransito;
    private JButton btnSalvar;
    private String statusSelecionado = ""; // Armazena o status escolhido

    private Pesquisa pes;

    public Editar_Cliente(ArrayList<Cliente> a) 
    { 
        super("Alterar Status da Viatura");

        Container cont = getContentPane();
        cont.setLayout(new BorderLayout());

        pes = new Pesquisa();

        // Painel principal
        JPanel painelPrincipal = new JPanel(new GridLayout(3, 1, 5, 5));
        cont.add(Tema.cabecalho("Estado da compra", "Atualize o estado de entrega de uma viatura"), BorderLayout.NORTH);
        cont.add(painelPrincipal, BorderLayout.CENTER);
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));

        // Campo de código da viatura
        JPanel painelCodigo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblCodigo = Tema.rotulo("Código da viatura:");
        campoCodigo = new JTextField(20);
        painelCodigo.add(lblCodigo);
        painelCodigo.add(campoCodigo);
        painelPrincipal.add(painelCodigo);

        // Grupo de radio buttons
        JPanel painelRadio = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rbProcesso = new JRadioButton("Em processo");
        rbConcluida = new JRadioButton("Entrega concluída");
        rbTransito = new JRadioButton("Em trânsito");

        ButtonGroup grupoStatus = new ButtonGroup();
        grupoStatus.add(rbProcesso);
        grupoStatus.add(rbConcluida);
        grupoStatus.add(rbTransito);

        TratarEV x = new TratarEV();

        rbProcesso.addItemListener(x);
        rbConcluida.addItemListener(x);
        rbTransito.addItemListener(x);

        painelRadio.add(rbProcesso);
        painelRadio.add(rbConcluida);
        painelRadio.add(rbTransito);
        painelPrincipal.add(painelRadio);

        // Botão de salvar
        JPanel painelBotao = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnSalvar = Tema.botaoPrimario(new JButton("Salvar alterações"));
        btnSalvar.setPreferredSize(new Dimension(170, 36));

        btnSalvar.addActionListener
        (
    		new ActionListener() 
	        {
	            public void actionPerformed(ActionEvent e) 
	            {
	                Cliente pai;
	                Doutorado dou;
	                Normal nor;
	                Revendedor rev;
	                Estado est;
	                int poz;
	                
	                String codigoViaturaStr = campoCodigo.getText().trim();
	
	                if (codigoViaturaStr.isEmpty()) 
	                {
	                    JOptionPane.showMessageDialog(null, "Por favor, informe o código da viatura.");
	                    return;
	                }
	
	                if (statusSelecionado.isEmpty()) 
	                {
	                    JOptionPane.showMessageDialog(null, "Por favor, selecione um status.");
	                    return; // Interrompe a execução se nenhum status foi selecionado.
	                }
	                
	                try 
	                {
	                    poz = pes.pesquisa_Code(a, codigoViaturaStr);
	
	                    if (poz == -1) 
	                        JOptionPane.showMessageDialog(null, "Nao existe cliente com o código: " + codigoViaturaStr, "ERRO", JOptionPane.ERROR_MESSAGE);
	                    else 
	                    {
	                        pai = (Cliente) a.get(poz);
	                        pai.setEstadoCompra(statusSelecionado);
	                        // O try-with-resources garante que FileWriter e BufferedWriter sejam fechados.
	                        try
	                        	(FileWriter fr = new FileWriter("data/Dados.txt");
	                             BufferedWriter bw = new BufferedWriter(fr)) 
	                        {
	                            for (int i = 0; i < a.size(); i++) 
	                            {
	                                Cliente clienteAtual = (Cliente) a.get(i);
	                                if (clienteAtual instanceof Doutorado) 
	                                {
	                                    dou = (Doutorado) clienteAtual;
	                                    bw.write(dou.getNumeroTelefone() + ";" + dou.getNomeCliente() + ";" + dou.getDataCompra() + ";" + dou.getEstadoCompra() + ";" + dou.getMarcaViatura() + ";" + dou.getModeloViatura() + ";" + dou.getCodeViatura() + ";" + dou.getCilindragem() + ";" + dou.getPrecoViatura() + ";" + 'p' + ";" + dou.getTipoViatura() + ";" + 'd' + ";" + dou.getDataCDoutoramento());
	                                }
	                                else if (clienteAtual instanceof Normal) 
	                                {
	                                    nor = (Normal) clienteAtual;
	                                    bw.write(nor.getNumeroTelefone() + ";" + nor.getNomeCliente() + ";" + nor.getDataCompra() + ";" + nor.getEstadoCompra() + ";" + nor.getMarcaViatura() + ";" + nor.getModeloViatura() + ";" + nor.getCodeViatura() + ";" + nor.getCilindragem() + ";" + nor.getPrecoViatura() + ";" + 'p' + ";" + nor.getTipoViatura() + ";" + 'n' + ";" + nor.getEstrangeiro() + ";" + nor.getNumeroAnosForaPais());
	                                }
	                                else if (clienteAtual instanceof Revendedor) 
	                                {
	                                    rev = (Revendedor) clienteAtual;
	                                    bw.write(rev.getNumeroTelefone() + ";" + rev.getNomeCliente() + ";" + rev.getDataCompra() + ";" + rev.getEstadoCompra() + ";" + rev.getMarcaViatura() + ";" + rev.getModeloViatura() + ";" + rev.getCodeViatura() + ";" + rev.getCilindragem() + ";" + rev.getPrecoViatura() + ";" + 'e' + ";" + rev.getQtyViaturas() + ";" + 'r' + ";" + rev.getNomeComercial());
	                                }
	                                else if (clienteAtual instanceof Estado) 
	                                {
	                                    est = (Estado) clienteAtual;
	                                    bw.write(est.getNumeroTelefone() + ";" + est.getNomeCliente() + ";" + est.getDataCompra() + ";" + est.getEstadoCompra() + ";" + est.getMarcaViatura() + ";" + est.getModeloViatura() + ";" + est.getCodeViatura() + ";" + est.getCilindragem() + ";" + est.getPrecoViatura() + ";" + 'e' + ";" + est.getQtyViaturas() + ";" + 'e' + ";" + est.getNomeIGovernal() + ";" + est.getIncluirManutencao());
	                                }
	                                bw.newLine();
	                            }
	                            JOptionPane.showMessageDialog(null, "Status da viatura " + codigoViaturaStr + " alterado para: " + statusSelecionado + "\nDados salvos no arquivo.");
	                        
	                        }
	                        catch (IOException io) 
	                        {
	                            JOptionPane.showMessageDialog(null, "Erro de I/O ao salvar dados: " + io.getMessage(), "ERRO DE ARQUIVO", JOptionPane.ERROR_MESSAGE);
	                        }
	                    }
	                }
	                catch (NumberFormatException ex_format) 
	                {
	                    JOptionPane.showMessageDialog(null, "Código da viatura '" + codigoViaturaStr + "' é inválido. Deve ser um número inteiro.", "ERRO DE FORMATO", JOptionPane.ERROR_MESSAGE);
	                }
	            }
	        }
		);

        painelBotao.add(btnSalvar);
        painelPrincipal.add(painelBotao);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(520, 320);
        setLocationRelativeTo(null); // Centraliza a janela
        setVisible(true);
    }

    public class TratarEV implements ItemListener
    {
        public void itemStateChanged(ItemEvent e) 
        {
            JRadioButton rb = (JRadioButton) e.getItemSelectable();
            if (e.getStateChange() == ItemEvent.SELECTED) 
            {
                statusSelecionado = rb.getText();
            }
        }
    }

}
