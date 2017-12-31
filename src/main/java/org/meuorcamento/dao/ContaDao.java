package org.meuorcamento.dao;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.stream.Collectors;

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
	
	/**
	 * O limite e de 6 meses
	 * @return lista de contas
	 */
	public List<Conta> listaTodos() {
		List<Conta> contas = null;
		Query q = em.createQuery("select c from Conta c where c.dataPagamento < :param1");
		q.setParameter("param1", dataParaSeisMeses());
		contas = q.getResultList();
		return contas;
	}
	
	private LocalDate dataParaSeisMeses() {
		return LocalDate.now().plusMonths(6).with(TemporalAdjusters.lastDayOfMonth());
	}
	
	public List<Conta> listaMesAtual() {
		
		List<Conta> todos = listaTodos();
		return todos.stream()
					.filter(conta -> conta.getDataPagamento().getMonth() == LocalDate.now().getMonth())
					.collect(Collectors.toList());
		
		
	}
	
	public List<Conta> listaMesPorNumero(int mes, int ano) {
		
		List<Conta> todos = listaTodos();
		return todos.stream()
				.filter(conta -> conta.getDataPagamento().getMonth() == LocalDate.now().withMonth(mes).getMonth())
				.filter(conta -> conta.getDataPagamento().getYear() == LocalDate.now().withYear(ano).getYear())
				.collect(Collectors.toList());
		
		
	}
	
}
