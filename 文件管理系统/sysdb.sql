use `filesys`;
-- Permissions Table
CREATE TABLE Permissions (
    PermissionID INT PRIMARY KEY AUTO_INCREMENT,
    PermissionName VARCHAR(255) NOT NULL
);
CREATE TABLE RolePermissions (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    RoleID INT NOT NULL,
    RoleName VARCHAR(255) NOT NULL,
    PermissionID INT NOT NULL,
    PermissionName VARCHAR(255) NOT NULL
);
-- Document View Time Table
CREATE TABLE DocumentViewTimes (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    DocumentID INT NOT NULL,
    DocumentName VARCHAR(255) NOT NULL,
    ViewUserID INT NOT NULL,
    ViewUserName VARCHAR(255) NOT NULL,
    ViewTime DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Users (
    Password VARCHAR(255) NOT NULL,
    UserID INT PRIMARY KEY AUTO_INCREMENT,
    DepartmentID INT NOT NULL,
    UserName VARCHAR(255) NOT NULL,
    Email VARCHAR(255) NOT NULL,
    PhoneNumber VARCHAR(20) NOT NULL,
    Gender CHAR(1) NOT NULL,
    AvatarURL VARCHAR(255),
    AccountStatus TINYINT(1) NOT NULL,
    CreateTime DATETIME DEFAULT CURRENT_TIMESTAMP,
    LastLoginIP VARCHAR(45),
    LastLoginTime DATETIME,
    Creator VARCHAR(255),
    Updater VARCHAR(255),
    UpdateTime DATETIME,
    Remarks TEXT,
    DepartmentName VARCHAR(255),
    UserType TINYINT(1) NOT NULL
);
-- Documents Table
CREATE TABLE Documents (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    DocumentName VARCHAR(255) NOT NULL,
    UploadUser VARCHAR(255) NOT NULL,
    UploadTime DATETIME DEFAULT CURRENT_TIMESTAMP,
    DocumentStatus TINYINT(1) NOT NULL,
    DocumentCategory VARCHAR(255),
    Keywords VARCHAR(255),
    ViewCount INT DEFAULT 0,
    DocumentAttachmentURL VARCHAR(255),
    DocumentPermission TINYINT(1) NOT NULL,
    VisibleDepartmentID INT,
    VisibleUserID INT
);

-- Roles Table
CREATE TABLE Roles (
    RoleID INT PRIMARY KEY AUTO_INCREMENT,
    RoleName VARCHAR(255) NOT NULL,
    RoleStatus TINYINT(1) NOT NULL,
    CreateTime DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- Departments Table
CREATE TABLE Departments (
    DepartmentName VARCHAR(255) NOT NULL,
    DepartmentStatus TINYINT(1) NOT NULL,
	ParentDepartment VARCHAR(255),
	ParentDepartmentID INT,
    DepartmentID INT PRIMARY KEY AUTO_INCREMENT,
    CreateTime DATETIME DEFAULT CURRENT_TIMESTAMP,
    TotalMembers INT NOT NULL
);


