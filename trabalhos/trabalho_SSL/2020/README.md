# Tarefa Prática (Handshake TLS + OpenSSL)

#### Nome: Bruno Aurélio Rôzza de Moura Campos (14104255)<br/>

## PARTE 1: Handshake SSL/TLS (Secure Socket Layer/Transport Layer Security)

### Questão 1
É  possível  verificar  as  possibilidades  do  SSL/TLS  do  seu  browser  e  do  seu  servidor.  Cole  os  resultados (screenshot) aqui e comente o que chamou a sua atenção em cada um dos resultados.

**a. https://www.ssllabs.com/  este  site  e  _teste  o  seu  browser_  (diferentes  tipos de  browser podem ter resultados diferentes na sua máquina).**

**b. https://www.ssllabs.com/ este site e teste um servidor que usa o SSL. Cuide para não acessar apenas um proxy de servidor real.**

**Obs.**: forward  secrecy significa  que se  uma  chave  for  comprometida  durante  uma  sessão,  esse conhecimento/fatonão  afeta  a  segurança  de  sessões  anteriores. A  troca  de  chaves  RSA  (RSA  key Exchange) não fornece forward secrecy pois se alguma chave privada for comprometida, todo o tráfego anterior pode ser decifrado.

**Resposta**<br/>

a. <br/>
`User Agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.80 Safari/537.36`

<img src='imagens/1.a.parte-1.png'  align="middle" height=auto widht=80% >

<img src='imagens/1.a.parte-2.png'  align="middle" height=auto widht=80% >

<img src='imagens/1.a.parte-3.png'  align="middle" height=auto widht=80% >


b.

<img src='imagens/1.b.parte-1.png'  align="middle" height=auto widht=80% >
<img src='imagens/1.b.parte-2.png'  align="middle" height=auto widht=80% >
<img src='imagens/1.b.parte-3.png'  align="middle" height=auto widht=80% >
<img src='imagens/1.b.parte-4.png'  align="middle" height=auto widht=80% >
<img src='imagens/1.b.parte-5.png'  align="middle" height=auto widht=80% >
<img src='imagens/1.b.parte-6.png'  align="middle" height=auto widht=80% >
<img src='imagens/1.b.parte-7.png'  align="middle" height=auto widht=80% >
<img src='imagens/1.b.parte-8.png'  align="middle" height=auto widht=80% >
<img src='imagens/1.b.parte-9.png'  align="middle" height=auto widht=80% >
<img src='imagens/1.b.parte-10.png'  align="middle" height=auto widht=80% >

<br/>
<br/>
<br/>

### 2. Questão
Leia  as  recomendações  da  página https://github.com/ssllabs/research/wiki/SSL-and-TLS-Deployment-Best-Practicese faça um pequeno resumo das seções 1 e 2 dessas recomendações.

**Resposta**
<br/>

##### Chave privada e certificado

O TLS começa com a identificação criptográfica do servidor. Para isso, é usado uma chave privada forte afim de evitar ataques de falsificação de identidade. Para garantir a segurança, há algumas dicas como:

- **Use chaves particulares de 2048 bits:** Para grande parte dos sites, chaves RSA de 2048 já são o suficiente.

- **Proteger Chaves Privadas:** Restringindo o acesso, gerando chaves com entropia suficiente.

- **Garantir cobertura suficiente de Hostname:** É uma forma de garantir que todas as rotas estão acessíveis e evitar avisos de certificados inválidos.

- **Obter certificados de uma CA confiável:** Isso é, escolher uma Autoridade de Certificação (CA) que seja confiável e séria. Para escolher uma CA, deve-se levar em consideração:
  - **Postura de segurança:** uma opção é examinar seu histórico de segurança.

  - **As CAs com foco nos negócios:** cujas atividades constituem uma parte substancial de seus negócios, têm tudo a perder se algo der errado

  - **Serviços oferecidos:** No mínimo, sua AC selecionada deve fornecer suporte para os métodos de revogação da Lista de Revogação de Certificados (CRL) e do Protocolo de Status de Certificados Online (OCSP), com disponibilidade e desempenho de rede sólidos. 

  - **Opções de gerenciamento de certificados** se for necessário operar um grande número de certificados e operar em um ambiente complexo, escolha uma autoridade de certificação que ofereça boas ferramentas para gerenciá-los.

  - **Suporte** é uma tranquilidade ter um bom suporte.

