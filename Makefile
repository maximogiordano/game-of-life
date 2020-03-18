# Java source files stored inside "src" folder
JAVA_FILES = $(shell find src/ -iname '*.java' -printf '%p ')
# Compiled class files will be placed under "bin" folder
CLASS_FILES = $(shell find bin -iname '*.class')

build:
	javac -d bin $(JAVA_FILES)

run:
	java -cp bin com.maximo.giordano.gol.Form

clear:
	rm -Rfv $(CLASS_FILES)

