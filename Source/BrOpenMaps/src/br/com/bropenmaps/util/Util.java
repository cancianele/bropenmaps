package br.com.bropenmaps.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import br.com.bropenmaps.dao.CidadesDAO;
import br.com.bropenmaps.dao.DAOFactory;
import br.com.bropenmaps.model.Cidade;
import br.com.bropenmaps.model.CidadesDist;
import br.com.zymboo.commons.util.CriptUtils;

/**
 * Classe utilitária do sistema
 * @author Rafael Melo Salum
 *
 */
public class Util {
	
	/**
	 * Constante que representa o máximo de resultado aceito por página
	 */
	public static final int MAX_RESULTADOS = 10;
	
	/**
	 * Quantidade de páginas a serem mostradas antes e depois da página atual
	 */
	final public static int PAG_ANT_DEP = 3;
	
	/**
	 * Tipo de validação para campo obrigatório
	 */
	final public static int OBRIGATORIO = 1;
	
	/**
	 * Tipo de validação para email
	 */
	final public static int EMAIL = 2;
	
	private static final ResourceBundle configs = ResourceBundle.getBundle("config");
	
	/*
	 * Created: 17 April 1997
	 * Author: Bert Bos <bert@w3.org>
	 *
	 * unescape: http://www.w3.org/International/unescape.java
	 *
	 * Copyright Â© 1997 World Wide Web Consortium, (Massachusetts
	 * Institute of Technology, European Research Consortium for
	 * Informatics and Mathematics, Keio University). All Rights Reserved. 
	 * This work is distributed under the W3CÂ® Software License [1] in the
	 * hope that it will be useful, but WITHOUT ANY WARRANTY; without even
	 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
	 * PURPOSE.
	 *
	 * [1] http://www.w3.org/Consortium/Legal/2002/copyright-software-20021231
	 */
	  
	 /**
	  * Desfaz as alterações feitas quando um termo precisa ser modificado ao ser utilizado como parâmetro em requisições HTTP (decoder para o {@link URLEncoder}).
	  * Este método transforma '+' em espaço.
	  * @param s - termo a ser modificado 
	  * @return Retorna o termo com as modificações realizadas
	  */
	  public static String unescape(String s) {
		if(s==null) { return s; }
		  
	    StringBuffer sbuf = new StringBuffer () ;
	    int l  = s.length() ;
	    int ch = -1 ;
	    int b, sumb = 0;
	    for (int i = 0, more = -1 ; i < l ; i++) {
	      /* Get next byte b from URL segment s */
	      switch (ch = s.charAt(i)) {
		case '%':
		  ch = s.charAt (++i) ;
		  int hb = (Character.isDigit ((char) ch) 
			    ? ch - '0'
			    : 10+Character.toLowerCase((char) ch) - 'a') & 0xF ;
		  ch = s.charAt (++i) ;
		  int lb = (Character.isDigit ((char) ch)
			    ? ch - '0'
			    : 10+Character.toLowerCase ((char) ch)-'a') & 0xF ;
		  b = (hb << 4) | lb ;
		  break ;
		case '+':
		  b = ' ' ;
		  break ;
		default:
		  b = ch ;
	      }
	      /* Decode byte b as UTF-8, sumb collects incomplete chars */
	      if ((b & 0xc0) == 0x80) {			// 10xxxxxx (continuation byte)
		sumb = (sumb << 6) | (b & 0x3f) ;	// Add 6 bits to sumb
		if (--more == 0) sbuf.append((char) sumb) ; // Aarg0dd char to sbuf
	      } else if ((b & 0x80) == 0x00) {		// 0xxxxxxx (yields 7 bits)
		sbuf.append((char) b) ;			// Store in sbuf
	      } else if ((b & 0xe0) == 0xc0) {		// 110xxxxx (yields 5 bits)
		sumb = b & 0x1f;
		more = 1;				// Expect 1 more byte
	      } else if ((b & 0xf0) == 0xe0) {		// 1110xxxx (yields 4 bits)
		sumb = b & 0x0f;
		more = 2;				// Expect 2 more bytes
	      } else if ((b & 0xf8) == 0xf0) {		// 11110xxx (yields 3 bits)
		sumb = b & 0x07;
		more = 3;				// Expect 3 more bytes
	      } else if ((b & 0xfc) == 0xf8) {		// 111110xx (yields 2 bits)
		sumb = b & 0x03;
		more = 4;				// Expect 4 more bytes
	      } else /*if ((b & 0xfe) == 0xfc)*/ {	// 1111110x (yields 1 bit)
		sumb = b & 0x01;
		more = 5;				// Expect 5 more bytes
	      }
	      /* We don't test if the UTF-8 encoding is well-formed */
	    }
	    return sbuf.toString() ;
	  }
	  
