package br.com.bropenmaps.model;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import br.com.bropenmaps.model.interfaces.IRamoAtividade;

/**
 * Classe que encapsula o comportamento da entidade {@link RamoAtividade}
 * 
 * @author Viviane Guerra
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name="ramo_atividade")
public class RamoAtividade implements IRamoAtividade {
	
	private Long ramoatividadeId;
	
	private String ramoatividadeNome;
	
	private Long ramoatividadeIdExterno;
	
	private boolean ramoatividadeAtivo;
	
	private Collection<Estabelecimento> estabelecimentos;

	private Termo termo;
	
	/**
	 * Retorna o id do ramo de atividade
	 * @return
	 */
	@Id 
	@SequenceGenerator(name="ramoatividadeId", sequenceName="ramo_atividade_id_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ramoatividadeId")
	@Column(name="id")
	public Long getRamoatividadeId() {
		return ramoatividadeId;
	}
	
	/**
	 * Configura o valor do id
	 * @param ramoatividadeId
	 */
	public void setRamoatividadeId(Long ramoatividadeId) {
		this.ramoatividadeId = ramoatividadeId;
	}
	
	/**
	 * Retorna o nome do ramo de atividade
	 * @return
	 */
	@Column(name="ramo_atividade")
	public String getRamoatividadeNome() {
		return ramoatividadeNome;
	}
	
	/**
	 * Configura o nome do ramo de atividade
	 * @param ramoatividadeNome
	 */
	public void setRamoatividadeNome(String ramoatividadeNome) {
		this.ramoatividadeNome = ramoatividadeNome;
	}
	
	/**
	 * Retorna o id externo do ramo de atividade
	 * @return
	 */
	@Column(name="ramo_atividade_id")
	public Long getRamoatividadeIdExterno() {
		return ramoatividadeIdExterno;
	}
	
	/**
	 * Configura o id externo do ramo de atividade
	 * @param ramoatividadeIdExterno
	 */
	public void setRamoatividadeIdExterno(Long ramoatividadeIdExterno) {
		this.ramoatividadeIdExterno = ramoatividadeIdExterno;
	}
	
	/**
	 * Retorna os estabelecimentos relacionados ao ramo de atividade
	 * @return
	 */
	@ManyToMany(
	        cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},
	        mappedBy="ramoatividade"
	    )
	 @Fetch(value=FetchMode.SUBSELECT)
	 @BatchSize(size=1000)
	public Collection<Estabelecimento> getEstabelecimentos() {
		return estabelecimentos;
	}

	/**
	 * Configura os estabelecimentos relacionados ao ramo de atividade
	 * @param estabelecimentos
	 */
	public void setEstabelecimentos(Collection<Estabelecimento> estabelecimentos) {
		this.estabelecimentos = estabelecimentos;
	}
	
	/**
	 * Retorna o termo relacionado
	 * @return
	 */
	@OneToOne
	@JoinColumn(name="id_termo")
	public Termo getTermo() {
		return termo;
	}
	
	/**
	 * Configura o termo relacionado
	 * @param termo
	 */
	public void setTermo(Termo termo) {
		this.termo = termo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((ramoatividadeId == null) ? 0 : ramoatividadeId.hashCode());
		result = prime
				* result
				+ ((ramoatividadeIdExterno == null) ? 0
						: ramoatividadeIdExterno.hashCode());
		result = prime
				* result
				+ ((ramoatividadeNome == null) ? 0 : ramoatividadeNome
						.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final RamoAtividade other = (RamoAtividade) obj;
		if (ramoatividadeId == null) {
			if (other.ramoatividadeId != null)
				return false;
		} else if (!ramoatividadeId.equals(other.ramoatividadeId))
			return false;
		if (ramoatividadeIdExterno == null) {
			if (other.ramoatividadeIdExterno != null)
				return false;
		} else if (!ramoatividadeIdExterno.equals(other.ramoatividadeIdExterno))
			return false;
		if (ramoatividadeNome == null) {
			if (other.ramoatividadeNome != null)
				return false;
		} else if (!ramoatividadeNome.equals(other.ramoatividadeNome))
			return false;
		return true;
	}
	
	/**
	 * Retorna true se o ramo de atividade esta ativo, false em caso contrario
	 * @return
	 */
	@Column(name="ramo_atividade_ativo")
	public boolean isRamoatividadeAtivo() {
		return ramoatividadeAtivo;
	}
	
	/**
	 * Define se o ramo de atividade esta ou nao ativo
	 * @param ramoatividadeAtivo - true ou false
	 */
	public void setRamoatividadeAtivo(boolean ramoatividadeAtivo) {
		this.ramoatividadeAtivo = ramoatividadeAtivo;
	}
	
}