package br.com.bropenmaps.dao.interfaces;

/**
 * Interface que representa uma classe que encapsula operações de acesso a banco
 * @author Rafael Melo Salum
 *
 * @param <T> - entidade relacionada aos acessos
 */
public interface IDAO<T> {
	
	T buscaPelaChave(Long id);
	
}