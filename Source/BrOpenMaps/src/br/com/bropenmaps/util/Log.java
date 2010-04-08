package br.com.bropenmaps.util;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;


/**
 * Classe que encapsula todo o comportamento de log do sistema
 * @author Rafael Melo Salum
 *
 */
public class Log {
	private Logger logger = null;
	
	public Log(final String nome) {
		logger = Logger.getLogger(nome);
	}
	
	public void gravaErro(final String msg, final Throwable e) {
		logger.log(Level.ERROR, msg, e); 
	}
	
	public void gravaErro(final String msg) {
		logger.log(Level.ERROR, msg); 
	}
	
	public void gravaLog(String msg) {
		logger.log(Level.INFO, msg);
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}
	
	public boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}
}