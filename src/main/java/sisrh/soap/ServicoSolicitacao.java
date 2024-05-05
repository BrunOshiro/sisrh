package sisrh.soap;

import java.util.List;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.xml.ws.WebServiceContext;

import sisrh.banco.Banco;
import sisrh.dto.Solicitacao;
import sisrh.dto.Solicitacoes;
import sisrh.seguranca.Autenticador;

@WebService
@SOAPBinding(style = Style.RPC)
public class ServicoSolicitacao {

	@Resource
	WebServiceContext context;

	@WebMethod(action = "listar")
	public Solicitacoes listar(@WebParam(name = "usuario") String usuario) throws Exception {
		Autenticador.autenticarUsuarioSenha(context);

		Solicitacoes solicitacoes = new Solicitacoes();
		List<Solicitacao> lista = Banco.listarSolicitacoes(usuario);
		for (Solicitacao solicitacao : lista) {
			solicitacoes.getSolicitacoes().add(solicitacao);
		}
		return solicitacoes;
	}
}
