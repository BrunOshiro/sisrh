package sisrh.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import io.swagger.annotations.Api;
import sisrh.banco.Banco;
import sisrh.dto.Empregado;

@Api
@Path("/empregado")
public class EmpregadoRest {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listarEmpregados() throws Exception {
		List<Empregado> lista = Banco.listarEmpregados();
		GenericEntity<List<Empregado>> entity = new GenericEntity<List<Empregado>>(lista) {
		};
		return Response.ok().entity(entity).build();
	}

	@GET
	@Path("{matricula}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response obterEmpregado(@PathParam("matricula") String matricula) throws Exception {
		try {
			Empregado empregado = Banco.buscarEmpregadoPorMatricula(matricula);
			if (empregado != null) {
				return Response.ok().entity(empregado).build();
			} else {
				return Response.status(Status.NOT_FOUND).entity("{ \"mensagem\" : \"Empregado nao encontrado!\" }")
						.build();
			}
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(
					"{ \"mensagem\" : \"Falha para obter empregado!\" , \"detalhe\" :  \"" + e.getMessage() + "\"  }")
					.build();
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response incluirEmpregado(Empregado empregado) {
		try {
			Empregado emp = Banco.incluirEmpregado(empregado);
			return Response.ok().entity(emp).build();
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity("{ \"mensagem\" : \"Falha na inclusao do empregado!\" , \"detalhe\" :  \"" + e.getMessage()
							+ "\"  }")
					.build();
		}
	}

	@PUT
	@Path("{matricula}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response alterarEmpregado(@PathParam("matricula") String matricula, Empregado empregado) {
		try {
			if (Banco.buscarEmpregadoPorMatricula(matricula) == null) {
				return Response.status(Status.NOT_FOUND).entity("{ \"mensagem\" : \"Empregado nao encontrado!\" }")
						.build();
			}
			Empregado emp = Banco.alterarEmpregado(matricula, empregado);
			return Response.ok().entity(emp).build();
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity("{ \"mensagem\" : \"Falha na alteracao do empregado!\" , \"detalhe\" :  \"" + e.getMessage()
							+ "\"  }")
					.build();
		}
	}

	@DELETE
	@Path("{matricula}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response excluirEmpregado(@PathParam("matricula") String matricula) throws Exception {
		try {
			if (Banco.buscarEmpregadoPorMatricula(matricula) == null) {
				return Response.status(Status.NOT_FOUND).entity("{ \"mensagem\" : \"Empregado nao encontrado!\" }")
						.build();
			}
			Banco.excluirEmpregado(matricula);
			return Response.ok().entity("{ \"mensagem\" : \"Empregado " + matricula + " excluido!\" }").build();
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity("{ \"mensagem\" : \"Falha na exclusao do empregado!\" , \"detalhe\" :  \"" + e.getMessage()
							+ "\"  }")
					.build();
		}
	}

}
