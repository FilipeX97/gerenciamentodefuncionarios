package br.com.projedata.main;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

import br.com.projedata.model.Funcionario;
import br.com.projedata.service.FuncionarioService;

public class GerenciamentoApplication {

	public static void main(String[] args) {
		
		FuncionarioService fs = new FuncionarioService();
		
		System.out.println("Cadastrando funcionários...");
		fs.adicionarFuncionarios(Arrays.asList(
				new Funcionario("Maria", LocalDate.parse("2000-10-18"), new BigDecimal(2009.44), "Operador"),
				new Funcionario("João", LocalDate.parse("1990-05-12"), new BigDecimal(2284.38), "Operador"),
				new Funcionario("Caio", LocalDate.parse("1961-05-02"), new BigDecimal(9836.14), "Coordenador"),
				new Funcionario("Miguel", LocalDate.parse("1988-10-14"), new BigDecimal(19119.88), "Diretor"),
				new Funcionario("Alice", LocalDate.parse("1995-01-05"), new BigDecimal(2234.68), "Recepcionista"),
				new Funcionario("Heitor", LocalDate.parse("1999-11-19"), new BigDecimal(1582.72), "Operador"),
				new Funcionario("Arthur", LocalDate.parse("1993-03-31"), new BigDecimal(4071.84), "Contador"),
				new Funcionario("Laura", LocalDate.parse("1994-07-08"), new BigDecimal(3017.45), "Gerente"),
				new Funcionario("Heloísa", LocalDate.parse("2003-05-24"), new BigDecimal(1606.85), "Eletricista"),
				new Funcionario("Helena", LocalDate.parse("1996-09-02"), new BigDecimal(2799.93), "Gerente")
		));
		
		System.out.println("Funcionários cadastrados com sucesso, inserindo um total de " + 
				fs.getListaFuncionario().size() + " funcionários!");
		
		System.out.println("Removendo funcionário João...");
		fs.deletarFuncionario("João", LocalDate.parse("1990-05-12"));
		
		System.out.println("Exibindo lista dos funcionários:");
		System.out.println(fs.exibirFuncionarios());
		
		System.out.println("Atualizando todos os salários dos funcionários...");
		fs.atualizarSalarioDeTodos(new BigDecimal(1.1));

		System.out.println("Agrupando os funcionários por função...");
		System.out.println(fs.exibirPorFuncao());
		
		System.out.println("Exibindo os funcionários que fazem aniversário no mês 10:");
		System.out.println(fs.exibirFuncionarioPorMes(10));
		
		System.out.println("Exibindo os funcionários que fazem aniversário no mês 12:");
		System.out.println(fs.exibirFuncionarioPorMes(12));
		
		System.out.println("Exibindo o funcionário com maior idade:");
		System.out.println(fs.exibirFuncionarioComMaiorIdade());
		
		System.out.println("Exibindo lista de funcionários em ordem alfabética:");
		System.out.println(fs.exibirListaOrdenada());
		
		System.out.println("Exibindo total dos salários dos funcionários(as):");
		System.out.println(fs.exibirSomaDosSalarios());
		
		System.out.println("Exibindo quantidade de salários mínimos de cada funcionário(a):");
		fs.exibirQuantidadeDeSalariosMinimosPorFuncionario();
		
	}

}