	 /**
	  * Desfaz as alterações feitas quando um termo precisa ser modificado ao ser utilizado como parâmetro em requisições HTTP (decoder para o {@link URLEncoder})
	  * @param s - termo a ser modificado 
	  * @return Retorna o termo com as modificações realizadas
	  */
	  public static String decoder(String s) {
			if(s==null) { return s; }
			  
		    StringBuffer sbuf = new StringBuffer () ;
		    int l  = s.length() ;
		    int ch = -1 ;
		    int b, sumb = 0;
		    for (int i = 0, more = -1 ; i < l ; i++) {
		      /* Get next byte b from URL segment s */
		      switch (ch = s.charAt(i)) {
			case '%':
			  ch = s.charAt (++i) ;
			  int hb = (Character.isDigit ((char) ch) 
				    ? ch - '0'
				    : 10+Character.toLowerCase((char) ch) - 'a') & 0xF ;
			  ch = s.charAt (++i) ;
			  int lb = (Character.isDigit ((char) ch)
				    ? ch - '0'
				    : 10+Character.toLowerCase ((char) ch)-'a') & 0xF ;
			  b = (hb << 4) | lb ;
			  break ;
			default:
			  b = ch ;
		      }
		      /* Decode byte b as UTF-8, sumb collects incomplete chars */
		      if ((b & 0xc0) == 0x80) {			// 10xxxxxx (continuation byte)
			sumb = (sumb << 6) | (b & 0x3f) ;	// Add 6 bits to sumb
			if (--more == 0) sbuf.append((char) sumb) ; // Aarg0dd char to sbuf
		      } else if ((b & 0x80) == 0x00) {		// 0xxxxxxx (yields 7 bits)
			sbuf.append((char) b) ;			// Store in sbuf
		      } else if ((b & 0xe0) == 0xc0) {		// 110xxxxx (yields 5 bits)
			sumb = b & 0x1f;
			more = 1;				// Expect 1 more byte
		      } else if ((b & 0xf0) == 0xe0) {		// 1110xxxx (yields 4 bits)
			sumb = b & 0x0f;
			more = 2;				// Expect 2 more bytes
		      } else if ((b & 0xf8) == 0xf0) {		// 11110xxx (yields 3 bits)
			sumb = b & 0x07;
			more = 3;				// Expect 3 more bytes
		      } else if ((b & 0xfc) == 0xf8) {		// 111110xx (yields 2 bits)
			sumb = b & 0x03;
			more = 4;				// Expect 4 more bytes
		      } else /*if ((b & 0xfe) == 0xfc)*/ {	// 1111110x (yields 1 bit)
			sumb = b & 0x01;
			more = 5;				// Expect 5 more bytes
		      }
		      /* We don't test if the UTF-8 encoding is well-formed */
		    }
		    return sbuf.toString() ;
		  }	  
	  
	  /**
	   * Cria uma expressão regular para busca da palavra com ou sem acentuação
	   * @param texto - texto a ser transformado
	   * @return regexp - expressão regular
	   */
	  public static String regExpPalavrasAcentuadas(String texto){
			String retorno = "";
			if(texto==null) { return retorno; }
			texto = StringUtils.replace(texto, "(", "\\(");
			texto = StringUtils.replace(texto, ")", "\\)");
			for (int ln = 0; ln < texto.length(); ln++){
				switch (texto.charAt(ln)){
					case ' ':
	                {
	                	retorno += " ";
	                    break;
	                }
					case 'a':
					case 'á':
					case 'à':
					case 'ä':
					case 'ã':
					case 'â':
	                {
	                	retorno += "(a|á|à|ã|â|ä)";
	                    break;
	                }
					case 'e':
					case 'é':
	                case 'è':
	                case 'ë':
	                case 'ê':
	                {
	                    retorno += "(e|é|è|ë|ê)";
	                    break;
	                }
	                case 'í':
	                case 'ì':
	                case 'ï':
	                case 'i':
	                case 'î':
	                {
	                    retorno += "(i|í|ì|ï|î|i)";
	                    break;
	                }
	                case 'ó':
	                case 'ò':
	                case 'ö':
	                case 'õ':
	                case 'ô':
	                case 'o':	
	                {
	                    retorno += "(o|ó|ò|õ|ö|ô)";
	                    break;
	                }
	                case 'ú':
	                case 'ù':
	                case 'ü':
	                case 'û':
	                case 'u':
	                {
	                    retorno += "(u|ú|ù|u|ü|û)";
	                    break;
	                }
	                case 'c':
	                case 'ç':
	                {
	                    retorno += "(c|ç)";
	                    break;
	                }
	                default :
	                {
	                    retorno += texto.charAt(ln);
	                }
	            }
			}
			return retorno;
		}
	  
