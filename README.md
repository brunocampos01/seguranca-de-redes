# Segurança da Informação e de Redes

### Pre requirements

- Multillidae<br/>
Pré requisitos para o multillidae<br/>
```
sudo apt install php7.2-curl \
                 php7.2-mbstring \
                 php7.2-xml \
                 php7.2-cli
```

- Aplicação do muiltillidae<br/>
```
sudo mkdir /var/www
sudo mkdir /var/www/html
cd /var/www/html
git clone https://git.code.sf.net/p/mutillidae/git mutillidae
```

- MetaSploit<br/>
https://github.com/rapid7/metasploit-framework/wiki/Nightly-Installers
```
curl https://raw.githubusercontent.com/rapid7/metasploit-omnibus/master/config/templates/metasploit-framework-wrappers/msfupdate.erb > msfinstall && \
                chmod 755 msfinstall && \
                ./msfinstall
```

  - Start metasploit<br/>
  `msfconsole`
  - See xploits<br/>
  `search tomcat`