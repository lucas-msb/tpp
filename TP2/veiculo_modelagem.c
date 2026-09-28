#include <stdio.h>
#include <stdlib.h>

#define MAX_COMBUSTIVEIS 5
#define TAM_FILA 5

typedef struct {
    int ano;
    int mes;
    int dia;
} Data;

typedef struct {
    int id;
    char marca[30];
    char modelo[30];
    int ano;
    char categoria[30];
    char combustivel[MAX_COMBUSTIVEIS][20];
    int numCombustiveis;
    int cilindros;
    double cilindrada;
    char transmissao[20];
    char tracao[20];
    double consumoCidade;
    double consumoEstrada;
    double co2;
    int turbo;
    Data dataRegistro;
} Veiculo;

void copiarString(char destino[], char origem[], int tamanho) {
    int i = 0;

    while (origem[i] != '\0' && i < tamanho - 1) {
        destino[i] = origem[i];
        i++;
    }

    destino[i] = '\0';
}

int compararString(char str1[], char str2[]) {
    int i = 0;
    char c1;
    char c2;

    while (str1[i] != '\0' && str2[i] != '\0') {
        c1 = str1[i];
        c2 = str2[i];

        if (c1 >= 'A' && c1 <= 'Z') {
            c1 = c1 + ('a' - 'A');
        }

        if (c2 >= 'A' && c2 <= 'Z') {
            c2 = c2 + ('a' - 'A');
        }

        if (c1 < c2) {
            return -1;
        }

        if (c1 > c2) {
            return 1;
        }

        i++;
    }

    if (str1[i] == '\0' && str2[i] == '\0') {
        return 0;
    }

    if (str1[i] == '\0') {
        return -1;
    }

    return 1;
}

Data parseData(char linha[]) {
    Data d;
    char parte[10];
    int i = 0;
    int j = 0;

    while (linha[i] != '-' && linha[i] != '\0') {
        parte[j] = linha[i];
        i++;
        j++;
    }

    parte[j] = '\0';
    d.ano = atoi(parte);

    i++;
    j = 0;

    while (linha[i] != '-' && linha[i] != '\0') {
        parte[j] = linha[i];
        i++;
        j++;
    }

    parte[j] = '\0';
    d.mes = atoi(parte);

    i++;
    j = 0;

    while (linha[i] != '\0') {
        parte[j] = linha[i];
        i++;
        j++;
    }

    parte[j] = '\0';
    d.dia = atoi(parte);

    return d;
}

void formatData(Data d) {
    printf("%02d/%02d/%04d", d.dia, d.mes, d.ano);
}

Veiculo parseVeiculo(char linha[]) {
    Veiculo v;
    char atributos[15][100];
    int i = 0;
    int j = 0;
    int campo = 0;

    while (linha[i] != '\0' && campo < 15) {
        if (linha[i] == ',') {
            atributos[campo][j] = '\0';
            campo++;
            j = 0;
        } else {
            atributos[campo][j] = linha[i];
            j++;
        }

        i++;
    }

    atributos[campo][j] = '\0';

    v.id = atoi(atributos[0]);

    copiarString(v.marca, atributos[1], 30);
    copiarString(v.modelo, atributos[2], 30);

    v.ano = atoi(atributos[3]);

    copiarString(v.categoria, atributos[4], 30);

    v.numCombustiveis = 0;
    i = 0;
    j = 0;

    while (atributos[5][i] != '\0' &&
           v.numCombustiveis < MAX_COMBUSTIVEIS) {

        if (atributos[5][i] == ';') {
            v.combustivel[v.numCombustiveis][j] = '\0';
            v.numCombustiveis++;
            j = 0;
        } else {
            v.combustivel[v.numCombustiveis][j] =
                atributos[5][i];
            j++;
        }

        i++;
    }

    if (j > 0 || atributos[5][0] != '\0') {
        v.combustivel[v.numCombustiveis][j] = '\0';
        v.numCombustiveis++;
    }

    v.cilindros = atoi(atributos[6]);
    v.cilindrada = atof(atributos[7]);

    copiarString(v.transmissao, atributos[8], 20);
    copiarString(v.tracao, atributos[9], 20);

    v.consumoCidade = atof(atributos[10]);
    v.consumoEstrada = atof(atributos[11]);
    v.co2 = atof(atributos[12]);

    v.turbo = compararString(atributos[13], "true") == 0;

    v.dataRegistro = parseData(atributos[14]);

    return v;
}

