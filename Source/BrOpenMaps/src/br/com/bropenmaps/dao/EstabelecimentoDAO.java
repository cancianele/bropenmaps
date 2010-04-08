package br.com.bropenmaps.dao;

import java.util.ArrayList;
import java.util.LinkedHashSet;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Restrictions;

import br.com.bropenmaps.model.Endereco;
import br.com.bropenmaps.model.Estabelecimento;
import br.com.bropenmaps.model.RamoAtividade;
import br.com.bropenmaps.util.Util;

/**
 * Classe que faz acesso ao banco de dados relacionados a entidade {@link Estabelecimento}
 * @author Rafael Melo Salum
 *
 */
public class EstabelecimentoDAO extends DAO<Estabelecimento> {
	
	/**
	 * Construtor da classe
	 */
	protected EstabelecimentoDAO() {
		
		super(Estabelecimento.class);
		
	}
	
	/**
	 * Busca todos os estabelecimentos vips do banco
	 * @return Lista de objetos do tipo {@link Estabelecimento}
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Estabelecimento> buscaVips() {
		
		return (ArrayList<Estabelecimento>) HibernateUtil.currentSession().createCriteria(Estabelecimento.class).add(Restrictions.eq("estabelecimentoVip", true)).list();
	
	}
	
	/**
	 * Busca uma porção dos estabelecimentos inativos do banco
	 * @param inicio - registro inicial no banco
	 * @param max - número máximo de registros a serem recuperados
	 * @param total - recebe a quantidade totol de estabelecimentos inativos
	 * @return Lista de objetos do tipo {@link Estabelecimento}
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Estabelecimento> buscaInativosPaginado(int inicio, int max, StringBuilder total) {
					
		String sql  = "select count(*) from estabelecimento where estabelecimentoativo = false;";
		
		total.append(HibernateUtil.currentSession()
			.createSQLQuery(sql)
			.uniqueResult());
		    				
		return (ArrayList<Estabelecimento>) HibernateUtil.currentSession()
			.createCriteria(Estabelecimento.class)
			.add(Restrictions.eq("estabelecimentoAtivo", false))
			.setFirstResult(inicio)
			.setMaxResults(max)
			.list();
	
	}
	
	/**
	 * Busca um estabelecimento pelo seu nome
	 * @param nome - nome do estabelecimento
	 * @return Estabelecimento buscado, ou null se estabelecimento não for encontrado
	 */
	public Estabelecimento buscaPeloNome(final String nome) {
		
		return (Estabelecimento) HibernateUtil.currentSession().createCriteria(Estabelecimento.class).add(Restrictions.eq("estabelecimentoNome", nome)).uniqueResult();
		
	}
	
	/**
	 * Retorna uma lista de estabelecimentos. Essa lista contém objetos que combinam com o filtro estabelecido pelos parâmetros informados ao método.
	 * Ele traz uma porção desses estabelecimentos determinada pelos parâmetros <i>inicio</i> e <i>max</i>.
	 * @param empresa - nome da empresa a ser encontrada. Aceita <code>null</code>.
	 * @param end - endereco da empresa a ser encontrada. Aceita <code>null</code>.
	 * @param cidade - cidade da empresa a ser encontrada. Aceita <code>null</code>.
	 * @param estado - estado da empresa a ser encontrada. Aceita <code>null</code>.
	 * @param tipoEst - categoria da empresa a ser encontrada. Aceita <code>null</code>.
	 * @param bairro - bairro da empresa a ser encontrada. Aceita <code>null</code>.
	 * @param cep - cep da empresa a ser encontrada. Aceita <code>null</code>.
	 * @param inicio - registro inicial da busca.
	 * @param max - máximo de registros a serem retornados.
	 * @param total - recebe o total de estabelecimentos que combinam com o filtro.
	 * @return Lista de objetos do tipo {@link Estabelecimento}
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Estabelecimento> buscaPorEnderecoEmpresaTipoPaginado(final String empresa, String end, final String cidade, final String estado, final String tipoEst, final String bairro, final String cep, int inicio, int max, StringBuilder total) {
		
		end = StringUtils.replace(end, ".", "");
		
		end = StringUtils.replace(end, ",", " ");
		
		String[] endTk = StringUtils.split(end);
		
		int c = 0;
		
		String qCidEst = "";
		
		if(cidade!=null && !cidade.equals("")) {
			
			qCidEst = " ed.enderecoCidade ~* '(.*?)" + Util.regExpPalavrasAcentuadas(StringEscapeUtils.escapeSql(cidade)) + "(.*?)'";
			
		}
		
		/*if(estado!=null && !estado.equals("")) {
			
			qCidEst += " and ed.enderecoEstado ilike '" + estado + "'";
			
		}*/
		
