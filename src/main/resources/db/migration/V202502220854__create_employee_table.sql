CREATE TABLE employees(
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(150) NOT NULL,
    salary decimal(13,2) NOT NULL,
    birthday TIMESTAMP NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE employees_audit(
    id BIGINT NOT NULL AUTO_INCREMENT,
    old_name VARCHAR(150),
    name VARCHAR(150),
    old_salary decimal(13,2),
    salary decimal(13,2),
    old_birthday TIMESTAMP,
    birthday TIMESTAMP,
    operation CHAR(1),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;