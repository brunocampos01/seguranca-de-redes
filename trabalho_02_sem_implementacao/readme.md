# Tarefa Prática 2 (OpenSSL e Apache) 

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
            -passfile:./chaveSecretaNomeAluno.bin
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
<br/>
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