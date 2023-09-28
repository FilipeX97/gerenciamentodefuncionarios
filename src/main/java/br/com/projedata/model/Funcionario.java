package br.com.projedata.model;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Funcionario extends Pessoa {

    private BigDecimal salario;
    private String funcao;
    
	public String exibirDadosOrganizadamente() {
		NumberFormat formato = DecimalFormat.getInstance(new Locale("pt", "BR"));
		((DecimalFormat) formato).applyPattern("#,##0.00");
		
		return "Funcionário(a) " + getNome() + ":\n"
				+ "Data de Nascimento: " + getData_nascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + ", "
				+ "Salário: R$ " + formato.format(getSalario()) + ", "
				+ "Função: " + getFuncao();
	}
	
	@Override
	public String toString() {
		NumberFormat formato = DecimalFormat.getInstance(new Locale("pt", "BR"));
		((DecimalFormat) formato).applyPattern("#,##0.00");
		
		return "Nome=" + super.getNome() + "; "
			+ "Data de Nascimento=" + getData_nascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "; "
			+ "Salário=" + formato.format(getSalario()) + "; "
			+ "Função=" + getFuncao();
	}
	
	public Funcionario() {}

	public Funcionario(String nome, LocalDate dataNascimento, BigDecimal salario, String funcao) {
		setNome(nome);
		setData_nascimento(dataNascimento);
		this.salario = salario;
		this.funcao = funcao;
	}

}