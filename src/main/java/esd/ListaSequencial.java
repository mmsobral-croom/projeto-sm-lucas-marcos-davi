package esd;

import java.util.Random;

import static java.util.Collections.swap;

public class ListaSequencial <T> {

    T[] area;
    int len = 0;
    final int defcap = 8;

    @SuppressWarnings("unchecked")
    public ListaSequencial() {
        area = (T[]) new Object[defcap];
    }

    @SuppressWarnings("unchecked")
    void expande() {
        // expande a capacidade da lista: nova capacidade deve ser o dobro da atual
        T[] areaExpandida = (T[]) new Object[area.length * 2];

        for (int i = 0; i < len; i++) {
            areaExpandida[i] = area[i];
        }

        area = areaExpandida;
    }

    public boolean esta_vazia() {
        // retorna true se lista estiver vazia, ou false caso contrário
        return len == 0;
    }

    public int capacidade() {
        // retorna um inteiro que representa a capacidade da lista
        return area.length;
    }

    public void adiciona(T elemento) {
        // adiciona um valor ao final da lista
        if (len == area.length) {
            expande();
        }

        area[len] = elemento;
        len++;
    }

    public void insere(int indice, T elemento) {
        // insere um valor na posição indicada por "indice"
        // move uma posição para frente os valores a partir dessa posição
        // dispara IndexOutOfBoundsException se "indice" for inválido
        if (indice > len || indice < 0) {
            throw new IndexOutOfBoundsException("Posição inválida");
        }

        if (indice == len) {
            adiciona(elemento);
            return;
        }

        if (len == area.length) {
            expande();
        }

        len++;
        for (int i = len - 1; i > indice; i--) {
            area[i] = area[i - 1];
        }
        area[indice] = elemento;

    }

    public void insere_rapido(int indice, T elemento) {
        // insere um valor na posição indicada por "indice"
        // usa a abordagem de ListaSequencialSimples
        // dispara IndexOutOfBoundsException se "indice" for inválido
        if (indice > len || indice < 0) {
            throw new IndexOutOfBoundsException("Posição inválida");
        }

        if (indice == len) {
            adiciona(elemento);
            return;
        }

        if (len == area.length) {
            expande();
        }

        area[len] = area[indice];
        area[indice] = elemento;
        len++;
    }

    public T remove(int indice) {
        // remove um valor da posição indicada pelo parâmetro "indice"
        // move uma posição para trás os valores das próximas posições
        // disparar uma exceção IndexOutOfBoundsException caso posição seja inválida
        // retorna o valor que foi removido da lista
        if (indice < 0 || indice >= len) {
            throw new IndexOutOfBoundsException("Indice inválido");
        }

        T valorRemovido = area[indice];

        for (int i = indice; i < len - 1; i++) {
            area[i] = area[i + 1];
        }

        len--;
        return valorRemovido;
    }

    public T remove_rapido(int indice) {
        // remove um valor da posição indica pelo parãmetro índice
        // move o último dado da lista para essa posição
        // dispara IndexOutOfBoundsException se indice for inválido
        // retorna o valor que ofi removido da lista
        if (indice < 0 || indice >= len) {
            throw new IndexOutOfBoundsException("Indice inválido");
        }

        T valorRemovido = area[indice];

        area[indice] = area[len - 1];
        len--;

        return valorRemovido;
    }

    public T remove_ultimo() {
        // remove o último valor da lista
        // disparar uma exceção IndexOutOfBoundsException caso lista vazia
        // retorna o valor que foi removido da lista
        if (esta_vazia()) {
            throw new IndexOutOfBoundsException("Lista vazia");
        }

        T ultimoValor = area[len - 1];
        len--;

        return ultimoValor;
    }

    public int procura(T valor) {
        // retorna um inteiro que representa aposição onde valor foi encontrado pela primeira vez (contando do início da lista)
        // retorna -1 se não o encontrar !
        for (int i = 0; i < len; i++) {
            if (area[i] == null && valor == null) {
                return i;
            }

            if (area[i] != null && area[i].equals(valor)) {
                return i;
            }
        }
        return -1;
    }

    public T obtem(int indice) {
        // retorna o valor armazenado na posição indica pelo parâmetro "indice"
        // disparar uma exceção IndexOutOfBoundsException caso posição seja inválida
        if (indice < 0 || indice >= len) {
            throw new IndexOutOfBoundsException("Indice inválido");
        }

        return area[indice];
    }

