all:
		git submodule init
		git submodule update
		$(MAKE) -C ancor2 dev-install
		mvn package

install: all
		mvn install
