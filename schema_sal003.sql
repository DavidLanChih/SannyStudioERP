use sannystudio;
create table sal003 (
	s003_Billno int primary key not null,
    s003_Name char(20) not null,
	s003_Sex char(10) not null,
    s003_BirthDate date null,
	s003_Phone char(10) not null,
	s003_ServiceItem char(4) not null,
	s003_SalePrice int not null,
	s003_CreateDate date not null,
	s003_Memo varchar(200) 
);