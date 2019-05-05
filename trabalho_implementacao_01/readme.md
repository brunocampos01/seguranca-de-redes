# Tarefa Prática 
OpenSSL + Exercícios de criptografia simétrica, hash, MAC, PBKDF e Criptografia Autenticada em Java

#### Alunos
Bruno Aurélio Rôzza de Moura Campos (14104255)<br/>
Caio Cargnin Cardoso (09138003)

#### Matéria
- Segurança da informação e sistemas - INE5680

## 1 parte - OpenSSL usando a Kali Linux

#### CIFRAR E DECIFRAR

1. **(ENTREGAR) Cifrar o arquivo `t1.txt` com o algoritmo AES no modo CTR. Ver modos de cifragem usando: openssl enc -help**
**Use o comando:**
```bash
openssl enc -aes-128-ctr –in t1.txt –out t1.aes -p
```
<img src="imagens/1-a.png">

 - **a) Qual chave foi usada para cifrar o arquivo?**<br/>
key=4CDF109430C8BB4E0DFD4B264C4291A8
 
 - **b) Qual IV foi usado?**<br/>
iv =FA458B36C3A0C9B8CEE0B7F7310FBAF5

 - **c) Como foram gerados a chave e o IV?**<br/>
 Tanto a chave quanto o IV foram gerados a partir da senha inserida (root). Um password é exigido como primeiro passo após executar o comando. Como ultimo argumento do comando foi inserido `-p` para exibir o iv e key.

 - **d) Onde ficam guardados a chave e o IV?**<br/>
A key e o IV são persistidos no arquivo `t1.aes` criado a partir do comando `-out t1.aes`

2. **(ENTREGAR) Agora, decifre o arquivo t1.aes.Use o comando:**
```bash
openssl enc -aes-128-ctr -d -in t1.aes -p
```
O argumento `–d` significa “decifrar”.

<img src="imagens/2.png">


#### HASH e MAC

3. **(ENTREGAR) Crie o arquivo t1.txt no gedit e escreva algum conteúdo dentro do arquivo. Para gerar o hash deste arquivo usando o algoritmo sha256, pode ser usado o seguinte comando:**
```bash
openssl dgst -sha256 t1.txt
```
**Execute os seguintes comandos e coloque a saída obtida (screenshot) de cada um deles:**
- **a. Obtenha a saída do comando:**
```bash
openssl dgst -sha256 -c t1.txt
```

<img src="imagens/3-a.png">

- **b. Modifique o conteúdo do arquivo t1.txt. Agora, recalcule o valor do hash com o mesmo comando do item a. O valor obtido é igual ao valor do item a ou é diferente? Explique.**

<img src="imagens/3-b.png">

Os valores gerados, com o parâmetro (`-dgst` _Message Digest Calculation_) são diferentes porque a função executa a partir de entradas diferentes.

- **c. Encontre um arquivo em alguma página web que tenha o valor do hash SHA-256 listado na página (Sugestão de página: http://httpd.apache.org/download.cgi#apache24) . Baixe o arquivo e recalcule o valor do SHA-256 com o openssl para conferir se o valor calculado é igual ao listado na página web. Mostre a tela da execução desse comando e indique o site usado.**

<img src="imagens/3-c1.png">
<br/>
<br/>
Valores igual:
<img src="imagens/3-c2.png">
<br/>
<br/>

4. **(ENTREGAR) Para gerar o MAC do arquivo use o comando abaixo, mostrando a tela de execução do comando. Explique os parâmetros usados no comando. Use o help e digite:**
```bash
openssl dgst –help
```
- a) 
```bash
openssl dgst -sha256 -mac HMAC -macopt hexkey:aabbcc t1.txt
```

<img src="imagens/4-a.png">

- `sha256` usa o algoritmo sha-256
- `mac` cria o MAC
- `HMAC` (Hash-based Authentication Message Code) criar MAC com hash com chave
- `macopt hexkey:aabbcc` especifica uma a chave como argumento na forma hexadecimal, 2 dígithexadecimais por byte.

---

## 2 parte – Criptografia em Java

1. **(ENTREGAR) (Dupla) Abra o projeto2CodigoLivro e teste o seu funcionamento. Responda:**
- **1.1. Qual algoritmo é usado no código? Em qual modo?**<br/>
Algoritmo AES no modo CBC. _Symmetric encryption example with padding and CBC using AES_

- **1.2. Explique o que faz o método generateKey da classe https://docs.oracle.com/javase/7/docs/api/javax/crypto/KeyGenerator.html**<br/>
Gera uma chave secreta.

