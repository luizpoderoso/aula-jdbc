DELIMITER $
-- a cada insert na tabela employees esse trigger será acionado
CREATE TRIGGER trg_employee_audit_insert AFTER INSERT ON employees
FOR EACH ROW
BEGIN
    INSERT INTO employees_audit (employee_id, name, salary, birthday, operation)
    VALUES (NEW.id, NEW.name, NEW.salary, NEW.birthday, 'I') ;
    -- o 'I' indica que a operation é um insert
END $