		String qEnd = "";
		
		if(endTk!=null && endTk.length==1) {
			
			qEnd = " and ed.enderecoDescricao ~* '(.*?)"+Util.regExpPalavrasAcentuadas(StringEscapeUtils.escapeSql(endTk[0]))+"(.*?)' ";
			
		}
		
		else if(endTk!=null && endTk.length > 1){
		
			qEnd += " and ("; 
				
			for (String string : endTk) {
				
				if(string.equalsIgnoreCase("r")) {
					qEnd += " (ed.enderecoDescricao ilike 'Rua%' OR ed.enderecoDescricao ilike 'R %' OR ed.enderecoDescricao ilike 'R.%')";
				}
				else if(string.equalsIgnoreCase("av") || string.equalsIgnoreCase("avn") || string.equalsIgnoreCase("avd") || string.equalsIgnoreCase("a")) {
					qEnd += " (ed.enderecoDescricao ilike 'Avenida%' OR ed.enderecoDescricao ilike 'Av %' OR ed.enderecoDescricao ilike 'Av.%')";
				}
				else if(string.equalsIgnoreCase("tr") || string.equalsIgnoreCase("trv") || string.equalsIgnoreCase("trs") || string.equalsIgnoreCase("ts") || string.equalsIgnoreCase("tvs") || string.equalsIgnoreCase("trave")) {
					qEnd += " (ed.enderecoDescricao ilike 'Travessa%' OR ed.enderecoDescricao ilike 'Tr %' OR ed.enderecoDescricao ilike 'Tr.%')";
				}						
				else if(string.equalsIgnoreCase("al") || string.equalsIgnoreCase("alm") || string.equalsIgnoreCase("ala") || string.equalsIgnoreCase("alam") || string.equalsIgnoreCase("alamd") || string.equalsIgnoreCase("almd")) {
					qEnd += " (ed.enderecoDescricao ilike 'Alameda%' OR ed.enderecoDescricao ilike 'Alm %' OR ed.enderecoDescricao ilike 'Alm.%' OR ed.enderecoDescricao ilike 'Al %' OR ed.enderecoDescricao ilike 'Al.%')";
				}
				else if(string.equalsIgnoreCase("pc") || string.equalsIgnoreCase("pç") || string.equalsIgnoreCase("pça") || string.equalsIgnoreCase("pca") || string.equalsIgnoreCase("prç") || string.equalsIgnoreCase("prc")) {
					qEnd += " (ed.enderecoDescricao ilike 'Praça%' OR ed.enderecoDescricao ilike 'Praca%' OR ed.enderecoDescricao ilike 'Pc %' OR ed.enderecoDescricao ilike 'Pc.%' OR ed.enderecoDescricao ilike 'Pç %' OR ed.enderecoDescricao ilike 'Pç.%')";
				}
				else {
					qEnd += " ed.enderecoDescricao ~* '(.*?)"+Util.regExpPalavrasAcentuadas(StringEscapeUtils.escapeSql(string))+"(.*?)'";
				}
				
				if(c!=endTk.length-1) {
					
					qEnd += " and";
					
				}
				
				else {
					
					qEnd += ") ";
					
				}
				
				c++;
				
			}
			
		}
		
		final ArrayList<RamoAtividade> ras = ((RamoAtividadeDAO) DAOFactory.getDAO("RamoAtividadeDAO")).buscaPeloNome(tipoEst);
		
		String idRas = ras.size()==0 ? "" : " ( er.ramoatividadeid in (";
		
		c = 0;
		
		for (RamoAtividade ramoAtividade : ras) {
			
			idRas += ramoAtividade.getRamoatividadeId();
			
			if(c!=ras.size()-1) {
				
				idRas += ",";
				
			}
			
			else {
				
				idRas += ")";
				
			}
			
			c++;
			
		}
		
		c = 0;
		
		String qEmpresa = "";
		
