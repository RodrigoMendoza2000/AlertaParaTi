CREATE TABLE  "SESIONES" 
   (	"ID" NUMBER, 
	"TIEMPO_INICIO" TIMESTAMP (6), 
	"TIEMPO_FIN" TIMESTAMP (6), 
	 CONSTRAINT "SESIONES_PK" PRIMARY KEY ("ID")
  USING INDEX  ENABLE
   )  DEFAULT COLLATION "USING_NLS_COMP"
/
