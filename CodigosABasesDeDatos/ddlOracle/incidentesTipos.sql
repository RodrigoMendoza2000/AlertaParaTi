CREATE TABLE  "INCIDENTES_TIPOS" 
   (	"ID" NUMBER, 
	"TIPO_INCIDENTE" VARCHAR2(100) COLLATE "USING_NLS_COMP", 
	 CONSTRAINT "INCIDENTES_TIPOS_PK" PRIMARY KEY ("ID")
  USING INDEX  ENABLE
   )  DEFAULT COLLATION "USING_NLS_COMP"
/