- **Use Algoritmos de Assinatura de Certificado Forte:** A segurança do certificado depende (1) da força da chave privada que foi usada para assinar o certificado e (2) da força da função de hash usada na assinatura.

##### Configuração

É uma garantia que as credenciais sejam apresentadas corretamente aos visitantes do site. Há uma série de medidas para ser levado em conta:
  - **Use protocolos seguros:** Na maioria das implantações, o certificado do servidor sozinho é insuficiente; Dois ou mais certificados são necessários para construir uma cadeia completa de confiança.

  - **Use Conjuntos de Codificação Segura** Existem cinco protocolos na família SSL / TLS: SSL v2, SSL v3, TLS v1.0, TLS v1.1 e TLS v1.2:
    - O SSL v2 é inseguro e não deve ser usado. 
    - O SSL v3 é inseguro quando usado com HTTP (o ataque POODLE).
    - O TLS v1.0 também é um protocolo legado que não deve ser usado.
    - O TLS v1.1 e v1.2 são ambos sem problemas de segurança conhecidos
    - O TLS v1.2 deve ser seu protocolo principal porque é a única versão que oferece criptografia autenticada moderna

  - **Use Conjuntos de Codificação Segura:** Em SSL e TLS, os conjuntos de criptografia definem como a comunicação segura ocorre. Eles são compostos de diferentes blocos de construção com a idéia de alcançar a segurança através da diversidade. Se um dos blocos de construção for fraco ou inseguro, é possível mudar para outro.

  - **Selecione as melhores suítes de codificação:** Ter servidores selecionando ativamente o melhor conjunto de criptografia disponível é fundamental para obter a melhor segurança.

  - **Use o sigilo antecipado:** O sigilo de encaminhamento (às vezes também chamado de sigilo de encaminhamento perfeito) é um recurso de protocolo que permite conversas seguras que não dependem da chave privada do servidor.

  - **Use troca de chaves forte:** Para a troca de chaves, os sites públicos normalmente podem escolher entre a troca de chaves Diffie-Hellman (DHE) efêmera clássica e sua variante de curva elíptica, ECDHE.

  - **Mitigar Problemas Conhecidos:** Nada é perfeitamente seguro, e é por isso que é uma boa prática ficar de olho no que acontece na segurança. Aplique prontamente correções de fornecedores se e quando elas estiverem disponíveis; caso contrário, confie em soluções alternativas para mitigação.


### 3. Questão
Explique de forma geral as quatro fases do handshake de acordo com as páginas do livro do Stallings 386, 387, 388 e 389 (o pdf do capítulo está anexado junto na tarefa).

**Resposta**<br/>

1. **Estabelecer capacidades de segurança:**
É a fase que inicia a comunicação. O cliente envia mensagem contendo alguns parâmetros:
  - versão do SSL
  - ID da sessão
  - Conjunto de cifras (cipherSuite) - é uma lista contendo algoritmo de troca de chave. Por exemplo, RSA, Diffie-Hellman, Diffie-Hellman anônimo Fortezza
  - Método de compactação(compression method) - é uma lista dos métodos de compactação que o cliente admite
Em seguida, o cliente aguarda a resposta do servidor.

2. **Autenticação de servidor e troca de chaves:** 
Nesta etapa, o servidor encaminha seus certificados, se necessãrio autenticar. A menssagem de certificado é exigida para qualquer troca de chaves que tenham sido acordadas, exceto se for Diffie-Hellman anônimo. A mensagem final desta fase é sempre exigida, é uma mensagem `server_done` enviada pelo servidor para indicar o final da resposta dele. Em seguida, o servidor aguardará uma resposta do cliente

3. **Autenticação do cliente e troca de chaves:**
Quando o cliente recebe uma mensagem `server_done` ele verifica se o certificado é válido e se os parâmetros `server_hello` são aceitáveis. Se tudo ok, o cliente responde seja com uma mensagem `certificate` ou `no_certificate`. Na sequência, é recebido uma mensagem `client_key_exchange` contendo asgum conjunto de cifras:
  - RSA
  - Diffie-Hellman anônimo ou efêmero
  - Diffie-Hellman fixo
  - Fortezza
No fim desta fase, o cliente pode enviar uma mensagem `certificate_verify` para oferecer uma cerificação explítica de um certificado. Contudo, essa mensagem só é enviada após qualquer certificado que tenha capacidade de assinatura, ou seja qualquer certificado menos Diffie-Hellman fixo

