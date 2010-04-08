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
 * Classe utilit�ria do hibernate. Ela realiza a inicializa��o com o banco e oferece conex�es com o mesmo.
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
	 * Obt�m a sess�o atual com o banco ou cria uma nova sess�o.
	 * @return Objeto {@link Session} que representa uma ou mais conex�es com o banco
	 * @throws HibernateException
	 */
	public static Session currentSession() throws HibernateException {
		Session s = (Session) session.get();
		// Abre a sessão se ainda não existir alguma aberta
		if (s == null) {
			s = sessionFactory.openSession();
			session.set(s);
		}
		return s;
	}
	
	/**
	 * Fecha uma sess�o do hibernate
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
	 * Retorna a f�brica de sess�es do hibernate.
	 * @return F�brica de sess�es
	 */
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}	
}