package autoimport.ui;
import autoimport.util.*;
import autoimport.model.*;
import autoimport.service.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;


public class Login extends JFrame
{
	private Container cont;
	private JLabel nameL, passL;
	private JPasswordField passField;
	private JComboBox<String> combo;
	private String comboTitles[];
	private JButton signButton, logButton, forgotButton;
	private ImageIcon befIcon;
	private JPanel panel;

	private ArrayList<Usuario> a;
	private Validacao val;
	private Pesquisa pes;

	public Login()
	{
		super("AutoImport MZ - Iniciar sessao");

        befIcon=new ImageIcon(ResourcePaths.imagem("appIcon.png"));
        setIconImage(befIcon.getImage());

		setContentPane(Tema.fundoMarca());
		cont = getContentPane();
		cont.setLayout(new GridBagLayout());

		a=new ArrayList<Usuario>();
		val=new Validacao();
		pes=new Pesquisa();
		lerusuario();

		// Cartao central com o formulario
		panel = Tema.cartao(new GridBagLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(32, 36, 28, 36));
		panel.setPreferredSize(new Dimension(380, 440));

		GridBagConstraints grid = new GridBagConstraints();
		grid.gridx = 0;
		grid.gridwidth = 2;
		grid.fill = GridBagConstraints.HORIZONTAL;
		grid.weightx = 1;

		JLabel marca = new JLabel("AutoImport MZ");
		marca.setFont(Tema.fonte(Font.BOLD, 24f));
		marca.setForeground(Tema.MARINHO);
		grid.gridy = 0;
		panel.add(marca, grid);

		JLabel sub = Tema.textoSuave("Inicie sessao para continuar");
		grid.gridy = 1;
		grid.insets = new Insets(4, 0, 24, 0);
		panel.add(sub, grid);

		nameL = Tema.rotulo("Utilizador");
		nameL.setToolTipText("Seleciona o nome do utilizador");
		grid.gridy = 2;
		grid.insets = new Insets(0, 0, 6, 0);
		panel.add(nameL, grid);

		comboTitles =new String [a.size()];
		comboTitles=val.ternomes(a);

		combo = new JComboBox<String>(comboTitles);
		combo.setPreferredSize(new Dimension(250, 36));
		grid.gridy = 3;
		grid.insets = new Insets(0, 0, 16, 0);
		panel.add(combo, grid);

		passL = Tema.rotulo("Password");
		grid.gridy = 4;
		grid.insets = new Insets(0, 0, 6, 0);
		panel.add(passL, grid);

		passField = new JPasswordField();
		passField.setPreferredSize(new Dimension(250, 36));
		passField.putClientProperty("JTextField.placeholderText", "Introduza a sua password");
		passField.putClientProperty("JPasswordField.showRevealButton", true);
		grid.gridy = 5;
		grid.insets = new Insets(0, 0, 24, 0);
		panel.add(passField, grid);

		signButton = Tema.botaoSecundario(new JButton("Criar conta"));
		signButton.addActionListener
		(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent ev)
				{
					new RegistoUser(a,combo);
				}
			}
		);

		logButton = Tema.botaoPrimario(new JButton("Entrar"));
		logButton.addActionListener
		(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent ev)
				{
					String pass,passValidar;
					int poz,nomeUser;

					pass=String.valueOf(passField.getPassword());
					passValidar=val.terPass(a,pass);
					poz=pes.posicaoPass(a,pass);
					nomeUser=combo.getSelectedIndex();

					if(poz==-1)
					{
						JOptionPane.showMessageDialog(Login.this,"A senha esta errada","Erro",JOptionPane.ERROR_MESSAGE);
						passField.setText("");
					}
					else
					{
						if(nomeUser==poz)
						{
							new Menu(a,passValidar);
							dispose();
						}
						else
							JOptionPane.showMessageDialog(Login.this,"A senha esta errada","Erro",JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		);
		logButton.setPreferredSize(new Dimension(250, 38));
		getRootPane().setDefaultButton(logButton);
		grid.gridy = 6;
		grid.insets = new Insets(0, 0, 10, 0);
		panel.add(logButton, grid);

		signButton.setPreferredSize(new Dimension(250, 38));
		grid.gridy = 7;
		grid.insets = new Insets(0, 0, 10, 0);
		panel.add(signButton, grid);

		forgotButton = Tema.botaoLink(new JButton("Esqueci a password"));
		forgotButton.addActionListener
		(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent ev)
				{
					new EditarConta(a);
				}
			}
		);
		grid.gridy = 8;
		grid.fill = GridBagConstraints.NONE;
		grid.insets = new Insets(0, 0, 0, 0);
		panel.add(forgotButton, grid);

		cont.add(panel);

		setSize(600,560);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
	}

	public void lerusuario()
	{
		String linha,nome,pin,numeroTelefone;
		StringTokenizer st;

		try
		{
			FileReader fr=new FileReader(ResourcePaths.dataFile("users.txt"));
			BufferedReader br=new BufferedReader(fr);

			linha=br.readLine();
			while(linha!=null)
			{
				st=new StringTokenizer(linha,";");

				nome=st.nextToken();
				pin=st.nextToken();
				numeroTelefone=st.nextToken();
				objusuario(nome,pin,numeroTelefone);

				linha=br.readLine();
			}
			a.trimToSize();
			br.close();

		}
		catch(FileNotFoundException fn)
		{
			JOptionPane.showMessageDialog(null,"Nao existe o ficheiro de usuario !","Erro",JOptionPane.ERROR_MESSAGE);
		}
		catch(IOException io)
		{
			JOptionPane.showMessageDialog(null,io.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE);
		}
	}
	public void objusuario(String nome,String pin,String numeroTelefone)
	{
		Usuario u=new Usuario();

		u.setNome(nome);
		u.setPin(pin);
		u.setNumeroTelefone(numeroTelefone);

		a.add(u);
	}

}
