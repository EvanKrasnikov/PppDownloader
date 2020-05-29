FROM alpine:3.11

RUN wget https://releases.hashicorp.com/consul/1.7.3/consul_1.7.3_linux_amd64.zip &&\
    unzip consul_1.7.3_linux_amd64.zip &&\
    mkdir consul.d

COPY PppDownloader-consul-config.json ./consul.d/PppDownloader-consul-config.json

