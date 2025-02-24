DELIMITER $
-- a cada delete na tabela employees esse trigger será acionado
CREATE TRIGGER trg_employee_audit_delete BEFORE DELETE ON employees
FOR EACH ROW
BEGIN
    INSERT INTO employees_audit (employee_id, name, salary, birthday, operation)
    VALUES (OLD.id, OLD.name, OLD.salary, OLD.birthday, 'D') ;
    -- o 'D' indica que a operation é um delete
END $
