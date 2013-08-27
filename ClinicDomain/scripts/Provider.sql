--<ScriptOptions statementTerminator=";"/>

CREATE TABLE provider (
		id INT8 NOT NULL,
		name VARCHAR(255),
		providerid INT8,
		specializations VARCHAR(255)
	);

CREATE UNIQUE INDEX provider_pkey ON provider (id ASC);

ALTER TABLE provider ADD CONSTRAINT provider_pkey PRIMARY KEY (id);

