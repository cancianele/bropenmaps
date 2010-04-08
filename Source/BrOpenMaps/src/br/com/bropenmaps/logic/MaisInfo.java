package br.com.bropenmaps.logic;

import java.io.IOException;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.bropenmaps.dao.DAOFactory;
import br.com.bropenmaps.dao.EstabelecimentoDAO;
import br.com.bropenmaps.dao.HibernateUtil;
import br.com.bropenmaps.model.Estabelecimento;
import br.com.zymboo.commons.util.CriptUtils;

/**
 * Renderiza a tela de mais informações de um estabelecimento para aparelhos móveis. A requisição deve conter um parâmetro chamado estId (id do estabelecimento) e back (url de retorno)
 * @author Rafael Melo Salum
 *
 */
@SuppressWarnings("serial")
public class MaisInfo extends HttpServlet {
	
	/**
	 * Requisição via get, que é redirecionada via post
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		doPost(req, resp);
		
	}
	
	/**
	 * Requisição via post. Requisita uma busca a camada de dados e retorna uma resposta HTML
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		final Long id = request.getParameter("estId")!=null ? Long.parseLong(request.getParameter("estId")) : null;
		
		final String back = request.getParameter("back");
		
		if(id!=null) {
			
			final Estabelecimento est = ((EstabelecimentoDAO) DAOFactory.getDAO("EstabelecimentoDAO")).buscaPelaChave(id);
			
			response.setContentType("text/html");
			
		    response.setHeader("Cache-Control", "no-cache"); 
			
		    final StringBuilder resultado = new StringBuilder();
		    
		    resultado.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		     
		    resultado.append("<!DOCTYPE html PUBLIC \"-//WAPFORUM//DTD XHTML Mobile 1.0//EN\""+
		                   "\"http://www.wapforum.org/DTD/xhtml-mobile10.dtd\">");
		     
		    resultado.append("<html>");
		     
		    resultado.append("<head><title>Resultado</title>");
		    
		    resultado.append("<style type=\"text/css\"><!-- @import url(\"../css/layout.css\"); --> .verde{color:#008000;} .vermelho{color:red;}</style></head>");
		    
		    
		    resultado.append("<body>");
		    
			 resultado.append("<div class=\"verde\">");
			 
			 resultado.append(est.getEstabelecimentoNome());
			 
			 resultado.append("</div>");
		    
			 if(est.getEstabelecimentoDescricao()!=null && !est.getEstabelecimentoDescricao().equals("")) {
				 
				 resultado.append("<div>");
				 
				 resultado.append(est.getEstabelecimentoDescricao());
				 
				 resultado.append("</div>");
				 
			 }
			 
			 resultado.append("<div>");
			 
			 resultado.append(est.getEnderecosHtmlMobile());
			 
			 resultado.append("</div>");
			 
			 resultado.append("<br/><div>");
			 
			 final CriptUtils c = new CriptUtils(ResourceBundle.getBundle("cript").getString("chave"));
			 
			 resultado.append("<a href=\""+c.decrypt(back)+"\">Voltar</a>");
			 
			 resultado.append("</div>");
			 
		     resultado.append("</body></html>");
		     
		     HibernateUtil.closeSession();
		     
		     response.getWriter().print(resultado);
		     
		}
		
	}

	
	
}