    public T primeiro() {
        // retorna o valor armazenado no início da lista
        // disparar uma exceção IndexOutOfBoundsException caso posição seja inválida
        if (area[0] == null) {
            throw new IndexOutOfBoundsException("Posição inválida");
        }

        return area[0];
    }

    public T ultimo() {
        // retorna o valor armazenado no final da lista
        // disparar uma exceção IndexOutOfBoundsException caso posição seja inválida
        if (area[len - 1] == null) {
            throw new IndexOutOfBoundsException("Posição inválida");
        }

        return area[len - 1];
    }

    public void substitui(int indice, T valor) {
        // armazena o valor na posição indicada por "indice", substituindo o valor lá armazenado atualmente
        // disparar uma exceção IndexOutOfBoundsException caso posição seja inválida
        if (indice < 0 || indice >= len) {
            throw new IndexOutOfBoundsException("Indice inválido");
        }

        area[indice] = valor;
    }

    public void insere_ordenado(Comparable valor) {
        // insere o valor na lista, preservando seu ordenamento
        if (valor == null) {
            throw new IndexOutOfBoundsException("Valor inválido");
        }

        if (len == area.length) {
            expande();
        }

        if (len == 0) {
            len++;
            area[0] = (T) valor;
            return;
        }

        int cmp = valor.compareTo(area[len - 1]);

        if (cmp > 0) {
            area[len] = (T) valor;
            len++;
            return;
        }

        for (int i = 0; i < len; i++) {
            cmp = valor.compareTo(area[i]);
            if (cmp < 0 || cmp == 0) {
                for (int j = len; j > i; j--) {
                    area[j] = area[j - 1];
                }
                area[i] = (T) valor;
                len++;
                break;
            }
        }
    }

    public void remove(T valor) {
        // remove este valor da lista
        boolean igual = false;

        for (int i = 0; i < len; i++) {
            if (valor == null) {
                if (area[i] == null) {
                    igual = true;
                } else {
                    igual = false;
                }
            }

            if (valor != null) {
                if (valor.equals(area[i])) {
                    igual = true;
                } else {
                    igual = false;
                }
            }

            if (igual == true) {
                for (int j = i; j < len - 1; j++) {
                    area[j] = area[j + 1];
                }
                area[len - 1] = null;
                len--;
                return;
            }
        }
    }

    public int busca_binaria(Comparable valor) {
        // procura o valor dentro da lista usando busca binária
        // retorna a posição onde se encontra, ou -1 caso não exista
        if (len == 0) {
            return -1;
        }

        int inicio = 0;
        int fim = len - 1;

        while (inicio <= fim) {
            int meio = (inicio + fim) / 2;

            if (area[meio] == null) {
                return -1;
            }

            int cmp = valor.compareTo(area[meio]);

            if (cmp == 0) {
                return meio;
            }

            if (cmp < 0) {
                fim = meio - 1;
            } else {
                inicio = meio + 1;
            }
        }

        return -1;
    }

    public void ordena() {
        // ordena a lista com algum bom algoritmo !
        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                Comparable menor = (Comparable) area[i];
                int cmp = menor.compareTo(area[j]);

                if (cmp > 0) {
                    T temp = area[j];
                    area[j] = area[i];
                    area[i] = temp;
                }
            }
        }
    }

    public boolean esta_ordenada() {
        // implemente aqui o método
        boolean ordenada = true;
        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                Comparable valor = (Comparable) area[j];
                int cmp = valor.compareTo(area[i]);

                if (cmp < 0) {
                    ordenada = false;
                    break;
                }
            }
        }

        return ordenada;
    }

    public int comprimento() {
        // retorna um inteiro que representa o comprimento da lista (quantos valores estão armazenados)
        return len;
    }

    public void limpa() {
        // esvazia a lista
        len = 0;
        for (int i = 0; i < area.length; i++) {
            area[i] = null;
        }
    }

    public void embaralha() {
        if (len > 1) {
            Random rand = new Random();
            for (int i = len - 1; i > 0; i--) {
                int j = rand.nextInt(0, i);
                swap(j, i);
            }
        }
    }

    void swap(int pos1, int pos2) {
        if (pos1 != pos2) {
            T val = area[pos1];
            area[pos1] = area[pos2];
            area[pos2] = val;
        }
    }
}