4. **Término:**
Na última etapa, o cliente envia uma mensagem `change_cipher_spec`. Cabe notar que essa mensagem não é considerada parte do protocolo de estabelecimento de sessão mas sim enviada usando o protocoloo de mudança de especificação de cifra. Alem da mensagem anterior, o cliente encaminha a mensagem `finished_message` sob os novos algoritmos, chaves e segredos.<br/>
Em resposta, o servidor envia sua mensagem `change_cipher_spec`, transfere o CipherSpec pendente para o atual e envia sua `fineshed_message` . A partir daqui, o cliente e servidor podem trocar dados na camada de aplicação.


### 4. Questão - handshake
Observandoo handshake dos protocolos TLS v1.2 e TLS v1.3 através da leitura do material dos seguintes sites e da observação das figuras, cite pelo menos 3 diferenças do handshake entre as duas versões.


**Resposta**
<br/>
1. Melhora de desempenho
2. Remoção de SHA1, MD5
3. Adição de Assinatura de handshake completa


### 5. Mostre a execução dos comandos (parte da execução) que realizam o handshake com os seguintes sites:

```
openssl s_client -connect www.ufsc.br:443 -tls1_2
openssl s_client -connect youtube.com:443 -tls1_3
```

1. Parte: openssl s_client -connect www.ufsc.br:443 -tls1_2
```
└─▪ openssl s_client -connect www.ufsc.br:443 -tls1_2



CONNECTED(00000003)
depth=2 OU = GlobalSign Root CA - R3, O = GlobalSign, CN = GlobalSign
verify return:1
depth=1 C = BE, O = GlobalSign nv-sa, CN = GlobalSign RSA OV SSL CA 2018
verify return:1
depth=0 C = BR, ST = SC, L = Florianopolis, O = UNIVERSIDADE FEDERAL DE SANTA CATARINA, CN = *.ufsc.br
verify return:1
---
Certificate chain
 0 s:C = BR, ST = SC, L = Florianopolis, O = UNIVERSIDADE FEDERAL DE SANTA CATARINA, CN = *.ufsc.br
   i:C = BE, O = GlobalSign nv-sa, CN = GlobalSign RSA OV SSL CA 2018
 1 s:C = BE, O = GlobalSign nv-sa, CN = GlobalSign RSA OV SSL CA 2018
   i:OU = GlobalSign Root CA - R3, O = GlobalSign, CN = GlobalSign
 2 s:OU = GlobalSign Root CA - R3, O = GlobalSign, CN = GlobalSign
   i:OU = GlobalSign Root CA - R3, O = GlobalSign, CN = GlobalSign
---
Server certificate
-----BEGIN CERTIFICATE-----
MIIGmTCCBYGgAwIBAgIMZgvf9yb37PeNbiMrMA0GCSqGSIb3DQEBCwUAMFAxCzAJ
BgNVBAYTAkJFMRkwFwYDVQQKExBHbG9iYWxTaWduIG52LXNhMSYwJAYDVQQDEx1H
bG9iYWxTaWduIFJTQSBPViBTU0wgQ0EgMjAxODAeFw0yMDA3MTAyMjIxMDJaFw0y
MjA3MTEyMjIxMDJaMHcxCzAJBgNVBAYTAkJSMQswCQYDVQQIEwJTQzEWMBQGA1UE
BxMNRmxvcmlhbm9wb2xpczEvMC0GA1UEChMmVU5JVkVSU0lEQURFIEZFREVSQUwg
REUgU0FOVEEgQ0FUQVJJTkExEjAQBgNVBAMMCSoudWZzYy5icjCCASIwDQYJKoZI
hvcNAQEBBQADggEPADCCAQoCggEBAPCZzjPXRpDab2nMZXDmxXgPMrT7UyZ0n1kt
ewJ32DfYNhcDjcjgpM5FZENcMC/SI0Zg+ol8Bt5Ce6QpE+XPSHdGa/L6PChkdviK
gmzQYHYdIz0jKnfDWNCuniyttaLnaE+R+Vei1aoZBZxVFIla6hC84xTbfy5FLzRZ
zKGjH6ZMTyWCRdu+Dxu84kJ2Htfp7nkFHjKpa5tQFjCrZdLwPMpCcLKUxz+0+WwO
eXB/old+8u/hxIzy1/IicMmW/swF01qGXqRdlehKIndlRh8geVn/MXlQWRafp9vW
8kQRajkfUzBacSQz3Y33YBSrfq8upNXRro1SqLz9rdXm7hIYs68CAwEAAaOCA0ow
ggNGMA4GA1UdDwEB/wQEAwIFoDCBjgYIKwYBBQUHAQEEgYEwfzBEBggrBgEFBQcw
AoY4aHR0cDovL3NlY3VyZS5nbG9iYWxzaWduLmNvbS9jYWNlcnQvZ3Nyc2FvdnNz
bGNhMjAxOC5jcnQwNwYIKwYBBQUHMAGGK2h0dHA6Ly9vY3NwLmdsb2JhbHNpZ24u
Y29tL2dzcnNhb3Zzc2xjYTIwMTgwVgYDVR0gBE8wTTBBBgkrBgEEAaAyARQwNDAy
BggrBgEFBQcCARYmaHR0cHM6Ly93d3cuZ2xvYmFsc2lnbi5jb20vcmVwb3NpdG9y
eS8wCAYGZ4EMAQICMAkGA1UdEwQCMAAwPwYDVR0fBDgwNjA0oDKgMIYuaHR0cDov
L2NybC5nbG9iYWxzaWduLmNvbS9nc3JzYW92c3NsY2EyMDE4LmNybDAdBgNVHREE
FjAUggkqLnVmc2MuYnKCB3Vmc2MuYnIwHQYDVR0lBBYwFAYIKwYBBQUHAwEGCCsG
AQUFBwMCMB8GA1UdIwQYMBaAFPjvf/LNeGeo3m+PJI2I8YcDArPrMB0GA1UdDgQW
BBQ3u1t6DrFB+gG2GuFOsKUTeiYyjDCCAX8GCisGAQQB1nkCBAIEggFvBIIBawFp
AHcAb1N2rDHwMRnYmQCkURX/dxUcEdkCwQApBo2yCJo32RMAAAFzOtFbLgAABAMA
SDBGAiEA2SarecMZHJxPFt1tw9kCqLqLO8KJQv1XhpuVkG6BaocCIQDaJL0nXuS0
du/68MkiuhtqlDjIDQwCdqB2CE0f3Kc2VAB2ACl5vvCeOTkh8FZzn2Old+W+V32c
YAr4+U1dJlwlXceEAAABczrRXlsAAAQDAEcwRQIgU+YTrwmH+gO7xYeQBjS0cUuH
36u0Ozt26cfi3wkDAfgCIQCuWd33/RW/O81zC4epKfDe8ecF6+L8B5k/e5ujbZ7t
SgB2AFGjsPX9AXmcVm24N3iPDKR6zBsny/eeiEKaDf7UiwXlAAABczrRXeMAAAQD
AEcwRQIhAMLC0vHWI90ZuA+iRjkhdme7Vw11kDOyjhCrIBbrTqLJAiBw/q8ebNIv
AxjCPdAXlqV4s3Uowoh7fJsPoJM/iaFKYDANBgkqhkiG9w0BAQsFAAOCAQEABv+H
4wgIYvB5ML05izXZ47POipwGSjR/xHpQ1aYAQhjLZMPkX84h8tsms/3cT5BxD9mK
gm9D8fCCUSZ3ya4Cztfkxg3qZBhEVSUb75DrH2lmgd2BsLLdtaf3ZR5N/OQbtHei
mw1hg3tkMlWvKFkmPHmUIlVX4U0kBPXoVMukB9jeksXsDaLfOiGlLaEnDG20bizQ
emtLAx1yTvvXMrSMwkKkeRiR17q0hz6LoGEi5mM5IS9VqB/R7Vr1i5HmpplLgc4t
bKIAU9cZ/czuUHSIj75iNmWHxgUfWnw7kE+zIUL7VKGr1Z+a2bVOe1A2QKb5guKV
mAFqJ5do4hLMU5k26g==
-----END CERTIFICATE-----
subject=C = BR, ST = SC, L = Florianopolis, O = UNIVERSIDADE FEDERAL DE SANTA CATARINA, CN = *.ufsc.br

issuer=C = BE, O = GlobalSign nv-sa, CN = GlobalSign RSA OV SSL CA 2018

---
No client certificate CA names sent
Peer signing digest: SHA256
Peer signature type: RSA
Server Temp Key: ECDH, P-256, 256 bits
---
SSL handshake has read 4362 bytes and written 340 bytes
Verification: OK
---
New, TLSv1.2, Cipher is ECDHE-RSA-AES256-GCM-SHA384
Server public key is 2048 bit
Secure Renegotiation IS supported
Compression: NONE
Expansion: NONE
No ALPN negotiated
SSL-Session:
    Protocol  : TLSv1.2
    Cipher    : ECDHE-RSA-AES256-GCM-SHA384
    Session-ID: D86C4D885B3CEB13BC42E56CC5439E2D438EDD5D79770C6EB2EAD7FD891EE1F2
    Session-ID-ctx: 
    Master-Key: 616FA86D2DAF2CAAB93C721622CEB0245333D30AF4ACC37A51236DBC245301AC1693C68F6B5BB7FE8AD839BD11EA3865
    PSK identity: None
    PSK identity hint: None
    SRP username: None
    TLS session ticket lifetime hint: 300 (seconds)
    TLS session ticket:
    0000 - 28 a3 b4 86 46 f7 cc a6-54 9d 3a 80 cb 5a 1d 3b   (...F...T.:..Z.;
    0010 - 45 74 71 56 3a 87 5b 5c-2c 48 6e 37 fd d1 e7 4f   EtqV:.[\,Hn7...O
    0020 - 02 47 0f 4c 7f 46 ef fb-b6 8d 46 3d 38 e4 00 47   .G.L.F....F=8..G
    0030 - 3d af d4 da f2 81 16 d4-8a 34 d3 b5 b1 9f a7 81   =........4......
    0040 - dc 54 1d c9 a8 10 69 fb-d3 15 6c f4 56 14 4c 92   .T....i...l.V.L.
    0050 - 24 09 9c d0 d8 5e 6b 79-20 60 d0 0b e6 7f d6 07   $....^ky `......
    0060 - 8c 11 06 be 90 3a 8d 50-35 b9 0c bf cc 1a ac 71   .....:.P5......q
    0070 - 67 17 80 55 79 bd 77 80-0d 09 b4 9a 32 4f e7 f0   g..Uy.w.....2O..
    0080 - 35 be f7 19 c5 84 bd b6-78 1c 86 9a c7 a2 0b 26   5.......x......&
    0090 - 04 25 8c 26 9b 34 29 d3-5d 9e d5 c2 e0 5f 0c d8   .%.&.4).]...._..
    00a0 - ce e8 52 6d d5 ed 7f 20-cc b1 f1 62 df 22 0d 5c   ..Rm... ...b.".\
    00b0 - 5b 50 8f b1 c9 ed 3a 25-a9 6b 31 ca bc bd e8 bc   [P....:%.k1.....

    Start Time: 1604879949
    Timeout   : 7200 (sec)
    Verify return code: 0 (ok)
    Extended master secret: no
