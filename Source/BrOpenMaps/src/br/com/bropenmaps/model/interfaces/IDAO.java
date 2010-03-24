package br.com.bropenmaps.dao.interfaces;

/**
 * Interface que representa uma classe que encapsula operações de acesso a banco
 * @author Luiz Gustavo Jordão Soares
 *
 * @param <T> - entidade relacionada aos acessos
 */
public interface IDAO<T> {
	
	T buscaPelaChave(Long id);
	
}