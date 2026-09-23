package autoimport.ui;
import autoimport.util.*;
import autoimport.model.*;
import autoimport.service.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

  
public class Login extends JFrame
{
	private Container cont;
	private JLabel nameL, passL, forgotL;
	private JPasswordField passField;
	private JComboBox<String> combo;
	private String comboTitles[];
	private JButton signButton, logButton;
	private JCheckBox box;
	private ImageIcon loginImage, passImage, forgotImage,befIcon;
	private TitledBorder title;
	private JPanel panel;

	private ArrayList<Usuario> a;
	private Validacao val;
	private Pesquisa pes;

	public Login()
	{
		super("Log In");

		ImageIcon icon = new ImageIcon(ResourcePaths.imagem("rose_lilaz_escuro.jpeg"));
		panel = new JPanel()
		{
            protected void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };


        befIcon=new ImageIcon(ResourcePaths.imagem("progIcon.png"));
        setIconImage(befIcon.getImage());

		setContentPane(panel);
		cont = getContentPane();
		GridBagLayout g = new GridBagLayout();
		cont.setLayout(g);
		panel = new JPanel();
		panel.setLayout(g);

		a=new ArrayList<Usuario>();
		val=new Validacao();
		pes=new Pesquisa();
		lerusuario();

		GridBagConstraints grid = new GridBagConstraints();
		grid.insets = new Insets(10,10,10,10);

		panel.setBackground(new Color(0, 128, 128,150));
		panel.setPreferredSize(new Dimension(350,400));

		//Adicionando TitleBorder
		title = BorderFactory.createTitledBorder("AutoImport MZ");
		title.setTitleJustification(TitledBorder.CENTER);
		title.setTitleColor(new Color(0, 255, 204)/*Color.white*/);
		title.setTitleFont(new Font("Serif", Font.BOLD, 20));
		panel.setBorder(title);

		grid.gridx = 0;
		grid.gridy = 0;
		nameL = new JLabel("Nome:         ");
		nameL.setToolTipText("Seleciona o nome aulado");
		loginImage = new ImageIcon(ResourcePaths.imagem("login.jpg"));
		nameL.setIcon(loginImage);
		nameL.setForeground(Color.white);
		nameL.setBorder(BorderFactory.createLineBorder(new Color(0, 255, 204)));
		nameL.setFont(new Font("Dialog", Font.BOLD, 20));
		panel.add(nameL, grid);

		grid.gridx = 1;
		grid.gridy = 0;

		comboTitles =new String [a.size()];
		comboTitles=val.ternomes(a);

		combo = new JComboBox<String>(comboTitles);
		grid.fill = GridBagConstraints.HORIZONTAL;
		combo.setPreferredSize(new Dimension(250, combo.getPreferredSize().height));
		panel.add(combo, grid);

		grid.gridx = 0;
		grid.gridy = 1;
		passL = new JLabel("Password: ");
		passImage = new ImageIcon(ResourcePaths.imagem("passImage.jpg"));
		passL.setIcon(passImage);
		passL.setBorder(BorderFactory.createLineBorder(new Color(0, 255, 204)));
		passL.setFont(new Font("Dialog", Font.BOLD, 20));
		panel.add(passL, grid);

		grid.gridx = 1;
		grid.gridy = 1;

		passField = new JPasswordField("",300);
		panel.add(passField, grid);

		grid.gridx = 0;
		grid.gridy = 2;

		signButton = new JButton("Sign In");
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

		logButton = new JButton("Log In");
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
						JOptionPane.showMessageDialog(null,"A senha esta errada","E R R O",JOptionPane.ERROR_MESSAGE);
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
							JOptionPane.showMessageDialog(null,"A senha esta errada","E R R O",JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		);

		panel.add(signButton, grid);

		grid.gridx = 1;
		grid.gridy = 2;
		panel.add(logButton, grid);

		forgotL = new JLabel("I forgot the password");
		forgotImage = new ImageIcon(ResourcePaths.imagem("ForgotImage.jpg"));
		forgotL.setIcon(forgotImage);
		grid.gridx = 0;
		grid.gridy = 3;
		panel.add(forgotL,grid);

		box = new JCheckBox();
		box.addItemListener
		(
			new ItemListener()
			{
				public void itemStateChanged(ItemEvent iv)
				{
					if(iv.getStateChange()==ItemEvent.SELECTED)
					{
						new EditarConta(a);
						box.setSelected(false);
					}
				}
			}
		);
		grid.gridx = 1;
		grid.gridy = 3;
		panel.add(box, grid);

		nameL.setForeground(Color.white);
		passL.setForeground(Color.white);
		forgotL.setForeground(Color.white);
		cont.add(panel);

		cont.repaint();
		cont.revalidate();

		setSize(600,500);
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
			FileReader fr=new FileReader("data/users.txt");
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
			JOptionPane.showMessageDialog(null,"Nao existe o ficheiro de usuario !","E R R O",JOptionPane.ERROR_MESSAGE);
		}
		catch(IOException io)
		{
			JOptionPane.showMessageDialog(null,io.getMessage(),"E R R O",JOptionPane.ERROR_MESSAGE);
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
