package autoimport.ui;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 * Paleta de cores, tipografia e componentes reutilizaveis da aplicacao.
 * Todas as janelas devem usar estas constantes em vez de cores soltas,
 * para que o aspeto visual seja consistente e facil de alterar num so sitio.
 */
public final class Tema
{
	private Tema() {}

	// Marca
	public static final Color MARINHO_ESCURO = new Color(0x0B1F33);
	public static final Color MARINHO        = new Color(0x13385C);
	public static final Color DOURADO        = new Color(0xE8A33D);

	// Acao
	public static final Color PRIMARIA        = new Color(0x2563EB);
	public static final Color PRIMARIA_HOVER  = new Color(0x1D4ED8);
	public static final Color PRIMARIA_SUAVE  = new Color(0xDBEAFE);

	// Superficies
	public static final Color FUNDO      = new Color(0xF4F6F9);
	public static final Color SUPERFICIE = Color.WHITE;
	public static final Color BORDA      = new Color(0xE2E8F0);

	// Texto
	public static final Color TEXTO          = new Color(0x0F172A);
	public static final Color TEXTO_SUAVE    = new Color(0x64748B);
	public static final Color TEXTO_INVERSO  = Color.WHITE;
	public static final Color TEXTO_INVERSO_SUAVE = new Color(0xA9BCD0);

	// Estados
	public static final Color SUCESSO = new Color(0x15803D);
	public static final Color PERIGO  = new Color(0xDC2626);
	public static final Color AVISO   = new Color(0xB45309);

	/** Instala o FlatLaf com a paleta da AutoImport MZ. Chamar antes de criar qualquer janela. */
	public static void instalar()
	{
		Map<String, String> extras = new HashMap<>();
		extras.put("@accentColor", hex(PRIMARIA));
		extras.put("@background", hex(FUNDO));
		extras.put("@foreground", hex(TEXTO));
		FlatLaf.setGlobalExtraDefaults(extras);

		FlatLightLaf.setup();

		UIManager.put("Component.arc", 8);
		UIManager.put("Button.arc", 8);
		UIManager.put("TextComponent.arc", 8);
		UIManager.put("Component.focusWidth", 1);
		UIManager.put("Component.innerFocusWidth", 0);
		UIManager.put("ScrollBar.thumbArc", 999);
		UIManager.put("ScrollBar.thumbInsets", new Insets(2, 2, 2, 2));
		UIManager.put("TitledBorder.titleColor", TEXTO_SUAVE);

		UIManager.put("Table.rowHeight", 30);
		UIManager.put("Table.showHorizontalLines", true);
		UIManager.put("Table.showVerticalLines", false);
		UIManager.put("Table.gridColor", BORDA);
		UIManager.put("Table.alternateRowColor", new Color(0xF8FAFC));
		UIManager.put("Table.selectionBackground", PRIMARIA_SUAVE);
		UIManager.put("Table.selectionForeground", TEXTO);
		UIManager.put("Table.intercellSpacing", new Dimension(0, 1));
		UIManager.put("TableHeader.background", new Color(0xF1F5F9));
		UIManager.put("TableHeader.foreground", TEXTO_SUAVE);
		UIManager.put("TableHeader.height", 34);
		UIManager.put("TableHeader.separatorColor", BORDA);
		UIManager.put("TableHeader.bottomSeparatorColor", BORDA);

		UIManager.put("TabbedPane.selectedForeground", PRIMARIA);
		UIManager.put("TabbedPane.underlineColor", PRIMARIA);
		UIManager.put("TabbedPane.tabHeight", 40);
		UIManager.put("TabbedPane.showTabSeparators", false);

		UIManager.put("MenuBar.borderColor", BORDA);
		UIManager.put("PopupMenu.borderCornerRadius", 8);
	}

	// ---------- Tipografia ----------

	public static Font fonte(int estilo, float tamanho)
	{
		Font base = UIManager.getFont("defaultFont");
		if (base == null)
			base = new Font(Font.SANS_SERIF, Font.PLAIN, 13);
		return base.deriveFont(estilo, tamanho);
	}

	public static JLabel titulo(String texto)
	{
		JLabel l = new JLabel(texto);
		l.setFont(fonte(Font.BOLD, 22f));
		l.setForeground(TEXTO);
		return l;
	}

	public static JLabel rotulo(String texto)
	{
		JLabel l = new JLabel(texto);
		l.setFont(fonte(Font.BOLD, 13f));
		l.setForeground(TEXTO);
		return l;
	}

	public static JLabel textoSuave(String texto)
	{
		JLabel l = new JLabel(texto);
		l.setFont(fonte(Font.PLAIN, 13f));
		l.setForeground(TEXTO_SUAVE);
		return l;
	}

	// ---------- Botoes ----------

	public static JButton botaoPrimario(JButton b)
	{
		b.putClientProperty("FlatLaf.style",
			"background: " + hex(PRIMARIA) + "; foreground: #ffffff; hoverBackground: " + hex(PRIMARIA_HOVER)
			+ "; pressedBackground: #1E40AF; borderWidth: 0; focusWidth: 0; font: bold");
		b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		return b;
	}

	public static JButton botaoSecundario(JButton b)
	{
		b.putClientProperty("FlatLaf.style",
			"background: #ffffff; foreground: " + hex(TEXTO) + "; borderColor: " + hex(BORDA) + "; focusWidth: 0");
		b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		return b;
	}