- **1.3. Explique como são usados os métodos init, update e doFinal para cifrar e para decifrar os dados nesse código. Leia a documentação e entenda bem o funcionamento desses métodos.**<br/>
`init`: é o método que inicia o "Cipher", cifra, recebendo o parâmetro o tipo de operação a cifrar ou decifrar `opmode` alem de a chave de criptografia e o IV.
Fonte: https://docs.oracle.com/javase/7/docs/api/javax/crypto/Cipher.html#init(int,%20java.security.Key,%20java.security.AlgorithmParameters)

`update`: Continua uma operação de encriptação ou descriptação de outras partes (dependendo de como essa cifra foi inicializada), processando outra parte de dados.
Os parâmetros deste método podem ser: um array de bytes que é o input a ser processado, o offset, o tamanho do input, o texto cifrado e o tamanho desse texto cifrado. 
Fonte: https://docs.oracle.com/javase/7/docs/api/javax/crypto/Cipher.html#update(byte[],%20int,%20int)

`doFinal`:
Finaliza a operação de cifragem ou decifragem das múltiplas partes de dados dependendo de como a cifra foi inicializada.
Fonte: https://docs.oracle.com/javase/7/docs/api/javax/crypto/Cipher.html#doFinal(byte[],%20int)


2. **(ENTREGAR) (Dupla) Nesse projeto você irá programar dois sistemas de decifragem, um usando o AES em modo CBC e outro usando o AES no modo contador (counter mode – CTR). Em ambos os casos um IV de 16 bytes é escolhido de forma aleatória e está colocado no início do texto cifrado (precede o texto cifrado). Para o modo CBC use o esquema de padding PKCS5. Para o modo CTR use NoPadding.
Inicialmente iremos testar apenas a função de decifragem. Use o projeto3Aes para auxiliar a responder as questões. 
Nas questões seguintes você recebe uma chave AES e um texto cifrado (ambos codificados em hexa) e o seu objetivo é recuperar o texto plano/texto decifrado. A resposta de cada questão é o texto decifrado (frase em português).**

- **a)**
   - Chave CBC: c38a0d7bdd11e031c24e4895913393f9
   - Texto cifrado em modo CBC (IV+texto cifrado):
b90d84b82b283d5f783b9721f5f8bd1fb170b4319815f1a4fdbaff6f052f6e58a06d0200f28b1d333d8e3f11fcafef750122226c1bcea8d69416f5a15e4901b3c2fb5c33507139fe88f18c72fb0c435d
**Resposta-texto decifrado:**<br/>

Modo CBC com PKCS5Padding do AES. IV nao foi cifrado.




- **b)**
    - Chave CTR: abd95641ecb005d475496cd0bda4555f
    - Texto cifrado em modo CTR (IV+texto cifrado):
7182eb9d1fd3d9ed3ae1594b3cd3b02bf4667cd27c5e0a01dc2e66f53480e5fa249269e1bd17e7e066824dcab22be4ccff41480a139eae1d390e1dd78548d7bb82841d88ae50fd4ea52727
<br/>
**Resposta-texto decifrado:**



3. **No código testeModifica, faça:**
 
 - **3.1. Explique o funcionamento do exemplo;**
É feito uma simulação de transferência de 100,00 unidades monetárias para a conta 1234-5678. Partindo do presuposto que o atacante tem conhecimento da estrutura da mensagem, é realizado uma interceptação modificando a mensagem com XOR de tal modo que há uma transferência de um valor muito maior 9000100,00 unidades monetárias.

 - **3.2. Explique o significado da seguinte linha de código:**
 ```java
 cipherText[11] ^= '0' ^ '9';
 ```
 <br/>
Na posição 11 do array `cipherText[]` é atribuído, através de um XOR, (primeiro algarismo de 0000100) com o mesmo valor `'0'`, redefinindo os bits. Em seguida é feito outro XOR com `'9'` para **adulterar** o valor da transferência de 0000100 para 9000100.


4. **No código teste HASH, faça:**
 - **4.1. Explique como o hash sem chave é usado nesse exemplo;**<br/>
É criado um hash com o algoritmo SHA256 a partir de uma menssagem.
Fonte: https://docs.oracle.com/javase/8/docs/api/java/security/MessageDigest.html

 - **4.2. O que significa o valor “false” na verificação do hash?**<br/>
 No código da classe `TamperedWithDigestExample.java` não há estrutura de seleção para verificar o hash, neste caso se o hash retornar vazio, o objeto MessageDigest pode lançar uma excessão do tipo `DigestException`.
 Fonte: https://docs.oracle.com/javase/8/docs/api/java/security/MessageDigest.html

5. **Sobre hash de senhas, leia o material disponível do site https://crackstation.net/hashing-security.htm e escreva um resumo sobre o que é “certo” e o que é “errado” ao criar um arquivo com hashes de senhas. Projete uma ideia de formato seguro de arquivo de senhas fundamentado/baseado nas boas práticas descritas no material. Você deve criar o seu formato e deve descrever razões das escolhas feitas para o seu formato.**<br/>

