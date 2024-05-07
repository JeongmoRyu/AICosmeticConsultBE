FROM 이미지

COPY ./build/libs/skins-api-0.0.1-SNAPSHOT.jar /service/service.jar

RUN echo "#!/bin/sh" >> /service/start.sh \
  && echo "if [ \${ENV} = \"qa\" ] || [ \${ENV} = \"prd\" ]; then" >> /service/start.sh \
  # && echo "  java -Xverify:none -javaagent:/service/intermax/intermax_patch8_\${ENV}/jspd/lib/jspd.jar -Dintermax.agentname=\${HOSTNAME} -Dintermax.agentgroup=\${DEFENDER_APP_ID} -Dintermax.agentservice=APMALL_Kube -Dintermax.alertgroup=APMALL_Kube -Dintermax.hostgroup=\${DEFENDER_APP_ID} -jar -Dspring.profiles.active=\${ENV} /service/service.jar" >> /service/start.sh \
  && echo "  java -jar -Dspring.profiles.active=\${ENV} -Djasypt.encryptor.password=pw /service/service.jar" >> /service/start.sh \
  && echo "else" >> /service/start.sh \
  && echo "  java -jar -Dspring.profiles.active=\${ENV} -Djasypt.encryptor.password=pw /service/service.jar" >> /service/start.sh \
  && echo "fi" >> /service/start.sh

RUN chmod +x /service/start.sh

EXPOSE 8080
ENTRYPOINT ["/service/start.sh"]
