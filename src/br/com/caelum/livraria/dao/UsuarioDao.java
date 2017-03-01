package br.com.caelum.livraria.dao;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.com.caelum.livraria.modelo.Usuario;

public class UsuarioDao implements Serializable {

	private static final long serialVersionUID = 1L;

	public boolean existeUsuario(Usuario usuario) {

		Usuario resultado = null;
		
		EntityManager em = new JPAUtil().getEntityManager();
		TypedQuery<Usuario> query = em.createQuery(
				  " select u from Usuario u "
				+ " where u.email = :pEmail and u.senha = :pSenha", Usuario.class);
		
		query.setParameter("pEmail", usuario.getEmail());
		query.setParameter("pSenha", usuario.getSenha());
		
		try {
			resultado = query.getSingleResult();
		} catch (NoResultException ex) {
			
		} finally {
			em.close();
		}

		return resultado != null;
	}
}
