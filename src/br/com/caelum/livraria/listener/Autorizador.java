package br.com.caelum.livraria.listener;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import br.com.caelum.livraria.modelo.Usuario;

public class Autorizador implements PhaseListener {

	private static final long serialVersionUID = 1L;

	@Override
	public void afterPhase(PhaseEvent event) {
		FacesContext context = event.getFacesContext();
		String nomePagina = context.getViewRoot().getViewId();

		System.out.println(nomePagina);

		Usuario usuarioLogado = (Usuario) context.getExternalContext().getSessionMap().get("usuarioLogado");

		if ("/login.xhtml".equals(nomePagina) || usuarioLogado != null) {
			return;
		}

		// se chegar aqui é pq o usuário não está logado e está tentando acessar
		// outras paginas
		// fazendo navegação programaticamente
		NavigationHandler navigator = context.getApplication().getNavigationHandler();
		navigator.handleNavigation(context, null, "/login?faces-redirect=true");

		context.renderResponse();
	}

	@Override
	public void beforePhase(PhaseEvent event) {

	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

}
