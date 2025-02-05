ALTER TABLE medicos ADD COLUMN ativo tinyint;

update medicos set ativo = 1;