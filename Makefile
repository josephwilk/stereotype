all: unit integration

unit:
	lein midje stereotype-clj.unit.*

integration:
	lein midje stereotype-clj.integration.*

ci:
	lein2 with-profile dev,1.3:dev,1.4:dev,1.5 midje
