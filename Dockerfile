FROM openeuler/openeuler:22.03 as BUILDER

RUN cd / \
    && yum install -y wget \
    && wget https://mirrors.tuna.tsinghua.edu.cn/Adoptium/17/jdk/x64/linux/OpenJDK17U-jdk_x64_linux_hotspot_17.0.10_7.tar.gz \
    && tar -zxvf OpenJDK17U-jdk_x64_linux_hotspot_17.0.10_7.tar.gz \
    && wget https://repo.huaweicloud.com/apache/maven/maven-3/3.8.1/binaries/apache-maven-3.8.1-bin.tar.gz \
    && tar -zxvf apache-maven-3.8.1-bin.tar.gz \
    && yum install -y git

COPY . /EasySoftware

ENV JAVA_HOME=/jdk-17.0.10+7
ENV PATH=${JAVA_HOME}/bin:$PATH

ENV MAVEN_HOME=/apache-maven-3.8.1
ENV PATH=${MAVEN_HOME}/bin:$PATH

RUN cd /EasySoftware \
    && mvn clean install package -Dmaven.test.skip

FROM openeuler/openeuler:22.03

RUN yum update -y \
    && yum install -y shadow

RUN groupadd -g 1001 easysoftware \
    && useradd -u 1001 -g easysoftware -s /bin/bash -m easysoftware

ENV WORKSPACE=/home/easysoftware

WORKDIR ${WORKSPACE}

COPY --chown=easysoftware --from=Builder /EasySoftware/target ${WORKSPACE}/target

RUN echo "umask 027" >> /home/easysoftware/.bashrc \
    && source /home/easysoftware/.bashrc \
    && chmod 550 -R /home/easysoftware \
	&& echo "set +o history" >> /etc/bashrc \
    && sed -i "s|HISTSIZE=1000|HISTSIZE=0|" /etc/profile \
    && sed -i "s|PASS_MAX_DAYS[ \t]*99999|PASS_MAX_DAYS 30|" /etc/login.defs

RUN dnf install -y wget \
    && wget https://mirrors.tuna.tsinghua.edu.cn/Adoptium/17/jre/x64/linux/OpenJDK17U-jre_x64_linux_hotspot_17.0.10_7.tar.gz \
    && tar -zxvf OpenJDK17U-jre_x64_linux_hotspot_17.0.10_7.tar.gz

RUN rm -rf /usr/bin/gdb* \
    && rm -rf /usr/share/gdb \
    && rm -rf /usr/share/gcc-10.3.1 \
	&& yum remove gdb-gdbserver findutils passwd shadow -y \
    && yum clean all

ENV JAVA_HOME=${WORKSPACE}/jdk-17.0.10+7-jre
ENV PATH=${JAVA_HOME}/bin:$PATH
ENV LANG="C.UTF-8"
ENV NO_ID_USER=anonymous

EXPOSE 8080

USER easysoftware

CMD java -jar ${WORKSPACE}/target/easysoftware-0.0.1-SNAPSHOT.jar --spring.config.location=${APPLICATION_PATH}
