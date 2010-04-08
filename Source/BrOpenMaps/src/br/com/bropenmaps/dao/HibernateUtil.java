package br.com.bropenmaps.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import br.com.bropenmaps.model.Cidade;
import br.com.bropenmaps.model.Endereco;
import br.com.bropenmaps.model.Estabelecimento;
import br.com.bropenmaps.model.RamoAtividade;
import br.com.bropenmaps.model.Termo;
import br.com.bropenmaps.util.Log;

/**
 * Classe utilitária do hibernate. Ela realiza a inicialização com o banco e oferece conexões com o mesmo.
 * @author Daniel Melo
 *
 */
public class HibernateUtil {
	
	private static final SessionFactory sessionFactory;

	private static Log log = new Log("br.com.bropenmaps.dao.HibernateUtil");

	static {
		try {
			sessionFactory = new AnnotationConfiguration().addPackage("br.com.bropenmaps.model")	
				.addAnnotatedClass(RamoAtividade.class)
				.addAnnotatedClass(Termo.class)
				.addAnnotatedClass(Estabelecimento.class)
				.addAnnotatedClass(Endereco.class)
				.addAnnotatedClass(Cidade.class)				
				.buildSessionFactory();
		} catch (Throwable ex) {
			log.gravaErro("Erro ao criar a SessionFactory inicial...", ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	private static final ThreadLocal<Session> session = new ThreadLocal<Session>();
	
	/**
	 * Obtém a sessão atual com o banco ou cria uma nova sessão.
	 * @return Objeto {@link Session} que representa uma ou mais conexões com o banco
	 * @throws HibernateException
	 */
	public static Session currentSession() throws HibernateException {
		Session s = (Session) session.get();
		// Abre a sessÃ£o se ainda nÃ£o existir alguma aberta
		if (s == null) {
			s = sessionFactory.openSession();
			session.set(s);
		}
		return s;
	}
	
	/**
	 * Fecha uma sessão do hibernate
	 * @throws HibernateException
	 */
	public static void closeSession() throws HibernateException {
		Session s = (Session) session.get();
		session.set(null);
		if (s != null) {
			s.close();
		}
	}
	
	/**
	 * Retorna a fábrica de sessões do hibernate.
	 * @return Fábrica de sessões
	 */
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}	
}