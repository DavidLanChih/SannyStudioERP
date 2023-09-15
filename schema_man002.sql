use sannystudio;
create table man002 (
	m002_No int primary key not null auto_increment,
    m002_Name char(20) not null,
	m002_Sex char(10) not null,
    m002_BirthDate date null,
	m002_Phone char(10) not null,
    m002_ID char(10) null,
    m002_Address nvarchar(100) null
);