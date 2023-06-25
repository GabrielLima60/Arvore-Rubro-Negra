package arvore;

import static java.lang.Math.random;
import java.io.PrintStream;

public class ArvoreBinario {
	private NoBinario root;
	public NoBinario trunk;

	public ArvoreBinario() {
		super();
		this.root = new NoBinario("raiz");
	}

	public ArvoreBinario(NoBinario root) {
		super();
		if (root == null) {
			this.root = new NoBinario("raiz");
		} else {
			this.root = root;
		}
	} 

	
  //imprimir a arvore binaria
  public void traversePreOrder(StringBuilder sb, String padding, String pointer, NoBinario No) {
    if (No != null) {
    	sb.append(padding);
    	sb.append(pointer);	
		if (No.getCor())
			sb.append("BLACK");
		
		else 
			sb.append("RED");
        
        sb.append("\n");
        
        StringBuilder paddingBuilder = new StringBuilder(padding);
        paddingBuilder.append("│  ");

        String paddingForBoth = paddingBuilder.toString();
        String pointerForRight = "└──";
        String pointerForLeft = (No.getDireita() != null) ? "├──" : "└──";
        
        traversePreOrder(sb, paddingForBoth, pointerForLeft, No.getEsquerda());
        traversePreOrder(sb, paddingForBoth, pointerForRight, No.getDireita());
    }
  }  
  
  public void imprimir(PrintStream os) {
    StringBuilder sb = new StringBuilder();
    traversePreOrder(sb,"","", this.root);
    os.print(sb.toString());
  }
  	
  	//retorna a quantidade de filhos abaixo de um nó
  	private int quantidadeFilhos(NoBinario noPai) {
  		if (noPai.getEsquerda() == null || noPai.getDireita() == null) 
  			return 0;
  		
  		else 	
  			return 1 + quantidadeFilhos(noPai.getDireita()) + quantidadeFilhos(noPai.getEsquerda());
  			
  	}

	//adiciona nós
  	public void adicionar(String Add) {
  		adicionar(Add, this.root);
  	}
  	

  	private void adicionar(String Add, NoBinario noPai){
  		
  		if (noPai.getEsquerda() == null) 
  			addNo(Add, "esquerda", noPai);

  		else if (noPai.getDireita() == null)
  			addNo(Add, "direita", noPai);		
  		
  		else if (quantidadeFilhos(noPai.getEsquerda()) <= quantidadeFilhos(noPai.getDireita())) 
  			adicionar(Add,noPai.getEsquerda());	
  		
  		else 
  			adicionar(Add, noPai.getDireita());
  	}
 	
  	
  	//funções de adicionar feitas pelo professor
  	
	public void addNo(String Add, String posicao, NoBinario noPai) {
		NoBinario noToAdd = new NoBinario(Add);
		noToAdd.setPai(noPai);
		if (noPai == null) {
			addFilho(noToAdd, posicao, root);
		} else {
			addFilho(noToAdd, posicao, noPai);
		}
	} 

	private void addFilho(NoBinario noToAdd, String posicao, NoBinario pai) {
		if (posicao.equals("direita")) {
			pai.setDireita(noToAdd);
		} else {
			pai.setEsquerda(noToAdd);
		}
		consertaArvoreRubroNegra(noToAdd);
	}
	
	//remover No binario
	
	public void removeNo(String remove) {
		removeNo(remove, this.root);
	}
	
	private void removeNo(String remove, NoBinario noPai) {
	
		if (noPai.getEsquerda() == null) {}
		
		else {
			if (noPai.getEsquerda().getDescricao().equals(remove)){
	  	        noPai.setEsquerda(null);
	  	      }
	  	      else {
	  	        removeNo(remove, noPai.getEsquerda());
	  	      }
		}	
	  	    
		if (noPai.getDireita() == null) {}	
		
		else {
	  	    if (noPai.getDireita().getDescricao().equals(remove)){
	  	    	noPai.setDireita(null);
	  	      }
	  	      else {
	  	        removeNo(remove, noPai.getDireita());
	  	      } 
		} 	 		
	}

