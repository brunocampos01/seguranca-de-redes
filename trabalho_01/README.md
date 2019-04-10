# Tarefa Prática 1

#### Nomes:
Bruno Aurélio Rôzza de Moura Campos (14104255)<br/>
Caio Cargnin Cardoso (09138003)

## PARTE 1.NMAP

### Questão 1. 
`nmap –sV -O 10.1.2.6` (IP da máquina Owasp Broken, o seu IP pode ser diferente)

<img src=questao_1.png>

 - Os parâmetros `-sV` servem para: detectar portas abertas para determinar informações de serviço / versão. Neste caso foi encontrado 9 portas abertas executando serviços do tipo ssh, http, imap, netbios-ssn, java-rmi e ssl.
 - O parâmetro `-O` serve para detectar o sistema operacional.

### Questão 2.
`nmap -v –A 10.1.2.6` (IP da máquina Owasp Broken)

<img src=questao_2_a.png>

<img src=questao_2_b.png>

<img src=questao_2_c.png>

- O parâmetro `-v` serve para retornar as ações do nmap de modo verboso.
- O parâmetro `-A` serve para detectar alem da sistema operacional do host atacado, as portas abertas, o estado das portas, o serviço que roda em cada porta e qual a versão que esta sendo executado. Com este parâmetro é rodado um script scanning, e traceroute, conforme figura 4.

### Questão 3.
`nmap –sS –v --top-ports 10 --reason -oA saidanmap www.ufsc.br`

<img src=questao_3.png>

- O parâmetro `-sS` serve para retornar os pacotes TCP SYN. Na imagem 5, na coluna REASON é mostrado quais portas com os seus respectivos serviços retornaram algum resposta.
- O parâmetro `-v` serve para retornar as ações do nmap de modo verboso.
- O parâmetro `--top-ports 10` retorna as portas mais comuns.
- O parâmetro `--reason` mostra o motivo pelo qual uma porta está em um estado específico.
- O parâmetro `-oA` mostra os três principais formatos de uma só vez.<br/>
Alem disso foi gerado 3 arquivos, `saidanmap.gnmap`, `saidanmap.nmap` e `saidanmap.xml` contendo uma tabela com a porta, estado, serviço e motivo do estado da porta, alem dos parâmetros, verbose, debugging, host, address, hostnames e scaninfo.


### Questão 4.
(Apresentação) Crie um comando nmap com opções diferentes das usadas nas questões
anteriores e explique a saída obtida pelo seu comando.

<img src=questao_4.png>

- O parâmetro `--traceroute` mostra todos os saltos e hosts passados até o alvo.


### Questão 5.
**a. Qual a diferença entre um scan de conexão TCP e um SYN scan ?**<br/>
- O scan TCP SYN é relativamente não-obstrusivo e camuflado, uma vez que ele nunca completa uma conexão TCP. Ele também permite uma diferenciação limpa e confiável entre os estados aberto (open), fechado (closed), e filtrado (filtered).
- O scan TCP é o scan padrão do TCP. Esse é o caso quando o usuário não tem privilégios para criar pacotes em estado bruto.


