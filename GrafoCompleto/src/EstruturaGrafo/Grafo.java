package EstruturaGrafo;

import EstruturaGrafo.Fila.Fila;
import EstruturaGrafo.ListaEncadeada.ListaEncadeada;
import EstruturaGrafo.ListaEncadeada.NoL;

import java.util.ArrayList;

import static java.lang.Double.POSITIVE_INFINITY;

public class Grafo
{
	private final int tamanho;
	private final No[] noe;
	private final ListaEncadeada[] listaAdjacencias;
	
	private static final int Inf = 999999999;
	private static final Double InfDoub = POSITIVE_INFINITY;

	private final int[] grausDeSaida;
	private final int[] grausDeEntrada;

	private final boolean[][] fechamento;
	private final double[][] pesoMenoresCaminhos;
	private final boolean[][][] participaMenoresCaminho;


	public Grafo(int t)
	{
		this.tamanho = t;

		noe = new No[tamanho];

		grausDeSaida = new int[tamanho];
		grausDeEntrada = new int[tamanho];

		fechamento = new boolean[tamanho][tamanho];
		pesoMenoresCaminhos = new double[tamanho][tamanho];
		participaMenoresCaminho = new boolean[tamanho][tamanho][tamanho];

		listaAdjacencias = new ListaEncadeada[tamanho];
		for(int i = 0; i<tamanho; i++)
		{
			listaAdjacencias[i] = new ListaEncadeada();
			grausDeEntrada[i] = 0;
			grausDeSaida[i] = 0;
		}
	}

	public void insereGrafo()
	{
		int k = 0;
		while (k < tamanho)
		{
			noe[k] = new No(k);
			k++;
		}
	}

//	cria uma adjacencia entre os Nos i e j com peso P, se os Nos estiverem inseridos no grafo
	public void cria_adjacencia(int i, int j, double P)
	{
		if (i > tamanho || j > tamanho)
		{
			System.out.println(i + " ou " + j + " não pertence ao grafo");
		} else
			{
				if (listaAdjacencias[i].existeElemento(j))
				{
					System.out.println("Adjacencia " + noe[i].getDado() + " e " + noe[j].getDado() + " ja inserida no grafo!");
				} else
					{
						listaAdjacencias[i].insereOrdenado(j, P);
						grausDeEntrada[j]++; //System.out.println("v: " + j + " gEntrada: " + grausDeEntrada[j]);
						grausDeSaida[i]++; //System.out.println("v: " + i + " gSaida: " + grausDeSaida[i]);
						System.out.println("Nova adjacencia adicionada ao Grafo! " + noe[i].getDado() + " e " + noe[j].getDado());
					}
				System.out.println(" ");
			}
	}

//	Se a adjacencia entre os nos i e j existe, ela sera removida do grafo
	public void remove_adjacencia(int i, int j)
	{
		if (i > tamanho || j > tamanho)
		{
			System.out.println(i + " ou " + j + " não pertence ao grafo");
		} else {
			if (!listaAdjacencias[i].existeElemento(j))
			{
				System.out.println("Adjacencia " + noe[i].getDado() + " e " + noe[j].getDado() + " ja removida ou n�o existe no grafo!");
			} else {
				listaAdjacencias[i].retiraAtual(j);
				grausDeEntrada[j]--;
				grausDeSaida[i]--;
				System.out.println("Adjacencia Removida: " + noe[i].getDado() + " e " + noe[j].getDado());
			}
			System.out.println(" ");
		}
	}


//	imprime toda a lista de adjacencia, mostrando o nome dos nos e seus pesos
	public void  imprime_adjacencias()
	{
		for (int k = 0; k < tamanho; k++)
		{
			System.out.println("Adjacencias de: " + noe[k].getDado());
			listaAdjacencias[k].mostraListaCompleta();
			System.out.println(" ");
		}
	}

//	atualiza a informacao contida no No do grafo
	public void seta_informacao(int i, int novo)
	{
		if (novo > tamanho)
		{
			System.out.println(novo + " não podera ser encotrado no grafo");
		}
		else {
			System.out.println(" ");
			System.out.println(noe[i].getDado());
			System.out.println("Antigo nome: " + noe[i].getDado());
			noe[i].setDado(novo);
			System.out.println("Novo nome: " + noe[i].getDado());
			System.out.println(" ");
		}
	}

//	Adicona o adjacencia na adj[] e retorna o numero de adjacencias de i
	public int[] adjacentes(int i)
	{
		//inicializa adjacentes
		int[] adj = new int[tamanho];
		for (int e = 0; e<tamanho; e++)
		{
			adj[e] = Inf;
		}

		//atualiza adjacentes
		if (i > tamanho)
		{
			System.out.println(i + " não pertence ao grafo");
		} else {
//			System.out.println(" ");
//			System.out.println("No: " + noe[i].getDado());

			//int index = 0;
//			int contador = 0;

			NoL aux = listaAdjacencias[i].getPrimeiro();
			while (aux != null)
			{
				adj[listaAdjacencias[i].getDado(aux)] = listaAdjacencias[i].getDado(aux);
//				System.out.println(adj[listaAdjacencias[i].getDado(aux)]);
				aux = listaAdjacencias[i].getProximoL(aux);
				//index++;
//				contador++;
			}
//			System.out.println("Numero de adjacencias: " + contador);
			return adj;
		}
		return adj;
	}

//	Retorna o peso da adjacencia entre i e j, se existir
	public double getPeso(int i, int j)
	{
		double peso;
		if (i > tamanho || j > tamanho)
		{
			System.out.println(i + " ou " + j + " não pertence ao grafo");
		} else {
			peso = listaAdjacencias[i].mostraElemento(j).getPeso();
			return peso;
		}
		return Inf;
	}

//	algoritmo de Warshall
	public void fechamento()
	{
		//inicializando matriz de fechamento
		for (int i=0; i < tamanho; i++)
		{
			for (int j=0; j < tamanho; j++)
			{
				fechamento[i][j] = listaAdjacencias[i].existeElemento(j);
			}
		}

		// warshall
		for (int k = 0; k <tamanho; k++)
		{
			for (int i = 0; i < tamanho; i++)
			{
				if (fechamento[i][k])
				{
					for (int j = 0; j < tamanho; j++)
					{
						fechamento[i][j] = fechamento[i][j] || fechamento[k][j];
					}
				}
			}
		}

		for (int h = 0; h < tamanho; h++)
		{
			for (int g = 0; g < tamanho; g++)
			{
				System.out.print(fechamento[h][g] + " ");
			}
			System.out.println();
		}
	}

