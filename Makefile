all: ml_chars

ml_chars:
	mvn clean install

run:
	bash run.sh sample-data clusters.txt

clean:
	mvn clean

.PHONY: all ml_chars run clean
