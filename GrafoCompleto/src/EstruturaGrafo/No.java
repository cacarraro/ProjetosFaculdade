package EstruturaGrafo;

public class No
{
//	private String nome;
	private int dado;

	public No(int dado)
	{
//		this.nome = n;
		this.dado =dado;
	}

	/*

	protected void setNome(String novoNome)
	{
		this.nome = novoNome;
	}


	public String getNome()
	{
		String n = nome;
		return n;
	}

	 */

	public int getDado() {
		return dado;
	}

	public void setDado(int dado) {
		this.dado = dado;
	}
}
