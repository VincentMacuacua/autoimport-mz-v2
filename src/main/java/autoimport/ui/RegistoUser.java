package autoimport.ui;
import autoimport.util.*;
import autoimport.model.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*; //Para as bordas
import java.io.*;
import java.util.*;

public class RegistoUser extends JFrame 
{
	private Container cont;
    private JTextField campoNome;
    private JTextField campoPin,campoT;
    private JButton botaoSalvar;
    private JLabel nome,pin,numeroT;
    private ArrayList<Usuario> list;

    public RegistoUser(ArrayList<Usuario> list,JComboBox combo) 
    {
        super("Criar conta - AutoImport MZ");
        this.list = list;

        Container cont = getContentPane();
        cont.setLayout(new BorderLayout());
        cont.add(Tema.cabecalho("Criar conta", "Registe um novo utilizador do sistema"), BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(6,1,0,6));
        form.setBorder(BorderFactory.createEmptyBorder(16, 24, 8, 24));
        cont.add(form, BorderLayout.CENTER);

        nome=Tema.rotulo("Nome");
        form.add(nome);

        campoNome = new JTextField();
        campoNome.putClientProperty("JTextField.placeholderText", "Nome do utilizador");
        form.add(campoNome);

        pin=Tema.rotulo("Password");
        form.add(pin);

        campoPin = new JPasswordField();
        campoPin.putClientProperty("JTextField.placeholderText", "Minimo 4 caracteres");
        campoPin.putClientProperty("JPasswordField.showRevealButton", true);
        form.add(campoPin);

        numeroT=Tema.rotulo("Contacto");
        numeroT.setToolTipText("Introduz o numero de Telefone (9 numero )");
        form.add(numeroT);

        campoT=new JTextField(9);
        campoT.putClientProperty("JTextField.placeholderText", "Ex.: 841234567");
        form.add(campoT);


        botaoSalvar = Tema.botaoPrimario(new JButton("Criar conta"));
        botaoSalvar.setPreferredSize(new Dimension(150, 36));
        getRootPane().setDefaultButton(botaoSalvar);
        JPanel paneBotao = new JPanel(new FlowLayout(FlowLayout.RIGHT, 24, 16));
        paneBotao.add(botaoSalvar);
        cont.add(paneBotao, BorderLayout.SOUTH);

        botaoSalvar.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                String nome = campoNome.getText();
                String pin = campoPin.getText();
                String numeroT = campoT.getText();
                
                if (nome.isEmpty() || pin.isEmpty() || numeroT.isEmpty()) 
                {
                    JOptionPane.showMessageDialog(null, "Por favor, preencha nome, PIN e número de telefone.","E R R O", JOptionPane.WARNING_MESSAGE);
                    limparCampos();
                    return;
                }

                if (nome.length() < 2 || pin.length() < 4) 
                {
                    JOptionPane.showMessageDialog(null, "Nome deve ter pelo menos 2 caracteres e PIN pelo menos 4.","E R R O", JOptionPane.WARNING_MESSAGE);
                    limparCampos();
                    return;
                }

                if (numeroT.length() < 9 || numeroT.length() > 10)
                {
                    JOptionPane.showMessageDialog(null, "Número de telefone deve ter entre 9 e 10 dígitos.","E R R O", JOptionPane.WARNING_MESSAGE);
                    campoT.setText("");
                    return;
                }
                

                String sub=numeroT.substring(0, 2);
                if (!(sub.equals("82") || sub.equals("83") || sub.equals("84") || sub.equals("85") || sub.equals("86") || sub.equals("87"))) 
                {
                    JOptionPane.showMessageDialog(null, "Número de telefone deve começar com: 82, 83, 84, 85, 86 ou 87.","E R R O", JOptionPane.WARNING_MESSAGE);
                    campoT.setText("");
                    return;
                }
                
                else
                {
	                Usuario u = new Usuario();
	                String pinCifrado = PasswordUtil.hash(pin);

	                u.setNome(nome);
	                u.setPin(pinCifrado);
	                u.setNumeroTelefone(numeroT);

	                combo.addItem(nome);
	                list.add(u);

	                try
	                {
	            		FileWriter	fr=new FileWriter(ResourcePaths.dataFile("users.txt"), true);
	                	BufferedWriter br = new BufferedWriter(fr);

		        		br.write(nome + ";" + pinCifrado + ";" +numeroT);
		                br.newLine();
		                br.close();
	                }
	                catch(IOException ex) 
	                {
	                    JOptionPane.showMessageDialog(null, "Erro ao salvar no arquivo: " + ex.getMessage());
	                }
	                JOptionPane.showMessageDialog(null, "Usuário salvo com sucesso!");
	                dispose();  // Fecha a janela imediatamente após salvar
                }
            }
        });

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(420, 420);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }
    private void limparCampos()
    {
        campoNome.setText("");
        campoPin.setText("");
        campoT.setText("");
    }
}

