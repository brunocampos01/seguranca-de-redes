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

### Requirements

- Java >= 1.8
 - Javac 11.0.2
- Ant >= 1.10.5
```shell script
sudo apt install ant
ant -version # Apache Ant(TM) version 1.10.5
 ```

- Development package
```bash
sudo apt-get install libssl-dev
```

- Virtual Machine: [OWASP](https://sourceforge.net/projects/owaspbwa/files/1.2/OWASP_Broken_Web_Apps_VM_1.2.ova/download)
- Create virtual interface to virtual box:
```bash
VBoxManage hostonlyif create
```

- OpenSSL >= 1.0.2o
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

---

#### Author
<a href="mailto:brunocampos01@gmail.com" target="_blank"><img class="" src="https://github.com/brunocampos01/devops/blob/master/images/gmail.png" width="28"></a>
<a href="https://github.com/brunocampos01" target="_blank"><img class="ai-subscribed-social-icon" src="https://github.com/brunocampos01/devops/blob/master/images/github.png" width="30"></a>
<a href="https://www.linkedin.com/in/brunocampos01/" target="_blank"><img class="ai-subscribed-social-icon" src="https://github.com/brunocampos01/devops/blob/master/images/linkedin.png" width="30"></a>
Bruno Aurélio Rôzza de Moura Campos 

---

#### Copyright
<a rel="license" href="http://creativecommons.org/licenses/by-sa/4.0/"><img alt="Creative Commons License" style="border-width:0" src="https://i.creativecommons.org/l/by-sa/4.0/88x31.png" /></a><br/>
