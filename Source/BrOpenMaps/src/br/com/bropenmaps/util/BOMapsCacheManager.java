package br.com.bropenmaps.util;

import java.util.ArrayList;
import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class BOMapsCacheManager {

	private static BOMapsCacheManager cManager;
		
	final private static Log logger = new Log("br.com.bropenmaps.util.BOMapsCacheManager");
	
	public static BOMapsCacheManager getInstance() {
		
		if(cManager==null) {
			
			CacheManager.create();
			
			CacheManager.getInstance().addCache(new Cache("bropenmaps", 5000, false, false, 120, 60));
			
			CacheManager.getInstance().addCache(new Cache("clima", 5000, false, false, 3600, 0));
			
			CacheManager.getInstance().addCache(new Cache("paginacao", 5000, false, false, 3600, 0));			
			
			cManager = new BOMapsCacheManager();
			
			return cManager;
			
		} else {
			
			return cManager;
			
		}
		
	}
	
	
	public boolean exists(String cache) {
		
		return CacheManager.getInstance().cacheExists(cache);
		
	}
	
	
	public void put(String chave, Object obj, String nomeCache) {
		
		logger.gravaLog("Colocando objeto no cache " + nomeCache + "... id: " + chave);
		
		if(!exists(nomeCache)) {
			
			logger.gravaLog("Cache '" + nomeCache + "' não existe. Criando...");
			
			CacheManager.getInstance().addCache(new Cache(nomeCache, 5000, false, false, 120, 60));
			
		}
		
		(CacheManager.getInstance().getCache(nomeCache)).put(new Element(chave, obj));
		
	}
	
	
	public Object get(String chave, String nomeCache) {
		
		logger.gravaLog("Obtendo objeto do cache "+nomeCache+"... id: " + chave);
		
		if(!exists(nomeCache)) {
			
			return null;
			
		}
		
		final Element element = (CacheManager.getInstance().getCache(nomeCache)).get(chave);
		
		if(element!=null) {
			
			return element.getObjectValue();
			
		} else {
			
			return null;
			
		}
		
	}
	
	
	public void removeCache(String nomeCache) {
		
		CacheManager.getInstance().removeCache(nomeCache);
		
	}
	
	
	@SuppressWarnings("unchecked")
	public ArrayList getAll(String nomeCache) {
		
		logger.gravaLog("Obtendo todos os objetos do cache "+nomeCache);
		
		final ArrayList objetos = new ArrayList<Object>();
		
		if(exists(nomeCache)) {
			
			final Cache cache = CacheManager.getInstance().getCache(nomeCache);
			
			final List<String> chaves = cache.getKeys();
			
			Element element = null;
			
			for (String chave : chaves) {
				
				element = cache.get(chave);
				
				if(element!=null) {
					
					objetos.add(element.getObjectValue());
					
				}
				
			}
		
		}
		
		return objetos;
		
	}
	
} 