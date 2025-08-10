# Transaction management

## Transaction manager

The transaction manager is responsible for managing transactions in the system. It provides methods to begin, commit,
and rollback transactions. The transaction manager is used to ensure that all operations within a transaction are
completed successfully before committing the changes to the database.

- DatasourceTransactionManager: This is the default transaction manager that uses the datasource to manage transactions.
- JtaTransactionManager: This transaction manager is used for managing transactions in a distributed environment.
- HibernateTransactionManager: This transaction manager is used for managing transactions in a Hibernate environment.
- JpaTransactionManager: This transaction manager is used for managing transactions in a JPA environment.

## Propagation

Propagation defines how transactions are handled in relation to each other. It determines whether a new transaction
should be created or if the current transaction should be used. The following propagation types are available:

- REQUIRED: This is the default propagation type. If a transaction already exists, it will be used. If not, a new
  transaction will be created.
- REQUIRES_NEW: A new transaction will always be created, regardless of whether a transaction
- NESTED: A new transaction will be created, but it will be nested within the current transaction. If the current
  transaction is rolled back, the nested transaction will also be rolled back.
- SUPPORTS: If a transaction exists, it will be used. If not, the operation
- NOT_SUPPORTED: The operation will not be executed within a transaction. If a transaction exists, it will be
  suspended.
- NEVER: The operation will not be executed within a transaction. If a transaction exists, an
- MANDATORY: The operation will be executed within a transaction. If no transaction exists, an exception will be thrown.

Default propagation type is REQUIRED.