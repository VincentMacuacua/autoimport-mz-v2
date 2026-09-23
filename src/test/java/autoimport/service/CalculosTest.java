package autoimport.service;

import autoimport.model.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculosTest
{
	private static final float DELTA = 2.0f;

	private ArrayList<Cliente> listaMista()
	{
		ArrayList<Cliente> lista = new ArrayList<Cliente>();

		lista.add(new Normal(
			"841234567", "Joao Sithole", "10/03/2024", "Concluida",
			"Toyota", "Hilux", 12345, 2500, 100000,
			"Pessoal", "Sim", 5
		));
		lista.add(new Doutorado(
			"821234567", "Ana Cossa", "05/01/2024", "Concluida",
			"Honda", "Civic", 54321, 1500, 100000,
			"Profissional", "07/10/2020"
		));
		lista.add(new Estado(
			"861234567", "Ministerio X", "01/01/2024", "Concluida",
			"Toyota", "Land Cruiser", 11111, 3000, 200000,
			3, "Ministerio X", "Sim"
		));
		lista.add(new Revendedor(
			"871234567", "AutoDealer Lda", "01/01/2024", "Concluida",
			"Mazda", "CX-5", 22222, 1800, 150000,
			6, "AutoDealer Lda"
		));

		return lista;
	}

	@Test
	void acumuladoresPorTipoSoContamOSeuProprioTipo()
	{
		ArrayList<Cliente> lista = listaMista();
		Calculos cal = new Calculos();

		assertEquals(lista.get(0).valorFinal(), cal.acumulador_Normal(lista), DELTA);
		assertEquals(lista.get(1).valorFinal(), cal.acumulador_Doutorado(lista), DELTA);
		assertEquals(lista.get(2).valorFinal(), cal.acumulador_Estado(lista), DELTA);
		assertEquals(lista.get(3).valorFinal(), cal.acumulador_Revendedor(lista), DELTA);
	}

	@Test
	void acumuladorGeralSomaTodosOsClientes()
	{
		ArrayList<Cliente> lista = listaMista();
		Calculos cal = new Calculos();

		double esperado = lista.get(0).valorFinal() + lista.get(1).valorFinal()
			+ lista.get(2).valorFinal() + lista.get(3).valorFinal();

		assertEquals(esperado, cal.acumuladorGeral(lista), DELTA);
	}

	@Test
	void acumuladorAdoaneiroSomaDireitosDeTodosOsClientes()
	{
		ArrayList<Cliente> lista = listaMista();
		Calculos cal = new Calculos();

		float esperado = lista.get(0).direitosAdoaneiro() + lista.get(1).direitosAdoaneiro()
			+ lista.get(2).direitosAdoaneiro() + lista.get(3).direitosAdoaneiro();

		assertEquals(esperado, cal.acumuladorAdoaneiro(lista), DELTA);
	}

	@Test
	void calculoMargemSubtraiCustoFixoDaEmpresa()
	{
		Calculos cal = new Calculos();

		assertEquals(100000.0, cal.calculo_margem(2000000.0), 0.001);
	}
}
