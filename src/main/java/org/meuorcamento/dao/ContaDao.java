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
import org.meuorcamento.model.TipoConta;
import org.meuorcamento.util.TokenGenerator;

@Stateful
public class ContaDao {

	@PersistenceContext
	private EntityManager em;
	
	
	private Conta geraContasParaDozeMeses(int plusMonth, Conta conta) {
		Conta contaFutura = new Conta();
		contaFutura.setNome(conta.getNome());
		contaFutura.setValor(conta.getValor());
		contaFutura.setDataPagamento(conta.getDataPagamento().plusMonths(plusMonth));
		contaFutura.setEstado(conta.isEstado());
		contaFutura.setTipoConta(conta.getTipoConta());
		contaFutura.setChaveGrupoContas(conta.getChaveGrupoContas());
		return contaFutura;
	}
	
	public void inserir(Conta conta) {
		conta.setChaveGrupoContas(TokenGenerator.generateToken(conta.getNome()));
		
		if(conta.isRepetir()) {
			for(int i=0;i<13;i++) {
				em.persist(geraContasParaDozeMeses(i, conta));
			}
		}else {
			em.persist(conta);
		}

	}
	
	public void alterar(Conta conta) {
		List<Conta> mesesExistentes = mesesExistentes(conta);
		int count = 0;
		if(conta.isRepetir()) {
			for (Conta c : mesesExistentes) {
				em.merge(geraContasParaDozeMeses(count++, c));
			}

		}else {
			em.merge(conta);
		}
		
	}
	
	public void remove(int id) {
		Conta c = em.find(Conta.class, id);
		em.remove(c);
	}
	
	public Conta getContaById(int id) {
		Conta conta = null;
		Query q = em.createQuery("select c from Conta c where c.id = :param1");
		q.setParameter("param1", id);
		conta = (Conta) q.getSingleResult();
		return conta;
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
	
	/**
	 * O limite e de 6 meses
	 * @return lista de contas
	 */
	public List<Conta> listaPorTipoConta(TipoConta tipoConta) {
		List<Conta> contas = null;
		Query q = em.createQuery("select c from Conta c where c.dataPagamento < :param1 and c.tipoConta = :param2");
		q.setParameter("param1", dataParaSeisMeses());
		q.setParameter("param2", tipoConta);
		contas = q.getResultList();
		return contas;
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

	private LocalDate dataParaSeisMeses() {
		return LocalDate.now().plusMonths(6).with(TemporalAdjusters.lastDayOfMonth());
	}

	public List<Conta> mesesExistentes(Conta conta) {
		List<Conta> contas = null;
		Query q = em.createQuery("select c from Conta c where c.chaveGrupoContas = :param1");
		q.setParameter("param1", conta.getChaveGrupoContas());
		contas = q.getResultList();
		return contas;
	}
	
}
