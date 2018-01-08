INSERT INTO User (id, email, password, role_id) VALUES
(1, 'user1@mail.com', 'user1', 'procedure_owner1'),
(2, 'user2@mail.com', 'user2', 'procedure_owner2'),
(3, 'user3@mail.com', 'user3', 'employee');

INSERT INTO Ropa (id, name, owner_id) VALUES
(1, 'User1-Ropa1', 1),
(2, 'Common Ropa', 1),
(3, 'User2 Ropa', 2);

INSERT INTO acl_sid (id, principal, sid) VALUES
(1, 1, 'user1@mail.com'),
(2, 1, 'user2@mail.com'),
(3, 2, 'user3@mail.com');

INSERT INTO acl_class (id, class) VALUES 
(1, 'com.process.demo.model.Ropa');

INSERT INTO acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) VALUES
(1, 1, 1, NULL, 1, 1), -- User1 Ropa object identity
(2, 1, 2, NULL, 1, 1), -- Common Ropa object identity
(3, 1, 3, NULL, 2, 1); -- User2 Ropa object identity

INSERT INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES
(1, 1, 0, 1, 2, 1, 0, 0),  -- user1@mail.com has Write permission for User1 Ropa
(2, 2, 0, 1, 8, 1, 0, 0),  -- user1@mail.com has Delete permission for Common Ropa
(3, 3, 0, 1, 1, 1, 0, 0),  -- user1@mail.com has Read permission for User2 Ropa
(4, 2, 1, 2, 1,  1, 0, 0),  -- user2@mail.com has Read permission for Common Ropa
(5, 3, 1, 2, 16, 1, 0, 0),  -- user2@mail.com has Admin permission for User2 Ropa
(6, 1, 1, 3, 1,  1, 0, 0),  -- user3@mail.com has Read permission for User1 Ropa
(7, 2, 2, 3, 1,  1, 0, 0),  -- user3@mail.com has Read permission for Common Ropa
(8, 3, 2, 3, 1,  1, 0, 0);  -- user3@mail.com has Read permission for User2 Ropa