	  /**
	   * Calcula a distância entre dois pontos que representa latitude e longitude
	   * @param x - latitude do primeiro ponto
	   * @param x0 - latitude do segundo ponto
	   * @param y - longitude do primeiro ponto
	   * @param y0 - longitude do segundo ponto
	   * @return Distância entre eles
	   */
	  public static double calcDistanciaDoisPontos(double x, double x0, double y, double y0) {
			
		return Math.sqrt(Math.pow((x-x0), 2) + Math.pow((y-y0), 2));		
					
	  }
	
	  /**
	   * Retorna um lista de objetos {@link CidadesDist} mais próximas a um determinado ponto, ordenadas decrescentemente pela proximidade ao ponto.
	   * @param pLat - latitude a ser comparada
	   * @param pLng - longitude a ser comparada
	   * @return Lista de objetos {@link CidadesDist}
	   */
	public static Collection<CidadesDist> retornaCidadeProxima(Double pLat, Double pLng) {
		
		final int lado = Integer.parseInt(configs.getString("lado_quad_km"));
		
		final double latKm = Double.parseDouble(configs.getString("lat_to_km"))*lado;
		
		final double lngKm = Double.parseDouble(configs.getString("lng_to_km"))*lado;
		
		double smLat = latKm;
		
		double smLng = lngKm;
		
		CidadesDAO gcidDAO = (CidadesDAO) DAOFactory.getDAO("CidadesDAO");
		
		ArrayList<Cidade> geoCids = (ArrayList<Cidade>)gcidDAO.listaGeoPorRegiao(pLat+smLat, pLat-smLat, pLng+smLng, pLng-smLng);
		
		while(geoCids == null || geoCids.size() == 0) {
			
			smLat += latKm;
			
			smLng += lngKm;
			
			geoCids = (ArrayList<Cidade>)gcidDAO.listaGeoPorRegiao(pLat+smLat, pLat-smLat, pLng+smLng, pLng-smLng);
			
		}
		
		double dist = 0;
		
		int cont = 0;
		
		List<CidadesDist> list = new ArrayList<CidadesDist>();
		
		for (Cidade gcodCid : geoCids) {
			
			dist = Util.calcDistanciaDoisPontos(gcodCid.getLatitude(), pLat, gcodCid.getLongitude(), pLng);
			
			if(list.size() != 0) {
			
				cont = 0;
				
				while(list.size() > cont) {
					
					if(list.get(cont).getDist() > dist) {
						
						list.add(cont, new CidadesDist(gcodCid, dist));
						
						break;
						
					}
					
					cont++;
					
				}
				
			} else {
				
				list.add(new CidadesDist(gcodCid, dist));
				
			}
			
		}
		
		return list;
		
	}

	/**
	 *  Verifica se duas strings são iguais independentemente da caixa e dos acentos
	 * @param p1
	 * @param p2
	 * @return true se são iguais, false em caso contrário
	 */
	public static  boolean verificaIgualdadeCaseInsensitiveSemAcento(String p1, String p2) {
		
		if(p1==null || p2==null) {
			
			return false;
			
		}
		
		p1 = regExpPalavrasAcentuadas(StringUtils.lowerCase(p1));
		
		final Pattern p = Pattern.compile(p1, Pattern.CASE_INSENSITIVE);
		
		Matcher m = p.matcher(p2);
		
		if(m.find()) {
			
			return true;
			
		} else {
			
			return false;
			
		}
		
	}
	
	/**
	 * Retira espaços em branco do início e do final de uma sequência de caracteres. Além disso remove os caracteres &nbsp; (espaço) das sequência. 
	 * @param str
	 * @return Sequência de caracteres sem os espaços.
	 */
	public static String trim(String str) {
		
		if(str==null) {
			
			return null;
			
		}

		str = StringUtils.replace(str, "&nbsp;", " ");
		
		return StringUtils.trim(str);
		
	}
	
