-- use sannystudio; 
create table sannystudio.inv001 (
i001_Code          char(6)  not NULL  primary key ,
i001_Name          char(50) not NULL  ,
i001_Kind          char(50)  not NULL  ,
i001_NowInventory  int      NULL    ,
i001_CreateDate    date     not NULL 
);