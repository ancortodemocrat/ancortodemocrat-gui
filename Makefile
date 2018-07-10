JAR_F=./target/ancor2-gui-1.0-SNAPSHOT-jar-with-dependencies.jar
ANCOR2GUI=java -jar $(JAR_F)

all: install

sub:
	git submodule init
	git submodule update
	$(MAKE) -C ancor2 dev-install

$(JAR_F): sub
	mvn package

package: $(JAR_F)


install: package
	mvn install

concord-test-prepare:
	$(MAKE) -C ancor2 T6-prepare-light
	$(MAKE) package

concord-test:
	$(ANCOR2GUI) concordancier -u \
	--corpus /tp/Augustin/Ancor/Données_maj/Tableau6/corpus_ESLO_test_1 \
	-lom /tmp/rjc18/t6/chaines/J48_ESLO_ESLO_1_0_0_LOM_SYSTEM.csv
