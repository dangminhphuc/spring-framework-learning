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

## Isolation

### What is Transaction Isolation?

In simple terms, isolation determines how much a transaction is separated from other concurrent transactions. A higher
isolation level provides more protection against concurrency side effects but
can reduce performance because it often involves more database-level locking.

The main problems that isolation levels solve are:

1. Dirty Reads: One transaction reads changes made by another transaction that has not yet been
   committed. If the other transaction rolls back, the first transaction has read "dirty" data that
   never officially existed.
2. Non-Repeatable Reads: One transaction reads the same row twice but gets different data each time
   because another transaction modified that row and committed the change in between the reads.
3. Phantom Reads: One transaction runs the same query twice but gets a different set of rows each time
   because another transaction has inserted or deleted rows that match the query criteria.

### Spring's Isolation Levels

Spring provides constants in the Isolation enum that map to the standard database isolation levels.

1. `ISOLATION_DEFAULT` (The Default Setting)
    * What it does: This is the default for @Transactional. It doesn't set a specific isolation level;
      instead, it uses the default isolation level of your underlying database (e.g., READ_COMMITTED
      for PostgreSQL, REPEATABLE_READ for MySQL).
    * When to use: In most cases, this is all you need. You trust the database's default behavior.

2. `ISOLATION_READ_UNCOMMITTED`
    * What it does: The lowest level. It allows dirty reads, non-repeatable reads, and phantom reads.
    * When to use: Very rarely. Only if you need high performance and can tolerate reading uncommitted
      data.

3. `ISOLATION_READ_COMMITTED`
    * What it does: Prevents dirty reads. Non-repeatable and phantom reads can still occur.
    * When to use: A good, balanced choice for many applications. It's a common default for many
      databases.

4. `ISOLATION_REPEATABLE_READ`
    * What it does: Prevents dirty reads and non-repeatable reads. Phantom reads can still occur.
    * When to use: When you need to ensure that if you read the same row multiple times in a
      transaction, it will not have been changed by another transaction.

5. `ISOLATION_SERIALIZABLE`
    * What it does: The highest level. It prevents all three problems (dirty, non-repeatable, and
      phantom reads) by essentially running transactions in a sequence.
    * When to use: When data consistency is absolutely critical and you must prevent any kind of
      concurrent interference. This comes at the highest performance cost.