	/**
	 * Cria uma porção HTML contendo a paginação de determinada busca.
	 * @param total - total de itens.
	 * @param inicio - item inicial
	 * @param metodoJs - método em Javascript para a alteração de páginas
	 * @param parametro - indica o tipo da busca
	 * @param acaoComp - filtro da busca
	 * @return Código HTML contendo a paginação
	 */
	public static StringBuilder criaPaginacao(StringBuilder total, int inicio, String metodoJs, String parametro, String acaoComp) {
		
		StringBuilder paginas = new StringBuilder("");	
		
		int pag = 0;
		if(!total.equals("")) {
			pag = Integer.parseInt(total.toString())%Util.MAX_RESULTADOS!=0 ? (Integer.parseInt(total.toString())/Util.MAX_RESULTADOS)+1 : (Integer.parseInt(total.toString())/Util.MAX_RESULTADOS);
		}
		
		if(pag>1) {
			int dif_parte1 = inicio-Util.PAG_ANT_DEP;
			int dif_parte2 = inicio+Util.PAG_ANT_DEP;
			
			paginas.append("<div class=\"left20px top10px bottom20px\">");
			if(dif_parte1>0) {
				if(pag-inicio < Util.PAG_ANT_DEP) {
					dif_parte1 -= (Util.PAG_ANT_DEP-(pag-inicio));
					if(dif_parte1<1) {
						dif_parte1 = 1;
					}
				}
								
				//Primeira
				if(inicio>1) {
					paginas.append("<div class=\"left left2px\"><a href=\"javascript:"+metodoJs+"("+(parametro!=null ? "'"+parametro+"', " : "")+"1,"+acaoComp+")\"><img src=\"imagens/bt_primeiro.png\" class=\"noborder\" alt=\"Primeiro\" title=\"Primeiro\"/></a></div>");
				}
				//Anterior
				if(dif_parte1>=1) {
					paginas.append("<div class=\"left left2px\"><a href=\"javascript:"+metodoJs+"("+(parametro!=null ? "'"+parametro+"', " : "")+(inicio-1)+","+acaoComp+")\"><img src=\"imagens/bt_anterior.png\" class=\"noborder\" alt=\"Anterior\" title=\"Anterior\"/></a></div>");
				}
								
				for (int i = dif_parte1; i <= inicio; i++) {
					paginas.append(i==inicio ? "<div class=\"left left2px paginacao_item\">"+i+"</div>" : "<div class=\"left left2px\"><a class=\"paginacao\" href=\"javascript:"+metodoJs+"("+(parametro!=null ? "'"+parametro+"', " : "")+i+","+acaoComp+")\">"+i+"</a></div>");
				}
				
				if(dif_parte2>pag) {
					for (int i = inicio+1; i <= pag; i++) {
						paginas.append(i==inicio ? "<div class=\"left left2px paginacao_item\">"+i+"</div>" : "<div class=\"left left2px\"><a class=\"paginacao\" href=\"javascript:"+metodoJs+"("+(parametro!=null ? "'"+parametro+"', " : "")+i+","+acaoComp+")\">"+i+"</a></div>");
					}
				}
				else {
					for (int i = inicio+1; i <= dif_parte2; i++) {
						paginas.append(i==inicio ? "<div class=\"left left2px paginacao_item\">"+i+"</div>" : "<div class=\"left left2px\"><a class=\"paginacao\" href=\"javascript:"+metodoJs+"("+(parametro!=null ? "'"+parametro+"', " : "")+i+","+acaoComp+")\">"+i+"</a></div>");
					}
				}
				//Próxima
				if(inicio<pag) {
					paginas.append("<div class=\"left left2px\"><a href=\"javascript:"+metodoJs+"("+(parametro!=null ? "'"+parametro+"', " : "")+(inicio+1)+","+acaoComp+")\"><img src=\"imagens/bt_proximo.png\" class=\"noborder\" alt=\"Próximo\" title=\"Próximo\"/></a></div>");
				}
				
				//Última
				if(inicio<pag) {
					paginas.append("<div class=\"left left2px\"><a href=\"javascript:"+metodoJs+"("+(parametro!=null ? "'"+parametro+"', " : "")+pag+","+acaoComp+")\"><img src=\"imagens/bt_ultimo.png\" class=\"noborder\" alt=\"Último\" title=\"Último\"/></a></div>");
				}					
			}
			else {
				//Primeira
				if(inicio>1) {
					paginas.append("<div class=\"left left2px\"><a href=\"javascript:"+metodoJs+"("+(parametro!=null ? "'"+parametro+"', " : "")+"1"+","+acaoComp+")\"><img src=\"imagens/bt_primeiro.png\" class=\"noborder\" alt=\"Primeiro\" title=\"Primeiro\"/></a></div>");
				}
				//Anterior
				if(inicio>1) {
					paginas.append("<div class=\"left left2px\"><a href=\"javascript:"+metodoJs+"("+(parametro!=null ? "'"+parametro+"', " : "")+(inicio-1)+","+acaoComp+")\"><img src=\"imagens/bt_anterior.png\" class=\"noborder\" alt=\"Anterior\" title=\"Anterior\"/></a></div>");				
				}
				
				
				if(Util.PAG_ANT_DEP*2>=pag) {
					for (int i = 1; i <= pag; i++) {
						paginas.append(i==inicio ? "<div class=\"left left2px paginacao_item\">"+i+"</div>" : "<div class=\"left left2px\"><a class=\"paginacao\" href=\"javascript:"+metodoJs+"("+(parametro!=null ? "'"+parametro+"', " : "")+i+","+acaoComp+")\">"+i+"</a></div>");
					}
				}
				else {
					for (int i = 1; i <= (Util.PAG_ANT_DEP*2)+1; i++) {
						paginas.append(i==inicio ? "<div class=\"left left2px paginacao_item\">"+i+"</div>" : "<div class=\"left left2px\"><a class=\"paginacao\" href=\"javascript:"+metodoJs+"("+(parametro!=null ? "'"+parametro+"', " : "")+i+","+acaoComp+")\">"+i+"</a></div>");
					}
				}
				//Próxima
				if(inicio<pag) {
					paginas.append("<div class=\"left left2px\"><a href=\"javascript:"+metodoJs+"("+(parametro!=null ? "'"+parametro+"', " : "")+(inicio+1)+","+acaoComp+")\"><img src=\"imagens/bt_proximo.png\" class=\"noborder\" alt=\"Próximo\" title=\"Próximo\"/></a></div>");				
				}
				
				//Última
				if(inicio<pag) {
					paginas.append("<div class=\"left left2px\"><a href=\"javascript:"+metodoJs+"("+(parametro!=null ? "'"+parametro+"', " : "")+pag+","+acaoComp+")\"><img src=\"imagens/bt_ultimo.png\" class=\"noborder\" alt=\"Último\" title=\"Último\"/></a></div>");
				}
			}
			paginas.append("</div>");
		}
		
		return paginas;
		
	}
	