		if(empresa!=null && !empresa.equals("")) {
			
			String[] empTk = StringUtils.split(empresa);
			
			if(empTk.length==1) {
				
				qEmpresa = ((idRas!=null && !idRas.equals("")) ? " OR " : "") + " e.estabelecimentoNome ~* '(.*?)" + Util.regExpPalavrasAcentuadas(StringEscapeUtils.escapeSql(empTk[0])) + "(.*?)'  " + ((idRas!=null && !idRas.equals("")) ? " ) " : "");
				
			}
			
			else {
				
				qEmpresa = (idRas!=null && !idRas.equals("")) ? " OR " : ""; 
				
				for (int i = 0; i < empTk.length; i++) {
					
					qEmpresa += " e.estabelecimentoNome ~* '" + Util.regExpPalavrasAcentuadas(StringEscapeUtils.escapeSql(empTk[i])) + "(.*?)'";
					
					if(c!=empTk.length-1) {
						
						qEmpresa += " and";
						
					}
					
					c++;
					
				}
				
				 qEmpresa += ((idRas!=null && !idRas.equals("")) ? " ) " : "");
				
			}
			
		}
		
		if(qEmpresa.equals("")) {
			
			 idRas += ((idRas!=null && !idRas.equals("")) ? " ) " : "");
			
		}
		
		String qBairro = "";
		
		if(bairro!=null && !bairro.equals("")) {
			
			qBairro = ((qCidEst+qEnd).equals("")  ?  "" : " AND ") + " ed.enderecoBairro ~* '(.*?)" + Util.regExpPalavrasAcentuadas(StringEscapeUtils.escapeSql(bairro)) + "(.*?)' ";
			
		}
		
		String qCep = "";
		
		if(cep!=null && !cep.equals("")) {
			
			qCep = ((qCidEst+qEnd).equals("")  ?  "" : " AND ") + " ed.enderecoCep ~* '(.*?)" + Util.regExpPalavrasAcentuadas(StringEscapeUtils.escapeSql(cep)) + "(.*?)' ";
			
		}
		
		if(!(qCidEst+qEnd+qBairro+qCep).equals("") && idRas.equals("") && !qEmpresa.equals("")) {
			
			qEmpresa = "AND " + qEmpresa;
			
		}
		
		if(!(qCidEst+qEnd+qBairro+qCep).equals("") && !idRas.equals("")) {
			
			idRas = " AND " + idRas;
			
		}
		
		String sql = "";
		
		if(tipoEst!=null && !tipoEst.equals("") && (qBairro+qCep+idRas+qEmpresa).equals("")) {
			
			return new ArrayList<Estabelecimento>();
			
		}
		
		if(estado!=null && !estado.equals("")) {
			
			sql = "select count(*) from (select distinct on (ed.enderecodescricao, ed.enderecocidade) * from estabelecimento e inner join \""+StringUtils.lowerCase(estado)+"\" ed on  e.estabelecimentoAtivo=TRUE AND ed.estabelecimentoid=e.estabelecimentoid inner join est_ramoatividade er on e.estabelecimentoid=er.estabelecimentoid where"+qCidEst+qEnd+qBairro+qCep+" "+idRas+qEmpresa+") as total";
			
		}
		
		else {
			
			sql  = "select count(*) from (select distinct on (ed.enderecodescricao, ed.enderecocidade, ed.enderecoestado) * from estabelecimento e inner join endereco ed on e.estabelecimentoAtivo=TRUE AND ed.estabelecimentoid=e.estabelecimentoid inner join est_ramoatividade er on e.estabelecimentoid=er.estabelecimentoid where"+qCidEst+qEnd+qBairro+qCep+" "+idRas+qEmpresa+") as total";
			
		}

		total.append(HibernateUtil.currentSession()
			.createSQLQuery(sql)
			.uniqueResult());
	    
		if(estado!=null && !estado.equals("")) {
			
			sql = "select distinct on (ed.enderecodescricao, ed.enderecocidade) * from estabelecimento e inner join \""+StringUtils.lowerCase(estado)+"\" ed on e.estabelecimentoAtivo=TRUE AND ed.estabelecimentoid=e.estabelecimentoid inner join est_ramoatividade er on e.estabelecimentoid=er.estabelecimentoid where"+qCidEst+qEnd+qBairro+qCep+" "+idRas+qEmpresa+" limit ? offset ?";
			
		}
		
		else {
			
			sql  = "select distinct on (ed.enderecodescricao, ed.enderecocidade, ed.enderecoestado) * from estabelecimento e inner join endereco ed on e.estabelecimentoAtivo=TRUE AND ed.estabelecimentoid=e.estabelecimentoid inner join est_ramoatividade er on e.estabelecimentoid=er.estabelecimentoid where"+qCidEst+qEnd+qBairro+qCep+" "+idRas+qEmpresa+" limit ? offset ?";
			
		}

