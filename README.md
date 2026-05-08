## Como rodar:

Após a configuração correta da versão do gradle, os produtos serão passados via um arquivo produto.txt:

## Comando para passar os produtos:

```./gradlew runMain --args="produtos.txt``` 

Escreve em um arquivo os produtos, e ao executar assim a aplicação fará as busca e filtros.

## Output:

```
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


## Outra maneira é colocar esse código** 


```java
    public static void main(String[] args) {
        buscarProduto("arroz");

        // String[] produtos = {"arroz", "feijão", "açúcar"};
        // buscarVariosProdutos(produtos);
    }
```

Dessa forma a busca fica muito mais limpa e direta.