void formatVeiculo(Veiculo v) {
    int i;

    printf("[%d ## %s ## %s ## %04d ## %s ## [",
           v.id,
           v.marca,
           v.modelo,
           v.ano,
           v.categoria);

    for (i = 0; i < v.numCombustiveis; i++) {
        printf("%s", v.combustivel[i]);

        if (i < v.numCombustiveis - 1) {
            printf(",");
        }
    }

    printf("] ## %d ## %.1f ## %s ## %s ## %.2f ## %.2f ## %.1f ## %s ## ",
           v.cilindros,
           v.cilindrada,
           v.transmissao,
           v.tracao,
           v.consumoCidade,
           v.consumoEstrada,
           v.co2,
           v.turbo ? "true" : "false");

    formatData(v.dataRegistro);

    printf("]\n");
}

Veiculo* lerCsv(char caminhoArquivo[], int *n) {
    FILE *arquivo;
    char linha[500];
    int totalLinhas = 0;
    int i = 0;
    int j;
    Veiculo *veiculos;

    arquivo = fopen(caminhoArquivo, "r");

    if (arquivo == NULL) {
        printf("Nao achou arquivo\n");
        *n = 0;
        return NULL;
    }

    fgets(linha, 500, arquivo);

    while (fgets(linha, 500, arquivo) != NULL) {
        totalLinhas++;
    }

    fclose(arquivo);

    veiculos = malloc(totalLinhas * sizeof(Veiculo));

    if (veiculos == NULL) {
        *n = 0;
        return NULL;
    }

    arquivo = fopen(caminhoArquivo, "r");

    if (arquivo == NULL) {
        free(veiculos);
        *n = 0;
        return NULL;
    }

    fgets(linha, 500, arquivo);

    while (fgets(linha, 500, arquivo) != NULL) {
        j = 0;

        while (linha[j] != '\0') {
            if (linha[j] == '\n' || linha[j] == '\r') {
                linha[j] = '\0';
                break;
            }

            j++;
        }

        veiculos[i] = parseVeiculo(linha);
        i++;
    }

    fclose(arquivo);

    *n = totalLinhas;

    return veiculos;
}

Veiculo* pesquisaSequencial(int id, Veiculo *carros, int n) {
    int i;

    for (i = 0; i < n; i++) {
        if (carros[i].id == id) {
            return &carros[i];
        }
    }

    return NULL;
}

int* contarCilindros(Veiculo vet[], int n, int maior) {
    int *contagem;
    int i;

    contagem = malloc((maior + 1) * sizeof(int));

    if (contagem == NULL) {
        return NULL;
    }

    for (i = 0; i <= maior; i++) {
        contagem[i] = 0;
    }

    for (i = 0; i < n; i++) {
        contagem[vet[i].cilindros]++;
    }

    return contagem;
}

void inserirOrdenado(Veiculo vet[],
                     Veiculo ordenado[],
                     int contagem[],
                     int n) {
    int i;
    int pos;

    for (i = n - 1; i >= 0; i--) {
        pos = contagem[vet[i].cilindros] - 1;

        ordenado[pos] = vet[i];

        contagem[vet[i].cilindros]--;
    }
}

void countingSort(Veiculo vet[], int n) {
    int i;
    int maior = 0;
    int *contagem;
    Veiculo *ordenado;

    for (i = 0; i < n; i++) {
        if (vet[i].cilindros > maior) {
            maior = vet[i].cilindros;
        }
    }

    contagem = contarCilindros(vet, n, maior);

    if (contagem == NULL) {
        return;
    }

    for (i = 1; i <= maior; i++) {
        contagem[i] = contagem[i] + contagem[i - 1];
    }

    ordenado = malloc(n * sizeof(Veiculo));

    if (ordenado == NULL) {
        free(contagem);
        return;
    }

    inserirOrdenado(vet, ordenado, contagem, n);

    for (i = 0; i < n; i++) {
        vet[i] = ordenado[i];
    }

    free(ordenado);
    free(contagem);
}

void mostrar(Veiculo vet[], int n) {
    int i;

    for (i = 0; i < n; i++) {
        formatVeiculo(vet[i]);
    }
}

void enfileirar(Veiculo fila[],
                int *fim,
                int *quantidade,
                Veiculo veiculo) {
    fila[*fim] = veiculo;
    *fim = (*fim + 1) % TAM_FILA;
    *quantidade = *quantidade + 1;
}

Veiculo desenfileirar(Veiculo fila[],
                       int *inicio,
                       int *quantidade) {
    Veiculo removido;

    removido = fila[*inicio];

    *inicio = (*inicio + 1) % TAM_FILA;
    *quantidade = *quantidade - 1;

    return removido;
}

