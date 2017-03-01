package br.com.caelum.livraria.bean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.caelum.livraria.dao.UsuarioDao;
import br.com.caelum.livraria.modelo.Usuario;

@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Usuario usuario = new Usuario();

	public String efetuaLogin() {
		FacesContext context = FacesContext.getCurrentInstance();
		System.out.println("Efetuando Login");

		boolean existeUsuario = new UsuarioDao().existeUsuario(this.usuario);

		if (existeUsuario) {
			
			// setando o usuario na sessao
			context.getExternalContext().getSessionMap().put("usuarioLogado", this.usuario);
			
			this.usuario = new Usuario();

			return "livro?faces-redirect=true";
		}

		context.addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Email ou senha inválido!", "Email ou senha inválido!"));
		
		// temos que fazer durar duas requisiçoes para mostrar a mensagem e email ou senha invalida por causa do redirect
		context.getExternalContext().getFlash().setKeepMessages(true);

		return "login?faces-redirect=true";

	}
	
	public String deslogar() {
		
		// remover a chave do session map, poi quando for verificar o usuario na sessao, não existirar mais no map e retonara para a tela de login
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getSessionMap().remove("usuarioLogado");
		
		return "login?faces-redirect=true";
	}

	public Usuario getUsuario() {
		return usuario;
	}
}
