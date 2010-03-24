package br.com.bropenmaps.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.bropenmaps.model.interfaces.ITermo;

/**
 * Classe que encapsula o comportamento da entidade {@link Termo}
 * 
 * @author Viviane Guerra
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name="termos")
public class Termo implements ITermo {

	private Integer id;
	
	private String termo;
	
	private boolean ativo;
	
	/**
	 * Retorna o id do termo
	 * @return
	 */
	@Id
	@SequenceGenerator(name="termoid", sequenceName="termos_id_seq")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "termoid")
	public Integer getId() {
		return id;
	}

	/**
	 * Configura o valor do id
	 * @param id
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	
	/**
	 * Retorna o termo
	 * @return
	 */
	public String getTermo() {
		return termo;
	}
	
	/**
	 * Configura o valor do termo
	 * @param termo
	 */
	public void setTermo(String termo) {
		this.termo = termo;
	}
	
	/**
	 * Retorna true se o termo esta ativo ou nao
	 * @return
	 */
	public boolean isAtivo() {
		return ativo;
	}
	
	/**
	 * Configura o valor true ou false indicando se o termo esta ativo ou nao.
	 * @return
	 */
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	
	
	
}
