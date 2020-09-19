build:
	make -s tncy
	mkdir -p bin
	javac -d bin -cp src:res:lib/common/*:lib/x86/* src/Main.java

run:
	java -Djava.library.path=sys/x86 -cp bin:res:lib/common/*:lib/x86/* Main

run-arm:
	java -Djava.library.path=sys/arm -cp bin:res:lib/common/*:lib/arm/* Main

archive:
	$(eval NAME := $(shell basename $(CURDIR)))
	mkdir -p tmp
	mkdir -p tmp/x86
	mkdir -p tmp/arm
	jar cfm tmp/x86/$(NAME).jar .mf -C bin . -C res .
	cp tmp/x86/$(NAME).jar tmp/arm/$(NAME).jar
	cp sys/x86/* tmp/x86
	cp sys/arm/* tmp/arm
	cp lib/common/* tmp/x86
	cp lib/common/* tmp/arm
	cp lib/arm/* tmp/arm
	cp lib/x86/* tmp/x86
	echo "#/bin/sh\njava -Djava.library.path=. -jar $(NAME).jar" > tmp/x86/$(NAME).sh
	cp tmp/x86/$(NAME).sh tmp/arm/$(NAME).sh
	echo "java -Djava.library.path=. -jar $(NAME).jar" > tmp/x86/$(NAME).bat
	chmod u+x tmp/x86/$(NAME).sh
	chmod u+x tmp/arm/$(NAME).sh
	chmod u+x tmp/x86/$(NAME).bat
	mkdir -p zip
	cd tmp/x86; zip ../../zip/$(NAME)-x86.zip *
	cd tmp/arm; zip ../../zip/$(NAME)-arm.zip *

exec:
	$(eval NAME := $(shell basename $(CURDIR)))
	java -Djava.library.path=tmp/x86 -jar tmp/x86/$(NAME).jar

exec-arm:
	$(eval NAME := $(shell basename $(CURDIR)))
	java -Djava.library.path=tmp/arm -jar tmp/arm/$(NAME).jar

clean:
	rm -r -f bin/*
	rm -r -f tmp/*
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

.PHONY: build run run-arm archive exec exec-arm clean tncy