	public int buscaProfundidade(int origem, int destino, boolean[]visitados)
	{
//		boolean[]visitados = new boolean[tamanho];

		if (origem == destino)
		{
			visitados[destino] = true;
			System.out.print(origem);
			for (int i = 0; i < tamanho; i++)
			{
				if (visitados[i])
				{
					return noe[i].getDado();
				}
			}
		}
		else if (!visitados[origem])
		{
			System.out.print(origem + " ");
			visitados[origem] = true;

			NoL aux = listaAdjacencias[origem].getPrimeiro();
			while (aux != null)
			{
				int x = buscaProfundidade(listaAdjacencias[origem].getDado(aux), destino, visitados);
				if (x != Inf)
				{
					return x;
				}
				aux = aux.getProximo();
			}
		}
		return Inf;
	}

	public int buscaLargura(Fila f, int destino, boolean[]visitados)
	{

		if (f.vazia())
		{
			System.out.println("fila vazia");
			return Inf;
		}
		else {
			int x = f.remove();
			System.out.print(x + " ");
			visitados[x] = true;
			if (x == destino)
			{
//				visitados[destino] = true;
//				System.out.println(x);
				for (int i = 0; i < tamanho; i++)
				{
					if (visitados[i])
					{
						return noe[i].getDado();
					}
				}
			}else
				{
					NoL aux = listaAdjacencias[x].getPrimeiro();
					while (aux != null)
					{
						if (!visitados[listaAdjacencias[x].getDado(aux)])
						{
							if (!f.contem(listaAdjacencias[x].getDado(aux)))
							{
								f.adiciona(listaAdjacencias[x].getDado(aux));
							}
						}
						aux = aux.getProximo();
					}
					return buscaLargura(f, destino, visitados);
				}
			return buscaLargura(f, destino, visitados);
		}
	}