	/**
	 * Cria uma porção HTML contendo a paginação de determinada busca para aparelhos móveis
	 * @param total - total de itens.
	 * @param inicio - item inicial
	 * @param acaoComp - filtro da busca
	 * @return Código HTML contendo a paginação
	 */
	public static StringBuilder criaPaginacaoMobile(StringBuilder total, int inicio, String acaoComp) {
		
		StringBuilder paginas = new StringBuilder("");	
		
		int pag = 0;
		if(!total.equals("")) {
			pag = Integer.parseInt(total.toString())%Util.MAX_RESULTADOS!=0 ? (Integer.parseInt(total.toString())/Util.MAX_RESULTADOS)+1 : (Integer.parseInt(total.toString())/Util.MAX_RESULTADOS);
		}
		
		if(pag>1) {
			int dif_parte1 = inicio-Util.PAG_ANT_DEP;
			int dif_parte2 = inicio+Util.PAG_ANT_DEP;
			
			paginas.append("<div id='paginacaoBusca'>");
			if(dif_parte1>0) {
				if(pag-inicio < Util.PAG_ANT_DEP) {
					dif_parte1 -= (Util.PAG_ANT_DEP-(pag-inicio));
					if(dif_parte1<1) {
						dif_parte1 = 1;
					}
				}
								
				//Primeira
				if(inicio>1) {
					paginas.append("<div class=\"left left2px\"><a class=\"paginacao nodecoration\" href=\""+acaoComp+"&inicio=1\">&lt;&lt;</a></div>");
				}
				//Anterior
				if(dif_parte1>=1) {
					paginas.append("<div class=\"left left5px right5px\"><a class=\"paginacao nodecoration\" href=\""+acaoComp+"&inicio="+(inicio-1)+"\">&lt;</a></div>");
				}
								
				for (int i = dif_parte1; i <= inicio; i++) {
					paginas.append(i==inicio ? "<div class=\"left left2px paginacao_item\">"+i+"</div>" : "<div class=\"left left2px\"><a class=\"paginacao nodecoration\" href=\""+acaoComp+"&inicio="+i+"\">"+i+"</a></div>");
				}
				
				if(dif_parte2>pag) {
					for (int i = inicio+1; i <= pag; i++) {
						paginas.append(i==inicio ? "<div class=\"left left2px paginacao_item\">"+i+"</div>" : "<div class=\"left left2px\"><a class=\"paginacao nodecoration\" href=\""+acaoComp+"&inicio="+i+")\">"+i+"</a></div>");
					}
				}
				else {
					for (int i = inicio+1; i <= dif_parte2; i++) {
						paginas.append(i==inicio ? "<div class=\"left left2px paginacao_item\">"+i+"</div>" : "<div class=\"left left2px\"><a class=\"paginacao nodecoration\" href=\""+acaoComp+"&inicio="+i+"\">"+i+"</a></div>");
					}
				}
				//Próxima
				if(inicio<pag) {
					paginas.append("<div class=\"left left5px\"><a class=\"paginacao nodecoration\" href=\""+acaoComp+"&inicio="+(inicio+1)+"\">&gt;</a></div>");
				}
				
				//Última
				if(inicio<pag) {
					paginas.append("<div class=\"left left5px\"><a class=\"paginacao nodecoration\" href=\""+acaoComp+"&inicio="+pag+"\">&gt;&gt;</a></div>");
				}					
			}
			else {
				//Primeira
				if(inicio>1) {
					paginas.append("<div class=\"left left2px\"><a class=\"paginacao nodecoration\" href=\""+acaoComp+"&inicio=1\">&lt;&lt;</a></div>");
				}
				//Anterior
				if(inicio>1) {
					paginas.append("<div class=\"left left5px right5px\"><a class=\"paginacao nodecoration\" href=\""+acaoComp+"&inicio="+(inicio-1)+"\">&lt;</a></div>");				
				}
				
				
				if(Util.PAG_ANT_DEP*2>=pag) {
					for (int i = 1; i <= pag; i++) {
						paginas.append(i==inicio ? "<div class=\"left left2px paginacao_item\">"+i+"</div>" : "<div class=\"left left2px\"><a class=\"paginacao nodecoration\" href=\""+acaoComp+"&inicio="+i+"\">"+i+"</a></div>");
					}
				}
				else {
					for (int i = 1; i <= (Util.PAG_ANT_DEP*2)+1; i++) {
						paginas.append(i==inicio ? "<div class=\"left left2px paginacao_item\">"+i+"</div>" : "<div class=\"left left2px\"><a class=\"paginacao nodecoration\" href=\""+acaoComp+"&inicio="+i+"\">"+i+"</a></div>");
					}
				}
				//Próxima
				if(inicio<pag) {
					paginas.append("<div class=\"left left5px\"><a class=\"paginacao nodecoration\" href=\""+acaoComp+"&inicio="+(inicio+1)+"\">&gt;</a></div>");				
				}
				
				//Última
				if(inicio<pag) {
					paginas.append("<div class=\"left left5px\"><a class=\"paginacao nodecoration\" href=\""+acaoComp+"&inicio="+pag+"\">&gt;&gt;</a></div>");
				}
			}
			paginas.append("<br class='clear'/></div>");
		}
		
		return paginas;
		
	}
	