	private void girarDireita(NoBinario no) {
		NoBinario pai = no.getPai();
		NoBinario filhoEsquerdo = no.getEsquerda();

		no.setEsquerda(filhoEsquerdo.getDireita());
		if (filhoEsquerdo.getDireita() != null) {
			no.getEsquerda().getDireita().setPai(no);
		}

		filhoEsquerdo.setDireita(no);
		no.setPai(filhoEsquerdo);

		trocarFilho(pai, no, filhoEsquerdo);
	}

	private void girarEsquerda(NoBinario no) {
		NoBinario pai = no.getPai();
		NoBinario filhoDireito = no.getDireita();

		no.setDireita(filhoDireito.getDireita());
		if (filhoDireito.getEsquerda() != null) {
			filhoDireito.getEsquerda().setPai(no);
		}

		filhoDireito.setEsquerda(no);
		no.setPai(filhoDireito);

		trocarFilho(pai, no, filhoDireito);
	}

	private void trocarFilho(NoBinario pai, NoBinario antigoFilho, NoBinario novoFilho) {
		if (pai == null) {
			root = novoFilho;
		} else if (pai.getEsquerda() == antigoFilho) {
			pai.setEsquerda(novoFilho);
		} else if (pai.getDireita() == antigoFilho) {
			pai.setDireita(novoFilho);
		} else {
			throw new IllegalStateException("o no passado não é um filho do nó pai");
		}

		if (novoFilho != null) {
			novoFilho.setPai(pai);
		}
	}

	private void consertaArvoreRubroNegra(NoBinario no) {

		boolean RED = false;
		boolean BLACK = true;

		NoBinario pai = no.getPai();

		// Case 1: Parent is null, we've reached the root, the end of the recursion
		if (pai == null) {
			no.setCor(BLACK);
			return;
		}

		// pai is black --> nothing to do
		if (pai.getCor() == BLACK) {
			return;
		}

		// From here on, pai is red
		NoBinario avo = pai.getPai();

		// Case 2:
		// Not having a avo means that parent is the root. If we enforce black roots
		// (rule 2), avo will never be null, and the following if-then block can be
		// removed.
		if (avo == null) {
			// As this method is only called on red nodes (either on newly inserted ones - or -
			// recursively on red avos), all we have to do is to recolor the root black.
			pai.setCor(BLACK);
			return;
		}

		// Get the tio (may be null/nil, in which case its color is BLACK)
		NoBinario tio = getTio(pai);

		// Case 3: tio is red -> recolor pai, avo and tio
		if (tio != null && tio.getCor() == RED) {
			pai.setCor(BLACK);
			avo.setCor(RED);
			tio.setCor(BLACK);

			// Call recursively for avo, which is now red.
			// It might be root or have a red pai, in which case we need to fix more...
			consertaArvoreRubroNegra(avo);
		}

		// pai is left child of avo
		else if (pai == avo.getEsquerda()) {
			// Case 4a: tio is black and node is left->right "inner child" of its avo
			if (no == pai.getDireita()) {
			girarEsquerda(pai);

			// Let "pai" point to the new root node of the rotated sub-tree.
			// It will be recolored in the next step, which we're going to fall-through to.
			pai = no;
			}

			// Case 5a: tio is black and node is left->left "outer child" of its avo
			girarDireita(avo);

			// Recolor original pai and avo
			pai.setCor(BLACK);
			avo.setCor(RED);
		}

		// pai is right child of avo
		else {
			// Case 4b: tio is black and node is right->left "inner child" of its avo
			if (no == pai.getEsquerda()) {
			girarDireita(pai);

			// Let "pai" point to the new root node of the rotated sub-tree.
			// It will be recolored in the next step, which we're going to fall-through to.
			pai = no;
			}

			// Case 5b: tio is black and node is right->right "outer child" of its avo
			girarEsquerda(avo);

			// Recolor original pai and avo
			pai.setCor(BLACK);
			avo.setCor(RED);
		}
	}

	private NoBinario getTio(NoBinario pai) {
		NoBinario avo = pai.getPai();
		if (avo.getEsquerda() == pai) {
			return avo.getDireita();
		} else if (avo.getDireita() == pai) {
			return avo.getEsquerda();
		} else {
			throw new IllegalStateException("pai is not a child of its avo");
		}
	}

}