---
closed
```

2. Parte: `openssl s_client -connect youtube.com:443  -tls1_3`
```
└─▪ openssl s_client -connect youtube.com:443 -tls1_3


CONNECTED(00000003)
depth=2 OU = GlobalSign Root CA - R2, O = GlobalSign, CN = GlobalSign
verify return:1
depth=1 C = US, O = Google Trust Services, CN = GTS CA 1O1
verify return:1
depth=0 C = US, ST = California, L = Mountain View, O = Google LLC, CN = *.google.com
verify return:1
---
Certificate chain
 0 s:C = US, ST = California, L = Mountain View, O = Google LLC, CN = *.google.com
   i:C = US, O = Google Trust Services, CN = GTS CA 1O1
 1 s:C = US, O = Google Trust Services, CN = GTS CA 1O1
   i:OU = GlobalSign Root CA - R2, O = GlobalSign, CN = GlobalSign
---
Server certificate
-----BEGIN CERTIFICATE-----
MIIJcDCCCFigAwIBAgIRAJEUcUm/1O6mAgAAAAB/FD0wDQYJKoZIhvcNAQELBQAw
QjELMAkGA1UEBhMCVVMxHjAcBgNVBAoTFUdvb2dsZSBUcnVzdCBTZXJ2aWNlczET
MBEGA1UEAxMKR1RTIENBIDFPMTAeFw0yMDEwMjAxODAzMjhaFw0yMTAxMTIxODAz
MjhaMGYxCzAJBgNVBAYTAlVTMRMwEQYDVQQIEwpDYWxpZm9ybmlhMRYwFAYDVQQH
Ew1Nb3VudGFpbiBWaWV3MRMwEQYDVQQKEwpHb29nbGUgTExDMRUwEwYDVQQDDAwq
Lmdvb2dsZS5jb20wWTATBgcqhkjOPQIBBggqhkjOPQMBBwNCAAQ+BGdFp7krsnlU
hY3Xcy7QwWmBe6ldsDUSufEVS79SiNE50r1UoK91Mm9pUxfR0zBt9T5jwM2U0lBO
ONFF3nMdo4IHBjCCBwIwDgYDVR0PAQH/BAQDAgeAMBMGA1UdJQQMMAoGCCsGAQUF
BwMBMAwGA1UdEwEB/wQCMAAwHQYDVR0OBBYEFMC/keyyTMG1w7rwKubzCEm6+5DI
MB8GA1UdIwQYMBaAFJjR+G4Q68+b7GCfGJAboOt9Cf0rMGgGCCsGAQUFBwEBBFww
WjArBggrBgEFBQcwAYYfaHR0cDovL29jc3AucGtpLmdvb2cvZ3RzMW8xY29yZTAr
BggrBgEFBQcwAoYfaHR0cDovL3BraS5nb29nL2dzcjIvR1RTMU8xLmNydDCCBMIG
A1UdEQSCBLkwggS1ggwqLmdvb2dsZS5jb22CDSouYW5kcm9pZC5jb22CFiouYXBw
ZW5naW5lLmdvb2dsZS5jb22CCSouYmRuLmRldoISKi5jbG91ZC5nb29nbGUuY29t
ghgqLmNyb3dkc291cmNlLmdvb2dsZS5jb22CGCouZGF0YWNvbXB1dGUuZ29vZ2xl
LmNvbYIGKi5nLmNvgg4qLmdjcC5ndnQyLmNvbYIRKi5nY3BjZG4uZ3Z0MS5jb22C
CiouZ2dwaHQuY26CDiouZ2tlY25hcHBzLmNughYqLmdvb2dsZS1hbmFseXRpY3Mu
Y29tggsqLmdvb2dsZS5jYYILKi5nb29nbGUuY2yCDiouZ29vZ2xlLmNvLmlugg4q
Lmdvb2dsZS5jby5qcIIOKi5nb29nbGUuY28udWuCDyouZ29vZ2xlLmNvbS5hcoIP
Ki5nb29nbGUuY29tLmF1gg8qLmdvb2dsZS5jb20uYnKCDyouZ29vZ2xlLmNvbS5j
b4IPKi5nb29nbGUuY29tLm14gg8qLmdvb2dsZS5jb20udHKCDyouZ29vZ2xlLmNv
bS52boILKi5nb29nbGUuZGWCCyouZ29vZ2xlLmVzggsqLmdvb2dsZS5mcoILKi5n
b29nbGUuaHWCCyouZ29vZ2xlLml0ggsqLmdvb2dsZS5ubIILKi5nb29nbGUucGyC
CyouZ29vZ2xlLnB0ghIqLmdvb2dsZWFkYXBpcy5jb22CDyouZ29vZ2xlYXBpcy5j
boIRKi5nb29nbGVjbmFwcHMuY26CFCouZ29vZ2xlY29tbWVyY2UuY29tghEqLmdv
b2dsZXZpZGVvLmNvbYIMKi5nc3RhdGljLmNugg0qLmdzdGF0aWMuY29tghIqLmdz
dGF0aWNjbmFwcHMuY26CCiouZ3Z0MS5jb22CCiouZ3Z0Mi5jb22CFCoubWV0cmlj
LmdzdGF0aWMuY29tggwqLnVyY2hpbi5jb22CECoudXJsLmdvb2dsZS5jb22CEyou
d2Vhci5na2VjbmFwcHMuY26CFioueW91dHViZS1ub2Nvb2tpZS5jb22CDSoueW91
dHViZS5jb22CFioueW91dHViZWVkdWNhdGlvbi5jb22CESoueW91dHViZWtpZHMu
Y29tggcqLnl0LmJlggsqLnl0aW1nLmNvbYIaYW5kcm9pZC5jbGllbnRzLmdvb2ds
ZS5jb22CC2FuZHJvaWQuY29tghtkZXZlbG9wZXIuYW5kcm9pZC5nb29nbGUuY26C
HGRldmVsb3BlcnMuYW5kcm9pZC5nb29nbGUuY26CBGcuY2+CCGdncGh0LmNuggxn
a2VjbmFwcHMuY26CBmdvby5nbIIUZ29vZ2xlLWFuYWx5dGljcy5jb22CCmdvb2ds
ZS5jb22CD2dvb2dsZWNuYXBwcy5jboISZ29vZ2xlY29tbWVyY2UuY29tghhzb3Vy
Y2UuYW5kcm9pZC5nb29nbGUuY26CCnVyY2hpbi5jb22CCnd3dy5nb28uZ2yCCHlv
dXR1LmJlggt5b3V0dWJlLmNvbYIUeW91dHViZWVkdWNhdGlvbi5jb22CD3lvdXR1
YmVraWRzLmNvbYIFeXQuYmUwIQYDVR0gBBowGDAIBgZngQwBAgIwDAYKKwYBBAHW
eQIFAzAzBgNVHR8ELDAqMCigJqAkhiJodHRwOi8vY3JsLnBraS5nb29nL0dUUzFP
MWNvcmUuY3JsMIIBAwYKKwYBBAHWeQIEAgSB9ASB8QDvAHUA9lyUL9F3MCIUVBgI
MJRWjuNNExkzv98MLyALzE7xZOMAAAF1R2UWdQAABAMARjBEAiAvemiwbXx8bLnF
EEfS6yRmhNQvjuWrSZ2QAkGI27T0uwIgUdOY4rjIp30kd2fJtPC8ED9b0tzQPnqo
FGldkqVENT8AdgDuwJXujXJkD5Ljw7kbxxKjaWoJe0tqGhQ45keyy+3F+QAAAXVH
ZRZLAAAEAwBHMEUCID8ITStQXlyiH+djv8BORQsfC8yGQ7jkLMmEky0kvAriAiEA
mr2pzu/QTUfyvwBe9ELteOmWnk9nbHdBf6dW2sBO8gAwDQYJKoZIhvcNAQELBQAD
ggEBAG2u6W8QxVea1QnAjEtL9prL+H8XDppBdIxmvjTLJSQcxlYDadYnUW9CX5gl
9DdXFJ0yqkQwXB5ZFGLNRVLopQGIHbg8lizTic+M5wGqH6F/SuliMJ+gnnYePIli
MFy8MV1Bh95rq+VFIY4XNQxVsUwdVEL0s55G6YvqbiZhCnowySqDRxzEtJYKMdnb
3XD7AIjZYt6IUwTD25Fvctq23hKNsYblBooNR/xKvSUB1U2As8WLlR43NWn7v0dc
aeAYNXPLZlkNvExzYds5Tv5TWulzVpxwXyzthXHN46sxRZghM0WurfKd7a0f8w0o
g22zGb9/UgJGjXmnkBnyzdkYfWI=
-----END CERTIFICATE-----
subject=C = US, ST = California, L = Mountain View, O = Google LLC, CN = *.google.com

