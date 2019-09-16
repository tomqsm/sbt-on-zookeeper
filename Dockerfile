FROM openjdk:12 as builder

RUN jlink \
    --add-modules java.sql,java.naming,java.management,java.instrument,java.security.jgss,jdk.unsupported,java.compiler,java.desktop \
    --verbose \
    --strip-debug \
    --compress 2 \
    --no-header-files \
    --no-man-pages \
    --output /opt/jre-minimal

FROM panga/alpine:3.8-glibc2.27

COPY --from=builder /opt/jre-minimal /opt/jre-minimal

ENV LANG=C.UTF-8 \
    PATH=${PATH}:/opt/jre-minimal/bin

ADD target/modules /opt/app/modules

ARG JVM_OPTS
ENV JVM_OPTS=${JVM_OPTS}

CMD java -Dspring.profiles.active=inmemory,default ${JVM_OPTS} --upgrade-module-path /opt/app/modules --module five.springapp
#docker build --build-arg JVM_OPTS=-Xmx512m -t min -f .\Dockerfile .
#docker run -it -e "JVM_OPTS=-Xmx512m" --rm --name min -p 8080:8080 min