**b. Qual questão anterior usa scan de conexão TCP e qual questão usa SYN scan?**<br/>
- A questão 3 usa `-sS (scan TCP SYN)` e segundo o site do [nmap](https://nmap.org/man/pt_PT/man-port-scanning-techniques.html) por default o Nmap executa um scan SYN, então as questões 1 e 2 tambem utilizam um `scan TCP SYN`.

**c. Comente pelo menos uma vulnerabilidade da máquina Owasp Broken, listando a identificação CVE (cve.mitre.org) da vulnerabilidade<br/>**
- Na questão 3 foi lista na porta TCP 445 uma serviço microsoft-ds. Este serviço apresenta uma vulnerabiliade que pode permite ataques remotos para causar DOS (denial of service).<br/>
Esta vulnerabilidade foi catalogada na CVE com as sequintes informações:

**CVE-ID**: CVE-2002-0597<br/>
**Description**: LANMAN service on Microsoft Windows 2000 allows remote attackers to cause a denial of service (CPU/memory exhaustion) via a stream of malformed data to microsoft-ds port 445.<br/>
**Date Entry Created:** 20030402

---

## PARTE 2.Nikto

### Questão 6.
Execute o comando: `nikto -host http://10.1.2.6/WackoPicko/ –o nikto.html–Format htm`

**a. Copie e cole screenshots (pedaços) de telas obtidas na execução do comando.**<br/>

<image src=nikto_output.png>

**b. Explique o que mais chamou sua atenção na saída obtida. Explique também alguma vulnerabilidade encontrada nessa aplicação (WackoPicko) que consta no relatório do arquivo muti.html.**

O que mais nos chamou a atenção foram 2 pontos:<br/>
- Não há autenticação para ser admin do servidor: 
`+ /WackoPicko/guestbook/admin.php: Guestbook admin page available without authentication.`
- É possível baixar todo o banco de dados: 
`+ OSVDB-52975: /WackoPicko/guestbook/admin/o12guest.mdb: Ocean12 ASP Guestbook Manager allows download of SQL database which contains admin password.`

Sobre as vulnerabilidades, o nikto retornou o arquivo `nikto.html` contendo todas as vulnerabilidades encontrada:<br/>

<image src="q6.png">

Nesta vulnerabilidade da imagem acima é possível ver que através de uma requisição HTTP GET é possível ter acesso privilegiado na página sem necessitar de autenticação.

---

## PARTE 3.OWASP –Vulnerabilidades em Aplicações Web

### Questão 7.
Explique as vulnerabilidades A1, A2, A3 e A7 do documento TOP TEN2017:

- A1: injection - 
É uma falha na codificação de uma aplicação qualquer (seja web ou local) que permite ao atancate inserir uma consulta SQL

- A2: Broken Authentication - 
É uma vulnerabilidade nas sessões nas aplicações que utilizam autenticação que permite aos invasores comprometerem senhas, tokens de sessão ou explorem outras falhas de implementação para assumir as identidades de outros usuários.

- A3: Sensitive Data Exposure - 
Refere-se a proteção incorreta dos dados críticos tais como, por exemplo, números de cartão de crédito, senhas, entre outros.

- A7: Cross-Site Scripting (XSS) - 
Os ataques XSS típicos incluem roubo de sessão, controle de conta, desvio de MFA, substituição ou desfiguração de nó DOM (como painéis de login de trojan), ataques contra o navegador do usuário, como downloads de software mal-intencionado, registro de chaves e outros ataques do lado do cliente.<br/>
Os alvos desta vulnerabilidade são so browsers dos usuários.<br/>
Outro ponto importante sobre esta vulnerabilidade é que o  problema de XSS é o segundo problema mais recorrente, registrado pelo OWASP Top 10.


### Questão 8.
**a. Acesse  a  aplicação  Mutillidae:  abra  o  browser da  sua  máquina  real  ou  na  Kali  Linux  no  site http://IP da Kali/mutillidae/ e clique em Login (ver figura 5).
No campo Username, digite a string ‘or1=1 --(tem  espaço  no  final,  depois  dos  tracinhos). O  campo Password  pode  ficar  em branco. Copie e cole a tela do seu experimento.**

**b. Explique  o  resultado  obtido  e  a  vulnerabilidade  explorada  no  experimento  (pesquise  no documento do TOP 10 da OWASP).**

**c. O que pode ser feito para impedir a exploração dessa vulnerabilidade?d.Clique em Logout.**

### Questão 9.
Repita a inserção da mesma string da questão anterior no seguinte link: http://IPda Kali/mutillidae/index.php?page=user-info.php<br/>

**a. Explique a vulnerabilidade explorada no experimento (pesquise no documento do TOP 10 da OWASP).**

**b. Copie e cole um screenshot da execução de um experimento.c. O que pode ser feito para impedir a exploração dessa vulnerabilidade?**

---

## PARTE 4.Vulnerabilidades em IoT

### Questão 12.
Leia a reportagem com título “Find webcams, databases, boats in the sea using Shodan”disponível emhttps://www.securitynewspaper.com/2018/11/27/find-webcams-databases-boats-in-the-sea-using-shodan/.<br/>
Responda:

**a. O que é o Shodane o que é possível fazer com este site?**

**b. (Apresentação) Faça o registro no site, pesquise e liste algum dispositivo IoT que você encontrou.**

### Questão 13.
Conforme    descrito    na    reportagem,    acesse    o    link http://166.161.197.253:5001/cgi-bin/guestimage.html. É uma câmera Mobotix.<br/>
Responda:
**a. O que é possível visualizar?**

**b. Um atacante poderia fazer o que com este acesso?**

---

## PARTE 5.Metasploit

### Questão 14.
Copie e cole o screenshot da sua tela ao realizar o experimentoanterior. Depois, explique o experimento:<br/>

**a. O que é o ataque do dicionário?**

**b. O que foi encontrado?**

**c. Qual foi a vulnerabilidade usada para obter esse resultado?**

**d. Como pode ser explorado esse resultado?**






### Referências:
- https://nmap.org/ acesso 08/04/2019
https://www.owasp.org/images/7/72/OWASP_Top_10-2017_(en).pdf.pdf acesso 08/04/2019