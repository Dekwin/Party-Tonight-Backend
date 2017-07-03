INSERT INTO partymaker2.role VALUES (1, 'ROLE_ADMIN');
INSERT INTO partymaker2.role VALUES (2, 'ROLE_PARTY_MAKER');
INSERT INTO partymaker2.role VALUES (3, 'ROLE_STREET_DANCER');
INSERT INTO partymaker2.role VALUES (4, 'ROLE_SERVICE_TAX');

-- service tax user
delete from partymaker2.user where partymaker2.user.id_role = 4;
INSERT INTO partymaker2.user(billing_email,email,id_role,password,enable,verified) VALUES ('service@billing.com','service@email.com', 4,'0000',false,false);