	//busca que sera feita na contagem do numero de componentes
	public void buscaProfSemDestino(int origem, boolean[] visitados)
	{
		visitados[origem] = true;
		System.out.print(origem + " ");
		int[] percorre = adjacentes(origem);
		for (int e = 0; e<tamanho; e++)
		{
			if (!visitados[e] && percorre[e] != Inf)
			{
				buscaProfSemDestino(e, visitados);
			}
		}
	}

	//mostra os vertices de cada componente e o numero de componentes
	public int getComponentes()
	{
		boolean[] visitados = new boolean[tamanho];
		int quantos = 0;
		for (int i = 0; i < tamanho; i++)
		{
			if (!visitados[i])
			{
				quantos++;
				System.out.println("Componente: " + quantos);
				buscaProfSemDestino(i, visitados);
				System.out.println();
			}
		}
		System.out.println("Total de Componentes: " + quantos);
		return quantos;
	}

	public boolean verificaCiclo(int i, boolean[] amarelo)
	{
		if (amarelo[i])
		{
			return true;
		}

		amarelo[i] = true;

		int[] adj = adjacentes(i);

		for (int e : adj)
		{
			if (e != Inf)
			{
				boolean tenta = verificaCiclo(e, amarelo);
				if (tenta)
				{
					System.out.println("t " + e);
					return true;
				}
			}
		}
		amarelo[i] = false;
		return false;
	}

	public boolean possuiCiclo()
	{
		boolean[] visitados = new boolean[tamanho];

		for (int j = 0; j < tamanho; j++)
		{
			visitados[j] = false;
		}
		for (int i = 0; i < tamanho; i++)
		{
			if (verificaCiclo(i, visitados))
			{
				System.out.println("true");
				return true;
			}
		}
		System.out.println("false");
		return false;
	}

	public ListaEncadeada[] prim(int inic)
	{
		ArrayList<Integer> N = new ArrayList<>();
		N.add(inic);

		ListaEncadeada[] A = new ListaEncadeada[tamanho];
		ListaEncadeada[] Al = new ListaEncadeada[tamanho];

		for(int i =0; i<tamanho; i++) //inicializacao...
		{
			A[i] = new ListaEncadeada();
			Al[i] = new ListaEncadeada();
		}

		while (N.size() < tamanho) //enquanto
		{
			for(int i = 0; i < N.size(); i++)//percorre os nos em N
			{
				for (int j = 0; j < tamanho; j++) //percorre os nos do grafo
				{
					if (N.get(i) == j) // se for o no em N adiciona todas as suas adjacencias
					{
						Al[j] = listaAdjacencias[j];
					}

					//para remover
					if (listaAdjacencias[j].existeElemento(N.get(i))) // se nao for o no em N e se existir adjacencia teminada no No de N
					{
						NoL aux = listaAdjacencias[j].getPrimeiro();
						while (aux != null)    //percorre a lista encadeada do elemento
						{
							if (listaAdjacencias[j].getDado(aux) == N.get(i)) //se for a adjacencia
							{
								Al[j].retiraAtual(N.get(i));    //remove da lista de arestas candidatas
							}
							aux = aux.getProximo();
						}
					}
				}
			}

			double menorPeso = Inf;
			int menorNoSai = Inf;
			int menorNoEntra = Inf;

			for (int k = 0; k < Al.length; k++)	//percorre Al e encontra o menor peso
			{
				NoL candidata = Al[k].getPrimeiro();
				while (candidata != null)
				{
					if (candidata.getPeso() < menorPeso) //ele nao considera 2 < 5, ou ele n ta resetando a variavel no inf
					{
						menorPeso = candidata.getPeso();
						menorNoSai = k;
						menorNoEntra = Al[k].getDado(candidata);
					}
					candidata = candidata.getProximo();
				}
			}

			A[menorNoSai].insereOrdenado(menorNoEntra, menorPeso);	//adiciona a aresta de menor peso em A
			if (!N.contains(menorNoEntra))
			{
				N.add(menorNoEntra);
			}
		}
		for (ListaEncadeada l:A)
		{
			l.mostraListaCompleta();
		}
		return A;
	}

