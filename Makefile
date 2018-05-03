

all: help

help:
	@echo make j1_validate
	@echo make j1_verify

j1_verify:
	@echo maven verify
	cd j1
	cd j1/

j1_validate:
	@echo mvn validate

