INSERT INTO afdeling (code, naam) VALUES
	('CAR', 'Cardiologie'),
	('KRA', 'Kraamafdeling'),
	('ONC', 'Oncologie'),
	('GER', 'Geriatrie');


INSERT INTO patient (code, opname, ontslag, afdeling_code) VALUES
 ('P001', '2020-06-11 14:03:00.0', NULL, 'KRA'),
 ('P002', '2020-05-05 07:34:00.0', NULL, 'GER'),
 ('P003', '2020-06-12 09:18:00.0', NULL, 'KRA'),
 ('P004', '2020-06-10 22:43:00.0', NULL, 'ONC'),
 ('P005', '2020-06-08 18:23:00.0', '2020-06-12 14:02:00', 'KRA'),
 ('P006', '2020-06-11 16:32:00.0', NULL, 'KRA');
