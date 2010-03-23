package br.com.bropenmaps.dao;

import java.lang.reflect.Field;
import java.util.Collection;

import org.hibernate.Session;
import org.hibernate.criterion.Order;

import br.com.bropenmaps.dao.interfaces.IDAO;
import br.com.bropenmaps.util.Log;

/**
 * Classe genérica de acesso a base de dados
 * @author Rafael Melo Salum
 *
 * @param <T> - entidade relacionada aos acessos
 */
public abstract class DAO<T> implements IDAO<T> {
	
	private Log logger = new Log("br.com.bropenmaps.dao.DAO");
	
	private Class<T> classePersitente;
	
	/**
	 * Construtor da classe
	 * @param classePersistente
	 */
	public DAO(final Class<T> classePersistente) {
		this.classePersitente = classePersistente;
	}
	
	/**
	 * Busca uma entidade no banco de dados pelo seu id
	 * @param id - id da entidade no banco
	 */
	@SuppressWarnings("unchecked")
	public T buscaPelaChave(final Long id) {
		if(logger.isDebugEnabled()) {logger.gravaLog("Buscando "+classePersitente+" com id "+id);}
		return (T) HibernateUtil.currentSession().load(classePersitente, id);
	}
	
	/**
	 * Busca uma entidade no banco de dados pelo seu id
	 * @param id - id da entidade no banco
	 */
	@SuppressWarnings("unchecked")
	public T buscaPelaChave(final Integer id) {
		if(logger.isDebugEnabled()) {logger.gravaLog("Buscando "+classePersitente+" com id "+id);}
		return (T) HibernateUtil.currentSession().load(classePersitente, id);
	}
	
	/**
	 * Insere uma entidade genérica no banco. Este método realiza o commit no banco após o seu término.
	 * @param bean - objeto genérico da entidade
	 */
	public void inserir(final T bean) {
		if(logger.isDebugEnabled()) {logger.gravaLog("Inserindo "+bean);}
		final Session session = HibernateUtil.currentSession();
		session.beginTransaction();
		session.save(bean);
		session.getTransaction().commit();
	}
	
	/**
	 * Insere uma entidade genérica no banco. Este método não realiza o commit no banco após o seu término.
	 * @param bean - objeto genérico da entidade
	 */
	public void inserirTx(final T bean) {
		if(logger.isDebugEnabled()) {logger.gravaLog("Inserindo "+bean);}
		final Session session = HibernateUtil.currentSession();
		session.save(bean);
	}
	
	/**
	 * Atualiza uma entidade genérica no banco. Este método realiza o commit no banco após o seu término.
	 * @param bean - objeto genérico da entidade
	 */
	public void atualizar(final T bean) {
		if(logger.isDebugEnabled()) {logger.gravaLog("Atualizando "+bean);}
		final Session session = HibernateUtil.currentSession();
		session.beginTransaction();
		session.update(bean);
		session.getTransaction().commit();
	}
	
	/**
	 * Atualiza uma entidade genérica no banco. Este método não realiza o commit no banco após o seu término.
	 * @param bean - objeto genérico da entidade
	 */
	public void atualizarTx(final T bean) {
		if(logger.isDebugEnabled()) {logger.gravaLog("Atualizando "+bean);}
		HibernateUtil.currentSession().update(bean);
	}
	
	/**
	 * Busca todas as entidades do tipo definido do banco de dados.
	 * @return Lista com objetos representando todas as entidades do tipo definido no banco de dados.
	 */
	@SuppressWarnings("unchecked")
	public Collection<T> buscaTodos() {
		if(logger.isDebugEnabled()) {logger.gravaLog("Buscanco todos de "+classePersitente);}
		Field[] atributos = classePersitente.getDeclaredFields();
		String campoId = null;
		for (int i = 0; i < atributos.length; i++) {
			if(atributos[i]!=null && atributos[i].getName().endsWith("Id")) {
				campoId = atributos[i].getName();
			}
		}
		if(campoId==null) {
			return HibernateUtil.currentSession().createCriteria(classePersitente).list();
		}
		else {
			return HibernateUtil.currentSession().createCriteria(classePersitente).addOrder(Order.desc(campoId)).list();
		}
	}
	
	/**
	 * Remove uma entidade no banco de dados.
	 * @param id - id da entidade no banco
	 */
	public void remover(final Long id) {
		final Session session = HibernateUtil.currentSession();
		session.beginTransaction();
		Object obj = session.load(classePersitente, id);
		session.delete(obj);
		session.getTransaction().commit();
	}
	
	/**
	 * Remove uma entidade no banco de dados.
	 * @param id - id da entidade no banco
	 */
	public void remover(final Integer id) {
		final Session session = HibernateUtil.currentSession();
		session.beginTransaction();
		Object obj = session.load(classePersitente, id);
		session.delete(obj);
		session.getTransaction().commit();
	}
	
	/**
	 * Remove uma entidade no banco de dados.
	 * @param bean - entidade a ser removida
	 */
	public void remover(final T bean) {
		final Session session = HibernateUtil.currentSession();
		session.beginTransaction();
		session.delete(bean);
		session.getTransaction().commit();
	}
}