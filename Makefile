SPRING_APP_PORT=9090
JAR_FILE=BlogBot.jar
DEBUG="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=8787"

.PHONY: clean

clean:
	@mvn clean

compile:
	@mvn clean install

debug: compile
	@java -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=8787 \
          -Dspring.profiles.active=$(PROFILE) \
          -Dserver.port=${SPRING_APP_PORT} \
          -jar target/${JAR_FILE}

start: compile
	@java -Dspring.profiles.active=$(PROFILE) \
	      -Dserver.port=${SPRING_APP_PORT} \
          -jar target/${JAR_FILE}
