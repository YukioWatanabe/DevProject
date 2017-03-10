package com.devproject.controler;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.devproject.jsf.util.FacesUtil;
import com.devproject.model.Funcao;
import com.devproject.model.Projeto;
import com.devproject.model.SolicitacaoAmizade;
import com.devproject.model.SolicitacaoParticipante;
import com.devproject.model.SolicitacaoProjeto;
import com.devproject.model.Usuario;
import com.devproject.model.UsuarioProjeto;
import com.devproject.model.UsuarioProjetoKey;
import com.devproject.service.ProjetoService;
import com.devproject.service.SolicitacaoService;
import com.devproject.service.UsuarioProjetoService;
import com.devproject.service.UsuarioService;

@ManagedBean(name="solicitacoes")
@ViewScoped
public class SolicitacoesBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private final String RECRUTAMENTO = "recrutamento";
	
	private final String PARTICIPACAO = "participacao";
	
	private final String AMIZADE = "amizade";
	
	private final String SOLICITANTE = "idSolicitante";
	
	@ManagedProperty(value="#{usuarioService}")
	private UsuarioService usuarioService;
	
	@ManagedProperty(value="#{projetoService}")
	private ProjetoService projetoService;
	
	@ManagedProperty(value="#{usuarioProjetoService}")
	private UsuarioProjetoService usuarioProjetoService;
	
	@ManagedProperty(value="#{solicitacaoService}")
	private SolicitacaoService solicitacoesService;
	
	@ManagedProperty(value="#{login.usuario.usuario}")
	private Usuario usuario;
	
	private List<SolicitacaoProjeto> solicitacoesRecrutamento;
	private List<SolicitacaoParticipante> solicitacoesParticipacao;
	private List<SolicitacaoAmizade> solicitacoesAmizade;
	private String tipoBusca;
	
    public void inicializar() {
    	tipoBusca = PARTICIPACAO;
    	solicitacoesRecrutamento = solicitacoesService.buscaSolicitacoesProjeto(usuario);
    	solicitacoesAmizade = solicitacoesService.buscaSolicitacoesAmizade(usuario);
    	solicitacoesParticipacao = solicitacoesService.buscaSolicitacoesParticipacao(usuario);
    }
    
    public void porRecrutamento() {
    	tipoBusca = RECRUTAMENTO;
    }
    
    public void porParticipacao() {
    	tipoBusca = PARTICIPACAO;
    }
    
    public void porAmizade() {
    	tipoBusca = AMIZADE;
    }
    
    public void aceitarSolicitacaoRecrutamento(){
    	Usuario solicitante = this.getUsuario(SOLICITANTE);
    	Projeto receptor = this.getProjetoReceptor();
    	SolicitacaoProjeto solicitacao = solicitacoesService.buscaSolicitacaoProjetoPorId(solicitante, receptor);
    	UsuarioProjeto vinculo = this.getUsuarioProjeto(solicitante, receptor, solicitacao.getFuncao());
    	
    	usuarioProjetoService.salvar(vinculo);
    	
    	solicitacao = solicitacoesService.deletarSolicitacaoProjeto(solicitante, receptor, solicitacoesRecrutamento);
    	FacesUtil.adicionaMensagemInfo("Solicitação aceita", "Solicitação de "+solicitacao.getPk().getSolicitante().getNome()+" foi aceita.");
    }

    public void rejeitarSolicitacaoRecrutamento(){
    	Usuario solicitante = this.getUsuario(SOLICITANTE);
    	Projeto receptor = this.getProjetoReceptor();
    	SolicitacaoProjeto solicitacao = solicitacoesService.buscaSolicitacaoProjetoPorId(solicitante, receptor);
    	solicitacao = solicitacoesService.deletarSolicitacaoProjeto(solicitante, receptor, solicitacoesRecrutamento);
    	
    	FacesUtil.adicionaMensagemInfo("Solicitação rejeitada", "Solicitação de "+solicitacao.getPk().getSolicitante().getNome()+" foi rejeitada.");
    }
    
    public void aceitarSolicitacaoParticipacao(){
    	Projeto solicitante = projetoService.buscarProjetoPorId(Long.parseLong(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idSolicitante")));
    	Usuario receptor = usuario;
    	SolicitacaoParticipante solicitacao = solicitacoesService.buscaSolicitacaoParticipantePorId(solicitante, receptor);
    	UsuarioProjeto vinculo = this.getUsuarioProjeto(receptor, solicitante, solicitacao.getFuncao());
    	
    	usuarioProjetoService.salvar(vinculo);
    	
    	solicitacao = solicitacoesService.deletarSolicitacaoParticipante(solicitante, receptor, solicitacoesParticipacao);
    	FacesUtil.adicionaMensagemInfo("Solicitação aceita", "Solicitação de "+solicitacao.getPk().getSolicitante().getTitulo()+" foi aceita.");
    }

    public void rejeitarSolicitacaoParticipacao(){
    	Projeto solicitante = projetoService.buscarProjetoPorId(Long.parseLong(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idSolicitante")));
    	Usuario receptor = usuario;
    	SolicitacaoParticipante solicitacao = solicitacoesService.buscaSolicitacaoParticipantePorId(solicitante, receptor);
    	solicitacao = solicitacoesService.deletarSolicitacaoParticipante(solicitante, receptor, solicitacoesParticipacao);
    	
    	FacesUtil.adicionaMensagemInfo("Solicitação rejeitada", "Solicitação de "+solicitacao.getPk().getSolicitante().getTitulo()+" foi rejeitada.");
    }
    
    private UsuarioProjeto getUsuarioProjeto(Usuario solicitante, Projeto receptor, Funcao funcao) {
    	UsuarioProjeto vinculo = new UsuarioProjeto();
    	vinculo.setPk(new UsuarioProjetoKey(solicitante, receptor));
    	vinculo.setFuncao(funcao);
    	vinculo.setDataInscricao(new Date());
    	return vinculo;
    }
    
	private Usuario getUsuario(String papel) {
		Long id = Long.parseLong(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(papel));
		return usuarioService.buscarPeloId(id);
	}
	
	private Projeto getProjetoReceptor() {
		Long idReceptor = Long.parseLong(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idReceptor"));
    	return projetoService.buscarProjetoPorId(idReceptor);
	}
    
	public void aceitarSolicitacaoAmizade(){
	    Usuario solicitante = getUsuario(SOLICITANTE);
	    Usuario receptor = usuario;
	    
	    SolicitacaoAmizade solicitacao = solicitacoesService.deletarSolicitacaoAmizade(solicitante, receptor, solicitacoesAmizade);
	    
	    usuario = usuarioService.salvarAmizade(solicitante, receptor);
	    
	    FacesUtil.adicionaMensagemInfo("Solicitação aceita", "Solicitação de "+solicitacao.getPk().getSolicitante().getNome()+" foi aceita.");
	}
	
	public void rejeitarSolicitacaoAmizade(){
		Usuario solicitante = getUsuario(SOLICITANTE);
	    Usuario receptor = usuario;
	    
	    SolicitacaoAmizade solicitacao = solicitacoesService.deletarSolicitacaoAmizade(solicitante, receptor, solicitacoesAmizade);
	    
	    FacesUtil.adicionaMensagemInfo("Solicitação rejeitada", "Solicitação de "+solicitacao.getPk().getSolicitante().getNome()+" foi rejeitada.");
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public SolicitacaoService getSolicitacoesService() {
		return solicitacoesService;
	}

	public void setSolicitacoesService(SolicitacaoService solicitacoesService) {
		this.solicitacoesService = solicitacoesService;
	}

	public List<SolicitacaoAmizade> getSolicitacoesAmizade() {
		return solicitacoesAmizade;
	}

	public void setSolicitacoesAmizade(List<SolicitacaoAmizade> solicitacoesAmizade) {
		this.solicitacoesAmizade = solicitacoesAmizade;
	}

	public String getTipoBusca() {
		return tipoBusca;
	}

	public void setTipoBusca(String tipoBusca) {
		this.tipoBusca = tipoBusca;
	}

	public UsuarioService getUsuarioService() {
		return usuarioService;
	}

	public void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	public UsuarioProjetoService getUsuarioProjetoService() {
		return usuarioProjetoService;
	}

	public void setUsuarioProjetoService(UsuarioProjetoService usuarioProjetoService) {
		this.usuarioProjetoService = usuarioProjetoService;
	}

	public ProjetoService getProjetoService() {
		return projetoService;
	}

	public void setProjetoService(ProjetoService projetoService) {
		this.projetoService = projetoService;
	}

	public List<SolicitacaoProjeto> getSolicitacoesRecrutamento() {
		return solicitacoesRecrutamento;
	}

	public void setSolicitacoesRecrutamento(List<SolicitacaoProjeto> solicitacoesRecrutamento) {
		this.solicitacoesRecrutamento = solicitacoesRecrutamento;
	}

	public List<SolicitacaoParticipante> getSolicitacoesParticipacao() {
		return solicitacoesParticipacao;
	}

	public void setSolicitacoesParticipacao(List<SolicitacaoParticipante> solicitacoesParticipacao) {
		this.solicitacoesParticipacao = solicitacoesParticipacao;
	}

}
