package br.com.bropenmaps.dao;

import java.util.ArrayList;

import br.com.bropenmaps.model.Cidade;

/**
 * Classe que faz acesso ao banco de dados relacionados a entidade {@link Cidade}
 * @author Rafael Melo Salum
 *
 */
public class CidadesDAO extends DAO<Cidade> {
	
	/**
	 * Construtor da classe
	 */
	protected CidadesDAO() {
		
		super(Cidade.class);
		
	}
	
	/**
	 * Lista as cidades em um determinado quadrante do mapa
	 * @param maxLat - latitude máxima
	 * @param minLat - latitude mínima
	 * @param maxLng - longitude máxima
	 * @param minLng - longitude mínima
	 * @return Lista de cidades dentro do quadrante
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Cidade> listaGeoPorRegiao(Double maxLat, Double minLat, Double maxLng, Double minLng) {
		
		final ArrayList<Cidade> result = (ArrayList<Cidade>)HibernateUtil.currentSession()
		.createSQLQuery("select * from geocodecidades where latitude > ? and latitude < ? and longitude > ? and longitude < ?")
		.addEntity(Cidade.class)
		.setDouble(0, minLat)
		.setDouble(1, maxLat)
		.setDouble(2, minLng)
		.setDouble(3, maxLng)
		.list();
	
		return result;
	}
	
}