-- =====================================================================
-- OPCIONAL: acentua os textos do banco para ficarem iguais ao front (db.json).
-- Os textos sao escritos com UNISTR('\00C1gua'), entao este arquivo e ASCII puro
-- e nao depende da codificacao do SQL Developer.
--
-- TESTE ANTES: rode  SELECT UNISTR('\00C1gua') FROM dual;
-- Se aparecer a palavra Agua com acento (A com acento agudo) certinho, o banco aceita acentos e voce pode rodar o resto.
-- Se aparecer "?gua" ou caracteres estranhos, NAO rode: o banco da FIAP nao
-- guarda acentos e o front deve acentuar os textos (arquivo src/data/rotulos.ts).
-- Rode com F5 (Run Script) e termine com COMMIT.
-- =====================================================================

UPDATE tb_recurso SET nm_recurso = UNISTR('\00C1gua') WHERE cd_recurso = 1;
UPDATE tb_causa SET ds_causa = UNISTR('Vazamento em torneira, cano, descarga ou reservat\00F3rio') WHERE cd_causa = 1;
UPDATE tb_causa SET ds_causa = UNISTR('Banho acima do tempo necess\00E1rio') WHERE cd_causa = 2;
UPDATE tb_causa SET ds_causa = UNISTR('Torneira aberta durante escova\00E7\00E3o, lou\00E7a ou limpeza') WHERE cd_causa = 3;
UPDATE tb_causa SET ds_causa = UNISTR('Aparelhos em standby ou ligados sem ningu\00E9m usando') WHERE cd_causa = 4;
UPDATE tb_causa SET nm_causa = UNISTR('Ilumina\00E7\00E3o desnecess\00E1ria') WHERE cd_causa = 5;
UPDATE tb_causa SET nm_causa = UNISTR('Climatiza\00E7\00E3o excessiva') WHERE cd_causa = 6;
UPDATE tb_causa SET ds_causa = UNISTR('Comida preparada ou comprada em excesso e n\00E3o consumida') WHERE cd_causa = 7;
UPDATE tb_dica SET ds_dica = UNISTR('Revise torneiras, canos e descargas com frequ\00EAncia. Um pequeno gotejamento cont\00EDnuo desperdi\00E7a \00E1gua o dia todo.') WHERE cd_dica = 1;
UPDATE tb_dica SET nm_titulo = UNISTR('Reaproveite a \00E1gua'), ds_dica = UNISTR('Use a \00E1gua da m\00E1quina de lavar ou da chuva para limpar quintais e cal\00E7adas.') WHERE cd_dica = 3;
UPDATE tb_dica SET nm_titulo = UNISTR('Troque por l\00E2mpadas LED'), ds_dica = UNISTR('L\00E2mpadas LED gastam menos energia e duram mais que as incandescentes.') WHERE cd_dica = 5;
UPDATE tb_dica SET nm_titulo = UNISTR('Planeje compras e por\00E7\00F5es'), ds_dica = UNISTR('Fa\00E7a lista de compras e prepare por\00E7\00F5es proporcionais a quem vai comer.') WHERE cd_dica = 7;
UPDATE tb_dica SET ds_dica = UNISTR('Transforme sobras em novas refei\00E7\00F5es ou doe alimentos ainda pr\00F3prios para consumo.') WHERE cd_dica = 9;
UPDATE tb_local SET nm_local = UNISTR('Casa da Fam\00EDlia Silva') WHERE cd_local = 1;
UPDATE tb_local SET nm_local = UNISTR('Comunidade Vila Esperan\00E7a') WHERE cd_local = 3;
UPDATE tb_local SET nm_local = UNISTR('Associa\00E7\00E3o de Moradores Alto Ipiranga') WHERE cd_local = 4;
UPDATE tb_local SET nm_local = UNISTR('Padaria P\00E3o Quente') WHERE cd_local = 5;
UPDATE tb_local SET nm_local = UNISTR('Mercadinho Bom Pre\00E7o') WHERE cd_local = 7;
UPDATE tb_local SET nm_local = UNISTR('Casa do Jo\00E3o e L\00FAcia') WHERE cd_local = 8;
UPDATE tb_local SET nm_local = UNISTR('Casa da Fam\00EDlia Oliveira'), nm_cidade = UNISTR('S\00E3o Paulo'), nm_bairro = UNISTR('Tatuap\00E9') WHERE cd_local = 9;
UPDATE tb_local SET nm_local = UNISTR('Casa do Seu Ant\00F4nio'), nm_cidade = UNISTR('S\00E3o Paulo') WHERE cd_local = 10;
UPDATE tb_local SET nm_local = UNISTR('Associa\00E7\00E3o de Moradores Vila Nova'), nm_cidade = UNISTR('S\00E3o Paulo') WHERE cd_local = 11;
UPDATE tb_local SET nm_cidade = UNISTR('S\00E3o Paulo') WHERE cd_local = 12;
UPDATE tb_local SET nm_cidade = UNISTR('S\00E3o Paulo') WHERE cd_local = 13;
UPDATE tb_registro SET ds_observacao = UNISTR('Sobra do almo\00E7o') WHERE cd_registro = 4;
UPDATE tb_registro SET ds_observacao = UNISTR('Vazamento recorrente no reservat\00F3rio') WHERE cd_registro = 8;
UPDATE tb_registro SET ds_observacao = UNISTR('Ilumina\00E7\00E3o do sal\00E3o comunit\00E1rio') WHERE cd_registro = 9;
UPDATE tb_registro SET ds_observacao = UNISTR('Equipamentos do centro comunit\00E1rio ligados') WHERE cd_registro = 10;
UPDATE tb_registro SET ds_observacao = UNISTR('Doa\00E7\00F5es mal armazenadas') WHERE cd_registro = 11;
UPDATE tb_registro SET ds_observacao = UNISTR('P\00E3es do dia sem venda') WHERE cd_registro = 13;
UPDATE tb_registro SET ds_observacao = UNISTR('P\00E3es do dia sem venda') WHERE cd_registro = 14;
UPDATE tb_registro SET ds_observacao = UNISTR('Fornos ligados fora do hor\00E1rio') WHERE cd_registro = 15;
UPDATE tb_registro SET ds_observacao = UNISTR('C\00E2mara fria desregulada') WHERE cd_registro = 18;
UPDATE tb_registro SET ds_observacao = UNISTR('P\00E3o vencido') WHERE cd_registro = 23;
UPDATE tb_registro SET ds_observacao = UNISTR('Vazamento no encanamento do sal\00E3o') WHERE cd_registro = 27;
UPDATE tb_registro SET ds_observacao = UNISTR('Ilumina\00E7\00E3o do sal\00E3o comunit\00E1rio') WHERE cd_registro = 28;
UPDATE tb_registro SET ds_observacao = UNISTR('P\00E3es do dia sem venda') WHERE cd_registro = 29;

COMMIT;
