Create Schema EMS_db

CREATE TABLE `employee` (
  `employeeId` int(11) NOT NULL AUTO_INCREMENT,
  `FirstName` varchar(45) NOT NULL,
  `LastName` varchar(45) NOT NULL,
  `MiddleName` varchar(45) DEFAULT NULL,
  `Email` varchar(45) NOT NULL,
  `BirthDate` date DEFAULT NULL,
  `JoiningDate` date NOT NULL,
  `LeavingDate` date DEFAULT NULL,
  `ContactNo` varchar(15) NOT NULL,
  `CurrentAddress` varchar(70) NOT NULL,
  `Department` varchar(45) NOT NULL,
  `Designation` varchar(45) NOT NULL,
  `modifieddate` date DEFAULT NULL,
  `createddate` date NOT NULL,
  PRIMARY KEY (`employeeId`),
  UNIQUE KEY `Email_UNIQUE` (`Email`),
  KEY `fk_deptid_idx` (`Department`),
  KEY `fk_designationid_idx` (`Designation`)
) 

CREATE TABLE `project` (
  `ProjectId` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `manager` int(11) NOT NULL,
  `teammember` int(11) DEFAULT '1',
  PRIMARY KEY (`ProjectId`),
  KEY `fk_manager_idx` (`manager`),
  CONSTRAINT `fk_manager` FOREIGN KEY (`manager`) REFERENCES `employee` (`employeeId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) 

CREATE TABLE `emptask` (
  `taskid` int(11) NOT NULL AUTO_INCREMENT,
  `EmployeeId` int(11) NOT NULL,
  `projectid` int(11) NOT NULL,
  `description` varchar(45) DEFAULT NULL,
  `startdate` date NOT NULL,
  `duedate` date NOT NULL,
  `actualstartdate` date DEFAULT NULL,
  `actualenddate` date DEFAULT NULL,
  `status` varchar(45) NOT NULL,
  `reasonformissingduedate` varchar(70) DEFAULT NULL,
  `category` varchar(45) DEFAULT NULL,
  `createddate` date DEFAULT NULL,
  `modifieddate` date DEFAULT NULL,
  PRIMARY KEY (`taskid`),
  KEY `fk_empid_idx` (`EmployeeId`),
  KEY `fk_projectid_idx` (`projectid`),
  CONSTRAINT `fk_projectid` FOREIGN KEY (`projectid`) REFERENCES `project` (`ProjectId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_empid` FOREIGN KEY (`EmployeeId`) REFERENCES `employee` (`employeeId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) 


INSERT INTO `ems_db`.`project`
(
`name`,
`manager`,
`teammember`)
VALUES
(
'Video Streaming',
12,
1);
INSERT INTO `ems_db`.`project`
(
`name`,
`manager`,
`teammember`)
VALUES
(
'Room wizard',
17,
7);
INSERT INTO `ems_db`.`project`
(
`name`,
`manager`,
`teammember`)
VALUES
(
'Payroll System',
14,
5);