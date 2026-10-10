package autoimport.ui;
import autoimport.util.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*; // Importa a classe Container
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class SeletorVideoMov extends JFrame
{

    private JButton botaoSelecionarVideo;
    private JLabel labelCaminhoVideo;
    private File arquivoVideoSelecionado;
    private Container cont; // Declara o Container

    public SeletorVideoMov() 
    {
        super("Seletor e Player de Video (.mov)");
        cont = getContentPane();
        criarGUI();
    }

    private void criarGUI() 
    {
        

        cont.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Layout simples

        // Label para mostrar o caminho do vídeo
        labelCaminhoVideo = Tema.textoSuave("Nenhum video selecionado.");
        labelCaminhoVideo.setPreferredSize(new Dimension(450, 25));

        // Botão para selecionar o vídeo
        botaoSelecionarVideo = Tema.botaoSecundario(new JButton("Selecionar video (.mov)"));
        botaoSelecionarVideo.addActionListener
        (
        	new ActionListener() 
	        {
	            public void actionPerformed(ActionEvent e) 
	            {
	                selecionarArquivoVideo();
	            }
	        }
    	);

        // Botão para "assistir" o vídeo (abrir com player padrão)
        JButton botaoAssistirVideo = Tema.botaoPrimario(new JButton("Assistir video"));
        botaoAssistirVideo.addActionListener
        (
        	new ActionListener() 
        	{
	            public void actionPerformed(ActionEvent e) 
	            {
	                assistirVideo();
	            }
        	}
    	);

        // Adicionar componentes ao container (content pane)
        cont.add(botaoSelecionarVideo);
        cont.add(labelCaminhoVideo);
        cont.add(botaoAssistirVideo);

        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(520, 170);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void selecionarArquivoVideo() 
    {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecione um arquivo de video .mov");

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivos de Video MOV", "mov");
        fileChooser.setFileFilter(filter);
        fileChooser.setAcceptAllFileFilterUsed(false);

        // 'this' agora se refere à instância de JFrame, que é um Component válido para o diálogo
        int resultado = fileChooser.showOpenDialog(this);

        if (resultado == JFileChooser.APPROVE_OPTION) 
        {
            arquivoVideoSelecionado = fileChooser.getSelectedFile();
            labelCaminhoVideo.setText("Selecionado: " + arquivoVideoSelecionado.getAbsolutePath());
            System.out.println("Video selecionado: " + arquivoVideoSelecionado.getAbsolutePath());
        }
        else
        {
            labelCaminhoVideo.setText("Selecao cancelada.");
            arquivoVideoSelecionado = null;
        }
    }

    private void assistirVideo() 
    {
        if (arquivoVideoSelecionado != null && arquivoVideoSelecionado.exists()) 
        {
            try 
            {
                if (Desktop.isDesktopSupported()) 
                {
                    Desktop desktop = Desktop.getDesktop();
                    if (desktop.isSupported(Desktop.Action.OPEN)) 
                    {
                        desktop.open(arquivoVideoSelecionado);
                    }
                    else 
                    {
                    	 // 'this' é o componente pai
                        JOptionPane.showMessageDialog(this,"A acao 'Abrir' nao e suportada neste sistema.","Erro ao Abrir", JOptionPane.ERROR_MESSAGE);
                    }
                } 
                else
                {
                	 // 'this' é o componente pai
                    JOptionPane.showMessageDialog(this,"A classe Desktop nao e suportada neste sistema.","Erro de Sistema", JOptionPane.ERROR_MESSAGE);
                }
            } 
            catch (IOException ex) 
            {
            	// 'this' é o componente pai
                JOptionPane.showMessageDialog(this, "Erro ao tentar abrir o video: " + ex.getMessage(),"Erro de IO", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
            catch (IllegalArgumentException ex) 
            {
            	// 'this' é o componente pai
                 JOptionPane.showMessageDialog(this, "Nenhum arquivo selecionado ou o arquivo nao existe.","Erro de Arquivo", JOptionPane.ERROR_MESSAGE);
            }
        } 
        else 
        {
        	 // 'this' é o componente pai
            JOptionPane.showMessageDialog(this,"Por favor, selecione um arquivo de video primeiro.","Nenhum Video", JOptionPane.WARNING_MESSAGE);
        }
    }
}
