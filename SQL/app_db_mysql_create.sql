CREATE TABLE role (
	role_id BIGINT NOT NULL,
	name varchar(20) NOT NULL UNIQUE,
	PRIMARY KEY (role_id)
);
CREATE TABLE appuser (
	user_id BIGINT NOT NULL,
	username varchar(20) NOT NULL UNIQUE,
	password varchar(40) NOT NULL,
	email varchar(40) NOT NULL UNIQUE,
	reg_date DATE NOT NULL,
	last_activity DATETIME NOT NULL,
	role_id BIGINT NOT NULL,
  wallet Double(16,2) NOT NULL
	PRIMARY KEY (user_id),
	FOREIGN KEY (role_id) REFERENCES role(role_id)
);


CREATE TABLE userdetails (
	user_id BIGINT NOT NULL,
	first_name varchar(20) NOT NULL,
	last_name varchar(20) NOT NULL,
	adress varchar(50),
	phone varchar(15),
	PRIMARY KEY (user_id),
	FOREIGN KEY (user_id) REFERENCES appuser(user_id)
);

CREATE TABLE bank (
	bank_id BIGINT NOT NULL,
	name varchar(20) NOT NULL UNIQUE,
	deposit_persent INT NOT NULL,
	credit_persent INT NOT NULL,
	PRIMARY KEY (bank_id)
);

CREATE TABLE account (
	account_id BIGINT NOT NULL,
	user_id BIGINT NOT NULL,
	bank_id BIGINT NOT NULL,
	value DOUBLE(16,2) NOT NULL,
	open_date DATE NOT NULL,
	PRIMARY KEY (account_id),
	FOREIGN KEY (user_id) REFERENCES appuser(user_id),
	FOREIGN KEY (bank_id) REFERENCES bank(bank_id)
);

CREATE TABLE transaction (
	transaction_id BIGINT NOT NULL,
	account_id BIGINT NOT NULL,
	value DOUBLE(16,2) NOT NULL,
	date DATETIME NOT NULL,
	PRIMARY KEY (transaction_id),
	FOREIGN KEY (account_id) REFERENCES account(account_id)
);

CREATE TABLE notification (
	notification_id BIGINT NOT NULL,
	user_id BIGINT NOT NULL,
	text TEXT NOT NULL,
	date DATETIME NOT NULL,
	PRIMARY KEY (notification_id),
	FOREIGN KEY (user_id) REFERENCES appuser(user_id)
);