	public int getGrauSaida(int elemento)
	{
		return grausDeSaida[elemento];
	}

	public int getGrauEntrada(int elemento)
	{
		return grausDeEntrada[elemento];
	}


	/////* PROJETO 2 */////
	public boolean conexo() //verifica se o grafo é conexo
	{
		return getComponentes() <= 1;
	}

//	public boolean EulerNaoDirecionado()
//	{
//		if (!conexo()) //se o grafo nao for conexo
//		{
//			System.out.println("nao ha caminho euleriano pois o grafo é desconexo");
//			return false;
//		}
//		else //se o grafo for conexo
//		{
//			int nGrauImpar = 0;	// encontra o numero de vertices com grau de saida impar
//
//			int i = 0;
//			while (i < tamanho && nGrauImpar <= 2) //percorre a lista de adjacencias e calcula o grau de todos os vertices
//			{
//				int grau = getGrauSaida(i);
//				if (grau % 2 == 1) 	// se o grau de saida do vertice for impar
//				{
//					nGrauImpar++; 	// soma ao numero de vertices impar
//				}
//				i++;
//			}
//
//			if (nGrauImpar == 0 || nGrauImpar == 2) // se existirem exatamente 0 ou 2 vertices de grau impar (no pseudocodigo do livro ela faz nGrau <= 2)
//			{
//				System.out.println("ha caminho euleriano");
//				return true;	// o grafo contem um caminho euleriano
//			}
//			else	// se houver 1 ou mais de 2 vertices impar
//			{
//				System.out.println("nao ha caminho euleriano pois foram encontrados mais de 2 vertices impares");
//				return false;	// o grafo nao contem um caminho euleriano
//			}
//		}
//	}

	public void EulerDirecionado()
	{
		if (!conexo()) //se o grafo nao for conexo
		{
			System.out.println("nao ha caminho euleriano pois o grafo é desconexo");
		}
		else //se o grafo for conexo
		{
			int nGrauImpar = 0;	// encontra o numero de vertices com grau de saida impar

			int i = 0;
			while (i < tamanho && nGrauImpar <= 2) //percorre a lista de adjacencias e calcula o grau de todos os vertices
			{
				int grau = (getGrauSaida(i) + getGrauEntrada(i));
				if (grau % 2 == 1) 	// se o grau de saida do vertice for impar
				{
					nGrauImpar++; 	// soma ao numero de vertices impar
				}
				i++;
			}

			if (nGrauImpar == 0 || nGrauImpar == 2) // se existirem exatamente 0 ou 2 vertices de grau impar (no pseudocodigo do livro ela faz nGrau <= 2)
			{
				System.out.println("ha caminho euleriano");
			}
			else	// se houver 1 ou mais de 2 vertices impar
			{
				System.out.println("nao ha caminho euleriano pois foram encontrados 1 vertice, ou mais de 2 vertices impares");
			}
		}
	}

