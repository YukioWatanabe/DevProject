package com.devproject.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.MapKey;
import javax.persistence.MapKeyClass;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "usuario")
public class Usuario extends Persistivel implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public Usuario(){}
	
	public Usuario(Long id){
		super(id);
	}

	@Size(max=30)
	@NotEmpty(message="O nome não pode estar em branco.")
	@Column(length = 30, nullable = false)
	private String nome;
	
	@Size(max=30)
	@NotEmpty(message="O sobrenome não pode estar em branco.")
	@Column(length = 30, nullable = false)
	private String sobrenome;
	
	@Size(min=4, max=30)
	@NotEmpty(message="O username não pode estar em branco.")
	@Column(unique = true, length = 30, nullable = false)
	private String username;
	
	@Size(max=40)
	@Email(message="O e-mail informado não é um e-mail válido.")
	@NotEmpty(message="O email não pode estar em branco.")
	@Column(length = 40, nullable = false)
	private String email;
	
	@Size(min=6, max=40)
	@NotEmpty(message="A senha não pode estar em branco.")
	@Column(length = 40, nullable = false)
	private String senha;
	
	@NotNull(message="A data de nascimento não é uma data válida.")
	@Temporal(TemporalType.DATE)
	@Past
	private Date dataNascimento;
	
	@Lob
	private byte[] foto;
	
	@Column(length = 16)
	private String telefoneResidencial;
	
	@Column(length = 20)
	private String telefoneComercial;
	
	@Column(length = 16)
	private String celular;
	
	@Column(length = 50)
	private String skype;
	
	@Column(length = 50)
	private String facebook;
	
	@Column(length = 50)
	private String linkedin;
	
	@Column(length = 500)
	private String sobre;
	
	@Column(length = 50)
	private String tema;
	
	@ElementCollection(targetClass=Habilidade.class, fetch=FetchType.LAZY)
	@Enumerated(EnumType.STRING)
	private List<Habilidade> habilidades = new ArrayList<Habilidade>(0);
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.usuario", cascade = CascadeType.ALL)
	@MapKeyClass(UsuarioProjetoKey.class)
	@MapKey(name="pk.usuario")
	private Map<UsuarioProjetoKey, UsuarioProjeto> projetos = new HashMap<UsuarioProjetoKey,UsuarioProjeto>(0);
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.receptor", cascade = CascadeType.ALL)
	@MapKeyClass(SolicitacaoAmizadeKey.class)
	@MapKey(name="pk.receptor")
	private Map<SolicitacaoAmizadeKey,SolicitacaoAmizade> solicitacoesAmizadeRecebidas = new HashMap<SolicitacaoAmizadeKey,SolicitacaoAmizade>(0);
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.solicitante", cascade = CascadeType.ALL)
	@MapKeyClass(SolicitacaoAmizadeKey.class)
	@MapKey(name="pk.solicitante")
	private Map<SolicitacaoAmizadeKey,SolicitacaoAmizade> solicitacoesAmizadeEnviadas = new HashMap<SolicitacaoAmizadeKey,SolicitacaoAmizade>(0);
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.solicitante", cascade = CascadeType.ALL)
	@MapKeyClass(SolicitacaoProjetoKey.class)
	@MapKey(name="pk.solicitante")
	private Map<SolicitacaoProjetoKey,SolicitacaoProjeto> solicitacoesProjetoEnviadas = new HashMap<SolicitacaoProjetoKey,SolicitacaoProjeto>(0);
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.receptor", cascade = CascadeType.ALL)
	@MapKeyClass(SolicitacaoParticipanteKey.class)
	@MapKey(name="pk.receptor")
	private Map<SolicitacaoParticipanteKey,SolicitacaoParticipante> solicitacoesProjetoRecebidas = new HashMap<SolicitacaoParticipanteKey,SolicitacaoParticipante>(0);
	
	@ManyToMany
	private List<Usuario> meusAmigos = new ArrayList<Usuario>(0);
	
	@ManyToMany(cascade = CascadeType.ALL, mappedBy = "meusAmigos")
	private List<Usuario> amigos = new ArrayList<Usuario>(0);
	
	@ManyToMany(mappedBy = "participantes", fetch=FetchType.LAZY)
	private List<Tarefa> tarefas = new ArrayList<Tarefa>(0);

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	public String getTelefoneResidencial() {
		return telefoneResidencial;
	}

	public void setTelefoneResidencial(String telefoneResidencial) {
		this.telefoneResidencial = telefoneResidencial;
	}

	public String getTelefoneComercial() {
		return telefoneComercial;
	}

	public void setTelefoneComercial(String telefoneComercial) {
		this.telefoneComercial = telefoneComercial;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getSkype() {
		return skype;
	}

	public void setSkype(String skype) {
		this.skype = skype;
	}

	public String getFacebook() {
		return facebook;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}

	public String getLinkedin() {
		return linkedin;
	}

	public void setLinkedin(String linkedin) {
		this.linkedin = linkedin;
	}

	public String getSobre() {
		return sobre;
	}

	public void setSobre(String sobre) {
		this.sobre = sobre;
	}

	public List<Habilidade> getHabilidades() {
		return habilidades;
	}

	public void setHabilidades(List<Habilidade> habilidades) {
		this.habilidades = habilidades;
	}

	public Map<UsuarioProjetoKey, UsuarioProjeto> getProjetos() {
		return projetos;
	}

	public void setProjetos(Map<UsuarioProjetoKey, UsuarioProjeto> projetos) {
		this.projetos = projetos;
	}

	public List<Usuario> getAmigos() {
		return amigos;
	}

	public void setAmigos(List<Usuario> amigos) {
		this.amigos = amigos;
	}

	public List<Tarefa> getTarefas() {
		return tarefas;
	}

	public void setTarefas(List<Tarefa> tarefas) {
		this.tarefas = tarefas;
	}

	public List<Usuario> getMeusAmigos() {
		return meusAmigos;
	}

	public void setMeusAmigos(List<Usuario> meusAmigos) {
		this.meusAmigos = meusAmigos;
	}

	public Map<SolicitacaoAmizadeKey, SolicitacaoAmizade> getSolicitacoesAmizadeRecebidas() {
		return solicitacoesAmizadeRecebidas;
	}

	public void setSolicitacoesAmizadeRecebidas(
			Map<SolicitacaoAmizadeKey, SolicitacaoAmizade> solicitacoesAmizadeRecebidas) {
		this.solicitacoesAmizadeRecebidas = solicitacoesAmizadeRecebidas;
	}

	public Map<SolicitacaoAmizadeKey, SolicitacaoAmizade> getSolicitacoesAmizadeEnviadas() {
		return solicitacoesAmizadeEnviadas;
	}

	public void setSolicitacoesAmizadeEnviadas(Map<SolicitacaoAmizadeKey, SolicitacaoAmizade> solicitacoesAmizadeEnviadas) {
		this.solicitacoesAmizadeEnviadas = solicitacoesAmizadeEnviadas;
	}

	public Map<SolicitacaoProjetoKey, SolicitacaoProjeto> getSolicitacoesProjetoEnviadas() {
		return solicitacoesProjetoEnviadas;
	}

	public void setSolicitacoesProjetoEnviadas(Map<SolicitacaoProjetoKey, SolicitacaoProjeto> solicitacoesProjetoEnviadas) {
		this.solicitacoesProjetoEnviadas = solicitacoesProjetoEnviadas;
	}

	public Map<SolicitacaoParticipanteKey, SolicitacaoParticipante> getSolicitacoesProjetoRecebidas() {
		return solicitacoesProjetoRecebidas;
	}

	public void setSolicitacoesProjetoRecebidas(
			Map<SolicitacaoParticipanteKey, SolicitacaoParticipante> solicitacoesProjetoRecebidas) {
		this.solicitacoesProjetoRecebidas = solicitacoesProjetoRecebidas;
	}

	public String getTema() {
		return tema;
	}

	public void setTema(String tema) {
		this.tema = tema;
	}
}
