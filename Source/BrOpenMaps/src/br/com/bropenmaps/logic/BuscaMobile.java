package br.com.bropenmaps.logic;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import br.com.bropenmaps.dao.DAOFactory;
import br.com.bropenmaps.dao.EstabelecimentoDAO;
import br.com.bropenmaps.dao.HibernateUtil;
import br.com.bropenmaps.model.Estabelecimento;
import br.com.bropenmaps.util.Util;
import br.com.bropenmaps.util.BOMapsCacheManager;

/**
 * Servelt responsável em receber e responder a requisição de uma nova busca para aparelhos móveis.
 * A requisição deve conter os parâmetros q (tipo de estabelecimento), cidade e estado(sigla do estado)
 * @author Rafael Melo Salum
 *
 */
@SuppressWarnings("serial")
public class BuscaMobile extends HttpServlet {
	
	/**
	 * Requisição via get, que é redirecionada via post
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		doPost(request, response);
	    		 
	}
	
	/**
	 * Requisição via post. Requisita uma busca a camada de dados e renderiza a resposta ao usuário
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String q = request.getParameter("q");
		
		String cidade = request.getParameter("cidade");
		
		String estado = request.getParameter("estado");
		
		String chaveCache = request.getParameter("cc")==null ? "" : request.getParameter("cc");
		
		String i = request.getParameter("inicio");
		
		int inicio = 1;
		
		if(i!=null) {
			
			inicio = Integer.parseInt(i); 
			
		}
		
		if(q!=null) {
			
			q = Util.unescape(q);
			
		}
		
		if(cidade!=null) {
			
			cidade = Util.unescape(cidade);
			
		}
		
		final BOMapsCacheManager cache = BOMapsCacheManager.getInstance();
		
		ArrayList<Estabelecimento> ests = null;
		
		final StringBuilder total = new StringBuilder();
		
	    final StringBuilder resultado = new StringBuilder();
	    
	    if(i==null) {
	    	
	    	cache.removeCache("bropenmaps");
	    	
	    }
	    
	    final StringBuilder cachePag = (StringBuilder) cache.get(inicio+chaveCache, "bropenmaps");
	    
		if(i!=null && cachePag!=null) {
			
			response.setContentType("text/html");
			
		    response.setHeader("Cache-Control", "no-cache"); 
		    
			response.getWriter().print(cachePag);
			 
		}
		
		else {
			
			if(chaveCache==null || chaveCache.equals("")) {
				
				synchronized (this) {
					
					chaveCache = StringUtils.remove(Util.geraChaveMD5(String.valueOf((new Date()).getTime())), "-");
					
				}
				
			}
			
			final EstabelecimentoDAO estDAO = (EstabelecimentoDAO) DAOFactory.getDAO("EstabelecimentoDAO");
			
			ests = estDAO.buscaPorEnderecoEmpresaTipoPaginado(null, null, cidade, estado, q, null, null, inicio, Util.MAX_RESULTADOS, total);
			
			response.setContentType("text/html");
			
		    response.setHeader("Cache-Control", "no-cache"); 
		    
		    resultado.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		     
		    resultado.append("<!DOCTYPE html PUBLIC \"-//WAPFORUM//DTD XHTML Mobile 1.0//EN\""+
		                   "\"http://www.wapforum.org/DTD/xhtml-mobile10.dtd\">");
		     
		    resultado.append("<html>");
		     
		    resultado.append("<head><title>Resultado</title>");
		    
		    resultado.append("<style type=\"text/css\"><!-- @import url(\"../css/layout.css\"); --> .verde{color:#008000;} .vermelho{color:red;}</style>"+"</head>");
		    
		    resultado.append("<body><a href=\"../index.jsp\"><img class=\"noborder\" src=\"../imagens/BrOpenMaps.png\" width=\"83\" height=\"35\"/></a><br/>Foram encontrados "+total+" estabelecimentos.<br/><hr/>");
		    
			int c = 1;
			
			resultado.append("<div>");
			
			for (Estabelecimento est : ests) {
				
				 resultado.append("<div>");
				 
				 resultado.append("<div class=\"verde\">");
				 
				 resultado.append(c + ". " + est.getEstabelecimentoNome());
				 
				 resultado.append("</div>");
				 
				 resultado.append("<div>");
				 
				 if(est.getEstabelecimentoDescricao()!=null && !est.getEstabelecimentoDescricao().equals("")) {
					 
					 resultado.append("<div>");
					 
					 resultado.append(est.getEstabelecimentoDescricao());
					 
					 resultado.append("</div>");
					 
				 }
				 
				 resultado.append("<div>");
				
				 resultado.append(est.getPrimeiroEndereco().getEnderecoDescricao());
				 
				 resultado.append("</div>");
				
				 resultado.append("<div>");
				
				 resultado.append(est.getPrimeiroEnderecoCidade() +"-"+ est.getPrimeiroEnderecoEstado());
					
				 resultado.append("</div>");
				 
				 if(est.getPrimeiroTelefone()!=null && !est.getPrimeiroTelefone().equals("")) {
					 
					resultado.append("<div>");
					
					resultado.append("<a href=\"wtai://wp/mc;"+Util.limpaTelefones(est.getPrimeiroTelefone())+"\">"+est.getPrimeiroTelefone()+"</a>");
					
					resultado.append("<br/><a href=\"wtai://wp/ap;"+Util.limpaTelefones(est.getPrimeiroTelefone())+";"+StringUtils.replace(est.getEstabelecimentoNome(), " ", "_")+"\">Mandar para contatos</a>");
					
					if(est.getEnderecosHtml()!=null && !est.getEnderecosHtml().equals("")) {
						
						String b = Util.criptografa("busca?q="+q+"&cidade="+cidade+"&estado="+estado+"&cc="+chaveCache+"&inicio="+inicio);
						
						resultado.append("<br/><a href=\"maisinfo?estId="+est.getEstabelecimentoId()+"&back="+b+"\">Mais Informações</a>");
						
					}
					
					if(est.getPrimeiroEndereco().getEnderecoLatitude()!=null) {
						
						String b = Util.criptografa("busca?q="+q+"&cidade="+cidade+"&estado="+estado+"&cc="+chaveCache+"&inicio="+inicio);
						
						resultado.append("<br/><a class=\"vermelho left2px\" href=\"mapa?lat="+est.getPrimeiroEndereco().getEnderecoLatitude()+"&lng="+est.getPrimeiroEndereco().getEnderecoLongitude()+"&back="+b+"#centro\">Ver no mapa</a>");
						
					}
					
					resultado.append("</div>");
					
				}
				
				 resultado.append("</div>");
				 
				 resultado.append("</div><br class=\"clear\"/>");
				  
				c++;
				 
			}
			
			resultado.append("</div><br class=\"clear\"/>");
			
			resultado.append(Util.criaPaginacaoMobile(total, inicio, "busca?q="+URLEncoder.encode(q, "UTF-8")+"&cidade="+URLEncoder.encode(cidade, "UTF-8")+"&estado="+estado+"&cc="+chaveCache));
			
		    resultado.append("</body></html>");
		    
		    HibernateUtil.closeSession();
		    
		    cache.put(inicio+chaveCache, resultado, "bropenmaps");
		    
		    response.getWriter().print(resultado);
			
		}
		
	}
	
}