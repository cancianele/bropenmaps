package br.com.bropenmaps.model;

import org.apache.commons.lang.StringUtils;

/**
 * Classe que encapsula o comportamento da entidade {@link CidadesDist}.
 * 
 * @author Luiz Gustavo Jordão Soares
 *
 */
@SuppressWarnings("serial")
public class CidadesDist extends Cidade {
	
	private Double dist;
	
	/**
	 * Método responsável por realizar o calculo entre as cidades.
	 * 
	 * @param geoCC
	 * @param dist
	 */
	public CidadesDist(Cidade geoCC, Double dist) {
		
		super.setId(geoCC.getId());
		
		super.setNome(StringUtils.removeEnd(geoCC.getNome().trim(), "*"));
		
		super.setUf(geoCC.getUf());
		
		super.setPais(geoCC.getPais());
		
		super.setLatitude(geoCC.getLatitude());
		
		super.setLongitude(geoCC.getLongitude());
		
		this.dist = dist;
		
	}
/**
 * Retorna valor associado à distância.
 * 
 * @return dist
 */
	public Double getDist() {
		return dist;
	}

	/**
	 * Muda valor associado à distância.
	 * 
	 * @param dist
	 */
	public void setDist(Double dist) {
		this.dist = dist;
	}

}