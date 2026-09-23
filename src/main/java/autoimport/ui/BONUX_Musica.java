package autoimport.ui;
import autoimport.util.*;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class BONUX_Musica extends JFrame
{
    private Clip clip;
    private ImageIcon pause, play, exit, icon;
    private JButton playButton, stopButton, exitButton;
    private Container cont;
    private JPanel panel;

    public BONUX_Musica() 
    {
        super("Tocador de Música");

        // Imagem de fundo
        icon = new ImageIcon(ResourcePaths.imagem("rose_lilaz_escuro.jpeg"));
        panel = new JPanel() 
        {
            protected void paintComponent(Graphics g) 
            {
                super.paintComponent(g);
                g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        setContentPane(panel);
        cont = getContentPane();

        
        // Cria o menu
        criarMenu();
        
        // Botão Play
        criarButton();

        // Seleciona uma música ao iniciar
        escolherECarregarAudio();

        // Configura a janela
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    public void criarButton()
    {
    	playButton = new JButton("");
        play = new ImageIcon(ResourcePaths.imagem("play.jpg"));
        playButton.setIcon(play);
        playButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (clip != null) 
                {
                    clip.setFramePosition(0);
                    clip.start();
                }
            }
        });

        // Botão Pause
        stopButton = new JButton("");
        pause = new ImageIcon(ResourcePaths.imagem("pause.png"));
        stopButton.setIcon(pause);
        stopButton.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                if (clip != null && clip.isRunning()) 
                {
                    clip.stop();
                }
            }
        });

        // Botão Sair
        exitButton = new JButton("");
        exit = new ImageIcon(ResourcePaths.imagem("exitmusic.png"));
        exitButton.setIcon(exit);
        exitButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) 
            {
                if (clip != null)
                {
                    clip.stop();
                    clip.close();
                }
                dispose();
            }
        });

        // Painel com botões
        JPanel panelButtons = new JPanel();
        panelButtons.setOpaque(false);
        panelButtons.setLayout(new FlowLayout());
        panelButtons.add(playButton);
        panelButtons.add(stopButton);
        panelButtons.add(exitButton);
        cont.add(panelButtons, BorderLayout.CENTER);
    }
    private void criarMenu() 
    {
        JMenuBar bar = new JMenuBar();
        JMenu arquivo = new JMenu("Arquivo");

        JMenuItem abrirFile = new JMenuItem("Abrir musica");
        abrirFile.addActionListener
        (
    		new ActionListener() 
	        {
	            public void actionPerformed(ActionEvent e) 
	            {
	                escolherECarregarAudio();
	            }
	        }
		);

        JMenuItem sairItem = new JMenuItem("Fechar");
        sairItem.addActionListener
        (
    		new ActionListener() 
	        {
	            public void actionPerformed(ActionEvent e) 
	            {
	                if (clip != null) 
	                {
	                    clip.stop();
	                    clip.close();
	                }
	                dispose();
	            }
	        }
		);

        arquivo.add(abrirFile);
        arquivo.addSeparator();
        arquivo.add(sairItem);

        bar.add(arquivo);
        setJMenuBar(bar);
    }

    private void escolherECarregarAudio() 
    {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecione uma musica (.wav)");

        FileNameExtensionFilter filtroWav = new FileNameExtensionFilter("Arquivos WAV", "wav");
        fileChooser.setFileFilter(filtroWav);

        int userSelection = fileChooser.showOpenDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION)
        {
            File audioFile = fileChooser.getSelectedFile();
            try 
            {
                if (clip != null && clip.isRunning()) 
                {
                    clip.stop();
                }
                if (clip != null) 
                {
                    clip.close();
                }

                AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
                clip = AudioSystem.getClip();
                clip.open(audioStream);
                setTitle("Tocando: " + audioFile.getName());
            } 
            catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) 
            {
                JOptionPane.showMessageDialog(this, "Erro ao carregar o áudio:\n" + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
//    public static void main(String[]args)
//    {
//    	new BONUX_Musica();
//    }
}
