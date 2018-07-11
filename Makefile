JAR_F=./target/ancor2-gui-1.0-SNAPSHOT-jar-with-dependencies.jar
ANCOR2GUI=java -jar $(JAR_F)

all: package
	cp $(JAR_F) ./ancor2-gui.jar
sub:
	git submodule init
	git submodule update
	$(MAKE) -C ancor2 dev-install

package: sub
	mvn package

install: package
	mvn install

concord-test-prepare:
	$(MAKE) -C ancor2 T6-prepare-light

concord-test:
	$(ANCOR2GUI) concordancier -u \
	--corpus /tp/Augustin/Ancor/Données_maj/Tableau6/corpus_ESLO_test_1 \
	-lom /tmp/rjc18/t6/chaines/J48_ESLO_ESLO_1_0_0_LOM_SYSTEM.csv
