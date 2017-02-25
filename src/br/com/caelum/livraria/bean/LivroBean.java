package br.com.caelum.livraria.bean;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import br.com.caelum.livraria.dao.DAO;
import br.com.caelum.livraria.modelo.Autor;
import br.com.caelum.livraria.modelo.Livro;
import br.com.caelum.livraria.util.RedirectView;

@ViewScoped
@ManagedBean
public class LivroBean {

	private Livro livro = new Livro();

	private Integer autorId;
	
	public List<Autor> getAutoresDoLivro() {
		return this.livro.getAutores();
	}
	
	public List<Livro> getLivros() {
		return new DAO<Livro>(Livro.class).listaTodos();
	}
	
	public void gravarAutor() {
		Autor autor = new DAO<Autor>(Autor.class).buscaPorId(autorId);
		if (!this.livro.getAutores().contains(autor)) {
			this.livro.adicionaAutor(autor);
		}
	}
	
	// o JSF ao encontrar um tipo diferente de String, chamara o toString() do objeto para saber o nome da view
	public RedirectView formAutor() {
		System.out.println("Chamando o formulário do Autor");
		return new RedirectView("autor");
	}
	
	public Integer getAutorId() {
		return autorId;
	}

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}

	public Livro getLivro() {
		return livro;
	}
	
	public List<Autor> getAutores() {
		return new DAO<Autor>(Autor.class).listaTodos();
	}
	
	public void gravar() {
		System.out.println("Gravando livro " + this.livro.getTitulo());

		if (livro.getAutores().isEmpty()) {
//			throw new RuntimeException("Livro deve ter pelo menos um Autor.");
			FacesContext.getCurrentInstance().addMessage("autor", new FacesMessage("Escolha pelo menos um Autor para gravar o livro!"));
			return;
		}

		new DAO<Livro>(Livro.class).adiciona(this.livro);
		
		this.livro = new Livro();
	}
	
	public void comecaComDigitoUm(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		if (value != null && !value.toString().startsWith("1")) {
			throw new ValidatorException(new FacesMessage("Valor de ISBN é inválido!"));
		}
	}

}
