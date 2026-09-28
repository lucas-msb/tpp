import java.io.*;
import java.util.*;

public class Veiculo {
    private int id;
    private String marca;
    private String modelo;
    private int ano;
    private String categoria;
    private String[] combustivel;
    private int cilindros;
    private double cilindrada;
    private String transmissao;
    private String tracao;
    private double consumoCidade;
    private double consumoEstrada;
    private double co2;
    private boolean turbo;
    private Data dataRegistro;

    public Veiculo(int id, String marca, String modelo, int ano, String categoria, String[] combustivel, 
                   int cilindros, double cilindrada, String transmissao, String tracao, 
                   double consumoCidade, double consumoEstrada, double co2, boolean turbo, Data dataRegistro) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.categoria = categoria;
        this.combustivel = combustivel;
        this.cilindros = cilindros;
        this.cilindrada = cilindrada;
        this.transmissao = transmissao;
        this.tracao = tracao;
        this.consumoCidade = consumoCidade;
        this.consumoEstrada = consumoEstrada;
        this.co2 = co2;
        this.turbo = turbo;
        this.dataRegistro = dataRegistro;
    }

    public int getId() { return this.id; }
    public String getMarca() { return this.marca; }
    public String getModelo() { return this.modelo; }
    public int getAno() { return this.ano; }
    public String getCategoria() { return this.categoria; }
    public String[] getCombustivel() { return this.combustivel; }
    public int getCilindros() { return this.cilindros; }
    public double getCilindrada() { return this.cilindrada; }
    public String getTransmissao() { return this.transmissao; }
    public String getTracao() { return this.tracao; }
    public double getConsumoCidade() { return this.consumoCidade; }
    public double getConsumoEstrada() { return this.consumoEstrada; }
    public double getCo2() { return this.co2; }
    public boolean isTurbo() { return this.turbo; }
    public Data getDataRegistro() { return this.dataRegistro; }

    // Separa a linha por virgulas e split no combustivel por ';'
    public static Veiculo parseVeiculo(String linha) {
        String[] atributos = linha.split(",");

        int id = Integer.parseInt(atributos[0]);
        String marca = atributos[1];
        String modelo = atributos[2];
        int ano = Integer.parseInt(atributos[3]);
        String categoria = atributos[4];
        String[] combustivel = atributos[5].split(";");
        int cilindros = Integer.parseInt(atributos[6]);
        double cilindrada = Double.parseDouble(atributos[7]);
        String transmissao = atributos[8];
        String tracao = atributos[9];
        double consumoCidade = Double.parseDouble(atributos[10]);
        double consumoEstrada = Double.parseDouble(atributos[11]);
        double co2 = Double.parseDouble(atributos[12]);
        boolean turbo = Boolean.parseBoolean(atributos[13]);
        Data dataRegistro = Data.parseData(atributos[14]); 

        return new Veiculo(id, marca, modelo, ano, categoria, combustivel, cilindros, 
                       cilindrada, transmissao, tracao, consumoCidade, consumoEstrada, 
                       co2, turbo, dataRegistro);
    }

    // Saida padronizada dos atributos em string
    public String format() {
        String combustiveis = String.join(",", this.combustivel);
        
        return String.format(
            "[%d ## %s ## %s ## %04d ## %s ## [%s] ## %d ## %.1f ## %s ## %s ## %.2f ## %.2f ## %.1f ## %b ## %s]",
            this.id, this.marca, this.modelo, this.ano, this.categoria, combustiveis,
            this.cilindros, this.cilindrada, this.transmissao, this.tracao,
            this.consumoCidade, this.consumoEstrada, this.co2, this.turbo, this.dataRegistro.format()
        );
    }   

    public static Veiculo pesquisaSequencial(int id, Veiculo[] carros) {
        for (Veiculo carro : carros) {
            if (carro != null && carro.getId() == id) {
                return carro;
            }
        }
        return null;
    }

    public static void main(String[] args) {
		//QUESTÃO 1
		/*Scanner sc = new Scanner(System.in);
		int id = 0;
		Veiculo[] veiculos = LeitorCsv.ler("/tmp/veiculos.csv");

		while ((id = sc.nextInt()) != -1){
			Veiculo achado = pesquisaSequencial(id, veiculos);
			System.out.println(achado.format());
		}
		sc.close();
		*/

		//QUESTÃO 4
		/*Scanner sc = new Scanner(System.in);
		int id = 0;
		Veiculo[] veiculos = LeitorCsv.ler("/tmp/veiculos.csv");
		ListaEncadeada lista = new ListaEncadeada();

		while ((id = sc.nextInt()) != -1){
			Veiculo achado = pesquisaSequencial(id, veiculos);
			lista.inserirFim(achado);
		}
		lista.insertionSort();
		lista.exibir();
		sc.close();
		*/

		//QUESTÃO 7
		/*
		Scanner sc = new Scanner(System.in);
		int id = 0;
		Veiculo[] veiculos = LeitorCsv.ler("/tmp/veiculos.csv");

		ListaEncadeada lista = new ListaEncadeada();

		while ((id = sc.nextInt()) != -1){
			Veiculo achado = pesquisaSequencial(id, veiculos);
			lista.inserirFim(achado);
		}
		lista.bucketSort();
		lista.exibir();
		sc.close();
		*/
		

		//QUESTÃO 9
		/*
		Scanner sc = new Scanner(System.in);
		int id = 0;
		Veiculo[] veiculos = LeitorCsv.ler("/tmp/veiculos.csv");

		ListaSequencial lista = new ListaSequencial(500);

		while ((id = sc.nextInt()) != -1){
			Veiculo achado = pesquisaSequencial(id, veiculos);
			lista.inserirFim(achado);
		}

		int n = sc.nextInt();
		sc.nextLine();

		for (int i = 0; i < n; i++){
			String linha = sc.nextLine();
			String[] partes = linha.split(" ");
            		String k = partes[0];

            		switch (k) {
                		case "II": {
                    		int idV = Integer.parseInt(partes[1]);
                    		lista.inserirInicio(pesquisaSequencial(idV, veiculos));
                    		break;
                		}

                		case "I*": {
                    		int pos = Integer.parseInt(partes[1]);
                    		int idV = Integer.parseInt(partes[2]);
                    		lista.inserir(pesquisaSequencial(idV, veiculos), pos);
                    		break;
                		}

                		case "IF": {
                    		int idV = Integer.parseInt(partes[1]);
                    		lista.inserirFim(pesquisaSequencial(idV, veiculos));
                    		break;
                		}

                		case "RI": {
                    		Veiculo r = lista.removerInicio();
                    		System.out.println("(R)" + r.getMarca() + " " + r.getModelo());
                    		break;
                		}

                		case "R*": {
                    		int pos = Integer.parseInt(partes[1]);
                    		Veiculo r = lista.remover(pos);
                    		System.out.println("(R)" + r.getMarca() + " " + r.getModelo());
                    		break;
                		}

                		case "RF": {
                    		Veiculo r = lista.removerFim();
                    		System.out.println("(R)" + r.getMarca() + " " + r.getModelo());
                    		break;
                		}
            		}
        	}

        	lista.mostrar();
        	sc.close();
		*/

		//QUESTÃO 12
		/*		Scanner sc = new Scanner(System.in);
		int id = 0;
		Veiculo[] veiculos = LeitorCsv.ler("/tmp/veiculos.csv");

		pilhaFlexivel pilha = new pilhaFlexivel();

		while ((id = sc.nextInt()) != -1){
		Veiculo achado = pesquisaSequencial(id, veiculos);
    		pilha.empilhar(achado);
		}


		int n = sc.nextInt();
		sc.nextLine();

		for (int i = 0; i < n; i++){
    			String linha = sc.nextLine();
    			String[] partes = linha.split(" ");
			String k = partes[0];

    			switch (k) {
        			case "I": {
            				int idV = Integer.parseInt(partes[1]);
            				pilha.empilhar(pesquisaSequencial(idV, veiculos));
            				break;
        			}
        			case "R": {
            				Veiculo r = pilha.desempilhar();
            				System.out.println("(R)" + r.getMarca() + " " + r.getModelo());
            				break;
        			}
    			}
		}
		pilha.mostrar();
		sc.close();*/

		//QUESTÃO 13
		/*Scanner sc = new Scanner(System.in);
		int id = 0;
		Veiculo[] veiculos = LeitorCsv.ler("/tmp/veiculos.csv");

		ListaEncadeada lista = new ListaEncadeada();

		while ((id = sc.nextInt()) != -1){
			Veiculo achado = pesquisaSequencial(id, veiculos);
			lista.inserirFim(achado);
		}

		int n = sc.nextInt();
		sc.nextLine();

		for (int i = 0; i < n; i++){
			String linha = sc.nextLine();
			String[] partes = linha.split(" ");
			String comando = partes[0];

			switch (comando) {

			case "II": {
				int idV = Integer.parseInt(partes[1]);
			    	lista.inserirInicio(pesquisaSequencial(idV, veiculos));
			    	break;
			}

			case "I*": {
				int pos = Integer.parseInt(partes[1]);
			    	int idV = Integer.parseInt(partes[2]);
			    	lista.inserir(pesquisaSequencial(idV, veiculos), pos);
			    	break;
			}

			case "IF": {
			    	int idV = Integer.parseInt(partes[1]);
			    	lista.inserirFim(pesquisaSequencial(idV, veiculos));
			    	break;
			}

			case "RI": {
			    	Veiculo r = lista.removerInicio();
			    	System.out.println("(R)" + r.getMarca() + " " + r.getModelo());
			    	break;
			}

			case "R*": {
			    	int pos = Integer.parseInt(partes[1]);
			    	Veiculo r = lista.remover(pos);
			    	System.out.println("(R)" + r.getMarca() + " " + r.getModelo());
			    	break;
			}

			case "RF": {
				Veiculo r = lista.removerFim();
			    	System.out.println("(R)" + r.getMarca() + " " + r.getModelo());
			    	break;
			}
		    }
		}

		lista.exibir();
		sc.close();*/	

    }
}

