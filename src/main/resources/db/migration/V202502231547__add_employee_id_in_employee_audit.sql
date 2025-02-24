-- corrigindo a falta da coluna employee_id na db
ALTER TABLE employees_audit ADD COLUMN employee_id BIGINT NOT NULL AFTER id;
-- o after id indica que a coluna virá logo depois da coluna id em employees_audit