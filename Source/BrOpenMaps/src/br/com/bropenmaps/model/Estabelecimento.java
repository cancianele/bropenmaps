package br.com.bropenmaps.model;

import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import br.com.bropenmaps.model.interfaces.IEstabelecimento;
import br.com.bropenmaps.util.HtmlUtil;
import br.com.bropenmaps.util.Util;
import br.com.zymboo.commons.model.annotations.XMLAtrib;
import br.com.zymboo.commons.model.annotations.XMLRoot;
import br.com.zymboo.commons.util.CriptUtils;

/**
 * Classe que encapsula o comportamento da entidade {@link Estabelecimento}
 * 
 * @author Viviane Guerra
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name="estabelecimento")
@BatchSize(size=200)
@XMLRoot(nome="estabelecimentos")
public class Estabelecimento implements IEstabelecimento, Cloneable {

	@XMLAtrib(nome="id", nomeTag="estabelecimento", atributo=false)
	private Long estabelecimentoId;
	
	@XMLAtrib(nome="nome", nomeTag="estabelecimento", atributo=false)
	private String estabelecimentoNome;
	
	@XMLAtrib(nome="descricao", nomeTag="estabelecimento", atributo=false)
	private String estabelecimentoDescricao;
	
	private String estabelecimentoLogo;
	
	private boolean estabelecimentoAtivo;
	
	private String estabelecimentoVideo;
	
	private String estabelecimentoFoto1;
	
	private String estabelecimentoFoto2;
	
	private String estabelecimentoFoto3;
	
	private String estabelecimentoFoto4;
	
	private Collection<Endereco> enderecos;
	
	private Collection<RamoAtividade> ramoatividade;
	
	private String estabelecimentoIcone;
	
	private Timestamp estabelecimentoData;
	
	private String estabelecimentoHotSite;
	
	private String[] enderecoBuscado;
	
	private String cidadeBuscada;
	
	private String estadoBuscado;
	
	private Long idEnderecoBuscado;
	
	@XMLAtrib(nome="primeiroendereco", nomeTag="estabelecimento", atributo=false)
	private Endereco primeiroEndereco;
	
	private Long primeiroEnderecoId;
	
	private String primeiroEnderecoDescricao;
	
	private String primeiroEnderecoCidade;
	
	private String primeiroEnderecoEstado;
	
	private String primeiroEnderecoLatitude;
	
	private String primeiroEnderecoLongitude;
	
	private boolean estabelecimentoVip;
	
	@XMLAtrib(nome="enderecos", nomeTag="estabelecimento", atributo=true, colecao=true, objetoColecao="br.com.bropenmaps.model.Endereco")	
	private LinkedHashSet<Endereco> enderecosSemRepeticao;
	
	/**
	 * Retorna valor associado ao id. 
	 * 
	 * @return estabelecimentoId
	 */
	@Id
	@SequenceGenerator(name="estabelecimentoId", sequenceName="estabelecimento_estabelecimentoId_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "estabelecimentoId")
	public Long getEstabelecimentoId() {
		return estabelecimentoId;
	}

	/**
	 * Muda valor associado ao id. 
	 * 
	 * @param estabelecimentoId
	 */
	public void setEstabelecimentoId(Long estabelecimentoId) {
		this.estabelecimentoId = estabelecimentoId;
	}

	/**
	 * Retorna valor associado ao nome.
	 * 
	 * @return estabelecimentoNome
	 */
	public String getEstabelecimentoNome() {
		return estabelecimentoNome;
	}

	/**
	 * Muda valor associado ao nome.
	 * 
	 * @param estabelecimentoNome
	 */
	public void setEstabelecimentoNome(String estabelecimentoNome) {
		this.estabelecimentoNome = estabelecimentoNome;
	}
	
	/**
	 * Retorna valor associado a descricao.
	 * 
	 * @return estabelecimentoDescricao
	 */
	public String getEstabelecimentoDescricao() {
		return estabelecimentoDescricao;
	}

	/**
	 * Muda valor associado a descricao.
	 * 
	 * @param estabelecimentoDescricao
	 */
	public void setEstabelecimentoDescricao(String estabelecimentoDescricao) {
		this.estabelecimentoDescricao = estabelecimentoDescricao;
	}
	
	/**
	 * Retorna valor associado a entidade {@link Endereco}
	 * 
	 * @return Collection enderereco
	 */
	@OneToMany(mappedBy="estabelecimento", cascade=CascadeType.ALL)
	@Fetch(value=FetchMode.JOIN)
	@BatchSize(size=200)
	public Collection<Endereco> getEnderecos() {
		return enderecos;
	}
	/**
	 * Muda valor associado a {@link Endereco}
	 * 
	 * @param enderecos
	 */
	public void setEnderecos(Collection<Endereco> enderecos) {
		this.enderecos = enderecos;
	}
	
	/**
	 * Retorna valor associado à entidade {@link RamoAtividade} 
	 * 
	 * @return Collection ramoAtividade
	 */
	@ManyToMany(
	        cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}
	    )
	    @JoinTable(
	        name="est_ramoatividade",
	        joinColumns={@JoinColumn(name="estabelecimentoId")},
	        inverseJoinColumns={@JoinColumn(name="ramoatividadeId")}
	    )
	@Fetch(value=FetchMode.SUBSELECT)
	@BatchSize(size=200)
	public Collection<RamoAtividade> getRamoatividade() {
		return ramoatividade;
	}
	
	/**
	 * Muda valor associado a {@link RamoAtividade}
	 * 
	 * @param ramoatividade
	 */
	public void setRamoatividade(Collection<RamoAtividade> ramoatividade) {
		this.ramoatividade = ramoatividade;
	}

	/**
	 * Retorna valor associado a logomarca.
	 * 
	 * @return estabelecimentoLogo
	 */
	public String getEstabelecimentoLogo() {
		return this.estabelecimentoLogo;
	}
	
	/**
	 * Muda valor associado a logomarca.
	 * 
	 * @param estabelecimentoLogo
	 */
	public void setEstabelecimentoLogo(String estabelecimentoLogo) {
		this.estabelecimentoLogo = estabelecimentoLogo;
	}
	
	/**
	 * Retorna valor associado ao icone.
	 * 
	 * @return estabelecimentoIcone
	 */
	public String getEstabelecimentoIcone() {
		return estabelecimentoIcone;
	}

	/**
	 * Muda valor associado ao icone.
	 * 
	 * @param estabelecimentoIcone
	 */
	public void setEstabelecimentoIcone(String estabelecimentoIcone) {
		this.estabelecimentoIcone = estabelecimentoIcone;
	}

	/**
	 * Retorna valor associado a data de inclusao e/ou alteracao.
	 * 
	 * @return estabelecimentoData
	 */
	public Timestamp getEstabelecimentoData() {
		return estabelecimentoData;
	}

	/**
	 * Muda valor associado a data de inclusao e/ou alteracao.
	 * 
	 * @param estabelecimentoData
	 */
	public void setEstabelecimentoData(Timestamp estabelecimentoData) {
		this.estabelecimentoData = estabelecimentoData;
	}

	/**
	 * Retorna html gerado para mostrar a logomarca.
	 * 
	 * @return
	 */
	@Transient
	public String getEstabelecimentoTagImgLogo() {
		
		return HtmlUtil.geraTagImg(getEstabelecimentoLogoCript(), "logo_estab");
		
	}
	
	/**
	 * Retorna caminho da logomarcar encriptado.
	 * 
	 * @return String
	 */
	@Transient
	public String getEstabelecimentoLogoCript() {
		
		try {
			
			if(estabelecimentoLogo!=null) {
				
				return URLEncoder.encode((new CriptUtils(ResourceBundle.getBundle("cript").getString("chave"))).encrypt(ResourceBundle.getBundle("config").getString("path_repositorio")+estabelecimentoId+System.getProperty("file.separator")+estabelecimentoLogo), "UTF-8");
				
			}
			
			else {
				
				return URLEncoder.encode((new CriptUtils(ResourceBundle.getBundle("cript").getString("chave"))).encrypt(ResourceBundle.getBundle("config").getString("path_repositorio")+"imgs_padrao"+System.getProperty("file.separator")+"sem_logo.png"), "UTF-8");
				
			}
			
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
			
		}
		
		return null;
		
	}
	
	/**
	 * Retorna caminho do ícone encriptado.
	 * 
	 * @return String
	 */
	@Transient
	public String getEstabelecimentoIconeCript() {
		
		try {
			
			if(estabelecimentoIcone!=null) {
				
				return URLEncoder.encode((new CriptUtils(ResourceBundle.getBundle("cript").getString("chave"))).encrypt(ResourceBundle.getBundle("config").getString("path_repositorio")+estabelecimentoId+System.getProperty("file.separator")+estabelecimentoIcone), "UTF-8");
				
			}
			
			else {
				
				return URLEncoder.encode((new CriptUtils(ResourceBundle.getBundle("cript").getString("chave"))).encrypt(ResourceBundle.getBundle("config").getString("path_repositorio")+"imgs_padrao"+System.getProperty("file.separator")+"sem_logo.png"), "UTF-8");
				
			}
			
		} catch (UnsupportedEncodingException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		return null;
		
	}
	
	/**
	 * Retorna se o estabelecimento e vip ou nao.
	 * 
	 * @return boolean
	 */
	public boolean isEstabelecimentoVip() {
		return estabelecimentoVip;
	}
	
	/**
	 * Muda status do estabelecimento para vip.
	 * 
	 * @param estabelecimentoVip
	 */
	public void setEstabelecimentoVip(boolean estabelecimentoVip) {
		this.estabelecimentoVip = estabelecimentoVip;
	}

	/**
	 * Retorna valor associado ao hotsite.
	 * 
	 * @return estabelecimentoHotSite
	 */
	@Transient
	public String getEstabelecimentoHotSite(String template) {
		
		if(this.estabelecimentoHotSite!=null && !this.estabelecimentoHotSite.equals("")) {
			
			return this.estabelecimentoHotSite;
			
		}
		
		else {
			
			this.estabelecimentoHotSite = "";
			
		}
		
		final Properties p = new Properties();
		
		p.put("file.resource.loader.path", ResourceBundle.getBundle("config").getString("path_absoluto_web"));
		
		p.put("file.resource.loader.cache ","true");  
		
		p.put("file.resource.loader.modificationCheckInterval ","10000"); 
		
		// inicializando o velocity
        final VelocityEngine ve = new VelocityEngine();
        
        org.apache.velocity.Template t = null;
        
        // criando o contexto que liga o java ao template
        final VelocityContext context = new VelocityContext();
        
		try {
			
			ve.init(p);
			
			t = ve.getTemplate(template);
			
			context.put("est", this);
			
	        final StringWriter writer = new StringWriter();
	 
	        // mistura o contexto com o template
	        t.merge(context, writer);
	        
	        this.estabelecimentoHotSite = writer.toString();
	        
		} catch (ResourceNotFoundException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (ParseErrorException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (Exception e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		return this.estabelecimentoHotSite;
		
	}
	
	/**
	 * Muda valor associado ao hotsite.
	 * 
	 * @param estabelecimentoHotSite
	 */
	public void setEstabelecimentoHotSite(String estabelecimentoHotSite) {
		this.estabelecimentoHotSite = estabelecimentoHotSite;
	}

	/**
	 * Retorna valor associado ao endereco buscado.
	 * 
	 * @return Array enderecoBuscado
	 */
	@Transient	
	public String[] getEnderecoBuscado() {
		
		if(enderecoBuscado!=null) {
			
			if(enderecoBuscado.length==1) {
				
				return enderecoBuscado;
				
			}
			
			else if(enderecoBuscado.length>1){
				
				return Arrays.copyOfRange(enderecoBuscado, 1, enderecoBuscado.length);
				
			}
			
		}
		
		return enderecoBuscado;
		
	}

	/**
	 * Muda valor associado ao endereco buscado.
	 * 
	 * @param enderecoBuscado
	 */
	public void setEnderecoBuscado(String[] enderecoBuscado) {
		this.enderecoBuscado = enderecoBuscado;
	}
	
	/**
	 * Retorna valor associado a cidade buscada.
	 * 
	 * @return cidadeBuscada
	 */
	@Transient
	public String getCidadeBuscada() {
		return cidadeBuscada;
	}

	/**
	 * Muda valor associado a cidade buscada.
	 * 
	 * @param cidadeBuscada
	 */
	public void setCidadeBuscada(String cidadeBuscada) {
		this.cidadeBuscada = cidadeBuscada;
	}
	
	/**
	 * Retorna valor associado ao estado buscado.
	 * 
	 * @return estadoBuscado
	 */
	@Transient
	public String getEstadoBuscado() {
		return estadoBuscado;
	}

	/**
	 * Muda valor associado ao estado buscado.
	 * 
	 * @param estadoBuscado
	 */
	public void setEstadoBuscado(String estadoBuscado) {
		this.estadoBuscado = estadoBuscado;
	}
	
	/**
	 * Retorna valor associado ao id do endereco buscado.
	 * 
	 * @return idEnderecoBuscado
	 */
	@Transient
	public Long getIdEnderecoBuscado() {
		return idEnderecoBuscado;
	}

	/**
	 * Muda valor associado ao id do endereço buscado.
	 * 
	 * @param idEnderecoBuscado
	 */
	public void setIdEnderecoBuscado(Long idEnderecoBuscado) {
		this.idEnderecoBuscado = idEnderecoBuscado;
	}

	/**
	 * Este metodo foi criado para otimizar o tempo de resposta do framework vraptor ao browser
	 * @return descricao
	 */
	@Transient
	public String getPrimeiroEnderecoDescricao() {
		
		final Endereco end = getPrimeiroEndereco();
		
		if(end == null) {
			
			return null;
			
		}
		
		return end.getEnderecoDescricao();
		
	}
	
	/**
	 * Este metodo foi criado para otimizar o tempo de resposta do framework vraptor ao browser
	 * @return id
	 */
	@Transient
	public Long getPrimeiroEnderecoId() {
		
		final Endereco end = getPrimeiroEndereco();
		
		if(end == null) {
			
			return null;
			
		}
		
		return end.getEnderecoId();
		
	}
	
	/**
	 * Este metodo foi criado para otimizar o tempo de resposta do framework vraptor ao browser
	 * @return cidade
	 */
	@Transient
	public String getPrimeiroEnderecoCidade() {
		
		final Endereco end = getPrimeiroEndereco();
		
		if(end == null) {
			
			return null;
			
		}
		
		return end.getEnderecoCidade();
		
	}
	
	/**
	 * Este metodo foi criado para otimizar o tempo de resposta do framework vraptor ao browser
	 * @return estado
	 */
	@Transient
	public String getPrimeiroEnderecoEstado() {
		
		final Endereco end = getPrimeiroEndereco();
		
		if(end == null) {
			
			return null;
			
		}
		
		return end.getEnderecoEstado();
		
	}
	
	/**
	 * Este metodo foi criado para otimizar o tempo de resposta do framework vraptor ao browser
	 * @return latitude
	 */
	@Transient
	public String getPrimeiroEnderecoLatitude() {
		
		final Endereco end = getPrimeiroEndereco();
		
		if(end == null) {
			
			return null;
			
		}
		
		return end.getEnderecoLatitude();
		
	}
	
	/**
	 * Este metodo foi criado para otimizar o tempo de resposta do framework vraptor ao browser
	 * @return longitude
	 */
	@Transient
	public String getPrimeiroEnderecoLongitude() {
		
		final Endereco end = getPrimeiroEndereco();
		
		if(end == null) {
			
			return null;
			
		}
		
		return end.getEnderecoLongitude();
		
	}
	
	/**
	 * Muda valor associado ao id do primeiro endereco.
	 * 
	 * @param id
	 */
	public void setPrimeiroEnderecoId(Long id) {
		this.primeiroEnderecoId = id;
	}
	
	/**
	 * Muda valor associado a descricao do primeiro endereco.
	 * 
	 * @param primeiroEnderecoDescricao
	 */
	public void setPrimeiroEnderecoDescricao(String primeiroEnderecoDescricao) {
		this.primeiroEnderecoDescricao = primeiroEnderecoDescricao;
	}

	/**
	 * Muda valor associado a cidade do primeiro endereco.
	 * 
	 * @param primeiroEnderecoCidade
	 */
	public void setPrimeiroEnderecoCidade(String primeiroEnderecoCidade) {
		this.primeiroEnderecoCidade = primeiroEnderecoCidade;
	}

	/**
	 * Muda valor associado a estado do primeiro endereco.
	 * 
	 * @param primeiroEnderecoEstado
	 */
	public void setPrimeiroEnderecoEstado(String primeiroEnderecoEstado) {
		this.primeiroEnderecoEstado = primeiroEnderecoEstado;
	}

	/**
	 * Muda valor associado a latitude do primeiro endereco.
	 * 
	 * @param primeiroEnderecoLatitude
	 */
	public void setPrimeiroEnderecoLatitude(String primeiroEnderecoLatitude) {
		this.primeiroEnderecoLatitude = primeiroEnderecoLatitude;
	}

	/**
	 * Muda valor associado a longitude do primeiro endereco.
	 * 
	 * @param primeiroEnderecoLongitude
	 */
	public void setPrimeiroEnderecoLongitude(String primeiroEnderecoLongitude) {
		this.primeiroEnderecoLongitude = primeiroEnderecoLongitude;
	}

	/**
	 * Retorna valor associado primeiro endereco.
	 * 
	 * @return {@link Endereco}
	 */
	@Transient
	public Endereco getPrimeiroEndereco() {
		
		if(this.primeiroEndereco!=null) {
			
			return primeiroEndereco;
			
		}
		
		if(getIdEnderecoBuscado()!=null && getIdEnderecoBuscado()!=0) {
			
			for (Endereco end : getEnderecos()) {
				
				if(end.getEnderecoId().equals(getIdEnderecoBuscado())) {
					
					this.primeiroEndereco = end;
					
					return end;
					
				}
				
			}
			
		}
		
		boolean  combina = true;
		
		if(getEnderecoBuscado()==null || getEnderecoBuscado().length==0) {
			
			if(getEnderecos()!=null && getEnderecos().size()>0) {
				
				if((getCidadeBuscada()!=null && !getCidadeBuscada().equals("")) || (getEstadoBuscado()!=null && !getEstadoBuscado().equals(""))) {
					
					for (Endereco end : getEnderecos()) {
						
						combina = true;
						
						if(getCidadeBuscada()!=null && !getCidadeBuscada().equals("")) {
								
							if(!Util.verificaIgualdadeCaseInsensitiveSemAcento(getCidadeBuscada(), end.getEnderecoCidade())) {
								
								combina = false;
								
								continue;
								
							}
							
						}
						
						if(getEstadoBuscado()!=null && !getEstadoBuscado().equals("")) {
							
							if(!Util.verificaIgualdadeCaseInsensitiveSemAcento(getEstadoBuscado(), end.getEnderecoEstado())) {
								
								combina = false;
								
								continue;
								
							}
							
						}
						
						if(combina) {
							
							return end;
							
						}
						
					}
					
				}
				
				return (new ArrayList<Endereco>(getEnderecos())).get(0);
				
			}
			
		}
		
		else {
			
			if(getEnderecos()!=null && getEnderecos().size()>0) {
				
				String[] tks = getEnderecoBuscado();
				
				Pattern p = null;
				
				Matcher m = null;
				
				for (Endereco end : getEnderecos()) {
					
					for (int i = 0; i < tks.length; i++) {
					
						p = Pattern.compile(tks[i], Pattern.CASE_INSENSITIVE);
					
						m = p.matcher(end.getEnderecoDescricao());
						
						if(!m.find()) {
							
							combina = false;
							
							break;
							
						}
						
					}
					
					if(combina) {
						
						if(getCidadeBuscada()!=null && !getCidadeBuscada().equals("")) {
							
							if(!Util.verificaIgualdadeCaseInsensitiveSemAcento(getCidadeBuscada(), end.getEnderecoCidade())) {
							
								combina  =false;
								
								continue;
								
							}
							
						}
						
						if(getEstadoBuscado()!=null && !getEstadoBuscado().equals("")) {
							
							if(!Util.verificaIgualdadeCaseInsensitiveSemAcento(getEstadoBuscado(), end.getEnderecoEstado())) {
								
								combina = false;
								
								continue;
								
							}
							
						}
						
						return end;
						
					}
					
					combina = true;
					
				}
				
				return (new ArrayList<Endereco>(getEnderecos())).get(0);
				
			}
			
		}
		
		return null;
	}
	
	/**
	 * Muda valor associado ao primeiro {@link Endereco}
	 * 
	 * @param primeiroEndereco
	 */
	public void setPrimeiroEndereco(Endereco primeiroEndereco) {
		this.primeiroEndereco = primeiroEndereco;
	}

	/**
	 * Retorna valor associado ao telefones do endereco.
	 * 
	 * @return String
	 */
	@Transient
	public String getTelefones(Endereco end) {
		
		final StringBuilder resultado = new StringBuilder();
		
		String[] tels = StringUtils.split(end.getEnderecoPabx(), "/");
		
		if(tels!=null && tels.length>0) {
			
			if(!getPrimeiroTelefone().contains(tels[0])) {
				
				resultado.append("<div class=\"left\"><b>PABX:</b></div><br class=\"clear\"/>");
				
				for (int i = 0; i < tels.length; i++) {
					
					resultado.append("<div class=\"left\">"+tels[i] + "</div><br class=\"clear\"/>");
					
				}
				
			}
			
		}
		
		tels = StringUtils.split(end.getEnderecoTelefone(), "/");
		
		if(tels!=null && tels.length>0) {
			
			if(!getPrimeiroTelefone().contains(tels[0])) {
				
				resultado.append("<div class=\"left\"><b>Tel(s).:</b></div><br class=\"clear\"/>");
				
				for (int i = 0; i < tels.length; i++) {
					
					resultado.append("<div class=\"left\">"+tels[i] + "</div><br class=\"clear\"/>");
					
				}
				
			}
			
		}
		
		tels = StringUtils.split(end.getEnderecoCelular(), "/");
		
		if(tels!=null && tels.length>0) {
			
			if(!getPrimeiroTelefone().contains(tels[0])) {
				
				resultado.append("<div class=\"left\"><b>Cel(s).:</b></div><br class=\"clear\"/>");
				
				for (int i = 0; i < tels.length; i++) {
					
					resultado.append("<div class=\"left\">"+tels[i] + "</div><br class=\"clear\"/>");
					
				}
				
			}
			
		}
		
		tels = StringUtils.split(end.getEnderecoFax(), "/");
		
		if(tels!=null && tels.length>0) {
			
			if(!getPrimeiroTelefone().contains(tels[0])) {
				
				resultado.append("<div class=\"left\"><b>Fax(es):</b></div><br class=\"clear\"/>");
				
				for (int i = 0; i < tels.length; i++) {
					
					resultado.append("<div class=\"left\">"+tels[i] + "</div><br class=\"clear\"/>");
					
				}
				
			}
			
		}
		
		return resultado.toString();
		
	}
	
	/**
	 * Retorna html com informacoes do primeiro endereco.
	 * 
	 * @return String
	 */
	@Transient
	public String getEnderecosInfoHtml(Endereco end) {
		
		final Properties p = new Properties();
		
		p.put("file.resource.loader.path", ResourceBundle.getBundle("config").getString("path_absoluto_web"));  
		
		p.put("file.resource.loader.cache ","true");  
		
		p.put("file.resource.loader.modificationCheckInterval ","10000"); 
		
		// inicializando o velocity
        final VelocityEngine ve = new VelocityEngine();
        
        org.apache.velocity.Template t = null;
        
        // criando o contexto que liga o java ao template
        final VelocityContext context = new VelocityContext();
        
		try {
			
			ve.init(p);
			
			t = ve.getTemplate("enderecos.jsp");
			
			context.put("endereco", end);
			
	        final StringWriter writer = new StringWriter();
	 
	        // mistura o contexto com o template
	        t.merge(context, writer);
	        
	        return writer.toString();
	        
		} catch (ResourceNotFoundException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (ParseErrorException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (Exception e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		return "";
		
	}
	
	/**
	 * Retorna html com todos enderecos.
	 * 
	 * @return String
	 */
	@Transient
	public String getEnderecosHtml() {
		
		final StringBuilder resultado = new StringBuilder();
		
		//boolean primeiro = true;
		
		Collection<Endereco> ends = getEnderecos();
		
		if(ends==null) {
			
			return null;
			
		}
		
		LinkedHashSet<Endereco> semRepeticao = new LinkedHashSet<Endereco>(ends);
		
		for (Endereco end : semRepeticao) {
			
			if(!(getPrimeiroEnderecoDescricao()!=null && end.getEnderecoDescricao()!=null && getPrimeiroEnderecoDescricao().equals(end.getEnderecoDescricao()) 
					&& getPrimeiroEnderecoCidade()!=null && end.getEnderecoCidade()!=null && getPrimeiroEnderecoCidade().equals(end.getEnderecoCidade())
					&& getPrimeiroEnderecoEstado()!=null && end.getEnderecoEstado()!=null && getPrimeiroEnderecoEstado().equals(end.getEnderecoEstado()))) {
				
				resultado.append(getEnderecosInfoHtml(end));
			
			}
			
		}
		
		return resultado.toString();
		
	}
	
	/**
	 * Retorna html dos endereço para mobile.
	 * 
	 * @return String
	 */
	@Transient
	public String getEnderecosHtmlMobile() {
		
		final StringBuilder resultado = new StringBuilder();
		
		//boolean primeiro = true;
		
		Collection<Endereco> ends = getEnderecos();
		
		if(ends==null) {
			
			return null;
			
		}
		
		LinkedHashSet<Endereco> semRepeticao = new LinkedHashSet<Endereco>(ends);
		
		String[] tels = null;
		
		for (Endereco end : semRepeticao) {
			
			if(!(getPrimeiroEnderecoDescricao()!=null && end.getEnderecoDescricao()!=null && getPrimeiroEnderecoDescricao().equals(end.getEnderecoDescricao()) 
					&& getPrimeiroEnderecoCidade()!=null && end.getEnderecoCidade()!=null && getPrimeiroEnderecoCidade().equals(end.getEnderecoCidade())
					&& getPrimeiroEnderecoEstado()!=null && end.getEnderecoEstado()!=null && getPrimeiroEnderecoEstado().equals(end.getEnderecoEstado()))) {
				
				resultado.append("<div class=\"left top5px pbottom5px\" style=\"width: 264px; border-bottom: 1px dashed #B3B2B2;\">");
				
			 	resultado.append("<div class=\"left farial  fontazul bold font12px txtleft cursor\">"+end.getEnderecoDescricao()+((end.getEnderecoBairro()!=null && !end.getEnderecoBairro().equals("")) ? "-"+ end.getEnderecoBairro() : "") + "</div><br class=\"clear\" />");
			 	
			 	resultado.append("<div class=\"left farial fontcinza font10px txtleft\">"+end.getEnderecoCidade()+","+ end.getEnderecoEstado()+"</div>"+((end.getEnderecoCEP()!=null && !end.getEnderecoCEP().equals("")) ? "<div class=\"left left10px farial fontcinza font10px txtleft\">CEP.: "+end.getEnderecoCEP()+"</div>" : ""));
			 	
			 	if(end.getEnderecoTelefone()!=null) {
			 		
			 		tels = StringUtils.split(end.getEnderecoTelefone(), "/");
			 		
			 		for (int i = 0; i < tels.length; i++) {
			 			
			 			resultado.append("<br class=\"clear\" /><div class=\"left farial fontcinza font10px txtleft\"><a href=\"wtai://wp/mc;"+Util.limpaTelefones(tels[i])+"\">"+tels[i]+"</a></div>");
			 			
					}
			 		
			 	}
			 	
			 	if(end.getEnderecoCelular()!=null) {
			 		
			 		tels = StringUtils.split(end.getEnderecoCelular(), "/");
			 		
			 		for (int i = 0; i < tels.length; i++) {
			 			
			 			resultado.append("<br class=\"clear\" /><div class=\"left farial fontcinza font10px txtleft\"><a href=\"wtai://wp/mc;"+Util.limpaTelefones(tels[i])+"\">"+tels[i]+"</a></div>");
			 			
					}
			 		
			 	}
			 	
			 	if(end.getEnderecoPabx()!=null) {
			 		
			 		tels = StringUtils.split(end.getEnderecoPabx(), "/");
			 		
			 		for (int i = 0; i < tels.length; i++) {
			 			
			 			resultado.append("<br class=\"clear\" /><div class=\"left farial fontcinza font10px txtleft\"><a href=\"wtai://wp/mc;"+Util.limpaTelefones(tels[i])+"\">"+tels[i]+"</a></div>");
			 			
					}
			 		
			 	}
			 	
			 	if(end.getEnderecoFax()!=null) {
			 		
			 		tels = StringUtils.split(end.getEnderecoFax(), "/");
			 		
			 		for (int i = 0; i < tels.length; i++) {
			 			
			 			resultado.append("<br class=\"clear\" /><div class=\"left farial fontcinza font10px txtleft\"><a href=\"wtai://wp/mc;"+Util.limpaTelefones(tels[i])+"\">"+tels[i]+"</a></div>");
			 			
					}
			 		
			 	}
			 	
			 	if(end.getEnderecoEmail()!=null && !end.getEnderecoEmail().equals("")) {
			 		
			 		resultado.append("<br class=\"clear\" /><div class=\"left farial fontcinza font10px txtleft\"><a href=\"mailto:"+end.getEnderecoEmail()+"\">"+end.getEnderecoEmail()+"</a></div>");
			 		
			 	}
			 	
			 	if(end.getEnderecoWebsite()!=null && !end.getEnderecoWebsite().equals("")) {
			 		
			 		resultado.append("<br class=\"clear\" /><div class=\"left farial fontcinza font10px txtleft\">"+end.getEnderecoWebsite()+"</div>");
			 		
			 	}
			 	
			 	resultado.append("</div><br class=\"clear\" />");
			
			}
			
		}
		
		return resultado.toString();
		
	}
	
	/**
	 * Retorna valor associado ao primeiro telefone.
	 * 
	 * @return string
	 */
	@Transient
	public String getPrimeiroTelefone() {
		
		if(getPrimeiroEndereco() == null) {
			
			return null;
			
		}
		
		Endereco pri = getPrimeiroEndereco();
		
		String tel = "";
		
		if(pri.getEnderecoPabx()!=null && !pri.getEnderecoPabx().equals("")) {
			
			tel = "PABX: " + StringUtils.split(pri.getEnderecoPabx(), "/")[0];
			
		}
		
		else if(pri.getEnderecoTelefone()!=null && !pri.getEnderecoTelefone().equals("")) {
			
			tel = "Tel: " + StringUtils.split(pri.getEnderecoTelefone(), "/")[0];
			
		}
		
		else if(pri.getEnderecoCelular()!=null && !pri.getEnderecoCelular().equals("")) {
			
			tel = "Cel: " + StringUtils.split(pri.getEnderecoCelular(), "/")[0];
			
		}
		else if(pri.getEnderecoFax()!=null && !pri.getEnderecoFax().equals("")) {
			
			tel = "Fax: " + StringUtils.split(pri.getEnderecoFax(), "/")[0];
				
		}
		
		return tel;
		
	}
	/**
	 * Retorna se o estabelecimento esta ativo.
	 * 
	 * @return boolean
	 */
	public boolean isEstabelecimentoAtivo() {
		return estabelecimentoAtivo;
	}

	/**
	 * Coloca estabelecimento como ativo.
	 * 
	 * @param estabelecimentoAtivo
	 */
	public void setEstabelecimentoAtivo(boolean estabelecimentoAtivo) {
		this.estabelecimentoAtivo = estabelecimentoAtivo;
	}
	
	/**
	 * Retorna endereços sem repeticao.
	 * 
	 * @return enderecosSemRepeticao
	 */
	@Transient
	public LinkedHashSet<Endereco> getEnderecosSemRepeticao() {
		
		if(enderecos==null) {
			
			return null;
			
		}
		
		if(enderecosSemRepeticao==null) {
			
			enderecosSemRepeticao = new LinkedHashSet<Endereco>();
			
			enderecosSemRepeticao.addAll(enderecos);
			
		}
		
		return enderecosSemRepeticao;
		
	}

	/**
	 * Muda valor associado aos endereços sem repeticao.
	 * 
	 * @param enderecosSemRepeticao
	 */
	public void setEnderecosSemRepeticao(
			LinkedHashSet<Endereco> enderecosSemRepeticao) {
		this.enderecosSemRepeticao = enderecosSemRepeticao;
	}

	/**
	 * @return int hashCode
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((estabelecimentoNome == null) ? 0 : estabelecimentoNome
						.hashCode());
		return result;
	}

	/**
	 * Realiza comparacao.
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
		final Estabelecimento other = (Estabelecimento) obj;
		if (estabelecimentoNome == null) {
			if (other.estabelecimentoNome != null)
				return false;
		} else if (!estabelecimentoNome.equals(other.estabelecimentoNome))
			return false;
		return true;
	}

	/**
	 * Retorna valor associado ao vídeo.
	 * 
	 * @return estabelecimentoVideo
	 */
	public String getEstabelecimentoVideo() {
		return estabelecimentoVideo;
	}

	/**
	 * Muda valor associado ao vídeo.
	 * 
	 * @param estabelecimentoVideo
	 */
	public void setEstabelecimentoVideo(String estabelecimentoVideo) {
		this.estabelecimentoVideo = estabelecimentoVideo;
	}

	/**
	 * Retorna valor associado a primeira foto.
	 * 
	 * @return estabelecimentoFoto1
	 */
	public String getEstabelecimentoFoto1() {
		return estabelecimentoFoto1;
	}

	/**
	 * Muda valor associado a primeira foto.
	 * 
	 * @param estabelecimentoFoto1
	 */
	public void setEstabelecimentoFoto1(String estabelecimentoFoto1) {
		this.estabelecimentoFoto1 = estabelecimentoFoto1;
	}

	/**
	 * Retorna valor associado a segunda foto.
	 * 
	 * @return estabelecimentoFoto2
	 */
	public String getEstabelecimentoFoto2() {
		return estabelecimentoFoto2;
	}

	/**
	 * Muda valor associado a segunda foto.
	 * 
	 * @param estabelecimentoFoto2
	 */
	public void setEstabelecimentoFoto2(String estabelecimentoFoto2) {
		this.estabelecimentoFoto2 = estabelecimentoFoto2;
	}

	/**
	 * Retorna valor associado a terceira foto.
	 * 
	 * @return estabelecimentoFoto3
	 */
	public String getEstabelecimentoFoto3() {
		return estabelecimentoFoto3;
	}

	/**
	 * Muda valor associado a tercerira foto.
	 * 
	 * @param estabelecimentoFoto3
	 */
	public void setEstabelecimentoFoto3(String estabelecimentoFoto3) {
		this.estabelecimentoFoto3 = estabelecimentoFoto3;
	}

	/**
	 * Retorna valor associado a quarta foto.
	 * 
	 * @return estabelecimentoFoto4
	 */
	public String getEstabelecimentoFoto4() {
		return estabelecimentoFoto4;
	}

	/**
	 * Muda valor associado a quarta foto.
	 * 
	 * @param estabelecimentoFoto4
	 */
	public void setEstabelecimentoFoto4(String estabelecimentoFoto4) {
		this.estabelecimentoFoto4 = estabelecimentoFoto4;
	}

	/**
	 * Faz objeto clone da entidade {@link Estabelecimento}
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		Estabelecimento est = new Estabelecimento();
		est.setCidadeBuscada(getCidadeBuscada());
		est.setEnderecoBuscado(getEnderecoBuscado());
		est.setEnderecos(getEnderecos());
		est.setEstabelecimentoAtivo(isEstabelecimentoAtivo());
		est.setEstabelecimentoDescricao(getEstabelecimentoDescricao());
		est.setEstabelecimentoFoto1(getEstabelecimentoFoto1());
		est.setEstabelecimentoFoto2(getEstabelecimentoFoto2());
		est.setEstabelecimentoFoto3(getEstabelecimentoFoto3());
		est.setEstabelecimentoFoto4(getEstabelecimentoFoto4());
		est.setEstabelecimentoHotSite(getEstabelecimentoHotSite("balao.jsp"));
		est.setEstabelecimentoId(getEstabelecimentoId());
		est.setEstabelecimentoLogo(getEstabelecimentoLogo());
		est.setEstabelecimentoNome(getEstabelecimentoNome());
		est.setEstabelecimentoVideo(getEstabelecimentoVideo());
		est.setEstadoBuscado(getEstadoBuscado());
		return est;
	}
	
	/**
	 * Retorna valor associado ao nome para area administrativa.
	 * 
	 * @return estabelecimentoNome
	 */
	@Transient
	public String getEstabelecimentoNomeCurto() {
		return Util.cortaTexto(getEstabelecimentoNome(), 60);
	}
	
	/**
	 * Muda valor associado a entidade {@link Endereco}
	 * 
	 * @param endereco
	 */
	@Transient
	public void setEndereco(Endereco endereco) {
		
		if(enderecos==null) {
			
			enderecos = new ArrayList<Endereco>();
			
			enderecos.add(endereco);
			
		}
		
		else {
			
			enderecos.add(endereco);
			
		}
		
	}

	/**
	 * Muda valor associado a entidade {@link RamoAtividade}
	 * 
	 * @param atividade
	 */
	@Transient
	public void setRAtividade(RamoAtividade atividade) {
		
		if(ramoatividade==null) {
			
			ramoatividade = new ArrayList<RamoAtividade>();
			
			ramoatividade.add(atividade);
			
		}
		
		else {
			
			ramoatividade.add(atividade);
			
		}
		
	}

}