class LeitorCsv {
    public static Veiculo[] ler(String caminhoArquivo) {
        List<Veiculo> lista = new ArrayList<>();
        File arquivo = new File(caminhoArquivo);

        // Try-with-resources garante que o arquivo seja fechado
        try (Scanner sc = new Scanner(arquivo)) {
            // Pula a linha do cabeçalho
            if (sc.hasNextLine()) {
                sc.nextLine();
            }
            while (sc.hasNextLine()) {
                String linha = sc.nextLine();
                if (!linha.trim().isEmpty()) {
                    lista.add(Veiculo.parseVeiculo(linha));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Nao achou arquivo");
        }

        return lista.toArray(new Veiculo[0]);
    }
}

class Data {
    private int ano;
    private int mes;
    private int dia;

    public Data(int ano, int mes, int dia) {
        this.ano = ano;
        this.mes = mes;
        this.dia = dia;
    }

    public int getAno() { return this.ano; }
    public int getMes() { return this.mes; }
    public int getDia() { return this.dia; }

    public static Data parseData(String linha) {
        String[] atributos = linha.split("-");
        int ano = Integer.parseInt(atributos[0]);
        int mes = Integer.parseInt(atributos[1]);
        int dia = Integer.parseInt(atributos[2]);

        return new Data(ano, mes, dia);
    }

    public String format() {
        return String.format("%02d/%02d/%04d", this.dia, this.mes, this.ano);
    }
}

// No da lista encadeada dupla
class Celula {
    public Veiculo conteudo;
    public Celula proxima;
    public Celula anterior;

    public Celula(Veiculo conteudo) {
        this.conteudo = conteudo;
        this.proxima = null;
        this.anterior = null;
    }
}

class ListaEncadeada {
    public Celula primeira;
    public Celula ultima;
    public int tamanho;

    // Inicializa usando um n cabeca
    public ListaEncadeada() {
        this.primeira = new Celula(null);
        this.ultima = this.primeira;
        this.tamanho = 0;
    }

    public void inserirFim(Veiculo v) {
        Celula nova = new Celula(v);
        this.ultima.proxima = nova;
        nova.anterior = this.ultima;
        this.ultima = nova;
        this.tamanho++;
    }

    public void inserirInicio(Veiculo v) {
        Celula nova = new Celula(v);
        nova.proxima = this.primeira.proxima;
        nova.anterior = this.primeira;

        if (this.primeira.proxima != null) {
            this.primeira.proxima.anterior = nova;
        } else {
            this.ultima = nova;
        }

        this.primeira.proxima = nova;
        this.tamanho++;
    }

    public Veiculo getElemento(int n) {
        if (n >= tamanho || n < 0) return null;

        Celula atual = this.primeira.proxima;
        for (int i = 0; i < n; i++) {
            atual = atual.proxima;
        }
        return atual.conteudo;
    }

    public void alteraElemento(int n, Veiculo v) {
        if (n >= tamanho || n < 0) return;
        Celula atual = this.primeira.proxima;
        for (int i = 0; i < n; i++) atual = atual.proxima;
        atual.conteudo = v;
    }

    public void inserir(Veiculo v, int n) {
        if (n == 0) {
            inserirInicio(v);
            return;
        }
        if (n == this.tamanho) {
            inserirFim(v);
            return;
        }

        Celula i = this.primeira.proxima;
        for (int j = 0; j < n; j++) i = i.proxima;

        Celula nova = new Celula(v);
        nova.anterior = i.anterior;
        nova.proxima = i;
        i.anterior.proxima = nova;
        i.anterior = nova;
        this.tamanho++;
    }

    public Veiculo removerInicio() {
        if (tamanho == 0) return null;

        Celula removida = this.primeira.proxima;
        Veiculo resp = removida.conteudo;

        this.primeira.proxima = removida.proxima;

        if (removida.proxima != null) {
            removida.proxima.anterior = this.primeira;
        } else {
            this.ultima = this.primeira;
        }

        this.tamanho--;
        return resp;
    }

    public Veiculo removerFim() {
        if (tamanho == 0) return null;

        Celula removida = this.ultima;
        Veiculo resp = removida.conteudo;

        this.ultima = removida.anterior;
        this.ultima.proxima = null;

        this.tamanho--;
        return resp;
    }

    // Atualiza ponteiros em volta para pular o n removido
    public Veiculo remover(int n) {
        if (n < 0 || n >= tamanho) return null;
        if (n == 0) return removerInicio();
        if (n == tamanho - 1) return removerFim();

        Celula removida = this.primeira.proxima;
        for (int i = 0; i < n; i++) removida = removida.proxima;

        Veiculo resp = removida.conteudo;
        removida.anterior.proxima = removida.proxima;
        removida.proxima.anterior = removida.anterior;

        this.tamanho--;
        return resp;
    }

    // Ordenacaoo por Marca: move o conteudo dos nos em vez de trocar os ponteiros
    public void insertionSort() {
        if (this.tamanho <= 1) return;

        Celula ii = this.primeira.proxima.proxima;

        for (int i = 1; i < this.tamanho; i++) {
            Celula jj = ii;
            Veiculo temp = jj.conteudo;

            while (jj.anterior != this.primeira && 
                   jj.anterior.conteudo.getMarca().toLowerCase().compareTo(temp.getMarca().toLowerCase()) > 0) {
                jj.conteudo = jj.anterior.conteudo;
                jj = jj.anterior;
            }

            jj.conteudo = temp;
            ii = ii.proxima;
        }
    }

    // Insere mantendo a lista ordenada por cilindrada
    public void inserirOrdem(Veiculo v) {
        Celula atual = primeira.proxima;

        while (atual != null && atual.conteudo.getCilindrada() <= v.getCilindrada()) {
            atual = atual.proxima;
        }

        if (atual == null) {
            inserirFim(v);
        } else if (atual == primeira.proxima) {
            inserirInicio(v);
        } else {
            Celula nova = new Celula(v);
            nova.anterior = atual.anterior;
            nova.proxima = atual;
            atual.anterior.proxima = nova;
            atual.anterior = nova;
            this.tamanho++;
        }
    }

    // Distribui em baldes por cilindrada e concatena tudo no final
    public void bucketSort() {
        int NUM_BALDES = 10;

        ListaEncadeada[] baldes = new ListaEncadeada[NUM_BALDES];
        for (int i = 0; i < NUM_BALDES; i++) {
            baldes[i] = new ListaEncadeada();
        }

        Celula atual = this.primeira.proxima;
        while (atual != null) {
            double chave = atual.conteudo.getCilindrada() / 8.1;
            int indice = (int) (chave * NUM_BALDES);

            if (indice >= NUM_BALDES) indice = NUM_BALDES - 1;
            if (indice < 0) indice = 0;

            ListaEncadeada balde = baldes[indice];
            Veiculo v = atual.conteudo;

            // Insere diretamente se for maior que o ultimo, senao faz busca ordenada
            if (balde.tamanho == 0 || balde.ultima.conteudo.getCilindrada() <= v.getCilindrada()) {
                balde.inserirFim(v);
            } else {
                balde.inserirOrdem(v);
            }

            atual = atual.proxima;
        }

        // Reseta a lista atual para remontar com os baldes
        this.primeira.proxima = null;
        this.ultima = this.primeira;
        this.tamanho = 0;

        for (int i = 0; i < NUM_BALDES; i++) {
            Celula c = baldes[i].primeira.proxima;
            while (c != null) {
                this.inserirFim(c.conteudo);
                c = c.proxima;
            }
        }
    }

    public void exibir() {
        Celula ii = primeira.proxima;
        while (ii != null) {
            System.out.println(ii.conteudo.format());
            ii = ii.proxima;
        }
    }
}

class ListaSequencial {
    public int primeiro;
    public int ultimo;
    public Veiculo[] vet;

    public ListaSequencial(int tamanho) {
        this.vet = new Veiculo[tamanho];
        this.primeiro = 0;
        this.ultimo = 0;
    }

    // Desloca elementos a direita para liberar o indice 0
    public void inserirInicio(Veiculo v) {
        if (ultimo == vet.length) return;

        for (int i = ultimo; i > primeiro; i--) {
            vet[i] = vet[i - 1];
        }

        vet[primeiro] = v;
        ultimo++;
    }

    public void inserir(Veiculo v, int pos) {
        if (ultimo == vet.length || pos < 0 || pos > ultimo) return;

        for (int i = ultimo; i > pos; i--) {
            vet[i] = vet[i - 1];
        }

        vet[pos] = v;
        ultimo++;
    }

    public void inserirFim(Veiculo v) {
        if (ultimo == vet.length) return;
        vet[ultimo] = v;
        ultimo++;
    }

    public Veiculo removerInicio() {
        if (primeiro == ultimo) return null;
        return remover(0);
    }

    public Veiculo removerFim() {
        if (primeiro == ultimo) return null;
        Veiculo resp = vet[ultimo - 1];
        vet[ultimo - 1] = null;
        ultimo--;
        return resp;
    }

    // Desloca elementos a esquerda para preencher o espaço vago
    public Veiculo remover(int n) {
        if (n < 0 || n >= ultimo) return null;
        Veiculo resp = vet[n];

        for (int i = n; i < ultimo - 1; i++) {
            vet[i] = vet[i + 1];
        }

        vet[ultimo - 1] = null;
        ultimo--;
        return resp;
    }

    public void mostrar() {
        for (int i = primeiro; i < ultimo; i++) {
            System.out.println(vet[i].format());
        }
    }
}

class PilhaFlexivel {
    public Celula topo;
    public int tamanho;

    public PilhaFlexivel() {
        this.topo = null;
        this.tamanho = 0;
    }

    // Insercaoo LIFO
    public void empilhar(Veiculo v) {
        Celula nova = new Celula(v);
        nova.proxima = topo;
        topo = nova;
        this.tamanho++;
    }

    public Veiculo desempilhar() {
        if (topo == null) return null;

        Veiculo resp = topo.conteudo;
        topo = topo.proxima;
        this.tamanho--;
        return resp;
    }

    public void mostrar() {
		Celula atual = topo;
		//mostrarRecursivoFundoTopo(atual);
		mostrarRecursivoTopFundo(atual);
	}

    // Recursao para imprimir do fundo para o topo da pilha sem alterar os nos
    private void mostrarRecursivoTopFundo(Celula c) {
        if (c != null) {
            mostrarRecursivoTopFundo(c.proxima);
            System.out.println(c.conteudo.format());
        }
    }

	private void mostrarRecursivoFundoTopo(Celula c) {
		if (c == null) {
        	return;
		}
		System.out.println(c.conteudo.format());
		mostrarRecursivoFundoTopo(c.proxima);
	}
}