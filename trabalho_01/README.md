# Tareffa Prática 1

## Nomes:
Bruno Aurélio Rôzza de Moura Campos 14104255

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
a. Qual a diferença entre um scan de conexão TCP e um SYN scan ?<br/>
- O scan TCP SYN é relativamente não-obstrusivo e camuflado, uma vez que ele nunca completa uma conexão TCP.
- O scan TCP é o scan padrão do TCP. Esse é o caso quando o usuário não tem privilégios para criar pacotes em estado bruto 


b. Qual questão anterior usa scan de conexão TCP e qual questão usa SYN scan?<br/>


c. Comente pelo menos uma vulnerabilidade da máquina Owasp Broken, listando a identificação CVE (cve.mitre.org) da vulnerabilidade<br/>