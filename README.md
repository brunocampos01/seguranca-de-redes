# Segurança da Informação e de Redes

![License](https://img.shields.io/badge/Code%20License-MIT-blue.svg)
![seguranca](https://img.shields.io/badge/UFSC-Seguran%C3%A7a%20da%20Informa%C3%A7%C3%A3o%20e%20de%20Redes-red.svg)

## Conteúdo
- Conceitos Básicos
- Criptografia Simétrica
- Funções Hash, MAC, Criptografia Autenticada, Derivação de Chaves
- Criptografia Assimétrica
- Gerenciamento e Distribuição de Chaves
- Protocolos criptográficos
- Autenticação
- Segurança da Rede e de Sistemas

## Trabalhos
- 

### Pre requirements

- Java >= 1.8

- Ant >= 1.10.5
```bash
sudo apt install ant
ant -version # Apache Ant(TM) version 1.10.5
 ```

- Development package
```bash
sudo apt-get install libssl-dev
```

- Openssl >= 1.0.2o
```bash
sudo apt install openssl
openssl version # OpenSSL 1.0.2o  27 Mar 2018
```

- Multillidae<br/>
Pré requisitos para o multillidae<br/>
```bash
sudo apt install php7.2-curl \
                 php7.2-mbstring \
                 php7.2-xml \
                 php7.2-cli
```

- Aplicação do muiltillidae<br/>
```bash
sudo mkdir /var/www
sudo mkdir /var/www/html
cd /var/www/html
git clone https://git.code.sf.net/p/mutillidae/git mutillidae
```

- MetaSploit<br/>
https://github.com/rapid7/metasploit-framework/wiki/Nightly-Installers
```bash
curl https://raw.githubusercontent.com/rapid7/metasploit-omnibus/master/config/templates/metasploit-framework-wrappers/msfupdate.erb \
                > msfinstall && \
                chmod 755 msfinstall && \
                ./msfinstall
```

  - Start metasploit<br/>
```bash
msfconsole
```

  - See xploits<br/>
```bash
search tomcat
```

### Autor
- Bruno Aurélio Rôzza de Moura Campos (brunocampos01@gmail.com)
---
### Copyright
<a rel="license" href="http://creativecommons.org/licenses/by-sa/4.0/"><img alt="Creative Commons License" style="border-width:0" src="https://i.creativecommons.org/l/by-sa/4.0/88x31.png" /></a><br />This work by <span xmlns:cc="http://creativecommons.org/ns#" property="cc:attributionName">Bruno A. R. M. Campos</span> is licensed under a <a rel="license" href="http://creativecommons.org/licenses/by-sa/4.0/">Creative Commons Attribution-ShareAlike 4.0 International License</a>.