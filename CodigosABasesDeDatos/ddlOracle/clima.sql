CREATE TABLE  "CLIMA" 
   (	"ID" NUMBER, 
	"TEMPERATURA" NUMBER, 
	"PORCENTAJE_LLUVIA" NUMBER, 
	"INDICE_UV" NUMBER, 
	"CALIDAD_AIRE" NUMBER, 
	"FECHA" TIMESTAMP (6), 
	 CONSTRAINT "CLIMA_PK" PRIMARY KEY ("FECHA")
  USING INDEX  ENABLE
   )  DEFAULT COLLATION "USING_NLS_COMP"
/
