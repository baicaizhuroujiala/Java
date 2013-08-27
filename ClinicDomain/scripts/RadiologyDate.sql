--<ScriptOptions statementTerminator=";"/>

CREATE TABLE radiologydate (
		id INT8 NOT NULL,
		date DATE,
		radiology_fk INT8
	);

CREATE UNIQUE INDEX radiologydate_pkey ON radiologydate (id ASC);

ALTER TABLE radiologydate ADD CONSTRAINT radiologydate_pkey PRIMARY KEY (id);

ALTER TABLE radiologydate ADD CONSTRAINT fk_radiologydate_radiology_fk FOREIGN KEY (radiology_fk)
	REFERENCES treatment (id);

