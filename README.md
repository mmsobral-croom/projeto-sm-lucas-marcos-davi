[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/0aiXsnlU)
# Projeto 1: Melhor Preço

No projeto 1 sua equipe deve desenvolver um programa que descubra em qual supermercado se pode comprar um conjunto de produtos pelo melhor preço.

O usuário deve criar uma cesta de compras, e então o preço total dessa cesta deve ser calculado para cada um dos supermercados cadastrados. Ao final, o programa deve mostrar os supermercados e respectivos preços de cesta ordenados pelo preço.

Os supermercados cadastrados até o momento são:
* Giassi
* Bistek
* Fort Atacadista

Para desenvolver esse software, usem os buscadores de preço disponibilizados neste repositório inicial. Existe um buscador para cada supermercado implementado em uma classe na package _sm_. A interface dos buscadores é a mesma:

* __ListaSequencial\<Produto\> busca(String nome)__: busca todos produtos cujos nomes contenham _nome_. O resultado é uma lista de objetos _Produto_.
* __Produto obtem(String productId)__: busca a descrição de um produto identificado pelo _productId_. Os valores de _productId_ são específicos de cada supermercado.

A classe Produto contém a descrição de um produto, e possui os seguintes métodos para acessar as informações:
* __String nome()__: o nome do produto, conforme definido pelo supermercado 
* __String id()__: o valor do _productId_ definido pelo supermercado
* __String marca()__: a marca do produto
* __float preco()__: o preço do produto
* __boolean disponivel()__: se o produto está disponível no supermercado

Um exemplo de consulta a produtos de um supermercado está contido em _Main.java_:

```java
public class Main {
    static void main() {

        // cria um acessador para o Giassi
        Giassi sm = new Giassi();

        // procura todos produtos cujo nome contenha "tapioca"
        ListaSequencial<Produto> produtos = sm.busca("tapioca");

        // Mostra cada um dos produtos encontrados
        for (int pos=0; pos < produtos.comprimento(); pos++) {
            IO.println(produtos.obtem(pos));
        }

    }
}
```



### Como rodar:



Após a configuração correta da versão do gralde, os produtos serão passados via um arquivo;.

```bash
# comandos para passar os produtos
 ./gradlew runMain --args="produtos.txt" 
```

> Escreve em um arquivo os produtos, e ao executar assim a aplicação fará as busca e filtros.


output:

````bash
Buscando produto: arroz

TOP 3 MELHORES PREÇOS
------------------------------
1º produto
Nome: Arroz Parboilizado Zilmar 5kg
Tamanho: 5000g
Preço: R$ 8.38
Mercado: Fort
Preço por kg: R$ 1.68
------------------------------
2º produto
Nome: Arroz Parboilizado Zilmar 1kg
Tamanho: 1000g
Preço: R$ 2.13
Mercado: Fort
Preço por kg: R$ 2.13
------------------------------
3º produto
Nome: Arroz Parboilizado Catarinão 1kg
Tamanho: 1000g
Preço: R$ 2.27
Mercado: Fort
Preço por kg: R$ 2.27
Buscando produto: banana

TOP 3 MELHORES PREÇOS
------------------------------
1º produto
Nome: Banana Caturra Embalagem 1.15kg Aprox. 9 Unid.
Tamanho: 1150g
Preço: R$ 7.59
Mercado: Bistek
Preço por kg: R$ 6.60
------------------------------
2º produto
Nome: Bebida Láctea Carolina Açaí com Banana Pet 1,25kg
Tamanho: 1250g
Preço: R$ 10.98
Mercado: Fort
Preço por kg: R$ 8.78
------------------------------
3º produto
Nome: Banana Branca Embalagem 1.3kg Aprox. 10 Unid.
Tamanho: 1300g
Preço: R$ 11.49
Mercado: Bistek
Preço por kg: R$ 8.84
Buscando produto: feijão

TOP 3 MELHORES PREÇOS
------------------------------
1º produto
Nome: Feijão Carioca Rio Belo 1kg
Tamanho: 1000g
Preço: R$ 3.99
Mercado: Fort
Preço por kg: R$ 3.99
------------------------------
2º produto
Nome: Feijão Preto Vila Nova 1kg
Tamanho: 1000g
Preço: R$ 4.28
Mercado: Fort
Preço por kg: R$ 4.28
------------------------------
3º produto
Nome: Feijão Preto Caldo Carioca 1kg
Tamanho: 1000g
Preço: R$ 4.38
Mercado: Fort
Preço por kg: R$ 4.38

```


**Outra maneira é colocar esse código** 


```java
    public static void main(String[] args) {
        buscarProduto("arroz");

        // String[] produtos = {"arroz", "feijão", "açúcar"};
        // buscarVariosProdutos(produtos);
    }
```

> Dessa forma a busca fica muito mais limpa e direta
