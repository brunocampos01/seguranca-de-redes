# Exercícios - semana 2 (individual)

#### Bruno Aurélio Rôzza de Moura Campos (14104255)<br/>

### Questão 73
(Usando o openssl) Crie o arquivo t1.txt no gedit e escreva algum conteúdo dentro do arquivo. Cifrar o arquivo t1.txt com o algoritmo AES no modo CTR. Use screenshot para documentar a execução do comando. Ver modos de cifragem usando: openssl enc -help
Use o comando: 

```
openssl enc -aes-128-ctr –in t1.txt –out t1.aes -p
```

<img src='imagens/73.jpg'  align="middle" height=auto widht=80% >

**a. Qual chave foi usada para cifrar o arquivo?**
<br/>
key=067ADC53840E47EE1397926567CC4ADB

**b. Qual IV foi usado?**
<br/>
iv =5D2C320AD73F799984EFA5FEDED61465

**c. Como foram gerados a chave e o IV?**
<br/>
O próprio openssl é quem gerou a chave e iv com base na senha passada no momento de criptografar o arquivo

**d. Onde ficam guardados a chave e o IV?**
<br/>
Somente é guardado o salt (salt=D577EE4602768B5D) e a partir do salt + senha é possível gerar novamente a **chave** e **IV**.

<br/>

### Questão 74
(Usando o openssl) Agora, decifre o arquivo t1.aes. Use screenshot para documentar a execução do
comando. Use o comando:

```
openssl enc -aes-128-ctr -d -in t1.aes -p
```
O argumento –d significa “decifrar”.

<img src='imagens/74.jpg'  align="middle" height=auto widht=80% >

<br/>

### Questão 75
(Usando o openssl) Gerar uma chave secreta usando o comando (coloque o seu nome): 

```
openssl rand -out chaveSecretaNomeAluno.bin -base64 128
```

<img src='imagens/75.jpg'  align="middle" height=auto widht=80% >

<br/>

### Questão 76
(Usando o openssl) Cifrar o arquivo msgPlana.txt (crie o arquivo com algum conteúdo) com a chave
secreta criada na questão anterior

```
openssl enc -aes-128-ctr -in msgPlana.txt -out msgCifrada -k file:./chaveSecretaNomeAluno.bin
```

<img src='imagens/76.jpg'  align="middle" height=auto widht=80% >

<br/>

### Questão 77
(Usando o openssl) Monte o comando para decifrar o arquivo msgCifrada. Mostre o comando e sua
execução

<img src='imagens/77.jpg'  align="middle" height=auto widht=80% >

<br/>

### Questão 78
(Usando o openssl) Crie o arquivo t1.txt no gedit e escreva algum conteúdo dentro do arquivo. Para
gerar o hash deste arquivo usando o algoritmo sha256, pode ser usado o seguinte comando: 

```
openssl dgst -sha256 t1.txt
```

**a. Obtenha a saída do comando: openssl dgst -sha256 -c t1.txt**

<img src='imagens/78.a.jpg'  align="middle" height=auto widht=80% >


<br/>

**b) Encontre um arquivo em alguma página web que tenha o valor do hash SHA-256 listado na página (Sugestão de página: http://httpd.apache.org/download.cgi#apache24). Baixe o arquivo e recalcule o valor do SHA-256 com o openssl para conferir se o valor calculado é igual ao listado na página web. Mostre a tela da execução desse comando e indique o site usado.**

<img src='imagens/78.b.jpg'  align="middle" height=auto widht=80% >

<br/>

### 79
(Usando o openssl) Para gerar o MAC do arquivo use o comando abaixo, mostrando a tela de
execução do comando. Explique os parâmetros usados no comando. 

**a. `openssl dgst -sha256 -mac HMAC -macopt hexkey:aabbcc t1.txt`**
- HMAC: é um tipo de MAC
- macopt: é um parâmetro de algoritmo de MAC 
- hexkey: é uma chave hexa com o valor aabbcc
- t1.txt: arquivo de entrada


**b. Para gerar uma chave de 128 bits, use o comando: openssl rand -hex 32**


**c. Agora repita o comando do HMAC usando esta chave e mostre a execução do comando.**

<img src='imagens/79.jpg'  align="middle" height=auto widht=80% >
