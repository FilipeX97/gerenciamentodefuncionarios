package br.com.projedata.model;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Pessoa {
	
	private String nome;
	private LocalDate data_nascimento;

}
