package br.com.bropenmaps.model;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.bropenmaps.model.interfaces.ICidade;

/**
 * Classe que encapsula o comportamento da entidade {@link Cidade}
 * 
 * @author Rafael Melo Salum
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name="geocodecidades")
public class Cidade implements ICidade {

	private Long id;
	
	private String nome;
	
	private String uf;
	
	private String regiao;
	
	private String populacao;
	
	private String pib;
	
	private String percapita;
	
	private String idh;
	
	private String area;
	
	private String clima;
	
	private String fonteInfo;
	
	private String cultura;
	
	private String fonteCultura;
	
	private String economia;
	
	private String fonteEconomia;
	
	private ArrayList<String> categorias;
	
	private String pais;
	
	private Double latitude;
	
	private Double longitude;
	
	private String pathImg;

	/**
	 * Retorna valor associado à regiao.
	 * 
	 * @return regiao
	 */
	@Transient
	public String getRegiao() {
		return regiao;
	}

	/**
	 * Muda valor associado à regiao.
	 * 
	 * @param regiao
	 */
	public void setRegiao(String regiao) {
		this.regiao = regiao;
	}
	
	/**
	 * Retorna valor associado ao pib.
	 * 
	 * @return pib
	 */
	@Transient
	public String getPib() {
		return pib;
	}

	/**
	 * Muda valor associado ao pib.
	 * 
	 * @param pib
	 */
	public void setPib(String pib) {
		this.pib = pib;
	}
	
	/**
	 * Retorna valor associado à renda per capita
	 * 
	 * @return percapita
	 */
	@Transient
	public String getPercapita() {
		return percapita;
	}

	/**
	 * Muda valor associado à renda per capita.
	 * 
	 * @param percapita
	 */
	public void setPercapita(String percapita) {
		this.percapita = percapita;
	}
	
	/**
	 * Retorna valor associado ao idh.
	 * 
	 * @return idh
	 */
	@Transient
	public String getIdh() {
		return idh;
	}

	/**
	 * Muda valor associado ao idh.
	 * 
	 * @param idh
	 */
	public void setIdh(String idh) {
		this.idh = idh;
	}
	
	/**
	 * Retorna valor associado à área da cidade.
	 * 
	 * @return area
	 */
	@Transient
	public String getArea() {
		return area;
	}

	/**
	 * Muda valor associado à área da cidade.
	 * 
	 * @param area
	 */
	public void setArea(String area) {
		this.area = area;
	}
	
	/**
	 * Retorna valor associado à média de clima da cidade.
	 * 
	 * @return clima
	 */
	@Transient
	public String getClima() {
		return clima;
	}

	/**
	 * Muda valor associado à média de clima da cidade.
	 * 
	 * @param clima
	 */
	public void setClima(String clima) {
		this.clima = clima;
	}
	
	/**
	 * Retorna valor associado à cultura.
	 * 
	 * @return cultura
	 */
	@Transient
	public String getCultura() {
		return cultura;
	}
	
	/**
	 * Muda valor associado à cultura.
	 * 
	 * @param cultura
	 */
	public void setCultura(String cultura) {
		this.cultura = cultura;
	}

	/**
	 * Retorna valor associado à economia.
	 * 
	 * @return economia
	 */
	@Transient
	public String getEconomia() {
		return economia;
	}

	/**
	 *  Muda valor associado à economia.
	 * 
	 * @param economia
	 */
	public void setEconomia(String economia) {
		this.economia = economia;
	}

	/**
	 * Retorna valor associado à fonte das informações gerais.
	 * 
	 * @return fonteinfo
	 */
	@Transient
	public String getFonteInfo() {
		return fonteInfo;
	}
	/**
	 * Muda valor associado à fonte de informações gerais.
	 * @param fonteInfo
	 */
	public void setFonteInfo(String fonteInfo) {
		this.fonteInfo = fonteInfo;
	}

	/**
	 * Retorna valor associado à fonte de cultura.
	 * 
	 * @return fontecultura
	 */
	@Transient
	public String getFonteCultura() {
		return fonteCultura;
	}

	/**
	 * Muda valor associado à fonte de cultura.
	 * 
	 * @param fonteCultura
	 */
	public void setFonteCultura(String fonteCultura) {
		this.fonteCultura = fonteCultura;
	}

	/**
	 * Retorna valor associado à fonte de economia.
	 * 
	 * @return fonteEconomia
	 */
	@Transient
	public String getFonteEconomia() {
		return fonteEconomia;
	}

	/**
	 * Muda valor associado à font de economia.
	 * 
	 * @param fonteEconomia
	 */	
	public void setFonteEconomia(String fonteEconomia) {
		this.fonteEconomia = fonteEconomia;
	}

	/**
	 * Retorna valor associado à categorias.
	 * 
	 * @return categorias
	 */
	@Transient
	public ArrayList<String> getCategorias() {
		return categorias;
	}

	/**
	 * Muda valor associado à categorias.
	 * 
	 * @param categorias
	 */
	public void setCategorias(ArrayList<String> categorias) {
		this.categorias = categorias;
	}

	/**
	 * Retorna valor associado ao caminho da bandeira.
	 * 
	 * @return pathImg
	 */
	@Transient
	public String getPathImg() {
		return pathImg;
	}

	/**
	 * Muda valor associado ao caminho da bandeira.
	 * 
	 * @param pathImg
	 */
	public void setPathImg(String pathImg) {
		this.pathImg = pathImg;
	}

	/**
	 * Retorna valor associado à populacao.
	 * 
	 * @return populacao
	 */
	@Transient
	public String getPopulacao() {
		return populacao;
	}

	/**
	 * Muda valor associado à populacao.
	 * 
	 * @param populacao
	 */
	public void setPopulacao(String populacao) {
		this.populacao = populacao;
	}
	
	/**
	 * Retorna valor associado ao id.
	 * 
	 * @return id
	 */
	@Id
	@SequenceGenerator(name="id", sequenceName="geocodecidades_id_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id")
	public Long getId() {
		return id;
	}

	/**
	 * Muda valor associado ao id.
	 * 
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Retorna valor associado ao país.
	 * 
	 * @return pais
	 */
	public String getPais() {
		return pais;
	}

	/**
	 * Muda valor associado ao país.
	 * 
	 * @param pais
	 */
	public void setPais(String pais) {
		this.pais = pais;
	}

	/**
	 * Retorna valor associado à latitude.
	 * 
	 * @return latitude
	 */
	public Double getLatitude() {
		return latitude;
	}

	/**
	 * Muda valor associado à latitude.
	 * 
	 * @param latitude
	 */
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	/**
	 * Retorna valor associado à longitude.
	 * 
	 * @return longitude
	 */
	public Double getLongitude() {
		return longitude;
	}

	/**
	 * Muda valor associado à longitude
	 * 
	 * @param longitude
	 */
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	
	/**
	 * Retorna valor associado ao nome.
	 * 
	 * @return nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * Muda valor associado ao nome.
	 * 
	 * @param nome
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * Retorna valor associado ao estado.
	 * 
	 * @return uf
	 */
	public String getUf() {
		return uf;
	}

	/**
	 * Muda valor associado ao estado.
	 * 
	 * @param uf
	 */
	public void setUf(String uf) {
		this.uf = uf;
	}
	
}