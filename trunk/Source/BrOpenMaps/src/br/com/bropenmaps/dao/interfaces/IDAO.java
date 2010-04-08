package br.com.bropenmaps.dao.interfaces;

/**
 * Interface que representa uma classe que encapsula opera��es de acesso a banco
 * @author Daniel Melo
 *
 * @param <T> - entidade relacionada aos acessos
 */
public interface IDAO<T> {
	
	T buscaPelaChave(Long id);
	
}