	/**
	 * Retira a acentuação de uma determina sequência de caracteres
	 * @param texto
	 * @return O parâmetro <i>texto</i> sem acentuação
	 */
	public static String retiraAcento(String texto){
		String retorno = "";
		for (int ln = 0; ln < texto.length(); ln++){
			switch (texto.charAt(ln)){
				case 'á':
				case 'à':
				case 'ä':
				case 'ã':
				case 'â':
                {
                	retorno += "a";
                    break;
                }
				case 'Á':
				case 'À':
				case 'Ä':
				case 'Ã':
				case 'Â':
                {
                	retorno += "A";
                    break;
                }
                case 'é':
                case 'è':
                case 'ë':
                case 'ê':
                {
                    retorno += "e";
                    break;
                }
                case 'É':
                case 'È':
                case 'Ë':
                case 'Ê':
                {
                    retorno += "E";
                    break;
                }
                case 'í':
                case 'ì':
                case 'ï':
                {
                    retorno += "i";
                    break;
                }
                case 'Í':
                case 'Ì':
                case 'Ï':
                {
                    retorno += "I";
                    break;
                }
                case 'ó':
                case 'ò':
                case 'ö':
                case 'õ':
                case 'ô':
                {
                    retorno += "o";
                    break;
                }
                case 'Ó':
                case 'Ò':
                case 'Õ':
                case 'Ô':
                case 'Ö':
                {
                    retorno += "O";
                    break;
                }
                case 'ú':
                case 'ù':
                case 'ü':
                {
                    retorno += "u";
                    break;
                }
                case 'Ú':
                case 'Ù':
                case 'Ü':
                {
                    retorno += "U";
                    break;
                }
                case 'ç':
                {
                    retorno += "c";
                    break;
                }
                case 'Ç':
                {
                    retorno += "C";
                    break;
                }
                default :
                {
                    retorno += texto.charAt(ln);
                }
            }
		}
		return retorno;
	}
	
