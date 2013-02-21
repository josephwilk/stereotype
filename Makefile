all: unit integration

unit:
	lein midje stereotype-clj.unit.*

integration:
	lein midje stereotype-clj.integration.*