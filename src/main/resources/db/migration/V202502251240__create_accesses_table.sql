CREATE TABLE accesses(
    employee_id BIGINT NOT NULL,
    module_id BIGINT NOT NULL,
    PRIMARY KEY (employee_id, module_id),
    CONSTRAINT fk_accesses_employee FOREIGN KEY (employee_id) REFERENCES employees(id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_accesses_module FOREIGN KEY (module_id) REFERENCES modules(id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
