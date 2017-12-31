package org.meuorcamento.ws.resource;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

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
	public Response getConta() {
		Conta conta = dao.listaMesAtual().get(0);
		return Response.ok(conta).build();
	}

	@GET
	@Path("/atual")
	public List<Conta> getContasAtual() {
		return dao.listaMesAtual();
	}
	
	@GET
	@Path("{mesAno}")
	public List<Conta> getContasPorNumero(@PathParam("mesAno") String mesAno) {
		int mes = Integer.valueOf(mesAno.split("-")[0]);
		int ano = Integer.valueOf(mesAno.split("-")[1]);
		return dao.listaMesPorNumero(mes, ano);
	}
	
	@GET
	@Path("/seisMeses")
	public List<Conta> getContasAll() {
		return dao.listaTodos();
	}
	
	@POST
	@Path("/salva")
	@Produces({MediaType.APPLICATION_JSON })
	@Consumes({MediaType.APPLICATION_JSON })
	public Response salva(@Valid Conta conta) {
		dao.inserir(conta);
		
		return Response.noContent().build();
	}

}
