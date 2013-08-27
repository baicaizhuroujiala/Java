ALTER TABLE TREATMENT DROP CONSTRAINT FK_TREATMENT_provider_fk
ALTER TABLE TREATMENT DROP CONSTRAINT FK_TREATMENT_patient_fk
ALTER TABLE DRUGTREATMENT DROP CONSTRAINT FK_DRUGTREATMENT_ID
ALTER TABLE SURGERY DROP CONSTRAINT FK_SURGERY_ID
ALTER TABLE RADIOLOGY DROP CONSTRAINT FK_RADIOLOGY_ID
ALTER TABLE RADIOLOGYDATE DROP CONSTRAINT FK_RADIOLOGYDATE_radiology_fk
DROP TABLE PATIENT
DROP TABLE TREATMENT
DROP TABLE DRUGTREATMENT
DROP TABLE SURGERY
DROP TABLE RADIOLOGY
DROP TABLE RADIOLOGYDATE
DROP TABLE PROVIDER
DELETE FROM SEQUENCE WHERE SEQ_NAME = 'SEQ_GEN'