issuer=C = US, O = Google Trust Services, CN = GTS CA 1O1

---
No client certificate CA names sent
Peer signing digest: SHA256
Peer signature type: ECDSA
Server Temp Key: X25519, 253 bits
---
SSL handshake has read 3833 bytes and written 315 bytes
Verification: OK
---
New, TLSv1.3, Cipher is TLS_AES_256_GCM_SHA384
Server public key is 256 bit
Secure Renegotiation IS NOT supported
Compression: NONE
Expansion: NONE
No ALPN negotiated
Early data was not sent
Verify return code: 0 (ok)
---
read:errno=0

```


## OpenSSL - GERAR PAR DE CHAVES RSA e entender seus componentes
 
### 7. 
**a. Gerar sua chave privada usando o comando:**
```
openssl genrsa -aes256-out seunome.privada.pem 2048
```

**b) Explique o que é o parâmetro `-aes256` do comando.**

**Resposta**<br/>

É a cifra utilizada.


**c) Explique  o  que  significa  a  seguinte  linha  do  arquivo seunome.privada.pem**

**Resposta**<br/>

A linha que começa com DEK-Info contém dois valores separados por vírgula: o nome do algoritmo de criptografia usado por EVP_get_cipherbyname() e um vetor de inicialização usado pela cifra codificada como um conjunto de dígitos hexadecimais.


### 8. Gere a chave pública a partir da chave privada com os comandos abaixo (guardar a chave pública no arquivo seunome.publica.pem). Explique a saídaobtida em cada um dos comandos. Guarde o arquivo geradoe envie o arquivo da sua chave pública junto nas respostas da tarefa.

```
openssl rsa -in brunocampos.privada.pem -pubout -out brunocampos.publica.pem
```

Saída

```
└─▪ cat brunocampos.publica.pem 
-----BEGIN PUBLIC KEY-----
MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqmE+LO8Hk8OCkj6ZBAnW
hhyATNQaD3GjcIN7Yt/oB1WPgPdabGMZmuIkFxOSG7Wb6+esAfIyGYNNN72F5fNr
+c132DZpcBCmknw+FK0jztdBIRsB46hrKpvvorrX2MGDm3gwaSrJMXjiWApwZtKt
tOe1kSN5isoJHayCT4ICsNKUgnAc8kENvLR7B+Yd9D+JUXboh9gS+/1P00Qd0fNI
piA0z32f8fnTe06SOCMRgisyl/9l+T7VwbFWzjFWqLouQ4boky5HhGoWzzijDP1I
WxGTGz3DEXJV1lhmvYfcUeShFGeEmG5aZ1eg06G6PsThRA/K6SZeNjLk0uuYe2PV
CQIDAQAB
-----END PUBLIC KEY-----
```

O comando retorna um arquivo contendo uma chave pública, gerada a partir de uma chave privada.


### 9. Digite o seguinte comando e depois abra o arquivo seunome.publica.componentes. Explique    os    componentesque    constam    nesse    arquivo(https://tools.ietf.org/html/rfc3447#appendix-A). Comando:

```
openssl rsa -in brunocampos.privada.pem -out brunocampos.publica.componentes -text -noout
```

**Resposta**<br/>

**Este arquivo tráz a sintaxe de chave privada RSA**

Uma chave privada RSA deve ser representada com o tipo ASN.1
<br/>
RSAPrivateKey:

```
RSAPrivateKey :: = SEQUENCE {
          versão versão,
          módulo INTEGER, - n
          publicExponent INTEGER, - e
          privateExponent INTEGER, - d
          prime1 INTEGER, - p
          prime2 INTEGER, - q
          expoente1 INTEIRO, - mod d (p-1)
          expoente2 INTEIRO, - mod d (q-1)
          coeficiente INTEGER, - (inverso de q) mod p
          otherPrimeInfos OtherPrimeInfos OPTIONAL
          }