**Soluções**<br/>
A melhor maneira de proteger senhas é empregar hashing de senha com _salt_ para garantir uma persistencia segura das senhas de uruários. Já sobre a implementação de código de hash de senha é sempre recomendado uma implementações já prontas

O sal precisa ser exclusivo por usuário por senha. Toda vez que um usuário cria uma conta ou altera sua senha, a senha deve ser dividida usando um novo sal aleatório. Nunca reutilize um sal. O sal também precisa ser longo, de modo que haja muitos sais possíveis. 

**Erros**<br/>
A maneira mais simples de decifrar um hash é tentar adivinhar a senha, calcular cada suposição e verificar se o hash do palpite é igual ao hash que está sendo quebrado. Se os hashes são iguais, o palpite é a senha.

Outro problema é a reutilização do sal que pode causar problemas caso mais de um usuário utilize a mesma senha, o que levaria a um mesmo hash.

**Exemplo**<br/>
Um bom formato para armazenar senhas pode ser:
`hash("hello" + "QxLUF1bgIAdeQX") = 9e209040c863f84a31e719795b2577523954739fe5ed3b58a75cff2127075ed1`
A senha do usuário é hello mas com um sal longo, dificialmente será repetido o mesmo hash.


6. **(ENTREGAR) No código testeModificaETrocaHash, faça:**
 - **6.1. Explique como o hash sem chave é usado nesse exemplo;**<br/>
 Na declaração da variável `MessageDigest hash = MessageDigest.getInstance("SHA256", "BCFIPS");` é definido um objeto do tipo `MessageDigest` o qual possui o método `getInstance()`. Este método é responsável por retornar um objeto MessageDigest que implementa o algoritmo especificado.
 Depois da declaração, o hash invoca o método `update()` para atualizar o valor da menssagem usando um byte específico. Depois esse hash é passado como parâmentro no método `doFinal()` de uma cifra e invoca o método `digest()` para gerar o resumo da mensagem,
 Fonte: https://docs.oracle.com/javase/8/docs/api/java/security/MessageDigest.html

 - **6.2. Explique a etapa de troca do hash do código abaixo.**
```java
// etapa de troca do hash
byte[] originalHash = hash.digest(Utils.toByteArray(input));
byte[] tamperedHash = hash.digest(Utils.toByteArray(
"Transferir 9000100 para Conta Corrente 1234-5678"));
for (int i = ctLength - hash.getDigestLength(),
j = 0; i != ctLength;
i++, j++)
{
cipherText[i] ^= originalHash[j] ^ tamperedHash[j];
}
```
O método `digest()`, da classe `MessageDigest`, gera o resumo da mensagem que recebeu como parâmetro. Esse método calcula a função hash no objeto atual e retorna o resumo da mensagem na forma de matriz de bytes. Então devido ao fato de os parâmentros recebido serem diferentes, o retorno deste método terá hashes diferentes.
Fonte: https://www.tutorialspoint.com/java_cryptography/java_cryptography_message_digest.htm

8. **(ENTREGAR) No código AES-CriptoAutenticada, faça:**

**Obs: para funcionar este código você deve descompactar o arquivo jce_policy-8 que está dentro do diretório deste projeto. LEIA o README para saber para onde você deve copiar os arquivos descompactados. Só fazendo isso você conseguirá criar o AES com tamanhos de chave maiores. Se você tiver o JAVA 7, você deve baixar o arquivo na Internet: procure por Java Cryptography Extension (JCE) Unlimited Strength Jurisdiction Policy Files 7.**

 - **8.1. Explique o funcionamento do método gcmTestWithGCMBlockCipherBC;**<br/>
 Este método basicamente testa usando GCMBlockCipher da BouncyCastle, não necessita de parâmetros e não possui retorno `void`. Durante sua execução, ele é responsável por instanciar as seguintes variáveis:
 chave `byte[] K`, texto plano `byte[] P`, IV `byte[] N`, tag `String T`, texto cifrado `byte[] C` e mensagem de entrada `String input`.
 Na sequencia, ele cifra, criando um objeto do tipo `GCMBlockCipher gcm` e depois passa os parâmetros para ser feito a cifragem da mensagem.
 Após a cifragem é feita a mudança do texto cifrado usando 2 XOR sequencias. Com a mensagem alterada, é feita a decifragem e os valores são exibidos.

 - **8.2. Como as tags (MACs) foram usadas para identificar a modificação no texto cifrado?**<br/>