		final ArrayList<Estabelecimento> ests = (ArrayList<Estabelecimento>) HibernateUtil.currentSession()
			.createSQLQuery(sql)
			.addEntity(Estabelecimento.class)
			.setInteger(0, max)
		    .setInteger(1, inicio)
			.list();
		
		//final ArrayList<Estabelecimento> ests = new ArrayList<Estabelecimento>();
		
		//Estabelecimento est = null;
		
		for (Estabelecimento est : ests) {
			
			est.setCidadeBuscada(cidade);
			
			est.setEstadoBuscado(estado);
			
			est.setEnderecoBuscado(endTk);
			
			est.getPrimeiroEndereco();
			
		}
		
		return ests;
		
	}
	
	/**
	 * Retorna uma lista de estabelecimentos. Essa lista contém objetos que combinam com o filtro estabelecido pelos parâmetros informados ao método.
	 * Este método retorna todos os estabelecimentos ativos quanto os inativos.
	 * Ele traz uma porção desses estabelecimentos determinada pelos parâmetros <i>inicio</i> e <i>max</i>.
	 * @param empresa - nome da empresa a ser encontrada. Aceita <code>null</code>.
	 * @param end - endereco da empresa a ser encontrada. Aceita <code>null</code>.
	 * @param cidade - cidade da empresa a ser encontrada. Aceita <code>null</code>.
	 * @param estado - estado da empresa a ser encontrada. Aceita <code>null</code>.
	 * @param tipoEst - categoria da empresa a ser encontrada. Aceita <code>null</code>.
	 * @param bairro - bairro da empresa a ser encontrada. Aceita <code>null</code>.
	 * @param cep - cep da empresa a ser encontrada. Aceita <code>null</code>.
	 * @param inicio - registro inicial da busca.
	 * @param max - máximo de registros a serem retornados.
	 * @param total - recebe o total de estabelecimentos que combinam com o filtro.
	 * @return Lista de objetos do tipo {@link Estabelecimento}
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Estabelecimento> buscaEstabelecimentoGeralPaginado(final String empresa, String end, final String cidade, final String estado, final String tipoEst, final String bairro, final String cep, int inicio, int max, StringBuilder total) {
		
		end = StringUtils.replace(end, ".", "");
		
		end = StringUtils.replace(end, ",", " ");
		
		String[] endTk = StringUtils.split(end);
		
		int c = 0;
		
		String qCidEst = "";
		
		if(cidade!=null && !cidade.equals("")) {
			
			qCidEst = " ed.enderecoCidade ~* '(.*?)" + Util.regExpPalavrasAcentuadas(StringEscapeUtils.escapeSql(cidade)) + "(.*?)'";
			
		}
		
		/*if(estado!=null && !estado.equals("")) {
			
			qCidEst += " and ed.enderecoEstado ilike '" + estado + "'";
			
		}*/
		
		String qEnd = "";
		
		if(endTk!=null && endTk.length==1) {
			
			qEnd = " and ed.enderecoDescricao ~* '(.*?)"+Util.regExpPalavrasAcentuadas(StringEscapeUtils.escapeSql(endTk[0]))+"(.*?)' ";
			
		}
		
		else if(endTk!=null && endTk.length > 1){
		
			qEnd += " and ("; 
				
			for (String string : endTk) {
				
				if(string.equalsIgnoreCase("r")) {
					qEnd += " (ed.enderecoDescricao ilike 'Rua%' OR ed.enderecoDescricao ilike 'R %' OR ed.enderecoDescricao ilike 'R.%')";
				}
				else if(string.equalsIgnoreCase("av") || string.equalsIgnoreCase("avn") || string.equalsIgnoreCase("avd") || string.equalsIgnoreCase("a")) {
					qEnd += " (ed.enderecoDescricao ilike 'Avenida%' OR ed.enderecoDescricao ilike 'Av %' OR ed.enderecoDescricao ilike 'Av.%')";
				}
				else if(string.equalsIgnoreCase("tr") || string.equalsIgnoreCase("trv") || string.equalsIgnoreCase("trs") || string.equalsIgnoreCase("ts") || string.equalsIgnoreCase("tvs") || string.equalsIgnoreCase("trave")) {
					qEnd += " (ed.enderecoDescricao ilike 'Travessa%' OR ed.enderecoDescricao ilike 'Tr %' OR ed.enderecoDescricao ilike 'Tr.%')";
				}						
				else if(string.equalsIgnoreCase("al") || string.equalsIgnoreCase("alm") || string.equalsIgnoreCase("ala") || string.equalsIgnoreCase("alam") || string.equalsIgnoreCase("alamd") || string.equalsIgnoreCase("almd")) {
					qEnd += " (ed.enderecoDescricao ilike 'Alameda%' OR ed.enderecoDescricao ilike 'Alm %' OR ed.enderecoDescricao ilike 'Alm.%' OR ed.enderecoDescricao ilike 'Al %' OR ed.enderecoDescricao ilike 'Al.%')";
				}
				else if(string.equalsIgnoreCase("pc") || string.equalsIgnoreCase("pç") || string.equalsIgnoreCase("pça") || string.equalsIgnoreCase("pca") || string.equalsIgnoreCase("prç") || string.equalsIgnoreCase("prc")) {
					qEnd += " (ed.enderecoDescricao ilike 'Praça%' OR ed.enderecoDescricao ilike 'Praca%' OR ed.enderecoDescricao ilike 'Pc %' OR ed.enderecoDescricao ilike 'Pc.%' OR ed.enderecoDescricao ilike 'Pç %' OR ed.enderecoDescricao ilike 'Pç.%')";
				}
				else {
					qEnd += " ed.enderecoDescricao ~* '(.*?)"+Util.regExpPalavrasAcentuadas(StringEscapeUtils.escapeSql(string))+"(.*?)'";
				}
				
				if(c!=endTk.length-1) {
					
					qEnd += " and";
					
				}
				
				else {
					
					qEnd += ") ";
					
				}
				
				c++;
				
			}
			
		}
		
		final ArrayList<RamoAtividade> ras = ((RamoAtividadeDAO) DAOFactory.getDAO("RamoAtividadeDAO")).buscaPeloNome(tipoEst);
		
		String idRas = ras.size()==0 ? "" : " ( er.ramoatividadeid in (";
		
		c = 0;
		
		for (RamoAtividade ramoAtividade : ras) {
			
			idRas += ramoAtividade.getRamoatividadeId();
			
			if(c!=ras.size()-1) {
				
				idRas += ",";
				
			}
			
			else {
				
				idRas += ")";
				
			}
			
			c++;
			
		}
		
		c = 0;
		
		String qEmpresa = "";
		
		if(empresa!=null && !empresa.equals("")) {
			
			String[] empTk = StringUtils.split(empresa);
			
			if(empTk.length==1) {
				
				qEmpresa = ((idRas!=null && !idRas.equals("")) ? " OR " : "") + " e.estabelecimentoNome ~* '(.*?)" + Util.regExpPalavrasAcentuadas(StringEscapeUtils.escapeSql(empTk[0])) + "(.*?)'  " + ((idRas!=null && !idRas.equals("")) ? " ) " : "");
				
			}
			
			else {
				
				qEmpresa = (idRas!=null && !idRas.equals("")) ? " OR " : ""; 
				
				for (int i = 0; i < empTk.length; i++) {
					
					qEmpresa += " e.estabelecimentoNome ~* '" + Util.regExpPalavrasAcentuadas(StringEscapeUtils.escapeSql(empTk[i])) + "(.*?)'";
					
					if(c!=empTk.length-1) {
						
						qEmpresa += " and";
						
					}
					
					c++;
					
				}
				
				 qEmpresa += ((idRas!=null && !idRas.equals("")) ? " ) " : "");
				
			}
			
		}
		
		if(qEmpresa.equals("")) {
			
			 idRas += ((idRas!=null && !idRas.equals("")) ? " ) " : "");
			
		}
		
		String qBairro = "";
		
		if(bairro!=null && !bairro.equals("")) {
			
			qBairro = ((qCidEst+qEnd).equals("")  ?  "" : " AND ") + " ed.enderecoBairro ~* '(.*?)" + Util.regExpPalavrasAcentuadas(StringEscapeUtils.escapeSql(bairro)) + "(.*?)' ";
			
		}
		
		String qCep = "";
		
		if(cep!=null && !cep.equals("")) {
			
			qCep = ((qCidEst+qEnd).equals("")  ?  "" : " AND ") + " ed.enderecoCep ~* '(.*?)" + Util.regExpPalavrasAcentuadas(StringEscapeUtils.escapeSql(cep)) + "(.*?)' ";
			
		}
		
		if(!(qCidEst+qEnd+qBairro+qCep).equals("") && idRas.equals("") && !qEmpresa.equals("")) {
			
			qEmpresa = "AND " + qEmpresa;
			
		}
		
		if(!(qCidEst+qEnd+qBairro+qCep).equals("") && !idRas.equals("")) {
			
			idRas = " AND " + idRas;
			
		}
		
		String sql = "";
		
		if(tipoEst!=null && !tipoEst.equals("") && (qBairro+qCep+idRas+qEmpresa).equals("")) {
			
			return new ArrayList<Estabelecimento>();
			
		}
		
		if(estado!=null && !estado.equals("")) {
			
			sql = "select count(*) from (select distinct on (ed.enderecodescricao, ed.enderecocidade) * from estabelecimento e inner join \""+StringUtils.lowerCase(estado)+"\" ed on ed.estabelecimentoid=e.estabelecimentoid inner join est_ramoatividade er on e.estabelecimentoid=er.estabelecimentoid where"+qCidEst+qEnd+qBairro+qCep+" "+idRas+qEmpresa+") as total";
			
		}
		
		else {
			
			sql  = "select count(*) from (select distinct on (ed.enderecodescricao, ed.enderecocidade, ed.enderecoestado) * from estabelecimento e inner join endereco ed on ed.estabelecimentoid=e.estabelecimentoid inner join est_ramoatividade er on e.estabelecimentoid=er.estabelecimentoid where"+qCidEst+qEnd+qBairro+qCep+" "+idRas+qEmpresa+") as total";
			
		}

		total.append(HibernateUtil.currentSession()
			.createSQLQuery(sql)
			.uniqueResult());
	    
		if(estado!=null && !estado.equals("")) {
			
			sql = "select distinct on (ed.enderecodescricao, ed.enderecocidade) * from estabelecimento e inner join \""+StringUtils.lowerCase(estado)+"\" ed on ed.estabelecimentoid=e.estabelecimentoid inner join est_ramoatividade er on e.estabelecimentoid=er.estabelecimentoid where"+qCidEst+qEnd+qBairro+qCep+" "+idRas+qEmpresa+" limit ? offset ?";
			
		}
		
		else {
			
			sql  = "select distinct on (ed.enderecodescricao, ed.enderecocidade, ed.enderecoestado) * from estabelecimento e inner join endereco ed on ed.estabelecimentoid=e.estabelecimentoid inner join est_ramoatividade er on e.estabelecimentoid=er.estabelecimentoid where"+qCidEst+qEnd+qBairro+qCep+" "+idRas+qEmpresa+" limit ? offset ?";
			
		}

		final ArrayList<Estabelecimento> ests = (ArrayList<Estabelecimento>) HibernateUtil.currentSession()
			.createSQLQuery(sql)
			.addEntity(Estabelecimento.class)
			.setInteger(0, max)
		    .setInteger(1, inicio)
			.list();
		
		//final ArrayList<Estabelecimento> ests = new ArrayList<Estabelecimento>();
		
		//Estabelecimento est = null;
		
		for (Estabelecimento est : ests) {
			
			est.setCidadeBuscada(cidade);
			
			est.setEstadoBuscado(estado);
			
			est.setEnderecoBuscado(endTk);
			
		}
		
		return ests;
		
	}
	
	/**
	 * Busca os estabelecimentos contidos numa determinada rota
	 * @param termo - tipo de estabelecimento buscado
	 * @param endTk - array de partes dos enderecos de origem e destino
	 * @param cidadeOrg - cidade de origem
	 * @param estadoOrg - estado de origem
	 * @param cidadeDest - cidade de destino
	 * @param estadoDest - estado de destino
	 * @return Lista de objetos {@link Estabelecimento}
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Estabelecimento> buscaEstabelecimentosRota(final String termo, final ArrayList<String> endTk, final String cidadeOrg, final String estadoOrg, final String cidadeDest, final String estadoDest) {
		
		final ArrayList<RamoAtividade> ras = ((RamoAtividadeDAO) DAOFactory.getDAO("RamoAtividadeDAO")).buscaPeloNome(termo);
		
		StringBuilder idRas = ras.size()==0 ? new StringBuilder("()") : new StringBuilder("(");
		
		int c = 0;
		
		for (RamoAtividade ramoAtividade : ras) {
			
			idRas.append(ramoAtividade.getRamoatividadeId());
			
			if(c!=ras.size()-1) {
				
				idRas.append(",");
				
			}
			
			else {
				
				idRas.append(")");
				
			}
			
			c++;
			
		}
		
		c = 0;
		
		StringBuilder qEnd = new StringBuilder("");
		
		//StringBuilder qEndCompleto = new StringBuilder("");
		
		ArrayList<Endereco> ends = new ArrayList<Endereco>();
		
		if(endTk.size()==1) {
			
			if(cidadeOrg.equals(cidadeDest) && estadoOrg.equals(estadoDest)) {
				
				qEnd.append(" ed.enderecoCidade ~* '(.*?)"+Util.regExpPalavrasAcentuadas(cidadeOrg)+"(.*?)' and ed.enderecoEstado ~* '(.*?)"+Util.regExpPalavrasAcentuadas(estadoOrg)+"(.*?)' and ");
				
			}
			
			else if(estadoOrg.equals(estadoDest)) {
				
				qEnd.append(" ed.enderecoEstado ~* '(.*?)"+Util.regExpPalavrasAcentuadas(estadoOrg)+"(.*?)' and ");
				
			}
			
			qEnd.append(" ed.enderecoDescricao ~* '(.*?)"+Util.regExpPalavrasAcentuadas(endTk.get(0))+"(.*?)' ");
			
			ends.addAll(
					HibernateUtil.currentSession()
							.createSQLQuery("select distinct on (ed.enderecodescricao, ed.enderecocidade, ed.enderecoestado) * from est_ramoatividade er inner join endereco ed on er.ramoatividadeid in "+idRas+" and ed.estabelecimentoid=er.estabelecimentoid inner join estabelecimento e on ed.estabelecimentoid=e.estabelecimentoid where  "+qEnd)
							.addEntity(Endereco.class)
							.list());
			
		}
		
		else if(endTk.size() > 1){
			
			String[] token;
			
			for (String end : endTk) {
				
				if(cidadeOrg.equals(cidadeDest) && estadoOrg.equals(estadoDest)) {
					
					qEnd.append(" ed.enderecoCidade ~* '(.*?)"+Util.regExpPalavrasAcentuadas(cidadeOrg)+"(.*?)' and ed.enderecoEstado ~* '(.*?)"+Util.regExpPalavrasAcentuadas(estadoOrg)+"(.*?)' and ");
					
				}
				
				else if(estadoOrg.equals(estadoDest)) {
					
					qEnd.append(" ed.enderecoEstado ~* '(.*?)"+Util.regExpPalavrasAcentuadas(estadoOrg)+"(.*?)' and ");
					
				}
				
				token = StringUtils.split(end);
				
				c = 0;
				
				for (String string : token) {
					
					if(string.equalsIgnoreCase("r")) {
						qEnd.append(" (ed.enderecoDescricao ilike 'Rua%' OR ed.enderecoDescricao ilike 'R %' OR ed.enderecoDescricao ilike 'R.%')");
					}
					else if(string.equalsIgnoreCase("av") || string.equalsIgnoreCase("avn") || string.equalsIgnoreCase("avd") || string.equalsIgnoreCase("a")) {
						qEnd.append(" (ed.enderecoDescricao ilike 'Avenida%' OR ed.enderecoDescricao ilike 'Av %' OR ed.enderecoDescricao ilike 'Av.%')");
					}
					else if(string.equalsIgnoreCase("tr") || string.equalsIgnoreCase("trv") || string.equalsIgnoreCase("trs") || string.equalsIgnoreCase("ts") || string.equalsIgnoreCase("tvs") || string.equalsIgnoreCase("trave")) {
						qEnd.append(" (ed.enderecoDescricao ilike 'Travessa%' OR ed.enderecoDescricao ilike 'Tr %' OR ed.enderecoDescricao ilike 'Tr.%')");
					}						
					else if(string.equalsIgnoreCase("al") || string.equalsIgnoreCase("alm") || string.equalsIgnoreCase("ala") || string.equalsIgnoreCase("alam") || string.equalsIgnoreCase("alamd") || string.equalsIgnoreCase("almd")) {
						qEnd.append(" (ed.enderecoDescricao ilike 'Alameda%' OR ed.enderecoDescricao ilike 'Alm %' OR ed.enderecoDescricao ilike 'Alm.%' OR ed.enderecoDescricao ilike 'Al %' OR ed.enderecoDescricao ilike 'Al.%')");
					}
					else if(string.equalsIgnoreCase("pc") || string.equalsIgnoreCase("pç") || string.equalsIgnoreCase("pça") || string.equalsIgnoreCase("pca") || string.equalsIgnoreCase("prç") || string.equalsIgnoreCase("prc")) {
						qEnd.append(" (ed.enderecoDescricao ilike 'Praça%' OR ed.enderecoDescricao ilike 'Praca%' OR ed.enderecoDescricao ilike 'Pc %' OR ed.enderecoDescricao ilike 'Pc.%' OR ed.enderecoDescricao ilike 'Pç %' OR ed.enderecoDescricao ilike 'Pç.%')");
					}
					else {
						qEnd.append(" ed.enderecoDescricao ~* '(.*?)"+Util.regExpPalavrasAcentuadas(string)+"(.*?)'");
					}
					
					if(c!=token.length-1) {
						
						qEnd.append(" and");
						
					}
					
					c++;
				
				}
				
				ends.addAll(
						HibernateUtil.currentSession()
								.createSQLQuery("select distinct on (ed.enderecodescricao, ed.enderecocidade, ed.enderecoestado) * from est_ramoatividade er inner join endereco ed on er.ramoatividadeid in "+idRas+" and ed.estabelecimentoid=er.estabelecimentoid inner join estabelecimento e on ed.estabelecimentoid=e.estabelecimentoid where "+qEnd)
								.addEntity(Endereco.class)
								.list());
				
				qEnd.setLength(0);
				
				//qEndCompleto.setLength(0);
				
			}
			
		}
		
		final ArrayList<Estabelecimento> ests = new ArrayList<Estabelecimento>();
		
		for (Endereco endereco : ends) {
			
			ests.add(endereco.getEstabelecimento());
			
		}
		
		return ests;
		
	}
	
	/**
	 * Busca estabelecimentos dado um termo (tipo de estabelecimento) e uma cidade.
	 * @param termo - tipo de estabelecimento a ser buscado
	 * @param cidade - cidade de onde virão os estabelecimentos
	 * @return Lista de objetos {@link Estabelecimento}
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Estabelecimento> buscaEstabelecimentosWeb(String termo, String cidade) {
		
		termo = Util.unescape(termo);
		
		cidade = Util.unescape(cidade);
		
		LinkedHashSet<Estabelecimento> est = null;
		
		if(termo!=null) {
			
			String[] tK = StringUtils.split(termo);
			
			ArrayList<RamoAtividade> ras = new ArrayList<RamoAtividade>();
			
			ArrayList<RamoAtividade> aux = null;
			
			String q = " (";
			
			int c = 0;
			
			for (int i = 0; i < tK.length; i++) {
				
				q += "e.estabelecimentoNome ~* '(.*?)"+Util.regExpPalavrasAcentuadas(StringUtils.lowerCase(tK[i]))+"(.*?)' ";
				
				if(c!=tK.length-1) {
					
					q += "or ";
					
				}
				
				else {
					
					q += ") ";
					
				}
				
				aux = ((RamoAtividadeDAO) DAOFactory.getDAO("RamoAtividadeDAO")).buscaPeloNome(tK[i]);
				
				if(aux!=null) {
					
						ras.addAll(aux);
					
				}
				
				c++;
				
			}
			
			String cidadeQ = "";
			
			if(cidade!=null) {
				
				cidadeQ += " ed.enderecoCidade ~* '(.*?)" + Util.regExpPalavrasAcentuadas(StringUtils.lowerCase(cidade)) + "(.*?)' ";
				
			}
			
			c = 0;
			
			String idRas = ras.size()==0 ? "" : " er.ramoatividadeid in (";
			
			for (RamoAtividade ramoAtividade : ras) {
				
				idRas += ramoAtividade.getRamoatividadeId();
				
				if(c!=ras.size()-1) {
					
					idRas += ",";
					
				}
				
				else {
					
					idRas += ") ";
					
				}
				
				c++;
				
			}
			
			final ArrayList<Estabelecimento> ests = (ArrayList<Estabelecimento>) HibernateUtil.currentSession()
						.createSQLQuery( "select distinct on (ed.enderecodescricao, ed.enderecocidade) * from est_ramoatividade er inner join endereco ed on ed.estabelecimentoid=er.estabelecimentoid inner join estabelecimento e on e.estabelecimentoAtivo=TRUE AND er.estabelecimentoid=e.estabelecimentoid where ("+idRas+" or "+q+") and "+cidadeQ + " limit 5 offset 0")
						.addEntity(Estabelecimento.class)
						.list();
			
			/*for (Endereco endereco : ends) {
				
				est.add(endereco.getEstabelecimento());
				
			}*/
			
			if(ests!=null) {
				
				est = new LinkedHashSet<Estabelecimento>(ests);
				
			}
			
			else {
				
				est = new LinkedHashSet<Estabelecimento>();
				
			}
			
			
		}
		
		return new ArrayList<Estabelecimento>(est);
		
	}
	
}
