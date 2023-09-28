package br.com.projedata.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import br.com.projedata.model.Funcionario;

public class FuncionarioService {
	
	List<Funcionario> listaFuncionario = new ArrayList<Funcionario>();
	
	public String exibirFuncionarios() {
		StringBuilder listaFormatada = new StringBuilder();
		listaFormatada.append("----------------------------------------------------------------------------\n");
		listaFormatada.append("-----------------              Funcionários              -------------------\n");
		listaFormatada.append("----------------------------------------------------------------------------\n");
	    
		for (int i = 0; i < listaFuncionario.size(); i++) {
		    if (i > 0) {
		        listaFormatada.append("\n");
		    }

		    listaFormatada.append(listaFuncionario.get(i).exibirDadosOrganizadamente());
		}

		listaFormatada.append("\n----------------------------------------------------------------------------");
	    
	    return listaFormatada.toString();
	}
	
	public Map<String, List<Funcionario>> exibirPorFuncao() {
		Map<String, List<Funcionario>> mapPorFuncao = new HashMap<String, List<Funcionario>>();
		List<String> funcoes = new ArrayList<String>(); 
		
		for (Funcionario funcionario : listaFuncionario) {
			if (!funcoes.contains(funcionario.getFuncao())) {
				funcoes.add(funcionario.getFuncao());
			}
		}
		
		for (String funcao : funcoes) {
			mapPorFuncao.put(
				funcao, listaFuncionario.stream()
					.filter(
						f -> f.getFuncao()
							.equals(funcao)
					)
					.collect(Collectors.toList()
				)
			);			
		}
		
		return mapPorFuncao;
	}
	
	public List<Funcionario> exibirFuncionarioPorMes(int mes) {
		return listaFuncionario.stream()
			.filter(f -> f.getData_nascimento().getMonthValue() == mes)
			.collect(Collectors.toList());
	}
	
	public String exibirFuncionarioComMaiorIdade() {
		Funcionario funcionarioMaisVelho = null;
		int idadeMaisVelha = 0;
		
		for (Funcionario f : listaFuncionario) {
			LocalDate dataAtual = LocalDate.now();
			int idade = dataAtual.getYear() - f.getData_nascimento().getYear();
			 
			if (f.getData_nascimento().getMonthValue() > dataAtual.getMonthValue() ||
                (f.getData_nascimento().getMonthValue() == dataAtual.getMonthValue() &&
            		f.getData_nascimento().getDayOfMonth() > dataAtual.getDayOfMonth())) {
				idade--;
            }
			
			if (idade > idadeMaisVelha) {
                idadeMaisVelha = idade;
                funcionarioMaisVelho = f;
            }
		}
		
		return "Nome: " + funcionarioMaisVelho.getNome() + ", Idade: " + idadeMaisVelha + " anos";
	}
	
	public List<Funcionario> exibirListaOrdenada() {
		List<Funcionario> listaOrdenada = new ArrayList<Funcionario>();
		listaOrdenada.addAll(listaFuncionario);
		listaOrdenada.sort(Comparator.comparing(Funcionario::getNome));
		return listaOrdenada;
	}
	
	public String exibirSomaDosSalarios() {
		BigDecimal somaDosSalarios = BigDecimal.ZERO;
		NumberFormat formato = DecimalFormat.getInstance(new Locale("pt", "BR"));
		((DecimalFormat) formato).applyPattern("#,##0.00");
		
		for (Funcionario f : listaFuncionario) {
			somaDosSalarios = somaDosSalarios.add(f.getSalario());
		}
		
		return "O total dos salários dos funcionários é de: R$ " + formato.format(somaDosSalarios);
	}
	
	public void exibirQuantidadeDeSalariosMinimosPorFuncionario() {
	    BigDecimal salarioMinimo = new BigDecimal("1212.00");

	    for (Funcionario f : listaFuncionario) {
	        BigDecimal salarioFuncionario = f.getSalario();
	        BigDecimal salarioEmSalariosMinimos = salarioFuncionario.divide(salarioMinimo, 2, RoundingMode.HALF_UP);
	        System.out.println(f.getNome() + " ganha " + salarioEmSalariosMinimos + " salários mínimos");
	    }
	}
	
