package br.com.bropenmaps.logic;

import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.bropenmaps.util.GoogleTileUtils;
import br.com.bropenmaps.util.SimplePoint;
import br.com.bropenmaps.util.Tile;
import br.com.bropenmaps.util.BOMapsCacheManager;
import br.com.zymboo.commons.util.CriptUtils;

/**
 * Renderiza um quadrante do mapa (9 partes). A requisição deve conter um parâmetro chamado x (coordenada da imagem), y (coordenada da imagem) e back (url de retorno), além de lat(latitude) e lng(longitude). 
 * @author Rafael Melo Salum
 *
 */
@SuppressWarnings("serial")
public class BKPMapa extends HttpServlet {
	
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
		
		Integer x = request.getParameter("x")!=null ? Integer.parseInt(request.getParameter("x")) : null;
		
		Integer y = request.getParameter("y")!=null ? Integer.parseInt(request.getParameter("y")) : null;
		
		String back = request.getParameter("back");
		
		Double dLatCenterPix = null;
		
		Double dLngCenterPix = null;
		
		final BOMapsCacheManager cache = BOMapsCacheManager.getInstance();
		
		int zoom = 0;
		
		if(x==null) {
		
			final String latitude = request.getParameter("lat");
			
			final String longitude = request.getParameter("lng");
			
			final Double lat = Double.parseDouble(latitude);
			
			final Double lng = Double.parseDouble(longitude);
			
			final Tile t = new Tile(lat, lng, zoom);
			
			final SimplePoint p = t.getTileCoord();
			
			final Rectangle2D r2d = GoogleTileUtils.getLatLong(p.getX(), p.getY(), zoom);
			
			final Double w = (Double) (r2d.getWidth()/(Double) 2.0);
			
			final Double h = (Double) (r2d.getHeight()/(Double) 2.0);
			
			final Double difLng = lng - r2d.getCenterX();
			
			dLngCenterPix = difLng*128/w;
			
			final Double difLat = Math.abs(Math.abs(lat) - Math.abs(r2d.getCenterY()));
			
			dLatCenterPix = difLat*128/h;
			
			if(lat>r2d.getCenterY()) {
				
				dLatCenterPix = -dLatCenterPix;
				
			}
			
			x = p.getX();
			
			y = p.getY();
			
			cache.put(x+","+y, "<img style=\"border:0px; position:absolute;top:"+(256+128+Math.round(dLatCenterPix))+"px;left:"+(256+128+Math.round(dLngCenterPix))+"px;\" src=\"../imagens/point.png\" height=\"18\"/>", "bropenmaps");
			
		}

		response.setContentType("text/html");
		
	    response.setHeader("Cache-Control", "no-cache");
	    
	    final StringBuilder resultado = new StringBuilder();
	    
	    resultado.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
	     
	    resultado.append("<!DOCTYPE html PUBLIC \"-//WAPFORUM//DTD XHTML Mobile 1.0//EN\""+
	                   "\"http://www.wapforum.org/DTD/xhtml-mobile10.dtd\">");
	     
	    resultado.append("<html><body style=\"width:99.99%;\">");	    
	    
		resultado.append("<div id=\"mapa\" style=\"position:relative;width:99.99%;\">");
		
		String[][] coord = montaNavegacaoMapas(x, y, zoom);
		
		for (int i = 0; i < coord.length; i++) {
			
			for (int j = 0; j < coord.length; j++) {
				
				resultado.append(coord[i][j]);
				
			}
			
		}		
		
		resultado.append("<a href=\"http://maps.google.com\"><img style=\"border:0px; position:absolute;top:740px;left:0px;\" src=\"../imagens/poweredby.png\"/></a>");
		
		resultado.append("<div style=\"position:absolute;top:748px;left:670px;font-family: arial;font-size: 13px;\"><a href=\"http://www.google.com/intl/en_ALL/help/terms_maps.html\" target=\"_blank\">Termos de uso</a></div>");
		
		resultado.append("<a style=\"position:absolute;top:381px;left:0px;\" href=\"mapa?x="+(x-1)+"&y="+y+"&back="+URLEncoder.encode(back, "UTF-8")+"\"><img style=\"border:0px;\" src=\"../imagens/esquerda.png\"/></a>");
		
		resultado.append("<a style=\"position:absolute;top:381px;left:755px;\" href=\"mapa?x="+(x+1)+"&y="+y+"&back="+URLEncoder.encode(back, "UTF-8")+"\"><img style=\"border:0px;\" src=\"../imagens/direita.png\"/></a>");
		
		resultado.append("<a style=\"position:absolute;top:0px;left:381px;\" href=\"mapa?x="+x+"&y="+(y-1)+"&back="+URLEncoder.encode(back, "UTF-8")+"\"><img style=\"border:0px;\" src=\"../imagens/cima.png\"/></a>");
		
		resultado.append("<a style=\"position:absolute;top:748px;left:381px;\" href=\"mapa?x="+x+"&y="+(y+1)+"&back="+URLEncoder.encode(back, "UTF-8")+"\"><img style=\"border:0px;\" src=\"../imagens/baixo.png\"/></a></div>");
		
		resultado.append("<a name=\"centro\" style=\"position:absolute;top:256px;left:512px;\">&nbsp;</a>");
		
		if(cache.get(x+","+y, "bropenmaps")!=null) {
			
			resultado.append(cache.get(x+","+y, "bropenmaps"));
			
		}		
		
		resultado.append("</div>");
		
		final CriptUtils c = new CriptUtils(ResourceBundle.getBundle("cript").getString("chave"));
		
		resultado.append("<div style=\"position:relative;width:99.99%;\"><input type=\"button\" value=\"Voltar\" onclick=\"window.location.href='"+c.decrypt(back)+"'\"/></div>");		
		
		resultado.append("</html></body>");
		
		 response.getWriter().print(resultado);
		
	}
	
	/**
	 * Monta as ações das setas de navegação do mapa
	 * @param x - coordenada do quadrante
	 * @param y - coordenada do quadrante
	 * @param zoom - nível de zoom
	 * @return Array contendo as imagens da seta e suas respectivas ações
	 */
	private String[][] montaNavegacaoMapas(Integer x, Integer y, int zoom) {		
		
		int limite = (int) Math.floor(131070/(Math.pow(2, zoom)));
		
		String coord[][] = new String[3][3];
		
		Integer xN = null;
		
		Integer yN = null;
		
		for (int i = 0; i < 3; i++) {
			
			for (int j = 0; j < 3; j++) {
				
				xN = (x+(j-1));
				
				yN = (y+(i-1));
				
				if(xN>limite) {
					
					xN = 0;
					
				}
				
				if(yN>limite) {
					
					yN = 0;
					
				}
				
				coord[i][j] = "<img "+((xN.equals(x+1) && yN.equals(y)) ? "id=\"cimg\"" : "" )+" style=\"position:absolute; top:"+(i*256)+"px; left:"+(j*256)+"px;\" src=\"http://mt"+j+".google.com/mt?n=404&x="+xN+"&y="+yN+"&zoom="+zoom+"\" />"; 
				
			}
			
		}
		
		return coord;
		
	}

}