```

Os campos do tipo RSAPrivateKey têm os seguintes significados:

- versão é o número da versão, para compatibilidade com o futuro revisões deste documento. Deve ser 0 para esta versão do documento, a menos que multi-prime seja usado, caso em que deve ser 1.

```
Versão :: = INTEIRO {dois primos (0), multi (1)}
               (RESTRINGIDA POR
               {- a versão deve ser múltipla se houver otherPrimeInfos -})
```

- módulo é o módulo RSA n.
- publicExponent é o expoente público RSA e.
- privateExponent é o expoente privado RSA d.
- prime1 é o fator principal p de n.
- prime2 é o fator principal q de n.
- expoente1 é d mod (p - 1).
- expoente2 é d mod (q - 1).
- coeficiente é o coeficiente CRT q ^ (- 1) mod p.
- otherPrimeInfos contém as informações para os primos adicionais r_3, ..., r_u, em ordem. Deve ser omitido se a versão for 0 e deve conter pelo menos uma instância de OtherPrimeInfo se a versão é 1.


## ASSINATURA DIGITAL

### 10.

**a. Você deve assinar o arquivo fornecido na tarefa (msgPlana.txt). Para isso, crie o hashdo arquivo msgPlana.txt e com a sua chave privada, assine o hash do arquivo**

```
openssl dgst -sha256 -sign brunocampos.privada.pem -out assinatura msgPlana.txt
```

**b) Responda: qual o conteúdo do arquivo assinatura? Essa assinatura garante quais características de segurança: integridade, autenticidade, confidencialidade?**

**Resposta**<br/>

<img src='imagens/10.a.png'  align="middle" height=auto widht=80% >

- É um conteúdo ilegível
- Garante integridade

### 11. Verifique se o hash assinado está ok, isto é, compare o hash assinado com o hash do arquivo original usando o comandoabaixo.Envie sua chave pública para que, durante a correção, possa ser feita a verificação da sua assinatura:

```
openssl dgst -sha256 -verify brunocampos.publica.pem -signature assinatura msgPlana.txt
```

**Resposta**<br/>

<img src='imagens/11.png'  align="middle" height=auto widht=80% >

Tudo OK.

## GERAR UM CERTIFICADO AUTO-ASSINADO(“auto” porque é assinado com SUA própria chave privada)

14. 

```
openssl req -new -key brunocampos.privada.pem -out certificado.csr
```

**Resposta**<br/>

Resposta: arquivo `certificado.csr`

<br/>

15.

```
openssl req -new -key brunocampos.privada.pem -out certificado.csr
```

**Resposta**<br/>

<img src='imagens/15.png'  align="middle" height=auto widht=80% >

Tudo OK.
