package org.meuorcamento.dao;

import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.meuorcamento.model.Conta;

@Stateful
public class ContaDao {

	@PersistenceContext
	private EntityManager em;
	
	
	public void inserir(Conta conta) {
		em.persist(conta);
	}
	
	public List<Conta> listaTodos() {
		List<Conta> contas = null;
		Query q = em.createQuery("select c from Conta c");
		contas = q.getResultList();
		return contas;
	}
	
}