	public void floydWarshall() //retorna uma matriz com os pesos/distancia dos menores caminhos entre todos os vertices
	{
		boolean[] vizinho = new boolean[tamanho];
//		double[][] peso = new double[tamanho][tamanho];

		//INICIALIZACAO
		for (int i = 0; i<tamanho; i++)
		{
			int[] adj = adjacentes(i);

			for (int y = 0; y <tamanho; y++) // coloca tds as adj de i num bool
			{
				if (adj[y] != Inf)	//se é Inf, nao sao adjacentes
				{
					vizinho[y] = true;
				}
				else
				{
					vizinho[y] = false;
				}
				for (int z = 0; z <tamanho; z++) {
					participaMenoresCaminho[i][y][z] = false;    //so marca como true os vertices no meio do caminho, se eles sao adjacentes ambos estao na ponta
				}
			}

			for (int j = 0; j<tamanho; j++)
			{
				if (vizinho[j]) //se i e j sao adj
				{
					NoL aux = listaAdjacencias[i].getPrimeiro();
					while (aux != null)
					{
						if (listaAdjacencias[i].getDado(aux) == j)
						{
							pesoMenoresCaminhos[i][j] = aux.getPeso();
						}
						aux = aux.getProximo();
					}
				}
				else	//senao
				{
					pesoMenoresCaminhos[i][j] = InfDoub;
				}
			}
		}

//		System.out.println("INICIALIZADO: ");  //OK
//		for (double[] d: peso )
//		{
//			for (double p: d)
//			{
//				System.out.print(p + "  ||  ");
//			}
//			System.out.println();
//		}
		System.out.println();

		//PERCORRE
		for (int k = 0; k < tamanho; k++)
		{
			for (int ii = 0; ii < tamanho; ii++)
			{
				for (int jj = 0; jj < tamanho; jj++)
				{
//					System.out.print("P: " + ii + " -> " + jj + ": " + peso[ii][jj]);
//					System.out.print(" <> P: " + ii + " -> " + k + ": " + peso[ii][k]);
//					System.out.print(" + P: " + k + " -> " + jj + ": " + peso[k][jj] + "\n");

					if (pesoMenoresCaminhos[ii][jj] > (pesoMenoresCaminhos[ii][k] + pesoMenoresCaminhos[k][jj]))
					{
						pesoMenoresCaminhos[ii][jj] = pesoMenoresCaminhos[ii][k] + pesoMenoresCaminhos[k][jj];
						participaMenoresCaminho[ii][jj][k] = true; //k esta no caminho de ii e jj
//						System.out.println("Caminho: " + ii + " -> " + jj + " passa por: " + k);
//						System.out.println("np DENTRO " + ii + " -> " + jj + ": " + peso[ii][jj]);
					}
//					System.out.println("np " + ii + " -> " + jj + ": " + peso[ii][jj]);
				}
			}
		}

//		System.out.println("FINALIZADO: ");
//		for (double[] d: pesoMenoresCaminhos)
//		{
//			for (double p: d)
//			{
//				System.out.print(p + "  ||  ");
//			}
//			System.out.println();
//		}
//		return peso;
	}

	public void imprimePesosMenoresCaminhos()
	{
		floydWarshall();
		for (int i =0; i<tamanho; i++)
		{
			for (int j = 0; j < tamanho; j++)
			{
				System.out.print(pesoMenoresCaminhos[i][j] + "	||	");
			}
			System.out.println();
		}
	}

	public void imprimeMenoresCaminhos()
	{
		floydWarshall();
		for (int i =0; i<tamanho; i++)
		{
//			System.out.print(i + " -> ");
			for (int j = 0; j < tamanho; j++)
			{
				System.out.print(i + " -> " + j + " caminho: " + i + " ");
				for (int k = 0; k<tamanho; k++)
				{
					if (participaMenoresCaminho[i][j][k]) {
						System.out.print(k + " ");
					}
				}
				System.out.print(j);
			System.out.print(" | ");
			}
			System.out.println();
		}
	}

	public double centralidadeProximidade(int v) //retorna a centralidade de proximidade em grafos direcionados (podem ser fracamente conexos)
	{
		if (conexo())
		{
			double somaMenorDistancia = 0;
			floydWarshall(); //garante que a matriz com os menores pesos dos menores caminhos e os menores caminhos foi preenchida

			for (int i = 0; i < tamanho; i++) {
				if (pesoMenoresCaminhos[v][i] != InfDoub) {
					somaMenorDistancia += pesoMenoresCaminhos[v][i];
				}
			}
			return 1 / somaMenorDistancia;
		}
		return 0;
	}

	public double centralidadeIntermediacao(int v)
	{
		double somaNgeodesicos = 0;
		floydWarshall(); //garante que a matriz com os menores pesos dos menores caminhos e os menores caminhos foi preenchida

		for (int i = 0; i < tamanho; i++) //percorre a matriz para encontrar o numero de caminhos em que v aparece
		{
			for (int j = 0; j <tamanho; j++)
			{
				if (participaMenoresCaminho[i][j][v])	//se v faz pate do caminho
				{
					somaNgeodesicos++;					//soma 1 ao umero de geodesicos
				}
			}
		}
		return somaNgeodesicos/((tamanho-1)*(tamanho-2));
	}
}
