package autoimport.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClienteTaxCalculationTest
{
	private static final float DELTA = 2.0f;

	@Test
	void normalComCilindragemAltaEEstrangeiroElegivelAplicaDesconto()
	{
		Normal normal = new Normal(
			"841234567", "Joao Sithole", "10/03/2024", "Concluida",
			"Toyota", "Hilux", 12345, 2500, 100000,
			"Pessoal", "Sim", 5
		);

		assertEquals(25000f, normal.direitosAdoaneiro(), DELTA);
		assertEquals(125000f, normal.direitosAdoaneiroTotal(), DELTA);
		assertEquals(137500f, normal.imposto(), DELTA);
		assertEquals(159500f, normal.iva(), DELTA);
		assertEquals(19785f, normal.valorFinal(), DELTA);
	}

	@Test
	void normalNaoEstrangeiroNaoRecebeDesconto()
	{
		Normal normal = new Normal(
			"841234567", "Joao Sithole", "10/03/2024", "Concluida",
			"Toyota", "Hilux", 12345, 2500, 100000,
			"Pessoal", "Nao", 5
		);

		// sem desconto: desconto() == iva()
		assertEquals(normal.iva() + normal.valor_Tax_Fixa(), normal.valorFinal(), DELTA);
	}

	@Test
	void doutoradoCalculaImpostoComCilindragemBaixa()
	{
		Doutorado doutorado = new Doutorado(
			"821234567", "Ana Cossa", "05/01/2024", "Concluida",
			"Honda", "Civic", 54321, 1500, 100000,
			"Profissional", "07/10/2020"
		);

		assertEquals(15000f, doutorado.direitosAdoaneiro(), DELTA);
		assertEquals(115000f, doutorado.direitosAdoaneiroTotal(), DELTA);
		assertEquals(133400f, doutorado.iva(), DELTA);
		assertEquals(148400f, doutorado.valorFinal(), DELTA);
	}

	@Test
	void estadoValorFinalDependeDaQuantidadeDeViaturas()
	{
		Estado estado = new Estado(
			"861234567", "Ministerio X", "01/01/2024", "Concluida",
			"Toyota", "Land Cruiser", 11111, 3000, 200000,
			3, "Ministerio X", "Sim"
		);

		assertEquals(245000f, estado.valorFinal(), DELTA);
	}

	@Test
	void revendedorComCincoOuMaisViaturasRecebeDescontoDeCincoPorCento()
	{
		Revendedor revendedor = new Revendedor(
			"871234567", "AutoDealer Lda", "01/01/2024", "Concluida",
			"Mazda", "CX-5", 22222, 1800, 150000,
			6, "AutoDealer Lda"
		);

		assertEquals(22500f, revendedor.direitosAdoaneiro(), DELTA);
		assertEquals(186300f, revendedor.iva(), DELTA);
		assertEquals(1076910f, revendedor.valorFinal(), DELTA);
	}
}
