package arvore;

public class HelloArvore {

	public static void main(String[] args) {
		
		ArvoreBinario arvoreBinario = new ArvoreBinario();
		
		arvoreBinario.adicionar(String.valueOf(1));
		arvoreBinario.adicionar(String.valueOf(2));
		arvoreBinario.adicionar(String.valueOf(3));
		
		for (int i = 4; i <=7; i++) {
			arvoreBinario.adicionar(String.valueOf(i));
		}
	    arvoreBinario.imprimir(System.out);
	}
}
