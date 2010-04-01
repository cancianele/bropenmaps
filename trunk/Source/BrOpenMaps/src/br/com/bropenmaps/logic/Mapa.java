package br.com.bropenmaps.logic;

import java.io.IOException;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.zymboo.commons.util.CriptUtils;

/**
 * Renderiza um quadrante do mapa (9 partes). A requisição deve conter um parâmetro chamado x (coordenada da imagem), y (coordenada da imagem) e back (url de retorno), além de lat(latitude) e lng(longitude). 
 * @author Rafael Melo Salum
 *
 */
@SuppressWarnings("serial")
public class Mapa extends HttpServlet {
	
	/**
	 * Requisição via get, que é redirecionada via post
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		doPost(req, resp);
		
	}
	
	/**
	 * Renderiza o mapa.
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {		
		
		String back = request.getParameter("back");
		
		int zoom = 16;		
		
		final String latitude = request.getParameter("lat");
		
		final String longitude = request.getParameter("lng");
		
		final Double lat = Double.parseDouble(latitude);
		
		final Double lng = Double.parseDouble(longitude);
			
		response.setContentType("text/html");
		
	    response.setHeader("Cache-Control", "no-cache");
	    
	    final StringBuilder resultado = new StringBuilder();
	    
	    resultado.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
	     
	    resultado.append("<!DOCTYPE html PUBLIC \"-//WAPFORUM//DTD XHTML Mobile 1.0//EN\""+
	                   "\"http://www.wapforum.org/DTD/xhtml-mobile10.dtd\">");
	     
	    resultado.append("<html><head>");
	    // ip 101 - ABQIAAAAcXrSK75X-t4Hr64cDvgnnBTmgmzaQzD4HL0Cf9XlpT9mIqiJABTqFpdg_EtBAQ_thkPVK-7exz7lZg
	    // ip 100 - ABQIAAAAcXrSK75X-t4Hr64cDvgnnBT90fv9ocPjXmf2RD5K6WvHf1LR6BSHaLPbE_LvOETwthYRrW1PPoXeIQ
	    resultado.append("<script src=\"http://maps.google.com/maps?file=api&amp;v=2&amp;sensor=false&amp;key=ABQIAAAAcXrSK75X-t4Hr64cDvgnnBTmgmzaQzD4HL0Cf9XlpT9mIqiJABTqFpdg_EtBAQ_thkPVK-7exz7lZg\"type=\"text/javascript\"></script>");
	    
	    //resultado.append("<script type=\"text/javascript\">function initialize() {if (GBrowserIsCompatible()) {var map = new GMap2(document.getElementById(\"mapa\"));map.setCenter(new GLatLng("+lat+","+lng+"), "+zoom+");map.setUIToDefault();}}</script>");
	    resultado.append("<script type=\"text/javascript\">" +
	    "function initialize() {" +
	    	"if (GBrowserIsCompatible()) {" +
	    		"var map = new GMap2(document.getElementById(\"mapa\"));" +
	    		"map.setCenter(new GLatLng("+lat+","+lng+"), "+zoom+");" +
				"map.setUIToDefault();" +
				"var point = new GPoint ("+lng+", "+lat+");" +
			    "var marker = new GMarker(point);" +
			    "map.addOverlay(marker);" +
    		"}" +
		"}" +
		"</script>");
	    
	    resultado.append("</head><body onload=\"initialize()\" onunload=\"GUnload()\">");
	    
		resultado.append("<div id=\"mapa\" style=\"position:absolute; height:99%;width:99%;\">");		
		
		resultado.append("</div>");
		
		final CriptUtils c = new CriptUtils(ResourceBundle.getBundle("cript").getString("chave"));
		
		resultado.append("<div style=\"position:relative;width:99.99%;\"><input type=\"button\" value=\"Voltar\" onclick=\"window.location.href='"+c.decrypt(back)+"'\"/></div>");		
		
		resultado.append("</body></html>");
		
		response.getWriter().print(resultado);
		
	}
	

}