build:
	make -s tncy
	mkdir -p bin
	javac -d bin -cp src:res:lib/* src/Main.java

run:
	java -cp bin:res:lib/* -Djava.library.path=res/natives Main

archive:
	mkdir -p zip
	cp res/natives/* zip
	cp lib/* zip
	jar cfm zip/main.jar .mf -C bin . -C res .
	echo "#/bin/sh\njava -Djava.library.path=. -jar main.jar" > zip/main.sh
	echo "java -Djava.library.path=. -jar main.jar" > zip/main.bat
	chmod u+x zip/main.sh
	chmod u+x zip/main.bat
	zip main.zip zip/*

exec:
	java -Djava.library.path=zip -jar zip/main.jar

clean:
	rm -r -f bin/*
	rm -r -f zip/*

tncy:
	@echo "\033[1;35m  _______ __ _   __"
	@echo "\033[35m |__   __/_/| | /_/"
	@echo "\033[35m    | |  ___| | ___  ___ ___  _ __ ___"
	@echo "\033[35m    | | / _ \ |/ _ \/ __/ _ \| '_ \` _ \ "
	@echo "\033[35m    | ||  __/ |  __/ (_| (_) | | | | | |"
	@echo "\033[35m    | |_\___|_|\___|\___\___/|_|_|_| |_|"
	@echo "\033[35m    | '_ \ \033[37m/ _\` | '_ \ / __| | | |"
	@echo "\033[35m    | | | |\033[37m (_| | | | | (__| |_| |"
	@echo "\033[35m    |_| |_|\033[37m\__,_|_| |_|\___|\__, |"
	@echo "                             __/ |"
	@echo "                            |___/ "
	@echo "  TGD"
	@echo "\033[0m   2018-2019"
	@echo ""

.PHONY: build run archive exec clean tncy