	public static JButton botaoPerigo(JButton b)
	{
		b.putClientProperty("FlatLaf.style",
			"background: " + hex(PERIGO) + "; foreground: #ffffff; hoverBackground: #B91C1C"
			+ "; pressedBackground: #991B1B; borderWidth: 0; focusWidth: 0; font: bold");
		b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		return b;
	}

	/** Botao sem fundo, com aspeto de hiperligacao. */
	public static JButton botaoLink(JButton b)
	{
		b.putClientProperty("JButton.buttonType", "borderless");
		b.setForeground(PRIMARIA);
		b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		return b;
	}

	// ---------- Paineis ----------

	/** Fundo de marca: gradiente azul-marinho, usado no login e no ecra inicial. */
	public static JPanel fundoMarca()
	{
		return fundoGradiente(MARINHO, MARINHO_ESCURO);
	}

	public static JPanel fundoGradiente(Color topo, Color base)
	{
		return new JPanel()
		{
			private Color c1 = topo, c2 = base;

			protected void paintComponent(Graphics g)
			{
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setPaint(new GradientPaint(0, 0, c1, getWidth(), getHeight(), c2));
				g2.fillRect(0, 0, getWidth(), getHeight());
				g2.dispose();
			}

			public void setBackground(Color cor)
			{
				// Permite mudar o tema do ecra inicial: o gradiente parte da cor dada
				super.setBackground(cor);
				if (c1 != null && !(cor instanceof javax.swing.plaf.UIResource))
				{
					c1 = cor;
					c2 = cor.darker().darker();
					repaint();
				}
			}
		};
	}

	/** Cartao branco com borda subtil e margem interior. */
	public static JPanel cartao(LayoutManager layout)
	{
		JPanel p = new JPanel(layout);
		p.setBackground(SUPERFICIE);
		p.setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createLineBorder(BORDA),
			BorderFactory.createEmptyBorder(20, 24, 20, 24)));
		return p;
	}

	/** Barra de titulo no topo de cada janela: titulo + subtitulo opcional. */
	public static JPanel cabecalho(String titulo, String subtitulo)
	{
		JPanel p = new JPanel(new BorderLayout());
		p.setBackground(SUPERFICIE);
		p.setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createMatteBorder(0, 0, 1, 0, BORDA),
			BorderFactory.createEmptyBorder(18, 24, 16, 24)));

		JLabel t = new JLabel(titulo);
		t.setFont(fonte(Font.BOLD, 20f));
		t.setForeground(TEXTO);
		p.add(t, BorderLayout.NORTH);

		if (subtitulo != null)
		{
			JLabel s = textoSuave(subtitulo);
			s.setBorder(BorderFactory.createEmptyBorder(4, 0, 0, 0));
			p.add(s, BorderLayout.CENTER);
		}
		return p;
	}

	/** Borda de seccao para formularios (titulo discreto + linha). */
	public static Border seccao(String titulo)
	{
		TitledBorder t = BorderFactory.createTitledBorder(
			BorderFactory.createMatteBorder(1, 0, 0, 0, BORDA), titulo);
		t.setTitleFont(fonte(Font.BOLD, 13f));
		t.setTitleColor(PRIMARIA);
		return BorderFactory.createCompoundBorder(
			BorderFactory.createEmptyBorder(8, 0, 8, 0),
			BorderFactory.createCompoundBorder(t, BorderFactory.createEmptyBorder(8, 4, 4, 4)));
	}

	/** Cartao de indicador (KPI): rotulo pequeno em cima, valor grande em baixo. */
	public static JPanel cartaoIndicador(String rotulo, String valor, Color corValor)
	{
		// Rotulo e valor ficam juntos no topo do cartao, mesmo quando o cartao e alto
		JPanel p = cartao(new BorderLayout());
		JPanel conteudo = new JPanel(new BorderLayout(0, 8));
		conteudo.setOpaque(false);
		JLabel r = textoSuave(rotulo.toUpperCase());
		r.setFont(fonte(Font.BOLD, 11f));
		JLabel v = new JLabel(valor);
		v.setFont(fonte(Font.BOLD, 24f));
		v.setForeground(corValor);
		conteudo.add(r, BorderLayout.NORTH);
		conteudo.add(v, BorderLayout.CENTER);
		p.add(conteudo, BorderLayout.NORTH);
		return p;
	}

	// ---------- Tabelas ----------

	/** Tabela so de leitura, com o estilo da aplicacao. */
	public static JTable tabela(Object[][] dados, Object[] colunas)
	{
		JTable t = new JTable(modeloLeitura(dados, colunas));
		t.setFillsViewportHeight(true);
		t.setFocusable(false);
		t.setRowSelectionAllowed(true);
		JTableHeader h = t.getTableHeader();
		h.setFont(fonte(Font.BOLD, 12f));
		h.setReorderingAllowed(false);
		return t;
	}

	public static DefaultTableModel modeloLeitura(Object[][] dados, Object[] colunas)
	{
		return new DefaultTableModel(dados, colunas)
		{
			public boolean isCellEditable(int linha, int coluna)
			{
				return false;
			}
		};
	}

	public static JScrollPane rolagem(Component c)
	{
		JScrollPane s = new JScrollPane(c);
		s.setBorder(BorderFactory.createLineBorder(BORDA));
		s.getViewport().setBackground(SUPERFICIE);
		return s;
	}

	private static String hex(Color c)
	{
		return String.format("#%02x%02x%02x", c.getRed(), c.getGreen(), c.getBlue());
	}
}
