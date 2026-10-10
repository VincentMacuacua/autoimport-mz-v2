package autoimport.persistence;
import autoimport.model.*;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.*;
import java.util.*;
import javax.swing.*;

public class ExportarComprasPDF 
{
    public void exportarParaPDF(ArrayList<Cliente> a, String caminhoSaida) 
    {
//        Document documento = new Document(PageSize.A2.rotate());
        Document documento = new Document();

        try 
        {
        	//Criacao do documento
            PdfWriter.getInstance(documento, new FileOutputStream(caminhoSaida));
            documento.open();
            //Colocacao do titulo no nosso documento
            Paragraph marca = new Paragraph("AutoImport MZ", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, MARINHO));
            documento.add(marca);
            Paragraph titulo = new Paragraph("Relatório de Compras (Entregas Concluídas ou em Trânsito)", FontFactory.getFont(FontFactory.HELVETICA, 11, TEXTO_SUAVE));
            titulo.setSpacingAfter(16);
            documento.add(titulo);

            // Criar tabelas para cada tipo de cliente
            //E colocando titulos na nossa tabela
            PdfPTable tabelaD = criarTabela
    		(
                new String[]
        		{
                    "Nome do cliente", "Numero do cliente", "Data de compra", "Estado de compra", "Marca da viatura",
                    "Modelo da viatura", "Codigo da viatura", "Cilindragem", "Preco da viatura", "Tipo da viatura",
                    "Data de conclusao do doutoramento"
                }
            );

            PdfPTable tabelaN = criarTabela
    		(
                new String[]
        		{
                    "Nome do cliente", "Numero do cliente", "Data de compra", "Estado de compra", "Marca da viatura",
                    "Modelo da viatura", "Codigo da viatura", "Cilindragem", "Preco da viatura", "Tipo da viatura",
                    "Esteve no estrangeiro", "Numero de anos fora"
                }
            );

            PdfPTable tabelaR = criarTabela
    		(
                new String[]
        		{
                    "Nome do cliente", "Numero do cliente", "Data de compra", "Estado de compra", "Marca da viatura",
                    "Modelo da viatura", "Codigo da viatura", "Cilindragem", "Preco da viatura", "Quantidade de viaturas",
                    "Nome Comercial"
                }
            );

            PdfPTable tabelaE = criarTabela
    		(
                new String[]
        		{
                    "Nome do cliente", "Numero do cliente", "Data de compra", "Estado de compra", "Marca da viatura",
                    "Modelo da viatura", "Codigo da viatura", "Cilindragem", "Preco da viatura", "Quantidade de viaturas",
                    "Nome da instituicao governamental", "Deseja incluir a manutencao"
                }
            );

            // Contadores para verificar se há dados em cada tabela
            int countD = Doutorado.ctDoutorado, countN = Normal.ctNormal, countR = Revendedor.ctRevendor, countE = Estado.ctEstado;

            // Processar cada cliente
            for (int i = 0; i < a.size(); i++) 
            {
                Cliente cliente = a.get(i);
                
                String estado = "";

                // Obter estado da compra
                if (cliente instanceof Doutorado) 
                {
                    estado = ((Doutorado) cliente).getEstadoCompra();
                }
                else if (cliente instanceof Normal) 
                {
                    estado = ((Normal) cliente).getEstadoCompra();
                }
                else if (cliente instanceof Revendedor) 
                {
                    estado = ((Revendedor) cliente).getEstadoCompra();
                }
                else if (cliente instanceof Estado) 
                {
                    estado = ((Estado) cliente).getEstadoCompra();
                }

                // Verificar se o estado é válido
                if (estado == null) 
                	continue;

                estado = estado.toLowerCase().trim();
                if (!(estado.equals("entrega concluida") || estado.equals("em transito"))) 
                    continue;

                // Adicionar dados às tabelas
                if (cliente instanceof Doutorado) 
                {
                    Doutorado d = (Doutorado) cliente;
                    adicionarDadosDoutorado(tabelaD, d);
                }
                else if (cliente instanceof Normal) 
                {
                    Normal n = (Normal) cliente;
                    adicionarDadosNormal(tabelaN, n);
                }
                else if (cliente instanceof Revendedor) 
                {
                    Revendedor r = (Revendedor) cliente;
                    adicionarDadosRevendedor(tabelaR, r);
                }
                else if (cliente instanceof Estado) 
                {
                    Estado e = (Estado) cliente;
                    adicionarDadosEstado(tabelaE, e);
                }
            }

            // Adicionar tabelas ao documento apenas se contiverem dados
            if (countD > 0) 
            {
                documento.add(tituloSeccao("Clientes Doutorados"));
                documento.add(tabelaD);
                documento.add(new Paragraph("\n"));
            }
            if (countN > 0) 
            {
                documento.add(tituloSeccao("Clientes Normais"));
                documento.add(tabelaN);
                documento.add(new Paragraph("\n"));
            }
            if (countR > 0) 
            {
                documento.add(tituloSeccao("Clientes Revendedores"));
                documento.add(tabelaR);
                documento.add(new Paragraph("\n"));
            }
            if (countE > 0) 
            {
                documento.add(tituloSeccao("Clientes do Estado"));
                documento.add(tabelaE);
                documento.add(new Paragraph("\n"));
            }

            // Verificar se há dados para exportar
            if (countD == 0 && countN == 0 && countR == 0 && countE == 0) 
            {
                documento.add(new Paragraph("Nenhum cliente com compras concluídas ou em trânsito foi encontrado."));
            }

            documento.close();
            JOptionPane.showMessageDialog(null,"PDF exportado com sucesso: " + caminhoSaida+"\nTotal de registros exportados: " + (countD + countN + countR + countE));
        }
        catch (Exception e) 
        {
            JOptionPane.showMessageDialog(null, "Erro ao exportar PDF: " + e.getMessage());
        }
    }
    //A funcao addCell somente aceita Strings, portanto se nos colocarmod null vai dar um erro
    //
    private void adicionarDadosDoutorado(PdfPTable tabela, Doutorado d) 
    {
        tabela.addCell(verificarNulo(d.getNomeCliente()));
        tabela.addCell(verificarNulo(d.getNumeroTelefone()));
        tabela.addCell(verificarNulo(d.getDataCompra()));
        tabela.addCell(verificarNulo(d.getEstadoCompra()));
        tabela.addCell(verificarNulo(d.getMarcaViatura()));
        tabela.addCell(verificarNulo(d.getModeloViatura()));
        tabela.addCell(String.valueOf(d.getCodeViatura()));
        tabela.addCell(String.valueOf(d.getCilindragem()));
        tabela.addCell(String.valueOf(d.getPrecoViatura()));
        tabela.addCell(verificarNulo(d.getTipoViatura()));
        tabela.addCell(verificarNulo(d.getDataCDoutoramento()));
    }

    private void adicionarDadosNormal(PdfPTable tabela, Normal n) 
    {
        tabela.addCell(verificarNulo(n.getNomeCliente()));
        tabela.addCell(verificarNulo(n.getNumeroTelefone()));
        tabela.addCell(verificarNulo(n.getDataCompra()));
        tabela.addCell(verificarNulo(n.getEstadoCompra()));
        tabela.addCell(verificarNulo(n.getMarcaViatura()));
        tabela.addCell(verificarNulo(n.getModeloViatura()));
        tabela.addCell(String.valueOf(n.getCodeViatura()));
        tabela.addCell(String.valueOf(n.getCilindragem()));
        tabela.addCell(String.valueOf(n.getPrecoViatura()));
        tabela.addCell(verificarNulo(n.getTipoViatura()));
        tabela.addCell(verificarNulo(n.getEstrangeiro()));
        tabela.addCell(String.valueOf(n.getNumeroAnosForaPais()));
    }

    private void adicionarDadosRevendedor(PdfPTable tabela, Revendedor r) 
    {
        tabela.addCell(verificarNulo(r.getNomeCliente()));
        tabela.addCell(verificarNulo(r.getNumeroTelefone()));
        tabela.addCell(verificarNulo(r.getDataCompra()));
        tabela.addCell(verificarNulo(r.getEstadoCompra()));
        tabela.addCell(verificarNulo(r.getMarcaViatura()));
        tabela.addCell(verificarNulo(r.getModeloViatura()));
        tabela.addCell(String.valueOf(r.getCodeViatura()));
        tabela.addCell(String.valueOf(r.getCilindragem()));
        tabela.addCell(String.valueOf(r.getPrecoViatura()));
        tabela.addCell(String.valueOf(r.getQtyViaturas()));
        tabela.addCell(verificarNulo(r.getNomeComercial()));
    }

    private void adicionarDadosEstado(PdfPTable tabela, Estado e)
    {
        tabela.addCell(verificarNulo(e.getNomeCliente()));
        tabela.addCell(verificarNulo(e.getNumeroTelefone()));
        tabela.addCell(verificarNulo(e.getDataCompra()));
        tabela.addCell(verificarNulo(e.getEstadoCompra()));
        tabela.addCell(verificarNulo(e.getMarcaViatura()));
        tabela.addCell(verificarNulo(e.getModeloViatura()));
        tabela.addCell(String.valueOf(e.getCodeViatura()));
        tabela.addCell(String.valueOf(e.getCilindragem()));
        tabela.addCell(String.valueOf(e.getPrecoViatura()));
        tabela.addCell(String.valueOf(e.getQtyViaturas()));
        tabela.addCell(verificarNulo(e.getNomeIGovernal()));
        tabela.addCell(verificarNulo(e.getIncluirManutencao()));
    }
    private String verificarNulo(String valor) 
    {
        if (valor != null) 
            return valor;
        return "";
    }

    // Mesmas cores de marca usadas na interface (ver autoimport.ui.Tema)
    private static final BaseColor MARINHO = new BaseColor(0x13, 0x38, 0x5C);
    private static final BaseColor TEXTO_SUAVE = new BaseColor(0x64, 0x74, 0x8B);

    private Paragraph tituloSeccao(String texto)
    {
        Paragraph p = new Paragraph(texto, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, MARINHO));
        p.setSpacingAfter(6);
        return p;
    }

    private PdfPTable criarTabela(String[] titulos) 
    {
        PdfPTable tabela = new PdfPTable(titulos.length);
        try 
        {
            tabela.setWidthPercentage(100);
            for (int i = 0; i < titulos.length; i++) 
            {
                String titulo = titulos[i];
                PdfPCell cell = new PdfPCell(new Phrase(titulo, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8, BaseColor.WHITE)));
                cell.setBackgroundColor(MARINHO);
                cell.setBorderColor(MARINHO);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPadding(5);
                tabela.addCell(cell);
            }
        } catch (Exception e) 
        {
            JOptionPane.showMessageDialog(null,"Erro ao criar tabela: " + e.getMessage());
        }
        return tabela;
    }
}