	public void adicionarFuncionario(Funcionario funcionario) {
		listaFuncionario.add(funcionario);
	}
	
	public void adicionarFuncionarios(List<Funcionario> funcionarios) {
		listaFuncionario.addAll(funcionarios);
	}
	
	public void atualizarDadosFuncionario(String nome, LocalDate data_nascimento, Funcionario funcionario) {
		Funcionario funcionarioNoSistema = listaFuncionario.stream()
			.filter(
					f -> f.getNome().equalsIgnoreCase(nome) &&
					f.getData_nascimento().equals(data_nascimento)
				)
			.findFirst().orElse(null);
			
			if(funcionario != null) {
				funcionarioNoSistema.setNome(
						funcionario.getNome() == null || funcionario.getNome().isEmpty() ? funcionarioNoSistema.getNome() : funcionario.getNome());
				funcionarioNoSistema.setData_nascimento(
						funcionario.getData_nascimento() == null ? funcionarioNoSistema.getData_nascimento() : funcionario.getData_nascimento());
				funcionarioNoSistema.setSalario(
						funcionario.getSalario() == null ? funcionarioNoSistema.getSalario() : funcionario.getSalario());
				funcionarioNoSistema.setFuncao(
						funcionario.getFuncao() == null || funcionario.getFuncao().isEmpty() ? funcionarioNoSistema.getFuncao() : funcionario.getFuncao());
				listaFuncionario.set(listaFuncionario.indexOf(funcionarioNoSistema), funcionarioNoSistema);
				System.out.println("Funcionário(a) " + funcionario.getNome() + " atualizado(a) com sucesso!");
			} else {
				System.out.println("Funcionário não encontrado!");
			}
	}
	
	public void atualizarSalarioDeTodos(BigDecimal valorAumento) {
		NumberFormat formatoPorcentagem = NumberFormat.getPercentInstance();
        String porcentagemFormatada = formatoPorcentagem.format(valorAumento.doubleValue() - 1.0);
        
		if(listaFuncionario.size() > 0) {
			listaFuncionario.forEach(f -> {
				f.setSalario(f.getSalario().multiply(valorAumento));
			});
			System.out.println("Aumento de todos os funcionários realizado com sucesso!");
			System.out.println("Foi realizado um aumento de: " + porcentagemFormatada + "!");
		} else {
			System.out.println("Não contém funcionários cadastrados.");
		}
	}
	
	public void atualizarSalarioDoFuncionario(BigDecimal valorAumento, String nome, LocalDate data_nascimento) {
		NumberFormat formatoPorcentagem = NumberFormat.getPercentInstance();
        String porcentagemFormatada = formatoPorcentagem.format(valorAumento.doubleValue() - 1.0);
		
		Funcionario funcionario = listaFuncionario.stream()
		.filter(
				f -> f.getNome().equalsIgnoreCase(nome) &&
				f.getData_nascimento().equals(data_nascimento)
			)
		.findFirst().orElse(null);
		
		if(funcionario != null) {
			funcionario.setSalario(funcionario.getSalario().multiply(valorAumento));
			listaFuncionario.set(listaFuncionario.indexOf(funcionario), funcionario);
			System.out.println("Salário do funcionário " + funcionario.getNome() + " atualizado com sucesso!");
			System.out.println("Foi realizado um aumento de: " + porcentagemFormatada + "!");
		} else {
			System.out.println("Funcionário não encontrado!");
		}
	}
	
	public boolean deletarFuncionario(String nome, LocalDate data_nascimento) {
		Funcionario funcionario = listaFuncionario.stream()
			.filter(
					f -> f.getNome().equalsIgnoreCase(nome) &&
					f.getData_nascimento().equals(data_nascimento)
				)
			.findFirst().get();
		
		if(listaFuncionario.contains(funcionario)) {
			listaFuncionario.remove(funcionario);
			System.out.println("Funcionário " + funcionario.getNome() + " removido com sucesso!");
			return true;
		} else {
			System.out.println("Funcionário não encontrado!");
			return false;
		}
	}

	public List<Funcionario> getListaFuncionario() {
		return listaFuncionario;
	}

}