O GCMBlockCipher checa o MAC do gcm `byte[] encT1 = gcm.getMac();`. Desta forma é possível saber que a mensagem foi adulterada.

 - **8.3. A detecção da modificação foi feita de forma manual ou automática no código? Explique.**<br/>
 A detecção foi realizada de modo automático através do método `getMac()` que pertence ao objeto `gcm`, sendo que este método é implementado na classe `GCMBlockCipher`

11. **(ENTREGAR) O projeto testeKeyStorev2 tem exemplo de uso do KeyStore do Java. Explique como funciona o código exemplo. A figura 1 mostra exemplo de estrutura do keystore Java. O exemplo de código na classe `KeyStrAdap.java` demonstra o uso do keystore do tipo BCFKS (keystore da BouncyCastle padrão FIPS). Execute o código da classe KeyStrAdap.java.
Texto da documentação: “The BCFKS key store is designed to be FIPS compliant. It is available in approved-mode operation and is also capable of storing some secret key types in addition to public/private keys and certificates. The BCFKS key store uses PBKDF2 with HMAC SHA512 for password to key conversion and AES CCM for encryption.”**
<br/>
Basicamente o código na classe `KeyStrAdap.java` guarda chaves secretas (simétricas), certificados de chaves públicas e chaves privadas. Para isso é implementado vários métodos, començando pelo 
   - `storeSecretKey()`: cria um objeto `KeyStore`, carrega o cofre de senhas em modo de steam e persiste uma chave a partir dos parâmetros `alias, secretKey, keyPass` 
   - `storeCertificate()`:  cria um objeto `KeyStore` carrega o cofre de senhas em modo de steam e persiste um certificado a partir dos parâmetros `alias, trustedCert`
   - `storePrivateKey`: cria um objeto `KeyStore` carrega o cofre de senhas em modo de steam e persiste uma _storePrivate_ a partir dos parâmetros `alias, eeKey, keyPass, eeCertChain`
   - `printKeyStore`: cria um objeto `KeyStore` carrega o cofre de senhas em modo de steam e imprime o resultado a partir do parâmetro recebido em `storePassword`.

12. **(ENTREGAR e APRESENTAR - 50 % da nota desta tarefa) Você irá implementar um sistema de comunicação entre funcionários de um ambiente usando a criptografia simétrica autenticada, derivação de chave e um keystore Java.** 
**Suponha um ambiente de uma empresa que tem os seguintes funcionários: Alice, Bob, Ana e Pedro. Alice define que quer conversar com Bob. Para conversar com Bob, Alice deve:**
 - **a) Gerar, de forma segura e correta, uma chave criptográfica e um IV usando mecanismos de derivação de chaves (PBKDF2 ou Argon2). Chave e IV devem ser escritos na tela;**
  - **b) Guardar a chave gerada e outros parâmetros necessários (talvez o IV) em um keystore Java. Esse keystore será o cofre de chaves único da empresa que guardará as chaves e/ou parâmetros criptográficos como o IV usados nas conversas entre os funcionários;**
  - **c) Alice cifra a msg usando a chave e o IV e envia para Bob (via chamada de método). Alice e Bob podem ser objetos funcionários do sistema. Msg original e msg cifrada devem ser escritas na tela.**
<br/>
Ao receber a msg, Bob deve:
  - **a) Obter a chave simétrica e/ou parâmetros como o IV usados para criptografar a msg que estão guardados no cofre da empresa (keystore Java). Bob deve escrever na tela a chave e IV obtidos do cofre;**
  - **b) Escrever na tela a msg cifrada recebida;**
  - **c) Decifrar a msg recebida e escrever a msg decifrada na tela.**
<br/>
NÃO É PERMITIDO: ter chaves e IV fixos e escritos no próprio código. Parâmetros devem ser guardados cifrados em arquivo (não podem ser guardados em texto plano). Não é permitido ter “passwords” no código-fonte.
O arquivo keystore Java é cifrado por padrão. A “senha” usada no keystore deve ser gerada com método
de derivação.










13. **(Questão desafio, não é obrigatória a entrega) Um atacante intercepta o seguinte texto cifrado
(codificado em hexa):
20814804c1767293b99f1d9cab3bc3e7 ac1e37bfb15599e5f40eef805488281d
Ele sabe que o texto plano (mensagem original) é a seguinte mensagem ASCII: "Pay Bob 100$" (excluindo
as aspas). Ele sabe que o texto cifrado usa a cifragem em modo CBC com um IV aleatório usando o AES
como cifrador de bloco. Mostre que o atacante pode mudar o texto cifrado de forma que a decifragem
resulte no seguinte texto: "Pay Bob 500$". Responda: qual é o texto cifrado resultante (codificado em
hexa)? Isso mostra que o CBC não fornece integridade. Dica: Você pode ler o início do capítulo 3 do livro
Beginning Cryptography with Java e ver os exemplos.**