package br.com.bropenmaps.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import br.com.zymboo.commons.util.CriptUtils;

/**
 * Classe utilitária para geração de códigos HTML
 * @author Rafael Melo Salum
 *
 */
public class HtmlUtil {
	
	public static final int ALTURA_MAX = 40;
	
	public static final int LARGURA_MAX = 136;
	
	private static Log logger = new Log("br.com.bropenmaps.util.Util");
	
	/**
	 * Gera o código de uma tag IMG do HTML
	 * @param filenameCript - nome criptografado do arquivo da imagem
	 * @param idTag - id da tag HTML
	 * @return Código da tag HTML
	 */
	public static String geraTagImg(String filenameCript, String idTag) {
		
		if(filenameCript==null) {
			
			try {
				
				return "<img id='"+idTag+"' src='GeraImagem?p="+URLEncoder.encode((new CriptUtils(ResourceBundle.getBundle("cript").getString("chave"))).encrypt(ResourceBundle.getBundle("config").getString("path_repositorio")+"imgs_padrao"+System.getProperty("file.separator")+"sem_logo.png"), "UTF-8")+"' class='noborder'/>";
				
			} catch (UnsupportedEncodingException e1) {
				
				logger.gravaErro("Encode não suportado.", e1);
				
				return "<img id='"+idTag+"' src='' class='noborder' height='42'/>";
				
			}
			
		}
		
		String filename = Util.decoder((filenameCript));
		
		CriptUtils cript = new CriptUtils(ResourceBundle.getBundle("cript").getString("chave"));
		
		filename = cript.decrypt(filename);
		
		if(filename != null && !"".equals(filename)) {
			
			BufferedImage bsrc = null;
			
			try {
				
				bsrc = ImageIO.read(new File(filename));
				
			} catch (IOException e) {
				
				try {
					
					return "<img id='"+idTag+"' src='GeraImagem?p="+URLEncoder.encode((new CriptUtils(ResourceBundle.getBundle("cript").getString("chave"))).encrypt(ResourceBundle.getBundle("config").getString("path_repositorio")+"imgs_padrao"+System.getProperty("file.separator")+"sem_logo.png"), "UTF-8")+"' class='noborder'/>";
					
				} catch (UnsupportedEncodingException e1) {
					
					logger.gravaErro("Encode não suportado.", e1);
					
					return "<img id='"+idTag+"' src='' class='noborder' height='42'/>";
					
				}
				
			}
			
			if(bsrc.getWidth()<=LARGURA_MAX && bsrc.getHeight()<=ALTURA_MAX) {
				
				return "<img id='"+idTag+"' src='GeraImagem?p="+filenameCript+"' class='noborder' height='"+bsrc.getHeight()+"' width='"+bsrc.getWidth()+"' />";
				
			}
			
			else {
				
				final HashMap<String , Integer> novoTamanho = Util.reduzTamanhoImagem(bsrc.getWidth(), bsrc.getHeight(), LARGURA_MAX, ALTURA_MAX);
				
				return "<img id='"+idTag+"' src='GeraImagem?p="+filenameCript+"' class='noborder' height='"+novoTamanho.get("altura")+"' width='"+novoTamanho.get("largura")+"' />";
				
			}

		}
		
		else {
			
			try {
				
				return "<img id='"+idTag+"' src='GeraImagem?p="+URLEncoder.encode((new CriptUtils(ResourceBundle.getBundle("cript").getString("chave"))).encrypt(ResourceBundle.getBundle("config").getString("path_repositorio")+"imgs_padrao"+System.getProperty("file.separator")+"sem_logo.png"), "UTF-8")+"' class='noborder'/>";
				
			} catch (UnsupportedEncodingException e1) {
				
				logger.gravaErro("Encode não suportado.", e1);
				
				return "<img id='"+idTag+"' src='' class='noborder' height='42'/>";
				
			}
			
		}
		
	}
	
}