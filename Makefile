.PHONY : build run

build:
	mvn clean package

run:
	java -jar target/spring-boot-example-1.0.jar