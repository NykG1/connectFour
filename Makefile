all: build

.PHONY: build doc test

build:
	-mkdir build
	javac -d build src/connect_four/*.java

run:
	make
	java -classpath build connect_four.Game

runAI:
	make
	java -classpath build connect_four.Game -r

doc:
	javadoc -d doc -private -sourcepath src connect_four

clean:
	rm -rf build
	rm -rf doc
