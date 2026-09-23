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
    private ImageIcon loginImage, passImage,numeroTIcon;
    private ArrayList<Usuario> list;

    public RegistoUser(ArrayList<Usuario> list,JComboBox combo) 
    {
        super("Cadastro de Usuário");
        this.list = list;
        
        ImageIcon icon = new ImageIcon(ResourcePaths.imagem("bg2.jpg"));
        JPanel fundo = new JPanel()
        {
            protected void paintComponent(Graphics g) 
            {
                super.paintComponent(g);
                g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        
        fundo.setLayout(new GridLayout(4,2,10,10));
        setContentPane(fundo);
        Container cont = getContentPane();

        nome=new JLabel("Nome:");
        nome.setIcon(loginImage);
        nome.setFont(new Font("Dialog", Font.BOLD, 20));
        loginImage = new ImageIcon(ResourcePaths.imagem("login.jpg"));
        nome.setIcon(loginImage);
        cont.add(nome);
        
        campoNome = new JTextField();
        campoNome.setFont(new Font("Dialog", Font.BOLD, 20));
        cont.add(campoNome);
        
        pin=new JLabel("Password:");
        pin.setFont(new Font("Dialog", Font.BOLD, 20));
        passImage = new ImageIcon(ResourcePaths.imagem("passImage.jpg"));
		pin.setIcon(passImage);
        cont.add(pin);
        
        campoPin = new JTextField();
        campoPin.setFont(new Font("Dialog", Font.BOLD, 20));
        cont.add(campoPin);
        
        numeroT=new JLabel("Contacto:");
        numeroT.setToolTipText("Introduz o numero de Telefone (9 numero )");
        numeroT.setFont(new Font("Dialog",Font.BOLD,20));
        numeroTIcon=new ImageIcon(ResourcePaths.imagem("numerT.png"));
        numeroT.setIcon(numeroTIcon);
        cont.add(numeroT);
        
        campoT=new JTextField(9);
        campoT.setFont(new Font("Dialog",Font.BOLD,20));
        cont.add(campoT);
        

        botaoSalvar = new JButton("Salvar");
        botaoSalvar.repaint();
        cont.add(botaoSalvar);
        //cont.add(new JLabel("")); // Preenche o espaço vazio da grid

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
	            		FileWriter	fr=new FileWriter("data/users.txt", true);
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
        setSize(300, 150);
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

