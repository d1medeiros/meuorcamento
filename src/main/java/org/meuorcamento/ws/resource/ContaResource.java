package org.meuorcamento.ws.resource;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.meuorcamento.dao.ContaDao;
import org.meuorcamento.model.Conta;

@Path("conta")
@Produces({MediaType.APPLICATION_JSON })
@Consumes({MediaType.APPLICATION_JSON })
public class ContaResource {
	
	@Inject
	private ContaDao dao;
	
	@GET
	@Path("/")
	public Conta getConta() {
		Conta conta = new Conta();
		conta.setId(1);
		conta.setNome("vivo");
		conta.setValor(100.12);
		return conta;
	}

	@GET
	@Path("/all")
	public List<Conta> getContas() {
		return dao.listaTodos();
	}

}
