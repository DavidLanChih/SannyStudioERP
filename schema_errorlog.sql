use sannystudio;
create table errorlog (
	e_Billno int primary key not null auto_increment,
    e_FormPostion char(100) not null,
    e_ErrorMessage varchar(500) not null,
	e_Date datetime not null
);