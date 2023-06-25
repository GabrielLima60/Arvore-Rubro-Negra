package arvore;

public class NoBinario {

	private static final boolean RED = false;

	private String descricao;
	private NoBinario direita;
	private NoBinario esquerda;

	private boolean cor;
	private NoBinario pai;

	public NoBinario(String descricao) {
		this.direita = null;
		this.esquerda = null;
		this.descricao = descricao;
		this.pai = null;
		this.cor = RED;
	}

	public NoBinario(String descricao, NoBinario pai,
			NoBinario direita, NoBinario esquerda) {
		super();
		this.pai = pai;
		this.descricao = descricao;
		this.direita = direita;
		this.esquerda = esquerda;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public NoBinario getDireita() {
		return direita;
	}

	public void setDireita(NoBinario direita) {
		this.direita = direita;
	}

	public NoBinario getEsquerda() {
		return esquerda;
	}

	public void setEsquerda(NoBinario esquerda) {
		this.esquerda = esquerda;
	}

	public boolean getCor() {
		return cor;
	}

	public void setCor(boolean cor) {
		this.cor = cor;
	}

	public NoBinario getPai() {
		return pai;
	}

	public void setPai(NoBinario pai) {
		this.pai = pai;
	}

}