	/**
	 * Encripta e codifica(codificação para URL) uma sequência de caracteres
	 * @param p - sequência de caracteres a ser alterada
	 * @return Parâmetro alterado
	 */
	public static String criptografa(String p) {
		
		try {
			
			return URLEncoder.encode((new CriptUtils(ResourceBundle.getBundle("cript").getString("chave"))).encrypt(p), "UTF-8");
			
		} catch (UnsupportedEncodingException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return null;
		}

	}
	
	/**
	 * Decripta e decodifica(codificação para URL) uma sequência de caracteres
	 * @param c - sequência de caracteres a ser alterada
	 * @return Parâmetro alterado
	 */
	public static String decriptografa(String c) {
		
		try {
			
			return URLEncoder.encode((new CriptUtils(ResourceBundle.getBundle("cript").getString("chave"))).encrypt(c), "UTF-8");
			
		} catch (UnsupportedEncodingException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return null;
		}
		
	}
	
	/**
	 * Extrai os parâmetros de uma requisição HTTP
	 * @param query - query string
	 * @return Objeto {@link HashMap} contendo os parâmetros
	 */
	public static HashMap<String, String> extraiParametros(String query) {
		
		if(query==null) { return null; }
		
		final HashMap<String, String> param = new HashMap<String, String>();
		
		String[] tk = StringUtils.split(query, "&");
		
		for (String p : tk) {
			
			param.put(StringUtils.substringBefore(p, "="), StringUtils.substringAfter(p, "="));
			
		}
		
		return param;
		
	}
	
	/**
	 * Extrai os parâmetros de uma requisição HTTP. A string da requisição, neste caso, está criptografada.
	 * @param query - query string
	 * @return Objeto {@link HashMap} contendo os parâmetros
	 */
	public static HashMap<String, String> extraiParametrosCript(String query) {
		
		if(query==null) { return null; }
		
		CriptUtils cript = new CriptUtils(ResourceBundle.getBundle("cript").getString("chave"));
		
		query = cript.decrypt(query);
		
		final HashMap<String, String> param = new HashMap<String, String>();
		
		String[] tk = StringUtils.split(query, "&");
		
		for (String p : tk) {
			
			param.put(StringUtils.substringBefore(p, "="), StringUtils.substringAfter(p, "="));
			
		}
		
		return param;
		
	}
	
	/**
     * Gera chave criptografada a partir de um valor
     * @param chave
     * @return chave criptografada
     */
    public static String geraChaveMD5(final String chave) {
    	
    		Log logger = new Log("br.com.bropenmaps.util");
    	
            MessageDigest md = null;
            byte[] buffer = { 'r', 's', 'a', 'l', 'u', 'm' };
            try {
                    md = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                    logger.gravaErro("Não foi possível inicializar algoritmo MD5, gerando chave a partir da hora atual em milisegundos",e);
                    return String.valueOf((new Date()).getTime());
            }
            if (chave == null) {
                    return String.valueOf((new Date()).getTime());
            }
            md.update(buffer);
            byte[] chaveBytes = md.digest(chave.getBytes());
            StringBuilder t = new StringBuilder("");
            for (int i = 0; i < chaveBytes.length; i++) {
                    t.append(chaveBytes[i]);
            }
            return t.toString();
    }
	
