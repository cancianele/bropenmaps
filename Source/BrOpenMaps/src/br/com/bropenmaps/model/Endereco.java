package br.com.bropenmaps.model;

import java.util.ArrayList;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.bropenmaps.model.interfaces.IEndereco;
import br.com.zymboo.commons.model.annotations.XMLAtrib;
import br.com.zymboo.commons.model.annotations.XMLRoot;


/**
 * Classe que encapsula o comportamento da entidade {@link Endereco} 
 * 
 * @author Rafael Melo Salum
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name="endereco")
@XMLRoot(nome="enderecos")
public class Endereco implements IEndereco {
	
	@XMLAtrib(nome="id", nomeTag="endereco", atributo=false)
	private Long enderecoId;
	
	@XMLAtrib(nome="descricao", nomeTag="endereco", atributo=false)
	private String enderecoDescricao;
	
	@XMLAtrib(nome="cep", nomeTag="endereco", atributo=false)
	private String enderecoCEP;
	
	@XMLAtrib(nome="cidade", nomeTag="endereco", atributo=false)
	private String enderecoCidade;
	
	@XMLAtrib(nome="estado", nomeTag="endereco", atributo=false)
	private String enderecoEstado;
	
	@XMLAtrib(nome="latitude", nomeTag="endereco", atributo=false)
	private String enderecoLatitude;
	
	@XMLAtrib(nome="longitude", nomeTag="endereco", atributo=false)
	private String enderecoLongitude;
	
	@XMLAtrib(nome="pais", nomeTag="endereco", atributo=false)
	private String enderecoPais;
	
	private Estabelecimento estabelecimento;
	
	@XMLAtrib(nome="telefone", nomeTag="endereco", atributo=false)
	private String enderecoTelefone;
	
	@XMLAtrib(nome="fax", nomeTag="endereco", atributo=false)
	private String enderecoFax;
	
	@XMLAtrib(nome="celular", nomeTag="endereco", atributo=false)
	private String enderecoCelular;
	
	@XMLAtrib(nome="pabx", nomeTag="endereco", atributo=false)
	private String enderecoPabx;
	
	@XMLAtrib(nome="email", nomeTag="endereco", atributo=false)
	private String enderecoEmail;
	
	@XMLAtrib(nome="website", nomeTag="endereco", atributo=false)
	private String enderecoWebsite;

	@XMLAtrib(nome="bairro", nomeTag="endereco", atributo=false)
	private String enderecoBairro; 
	
	/**
	 * Retorna valor associado ao id.
	 * 
	 * @return enderecoId
	 */
	@Id
	@SequenceGenerator(name="enderecoId", sequenceName="endereco_enderecoid_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "enderecoId")
	public Long getEnderecoId() {
		return enderecoId;
	}

	/**
	 * Muda valor associado ao id.
	 * 
	 * @param enderecoId
	 */
	public void setEnderecoId(Long enderecoId) {
		this.enderecoId = enderecoId;
	}

	/**
	 * Retorna valor associado à descrição.
	 * 
	 * @return enderecoDescricao
	 */
	public String getEnderecoDescricao() {
		return enderecoDescricao;
	}
	
	/**
	 * Muda valor associado à descrição. 
	 * 
	 * @param enderecoDescricao
	 */
	public void setEnderecoDescricao(String enderecoDescricao) {
		this.enderecoDescricao = enderecoDescricao;
	}

	/**
	 * Retorna valor associado ao cep.
	 * 
	 * @return enderecoCEP
	 */
	public String getEnderecoCEP() {
		return enderecoCEP;
	}

	/**
	 * Muda valor associado ao cep.
	 * 
	 * @param enderecoCEP
	 */
	public void setEnderecoCEP(String enderecoCEP) {
		this.enderecoCEP = enderecoCEP;
	}

	/**
	 * Retorna valor associado à cidade.
	 * 
	 * @return enderecoCidade
	 */
	public String getEnderecoCidade() {
		return enderecoCidade;
	}

	/**
	 * Muda valor associado à cidade.
	 * 
	 * @param enderecoCidade
	 */
	public void setEnderecoCidade(String enderecoCidade) {
		this.enderecoCidade = enderecoCidade;
	}

	/**
	 * Retorna valor associado ao estado.
	 * 
	 * @return enderecoEstado
	 */
	public String getEnderecoEstado() {
		return enderecoEstado;
	}

	/**
	 * Muda valor associado ao estado.
	 * 
	 * @param enderecoEstado
	 */
	public void setEnderecoEstado(String enderecoEstado) {
		this.enderecoEstado = enderecoEstado;
	}

	/**
	 * Retorna valor associado à latitude.
	 * 
	 * @return enderecoLatitude
	 */
	public String getEnderecoLatitude() {
		return enderecoLatitude;
	}

	/**
	 * Muda valor associado à latitude.
	 * 
	 * @param enderecoLatitude
	 */
	public void setEnderecoLatitude(String enderecoLatitude) {
		this.enderecoLatitude = enderecoLatitude;
	}

	/**
	 * Retorna valor associado à longitude.
	 * 
	 * @return enderecoLongitude
	 */
	public String getEnderecoLongitude() {
		return enderecoLongitude;
	}

	/**
	 * Muda valor associado à longitude.
	 * 
	 * @param enderecoLongitude
	 */
	public void setEnderecoLongitude(String enderecoLongitude) {
		this.enderecoLongitude = enderecoLongitude;
	}

	/**
	 * Retorna valor associado ao país.
	 * 
	 * @return enderecoPais
	 */
	public String getEnderecoPais() {
		return enderecoPais;
	}

	/**
	 * Muda valor associado ao país.
	 * 
	 * @param enderecoPais
	 */
	public void setEnderecoPais(String enderecoPais) {
		this.enderecoPais = enderecoPais;
	}

	/**
	 * Retorna valor associado à enditade {@link Estabelecimento}
	 * 
	 * @return {@link Estabelecimento}
	 */
	@ManyToOne(cascade={CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.EAGER)
    @JoinColumn(name="estabelecimentoId")
	public Estabelecimento getEstabelecimento() {
		if(this.estabelecimento==null) {
			return estabelecimento;
		}
		try {
			return (Estabelecimento) estabelecimento.clone();
		} catch (CloneNotSupportedException e) {			
			e.printStackTrace();
		}
		return estabelecimento;
	}

	/**
	 * Muda valor associado à entidade {@link Estabelecimento}
	 * 
	 * @param estabelecimento
	 */
	public void setEstabelecimento(Estabelecimento estabelecimento) {
		this.estabelecimento = estabelecimento;
	}
	
	/**
	 * Retorna valor associado ao telefone.
	 * 
	 * @return enderecoTelefone
	 */
	public String getEnderecoTelefone() {
		return enderecoTelefone;
	}
	
	/**
	 * Muda valor associado ao telefone.
	 * 
	 * @param enderecoTelefone
	 */
	public void setEnderecoTelefone(String enderecoTelefone) {
		this.enderecoTelefone = enderecoTelefone;
	}

	/**
	 * Retorna valor associado ao fax.
	 * 
	 * @return enderecoFax
	 */
	public String getEnderecoFax() {
		return enderecoFax;
	}

	/**
	 * Muda valor associado ao fax.
	 * 
	 * @param enderecoFax
	 */
	public void setEnderecoFax(String enderecoFax) {
		this.enderecoFax = enderecoFax;
	}

	/**
	 * Retorna valor associado ao celular.
	 * 
	 * @return enderecoCelular
	 */
	public String getEnderecoCelular() {
		return enderecoCelular;
	}

	/**
	 * Muda valor associado ao celular.
	 * 
	 * @param enderecoCelular
	 */
	public void setEnderecoCelular(String enderecoCelular) {
		this.enderecoCelular = enderecoCelular;
	}

	/**
	 * Retorna valor associado ao email.
	 * 
	 * @return enderecoEmail
	 */
	public String getEnderecoEmail() {
		return enderecoEmail==null ? "" : enderecoEmail;
	}

	/**
	 * Muda valor associado ao email.
	 * 
	 * @param enderecoEmail
	 */
	public void setEnderecoEmail(String enderecoEmail) {
		this.enderecoEmail = enderecoEmail;
	}

	/**
	 * Retorna valor associado ao site.
	 * 
	 * @return enderecoWebsite
	 */
	public String getEnderecoWebsite() {
		return enderecoWebsite==null ? "" : enderecoWebsite;
	}

	/**
	 * Muda valor associado ao site.
	 * 
	 * @param enderecoWebsite
	 */
	public void setEnderecoWebsite(String enderecoWebsite) {
		this.enderecoWebsite = enderecoWebsite;
	}
	
	/**
	 * Retorna valor associado ao pabx.
	 * 
	 * @return enderecoPabx
	 */
	public String getEnderecoPabx() {
		return enderecoPabx;
	}

	/**
	 * Muda valor associado a pabx.
	 * 
	 * @param enderecoPabx
	 */
	public void setEnderecoPabx(String enderecoPabx) {
		this.enderecoPabx = enderecoPabx;
	}
	
	/**
	 * Muda valor associado à lista de telefones.
	 * 
	 * @param tels
	 * @param tipo
	 */
	@Transient
	public void setEnderecoTelefones(ArrayList<String> tels, String tipo) {
		
		if(tipo!=null && tipo.equals("Tel") && tels!=null) {
			
			this.enderecoTelefone = "";
			
			int i = 0;
			
			for (String tel : tels) {
				
				this.enderecoTelefone += tel;
				
				if(i+1!=tels.size()) {
					
					this.enderecoTelefone += "/";
					
				}
				
				i++;
				
			}
			
		}
		
		else if(tipo!=null && tipo.equals("Cel") && tels!=null) {
			
			this.enderecoCelular = "";
				
			int i = 0;
			
			for (String tel : tels) {
				
				this.enderecoCelular += tel;
				
				if(i+1!=tels.size()) {
					
					this.enderecoCelular += "/";
					
				}
				
				i++;
				
			}
			
		}
		
		else if(tipo!=null && tipo.equals("Fax") && tels!=null) {
			
			this.enderecoFax = "";
			
			int i = 0;
			
			for (String tel : tels) {
				
				this.enderecoFax += tel;
				
				if(i+1!=tels.size()) {
					
					this.enderecoFax += "/";
					
				}
				
				i++;
				
			}
			
		}
		
		else if(tipo!=null && tipo.equals("PABX") && tels!=null) {
			
			this.enderecoPabx = "";
			
			int i = 0;
			
			for (String tel : tels) {
				
				this.enderecoPabx += tel;
				
				if(i+1!=tels.size()) {
					
					this.enderecoPabx += "/";
					
				}
				
				i++;
				
			}
	
		}
		
	}
	
	/**
	 * Retorna valor associado ao bairro.
	 * 
	 * @return enderecoBairro
	 */
	public String getEnderecoBairro() {
		return enderecoBairro;
	}
	
	/**
	 * Muda valor associado ao bairro.
	 * 
	 * @param enderecoBairro
	 */
	public void setEnderecoBairro(String enderecoBairro) {
		this.enderecoBairro = enderecoBairro;
	}

	/**
	 * Muda valor associado aos telefones.
	 * 
	 * @param tel
	 * @param tipo
	 */
	@Transient
	public void setEnderecoTelefones(String tel, String tipo) {
		
		if(tipo!=null && tipo.equals("Tel")) {
			
			this.enderecoTelefone = tel;
			
		}
		
		else if(tipo!=null && tipo.equals("Cel")) {
			
			this.enderecoCelular = tel;
			
		}
		
		else if(tipo!=null && tipo.equals("Fax")) {
			
			this.enderecoFax = tel;
			
		}
		
		else if(tipo!=null && tipo.equals("PABX")) {
			
			this.enderecoPabx = tel;
			
		}
		
	}
	
	/**
	 *  @return int hashCode 
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((enderecoBairro == null) ? 0 : enderecoBairro.hashCode());
		result = prime * result
				+ ((enderecoCEP == null) ? 0 : enderecoCEP.hashCode());
		result = prime * result
				+ ((enderecoCidade == null) ? 0 : enderecoCidade.hashCode());
		result = prime
				* result
				+ ((enderecoDescricao == null) ? 0 : enderecoDescricao
						.hashCode());
		result = prime * result
				+ ((enderecoEstado == null) ? 0 : enderecoEstado.hashCode());
		result = prime * result
				+ ((enderecoPais == null) ? 0 : enderecoPais.hashCode());
		return result;
	}

	/**
	 * Compara.
	 * 
	 * @return boolean
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Endereco other = (Endereco) obj;
		if (enderecoBairro == null) {
			if (other.enderecoBairro != null)
				return false;
		} else if (!enderecoBairro.equals(other.enderecoBairro))
			return false;
		if (enderecoCEP == null) {
			if (other.enderecoCEP != null)
				return false;
		} else if (!enderecoCEP.equals(other.enderecoCEP))
			return false;
		if (enderecoCidade == null) {
			if (other.enderecoCidade != null)
				return false;
		} else if (!enderecoCidade.equals(other.enderecoCidade))
			return false;
		if (enderecoDescricao == null) {
			if (other.enderecoDescricao != null)
				return false;
		} else if (!enderecoDescricao.equals(other.enderecoDescricao))
			return false;
		if (enderecoEstado == null) {
			if (other.enderecoEstado != null)
				return false;
		} else if (!enderecoEstado.equals(other.enderecoEstado))
			return false;
		if (enderecoPais == null) {
			if (other.enderecoPais != null)
				return false;
		} else if (!enderecoPais.equals(other.enderecoPais))
			return false;
		return true;
	}

	/**
	 * @return String com informações 
	 */
	@Override
	public String toString() {
		
		final StringBuilder s = new StringBuilder();
		
		s.append("Estabelecimento: " + this.estabelecimento.getEstabelecimentoNome() + "\n");
		s.append("Estabelecimento: " + this.estabelecimento.getEstabelecimentoDescricao() + "\n");
		s.append("Descricao: " + this.enderecoDescricao + "\n");
		s.append("Email: " + this.enderecoEmail + "\n");
		s.append("Site: " + this.enderecoWebsite + "\n");
		s.append("Cidade: " + this.enderecoCidade + "\n");
		s.append("Bairro: " + this.enderecoBairro + "\n");
		s.append("Estado: " + this.enderecoEstado + "\n");
		s.append("CEP: " + this.enderecoCEP + "\n");
		s.append("Telefone: " + this.enderecoTelefone + "\n");
		s.append("Celular: " + this.enderecoCelular + "\n");
		s.append("Fax: " + this.enderecoFax + "\n");
		s.append("PABX: " + this.enderecoPabx + "\n");
		
		return s.toString();
	}

}