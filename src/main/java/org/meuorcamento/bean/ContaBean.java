package org.meuorcamento.bean;

import javax.enterprise.inject.Model;

import org.meuorcamento.model.Conta;

@Model
public class ContaBean {
	
//	@Inject
//	private ContaDao dao;
	
	private Conta conta = new Conta();
	
	
	public Conta getConta() {
		return conta;
	}


	public void setConta(Conta conta) {
		this.conta = conta;
	}


	public String salvar(Conta conta) {
		System.out.println(conta);
		return "/conta?faces-redirect=true";
	}
	
	

}