	/**
	 * Reduz o tamanho de uma imagem proporcionalmente
	 * @param midiaLargura
	 * @param midiaAltura
	 * @return Objeto {@link HashMap} contendo as novas medidas. Elas podem ser acessadas pelas chaves <i>largura</i> e <i>altura</i>
	 */
	public static HashMap<String, Integer> reduzTamanhoImagem(int midiaLargura, int midiaAltura, int larguraMax, int alturaMax) {
		
		HashMap<String, Integer> tam = new HashMap<String, Integer>();
		
		int novaLargura = larguraMax;
		
		int novaAltura = alturaMax;
		
        double thumbRatio = (double) larguraMax / (double) alturaMax;
        
        double imageRatio = 0;
        
        if(midiaLargura!=-1 || midiaAltura!=-1) {
        
        	imageRatio = (double) midiaLargura / (double) midiaAltura;

            if (thumbRatio < imageRatio) {
            	
            	novaAltura = (int) (novaLargura / imageRatio);
                    
            } else {
            	
            	novaLargura = (int) (novaAltura * imageRatio);
                    
            }
            
            tam.put("largura", novaLargura);
            
            tam.put("altura", novaAltura);
            
        }
        
        else {
        	
        	tam.put("largura", 0);
            
            tam.put("altura", 0);
            
        }
		return tam;
	}
	
	/**
	 * Valida um endereço de email
	 * @param email - endereço a ser validado
	 * @return O valor true se o email é válido, false em caso contrário
	 */
	public static boolean validaEmail(String email) {
		
	      //Set the email pattern string
	      Pattern p = Pattern.compile(".+@.+\\.[a-z]+");

	      //Match the given string with the pattern
	      Matcher m = p.matcher(email);

	      //check whether match is found 
	      boolean matchFound = m.matches();

	      if (matchFound) return true; else return false;
		
	}
	
	/**
	 * Valida um formulário. Dois parâmetros são passados. O primeiro é um estrutura que representa os campos do formulário, esta estrutura é formada por
	 * uma coleção de pares nome do campo/valor do campo. O segundo parâmtetro é também uma coleção formada por pares nome do campo/tipo de validação.
	 * @param params - parâmetros do formulário a serem validados
	 * @param tipo - tipo de validação para cada parêmetro
	 * @return Estrutura contendo os erros encontrados, que uma coleção de pares nome do campo/erro encontrado.
	 */
	public static HashMap<String, String> validaForm(final HashMap<String, String> params, final HashMap<String, Integer[]> tipo) {
		
		Set<String> chaves = params.keySet();
		
		HashMap<String, String> erros = new HashMap<String, String>();
		
		Integer[] ts = null;
		
		String tk[] = null;
		
		for (String s : chaves) {
			
			ts = tipo.get(s);
			
			for (Integer t : ts) {
				
				if(t.equals(OBRIGATORIO)) {
					
					if(params.get(s)==null || params.get(s).equals("")) {
						
						erros.put(s, "Campo obrigatório.");
						
					}
					
				}
				
				else if(t.equals(EMAIL)) {
					
					if(params.get(s)!=null) {
						
						tk = StringUtils.split(params.get(s), ",");
						
						for (int i = 0; i < tk.length; i++) {
							
							if(!validaEmail(StringUtils.trim(tk[i]))) {
								
								erros.put(s, "Informe email(s) válido(s).");
								
								break;
								
							}
							
						}
						
					}
					
				}
				
			}
			
		}
		
		return erros;
		
	}
	
	/**
	 * Retira do telefone todos caracteres que não sejam números
	 * @param tel - telefone que se deseja remover os caracteres indesejados
	 * @return Somente os números do telefone
	 */
	public static String limpaTelefones(String tel) {
		
		if(tel==null) {
			
			return "";
			
		}
		
		String novo = "";
		
		if(tel.charAt(0)!='0') {
			
			tel = "0" + tel;
			
		}
		
		for (int i = 0; i < tel.length(); i++) {
			
			if(StringUtils.isNumeric(String.valueOf(tel.charAt(i)))) {
				
				novo += tel.charAt(i);
				
			}
			
		}
		
		return novo;
		
	}
	
	/**
	 * Corta o texto no espaço mais próximo do limite e acrescenta retecencias.
	 * 
	 * @param texto
	 * @param limite
	 * @return
	 */
	public static String cortaTexto(String texto, Integer limite){
		texto += " ";
		Integer i = limite;
		if (texto.length() > limite){
			while (texto.charAt(i) != ' ' && i < texto.length()){
				i++;
			}
			texto = StringUtils.substring(texto, 0, i)+"...";
		}
		
		return texto;
	}
	
}