void mostrarRemovido(Veiculo veiculo) {
    printf("(R)%s %s\n", veiculo.marca, veiculo.modelo);
}

void mostrarFila(Veiculo fila[], int inicio, int quantidade) {
    int i;
    int posicao;

    for (i = 0; i < quantidade; i++) {
        posicao = (inicio + i) % TAM_FILA;
        formatVeiculo(fila[posicao]);
    }
}

void inserirInicioLista(Veiculo lista[],
                        int *n,
                        Veiculo veiculo) {
    int i;

    for (i = *n; i > 0; i--) {
        lista[i] = lista[i - 1];
    }

    lista[0] = veiculo;
    *n = *n + 1;
}

void inserirLista(Veiculo lista[],
                  int *n,
                  Veiculo veiculo,
                  int posicao) {
    int i;

    for (i = *n; i > posicao; i--) {
        lista[i] = lista[i - 1];
    }

    lista[posicao] = veiculo;
    *n = *n + 1;
}

void inserirFimLista(Veiculo lista[],
                     int *n,
                     Veiculo veiculo) {
    lista[*n] = veiculo;
    *n = *n + 1;
}

Veiculo removerInicioLista(Veiculo lista[], int *n) {
    Veiculo removido;
    int i;

    removido = lista[0];

    for (i = 0; i < *n - 1; i++) {
        lista[i] = lista[i + 1];
    }

    *n = *n - 1;

    return removido;
}

Veiculo removerLista(Veiculo lista[],
                     int *n,
                     int posicao) {
    Veiculo removido;
    int i;

    removido = lista[posicao];

    for (i = posicao; i < *n - 1; i++) {
        lista[i] = lista[i + 1];
    }

    *n = *n - 1;

    return removido;
}

Veiculo removerFimLista(Veiculo lista[], int *n) {
    Veiculo removido;

    removido = lista[*n - 1];

    *n = *n - 1;

    return removido;
}

void mostrarRemovidoLista(Veiculo veiculo) {
    printf("(R)%s %s\n", veiculo.marca, veiculo.modelo);
}

void mostrarLista(Veiculo lista[], int n) {
    int i;

    for (i = 0; i < n; i++) {
        formatVeiculo(lista[i]);
    }
}

int main() {
    int n;
    int quantidade = 0;
    int id;
    int quantidadeComandos;
    int posicao;
    int i;
    char comando[3];

    Veiculo *veiculos;
    Veiculo *lista;
    Veiculo *achado;
    Veiculo removido;

    veiculos = lerCsv("/tmp/veiculos.csv", &n);

    if (veiculos == NULL) {
        return 1;
    }

    lista = malloc(n * sizeof(Veiculo));

    if (lista == NULL) {
        free(veiculos);
        return 1;
    }

    while (scanf("%d", &id) == 1 && id != -1) {
        achado = pesquisaSequencial(id, veiculos, n);

        if (achado != NULL) {
            inserirFimLista(lista, &quantidade, *achado);
        }
    }

    scanf("%d", &quantidadeComandos);

    for (i = 0; i < quantidadeComandos; i++) {
        scanf("%s", comando);

        if (compararString(comando, "II") == 0) {
            scanf("%d", &id);

            achado = pesquisaSequencial(id, veiculos, n);

            if (achado != NULL) {
                inserirInicioLista(lista, &quantidade, *achado);
            }
        } else if (compararString(comando, "I*") == 0) {
            scanf("%d", &posicao);
            scanf("%d", &id);

            achado = pesquisaSequencial(id, veiculos, n);

            if (achado != NULL) {
                inserirLista(lista, &quantidade, *achado, posicao);
            }
        } else if (compararString(comando, "IF") == 0) {
            scanf("%d", &id);

            achado = pesquisaSequencial(id, veiculos, n);

            if (achado != NULL) {
                inserirFimLista(lista, &quantidade, *achado);
            }
        } else if (compararString(comando, "RI") == 0) {
            if (quantidade > 0) {
                removido = removerInicioLista(lista, &quantidade);
                mostrarRemovidoLista(removido);
            }
        } else if (compararString(comando, "R*") == 0) {
            scanf("%d", &posicao);

            if (posicao >= 0 && posicao < quantidade) {
                removido = removerLista(
                    lista,
                    &quantidade,
                    posicao
                );

                mostrarRemovidoLista(removido);
            }
        } else if (compararString(comando, "RF") == 0) {
            if (quantidade > 0) {
                removido = removerFimLista(lista, &quantidade);
                mostrarRemovidoLista(removido);
            }
        }
    }

    mostrarLista(lista, quantidade);

    free(lista);
    free(veiculos);

    return 0;
}
