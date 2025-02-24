DELIMITER $
-- a cada update na tabela employees esse trigger será acionado
CREATE TRIGGER trg_employee_audit_update BEFORE UPDATE ON employees
FOR EACH ROW
BEGIN
    INSERT INTO employees_audit (employee_id, name, old_name, salary, old_salary, birthday, old_birthday, operation)
    VALUES (NEW.id, NEW.name, OLD.name, NEW.salary, OLD.salary, NEW.birthday, OLD.birthday, 'U') ;
    -- o 'U' indica que a operation é um update
END $
