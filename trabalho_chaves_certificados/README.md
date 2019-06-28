# Tarefa Prática 2 (OpenSSL e Apache)

#### Nomes:
Bruno Aurélio Rôzza de Moura Campos (14104255)<br/>
Caio Cargnin Cardoso (09138003)

## PARTE 1.OpenSSL

### 2. (Entregar) Gere a chave pública a partir da chave privada com os comandos abaixo (guardar a chave pública no arquivo seunome.publica.pem). Explique a saída obtida em cada um dos comandos. **Guarde o arquivo gerado e envie o arquivo da sua chave pública junto nas respostas da tarefa.**

**a. Explique a saída obtida no seguinte comando:**<br/>

```bash
openssl rsa -in seunome.privada.pem -pubout -out seunome.publica.pem
```

**Resposta:**<br/>
É necessário gerar a chave privada primeiro, então:<br/>

```bash
openssl genrsa -aes256-out brunocampos.privada.pem 2048
```

Em seguida o comando `openssl rsa -in seunome.privada.pem -pubout -out seunome.publica.pem` irá pegar a chave privada e criará a chave pública codificada em base64.

<img src=imagens/2.a.png>

- Nota: arquivo gerado `brunocampos.publica.pem` em anexo.


### 3. (Entregar) Digite o seguinte comando e depois abra o arquivo seunome.publica.componentes. Explique os componentes que constam nesse arquivo(https://tools.ietf.org/html/rfc3447#appendix-A).
Comando:

```bash
openssl rsa -in seunome.privada.pem -out seunome.publica.componentes -text -noout
```

**Resposta:**<br/>

Output do comando:

<img src=imagens/3.a.1.png>
<img src=imagens/3.a.2.png>


Segundo o site https://tools.ietf.org/html/rfc3447#appendix-A, uma chave privada RSA deve ser representada com o tipo ASN.1
   RSAPrivateKey:

      RSAPrivateKey :: = SEQUENCE {
          versão versão,
          modulo INTEGER, - n
          publicExponent INTEGER, - e
          privateExponent INTEGER, - d
          prime1 INTEGER, - p
          prime2 INTEGER, - q
          exponent1 INTEGER, - d mod (p-1)
          exponent2 INTEGER, - d mod (q-1)
          coeficiente INTEGER, - (inverso de q) mod p
          otherPrimeInfos OtherPrimeInfos OPCIONAL
      }

Já os campos do tipo RSAPrivateKey têm os seguintes significados:
- `modulus` é o módulo RSA n.
- `publicExponent` é o expoente público da RSA e.
- `privateExponent` é o expoente privado da RSA d.
- ` prime1` é o fator primo p de n.
- `prime2` é o fator primo q de n.
- `expoent1` é d mod (p - 1).
- `expoent2` é d mod (q - 1).
- `coefficient` é o coeficiente CRT q ^ (- 1) mod p.

Nota: arquivo gerado `brunocampos.publica.componentes` em anexo.

## PARTE 2. Assinatura Digital

### 4. (Entregar todos os itens)

**a. Você deve assinar o arquivo fornecido na tarefa (msgPlana.txt). Para isso, crie o hash do arquivo msgPlana.txt e com a sua chave privada, assine o hash do arquivo:**

```bash
openssl  dgst  -sha256  -sign  seunome.privada.pem  -out   assinatura msgPlana.txt
```

**Resposta:**<br/>

Comando executado:
```bash
openssl  dgst  -sha256  -sign  brunocampos.privada.pem  -out   assinatura ../projetos/seguranca/trabalho_02_sem_implementacao/msgPlana.txt
```

- Nota: arquivo gerado `assinatura` em anexo.

**b. Responda: qual o conteúdo do arquivo assinatura?Essa assinatura garante quais características de segurança: integridade, autenticidade, confidencialidade?**

**Resposta:**<br/>

O conteúdo do arquivo (imagem abaixo) é o hash obtido pelo algoritmo SHA256 criptografado com a chave privada.

<img src=imagens/4.b.png>

É garantido a integridade do arquivo gerado.


### 5. (Entregar) Verifique se o hash assinado está ok, isto é, compare o hash assinado com o hash do arquivo original usando o comando abaixo. Envie sua chave pública para que, durante a correção, possa ser feita a verificação da sua assinatura:

```bash
openssl  dgst -sha256 -verify  seunome.publica.pem  -signature  assinatura msgPlana.txt
```

**Resposta:**<br/>

<img src=imagens/5.a.png>

- Nota: arquivo `brunocampos.publica.pem` em anexo.


### 6. (Entregar) Gerar uma chave secreta usando o comando (coloque o seu nome):
```bash
openssl rand -out chaveSecretaNomeAluno.bin -base64 128
```

**Resposta:**<br/>

<img src=imagens/6.png>

- Nota: arquivo `chave_secreta_brunocampos.bin` em anexo.


### 7. (Entregar) Cifrar o arquivo msgPlana.txt com a chave secreta criada na questão anterior:

```bash
openssl enc -aes-128-ctr \
            -in msgPlana.txt \
            -out msgCifrada \
            -pass file:./chaveSecretaNomeAluno.bin
```

**Resposta:**<br/>

