-- =====================================================================
-- GLOBAL SOLUTION 2026/2 - CITIZENTECH - DESPERDICIO ZERO
-- Somente DDL (tabelas, comentarios e sequences), para importar no
-- Oracle Data Modeler. O script completo (com INSERTs, consultas,
-- UPDATE e DELETE) esta em gs_database_desperdicio_zero.sql
-- =====================================================================

-- ---------------------------------------------------------------------
-- TB_LOCAL: casas, comunidades e negocios que monitoram o desperdicio
-- ---------------------------------------------------------------------
CREATE TABLE tb_local (
    cd_local     NUMBER(10)    NOT NULL,
    nm_local     VARCHAR2(100) NOT NULL,
    ds_tipo      VARCHAR2(20)  NOT NULL,
    nm_cidade    VARCHAR2(60)  NOT NULL,
    nm_bairro    VARCHAR2(60)  NOT NULL,
    qt_pessoas   NUMBER(6)     NOT NULL,
    dt_cadastro  DATE          NOT NULL,
    CONSTRAINT pk_tb_local      PRIMARY KEY (cd_local),
    CONSTRAINT ck_local_tipo    CHECK (ds_tipo IN ('CASA', 'COMUNIDADE', 'NEGOCIO')),
    CONSTRAINT ck_local_pessoas CHECK (qt_pessoas > 0)
);
COMMENT ON TABLE tb_local IS 'Locais monitorados: casas, comunidades ou negocios. qt_pessoas = moradores, funcionarios ou frequentadores';

-- ---------------------------------------------------------------------
-- TB_RECURSO: agua, energia e alimento, com unidade e valores de referencia
-- ---------------------------------------------------------------------
CREATE TABLE tb_recurso (
    cd_recurso          NUMBER(10)   NOT NULL,
    nm_recurso          VARCHAR2(30) NOT NULL,
    ds_unidade          VARCHAR2(10) NOT NULL,
    vl_custo_unitario   NUMBER(10,4) NOT NULL,
    vl_fator_co2        NUMBER(10,4) NOT NULL,
    CONSTRAINT pk_tb_recurso     PRIMARY KEY (cd_recurso),
    CONSTRAINT uq_recurso_nome   UNIQUE (nm_recurso),
    CONSTRAINT ck_recurso_unid   CHECK (ds_unidade IN ('L', 'KWH', 'KG')),
    CONSTRAINT ck_recurso_custo  CHECK (vl_custo_unitario > 0),
    CONSTRAINT ck_recurso_co2    CHECK (vl_fator_co2 >= 0)
);
COMMENT ON TABLE tb_recurso IS 'Recursos monitorados (agua, energia, alimento) com unidade de medida, custo por unidade em reais e kg de CO2 por unidade (valores de referencia ficticios)';

-- ---------------------------------------------------------------------
-- TB_CAUSA: motivos do desperdicio, sempre ligados a um recurso (1:N)
-- ---------------------------------------------------------------------
CREATE TABLE tb_causa (
    cd_causa     NUMBER(10)    NOT NULL,
    cd_recurso   NUMBER(10)    NOT NULL,
    nm_causa     VARCHAR2(80)  NOT NULL,
    ds_causa     VARCHAR2(200),
    CONSTRAINT pk_tb_causa          PRIMARY KEY (cd_causa),
    CONSTRAINT fk_causa_recurso     FOREIGN KEY (cd_recurso) REFERENCES tb_recurso (cd_recurso),
    CONSTRAINT uq_causa_nome        UNIQUE (cd_recurso, nm_causa),
    CONSTRAINT uq_causa_id_recurso  UNIQUE (cd_causa, cd_recurso)
);
COMMENT ON TABLE tb_causa IS 'Causas do desperdicio (vazamento, sobra de comida, aparelho ligado sem uso), cada uma pertencente a um recurso';

