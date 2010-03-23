package br.com.bropenmaps.dao;

import java.util.HashMap;

import br.com.bropenmaps.dao.interfaces.IDAO;
import br.com.bropenmaps.util.Log;

/**
 * FÃ¡brica de objetos do tipo DAO
 * @author Rafael Melo Salum
 *
 */
public class DAOFactory {
	
	//Pool que armazena objetos DAO
	private final static HashMap<String, IDAO<?>> cache = new HashMap<String, IDAO<?>>();
	//Pacote dos objetos DAO
	private final static String pacoteDAO = "br.com.bropenmaps.dao";
	//Classe que encapsula em uma classe o comportamento de log
	private final static Log factoryLog = new Log("br.com.bropenmaps.dao.DAOFactory");
	
	/**
	 * Construtor da classe
	 */
	private DAOFactory() {
		
	}
	
	/**
	 * Retorna um instância de objeto de acesso a dados
	 * @param nome - nome class de acesso
	 * @return Um objeto que encapsula o acesso a base de dados {@link DAO}
	 */
	public static IDAO<?> getDAO(final String nome) {
		
		if(factoryLog.isDebugEnabled()) { factoryLog.gravaLog("Iniciando getDAO..."); }
		
		if(nome==null) { return null; }
		
		final String nomeCompleto = pacoteDAO + "." + nome;
		
		if(factoryLog.getLogger().isDebugEnabled()) { factoryLog.gravaLog("Buscando o objeto da classe "+nomeCompleto+" no pool de DAOs..."); }
		
		IDAO<?> dao = cache.get(nome);
		
		if(dao==null) {
		
			if(factoryLog.isDebugEnabled()) { factoryLog.gravaLog("Objeto da classe "+nomeCompleto+" não encontrado..."); }
			
			try {
			
				if(factoryLog.isDebugEnabled()) { factoryLog.gravaLog("Criando novo objeto da classe "+nomeCompleto+" ..."); }
				
				dao = (IDAO<?>) Class.forName(nomeCompleto).newInstance();
				
				if(factoryLog.isDebugEnabled()) { factoryLog.gravaLog("Colocando objeto da classe "+nomeCompleto+" no pools de DAOs..."); }
				
				cache.put(nome, dao);
				
			} catch (InstantiationException e) {
				
				factoryLog.gravaErro("Impossível instanciar "+nomeCompleto, e);
				
			} catch (IllegalAccessException e) {
				
				factoryLog.gravaErro("Acesso inválido a "+nomeCompleto, e);
				
			} catch (ClassNotFoundException e) {
				
				factoryLog.gravaErro(nomeCompleto+" não existe!", e);
				
			}
			
		}
		
		return dao;
	}

}