<img src=imagens/7.png>

- Nota: arquivo `msgCifrada` em anexo.


### 8. (Entregar) Cifrar a sua chaveSecreta (chaveSecretaNomeAluno.bin) usando o meu certificado (certificado de Carla):

```bash
openssl rsautl -encrypt -oaep \
               -inkey certificadoCarla.crt \
               -certin \
               -in chaveSecretaNomeAluno.bin \
               -out chaveSecretaNomeAlunoCifrada.enc
```

**Resposta:**<br/>

```bash
openssl rsautl -encrypt -oaep \
               -inkey ../projetos/seguranca/trabalho_02_sem_implementacao/certificadoCarla.crt \
               -certin \
               -in chave_secreta_brunocampos.bin \
               -out chave_secreta_brunocampos.enc
```

<img src=imagens/8.png>

- Nota: arquivo `chave_secreta_brunocampos.enc` em anexo.

### 9. (Entregar) Explique o que foi feito nas questões 6, 7 e 8. Explique também como será feito o processo de decifragem.


**Observação:** vou verificar com os comandos abaixo!!

```bash
openssl rsautl -decrypt -oaep \
               -inkey chavePrivadaCarla.key \
               -in chaveSecretaNomeAlunoCifrada.enc \
               -out chaveSecretaNomeAlunoDecifrada.bin
```

```bash
openssl enc -aes-128-ctr -d \
            -in msgCifrada \
            -pass file:./chaveSecretaAlunoDecifrada.bin
```

**Resposta:**<br/>

Na questão 6 foi gerado uma chave secreta usando o parâmetro `rand` que gera um psedo-random em bytes e lançado esse valor no arquivo `chave_secreta_brunocampos.bin` com enconding na base64.
<br/>
Na questão 7, foi cifrado o arquivo `msgPlana.txt` com a chave secreta (Passphrase source) `chave_secreta_brunocampos.bin` e lançado essa cifragem no arquivo `msgCifrada`.
<br/>
Por fim, na questão 8 foi cifrado a chave secreta `chave_secreta_brunocampos.bin` com o certificado `certificadoCarla.crt` e lançado no arquivo `chave_secreta_brunocampos.enc`.
<br/>
Decifragem: 
De forma inversa, para decifrar é necessário informar a chave privada do certificado, o certificado e qual o arquivo de saída.

## GERAR SEU CERTIFICADO NA ICPEDU

### 10. (Entregar)
- Acessar o site https://p1.icpedu.rnp.br/default/public/default e gerar o seu certificado digital pessoal.
- Clique em “Emitir”.
- Logue pela Federação Café na UFSC.
- Depois de autenticar com o email e senha do idufsc, você obterá a tela da figura 1. 
- Coloque  uma  senha  para  proteger  o  arquivo  PKCS12  que  será  gerado. 
- NOTA: Documente com screeshots o processo. Depois de emitir, você obterá a tela da figura 2.

**Resposta:**<br/>

<img src=imagens/10.png>

<img src=imagens/10.2.png>

- Nota: arquivo `SAEC P1 - Bruno Aurelio Rozza de Moura.p12` em anexo.


### 11. (Entregar) Agora, clique em https://p1.icpedu.rnp.br/index/howto e instale o seu certificado no navegador. Documente com screenshots.

**Resposta:**<br/>

<img src=imagens/11.1.png>

<img src=imagens/11.2.png>


### 12. (Entregar) Explique o formato deste certificado:

**a) Qual é o formato?**

**Resposta:**<br/>

É uma arquivo com extensão `.p12` 

**b. Onde ficam a chave pública e a chave privada?**

**Resposta:**<br/>

Ficam no arquivo `'SAEC P1 - Bruno Aurelio Rozza de Moura.p12'`. É possível extrair a chave privada do arquivo com os comandos:

**Private**
```bash
openssl pkcs12 -in ../projetos/seguranca/trabalho_02_sem_implementacao/'SAEC P1 - Bruno Aurelio Rozza de Moura.p12' \
               -nocerts -nodes \
               | openssl rsa > id_rsa_trab_02
```

- Nota: arquivos `id_rsa_trab_02` em anexo.


**c. Quem é a autoridade certificadora que assinou o certificado?**

**Resposta:**<br/>

AC Raiz da ICPEDU V2


### 14. (Entregar) Agora o certificado X.509 AUTO-ASSINADO será efetivamente criado (assinado por você mesmo, usando a SUA chave privada), usando o comando:
```bash
openssl x509 -req -days 90 -sha512 \
                  -in ertifcicado.csr \
                  -signkey  seunome.privada.pem \
                  -out certificado.crt
```

**Resposta:**<br/>

```bash
openssl x509 -req -days 90 -sha512 \
                  -in  ../projetos/seguranca/trabalho_02_sem_implementacao/'SAEC P1 - Bruno Aurelio Rozza de Moura.p12' \
                  -signkey  brunocampos.privada.pem \
                  -out ../projetos/seguranca/trabalho_02_sem_implementacao/certificado_brunocampos.crt
```

<img src=imagens/14.png>

- Nota: arquivos `certificado_brunocampos.crt` em anexo.

## Apache2

### 16-35

<img src=imagens/16.png>