-- ---------------------------------------------------------------------
-- TB_DICA: dicas de economia por recurso (1:N)
-- ---------------------------------------------------------------------
CREATE TABLE tb_dica (
    cd_dica          NUMBER(10)    NOT NULL,
    cd_recurso       NUMBER(10)    NOT NULL,
    nm_titulo        VARCHAR2(80)  NOT NULL,
    ds_dica          VARCHAR2(300) NOT NULL,
    vl_economia_pct  NUMBER(5,2)   NOT NULL,
    CONSTRAINT pk_tb_dica        PRIMARY KEY (cd_dica),
    CONSTRAINT fk_dica_recurso   FOREIGN KEY (cd_recurso) REFERENCES tb_recurso (cd_recurso),
    CONSTRAINT uq_dica_titulo    UNIQUE (cd_recurso, nm_titulo),
    CONSTRAINT ck_dica_economia  CHECK (vl_economia_pct BETWEEN 0 AND 100)
);
COMMENT ON TABLE tb_dica IS 'Dicas de reducao do desperdicio por recurso, com economia estimada em porcentagem (valores ilustrativos)';

-- ---------------------------------------------------------------------
-- TB_REGISTRO: desperdicio lancado em um local (1:N com local, recurso e causa)
-- A FK composta (causa + recurso) garante que a causa pertence ao recurso
-- ---------------------------------------------------------------------
CREATE TABLE tb_registro (
    cd_registro     NUMBER(10)    NOT NULL,
    cd_local        NUMBER(10)    NOT NULL,
    cd_recurso      NUMBER(10)    NOT NULL,
    cd_causa        NUMBER(10)    NOT NULL,
    qt_desperdicio  NUMBER(10,2)  NOT NULL,
    dt_registro     DATE          NOT NULL,
    ds_observacao   VARCHAR2(200),
    CONSTRAINT pk_tb_registro      PRIMARY KEY (cd_registro),
    CONSTRAINT fk_registro_local   FOREIGN KEY (cd_local)   REFERENCES tb_local (cd_local),
    CONSTRAINT fk_registro_recurso FOREIGN KEY (cd_recurso) REFERENCES tb_recurso (cd_recurso),
    CONSTRAINT fk_registro_causa   FOREIGN KEY (cd_causa, cd_recurso) REFERENCES tb_causa (cd_causa, cd_recurso),
    CONSTRAINT ck_registro_qt      CHECK (qt_desperdicio > 0)
);
COMMENT ON TABLE tb_registro IS 'Desperdicios lancados por local, com recurso, causa, quantidade na unidade do recurso e data. A data futura e validada na API';

-- ---------------------------------------------------------------------
-- TB_META: associativa N:N entre local e recurso, com meta mensal
-- A meta e o teto mensal de desperdicio, na unidade do recurso
-- ---------------------------------------------------------------------
CREATE TABLE tb_meta (
    cd_meta          NUMBER(10)   NOT NULL,
    cd_local         NUMBER(10)   NOT NULL,
    cd_recurso       NUMBER(10)   NOT NULL,
    qt_meta_mensal   NUMBER(10,2) NOT NULL,
    dt_inicio        DATE         NOT NULL,
    CONSTRAINT pk_tb_meta        PRIMARY KEY (cd_meta),
    CONSTRAINT fk_meta_local     FOREIGN KEY (cd_local)   REFERENCES tb_local (cd_local) ON DELETE CASCADE,
    CONSTRAINT fk_meta_recurso   FOREIGN KEY (cd_recurso) REFERENCES tb_recurso (cd_recurso),
    CONSTRAINT uq_meta_local_rec UNIQUE (cd_local, cd_recurso),
    CONSTRAINT ck_meta_qt        CHECK (qt_meta_mensal > 0)
);
COMMENT ON TABLE tb_meta IS 'Associativa N:N entre local e recurso: teto mensal de desperdicio que o local se propoe a respeitar para cada recurso';

-- ---------------------------------------------------------------------
-- SEQUENCES (iniciam em 100 para nao colidir com os ids dos INSERTs do script completo)
-- ---------------------------------------------------------------------
CREATE SEQUENCE sq_tb_local    START WITH 100 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE sq_tb_recurso  START WITH 100 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE sq_tb_causa    START WITH 100 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE sq_tb_dica     START WITH 100 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE sq_tb_registro START WITH 100 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE sq_tb_meta     START WITH 100 INCREMENT BY 1 NOCACHE NOCYCLE;
