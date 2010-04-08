package br.com.bropenmaps.dao;

import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Restrictions;

import br.com.bropenmaps.model.RamoAtividade;
import br.com.bropenmaps.model.Termo;
import br.com.bropenmaps.util.Util;

/**
 * Classe que faz acesso ao banco de dados relacionados a entidade {@link RamoAtividade}
 * @author Rafael Melo Salum
 *
 */
public class RamoAtividadeDAO extends DAO<RamoAtividade> {

	/**
	 * Construtor da classe
	 */
	protected RamoAtividadeDAO() {
		
		super(RamoAtividade.class);
		
	}
	
	/**
	 * Busca uma entidade {@link RamoAtividade} pelo seu id externo, ou seja, o mesmo id encontrado em outros sites do gênero
	 * @param idExterno - id da categoria encontrada em outros sites do genêro
	 * @param termo - id do termo relacionado
	 * @return Objeto {@link RamoAtividade}
	 */
	public RamoAtividade buscaPelaIdExterno(Long idExterno, Integer termo) {
		
		return (RamoAtividade) HibernateUtil.currentSession().createCriteria(RamoAtividade.class).add(Restrictions.eq("ramoatividadeIdExterno", idExterno)).add(Restrictions.eq("termo.id", termo)).uniqueResult();
		
	}
	
	/**
	 * Busca as entidade {@link RamoAtividade} relacionadas a uma entidade {@link Termo}
	 * @param termo - id do termo
	 * @return Lista de objetos {@link RamoAtividade}
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<RamoAtividade> buscaPeloTermo(Integer termo) {
		
		return (ArrayList<RamoAtividade>) HibernateUtil.currentSession().createCriteria(RamoAtividade.class).add(Restrictions.eq("termo.id", termo)).list();
		
	}
	
	/**
	 * Busca uma entidade {@link RamoAtividade} pelo seu nome
	 * @param nome - nome do ramo de atividade
	 * @return Lista de objetos {@link RamoAtividade}
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<RamoAtividade> buscaPeloNome(String nome) {
		
		if(nome==null || nome.equals("")) {
			
			return new ArrayList<RamoAtividade>();
			
		}
		
		String novo = "";
		
		if(nome!=null) {
			
			if(nome.length()==5) {
					
				novo = StringUtils.substring(nome, 0, nome.length()-1);
				
				nome = novo;
			}
			
			else if(nome.length()>5) {
				
				novo = StringUtils.substring(nome, 0, nome.length()-2);
				
				nome = novo;
				
			}
		
		}
		
		ArrayList<RamoAtividade> ras = (ArrayList<RamoAtividade>) HibernateUtil.currentSession().createSQLQuery("select * from ramo_atividade where ramo_atividade ~* ?").addEntity(RamoAtividade.class).setString(0, "(.*?)"+ Util.regExpPalavrasAcentuadas(StringUtils.lowerCase(nome))+"(.*?)").list();
		
		if(ras==null || ras.size()==0) {
			
			ras = (ArrayList<RamoAtividade>) HibernateUtil.currentSession().createSQLQuery("select ra.* from ramo_atividade ra inner join termos t on t.id=ra.id_termo where t.termo ~* ?").addEntity(RamoAtividade.class).setString(0, "(.*?)"+Util.regExpPalavrasAcentuadas(StringUtils.lowerCase(nome))+"(.*?)").list();
			
		}
		
		return ras;
		
	}
	
}