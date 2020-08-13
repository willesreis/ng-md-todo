CREATE TABLE Task (
    id INTEGER NOT NULL IDENTITY, 
    checked BOOL NULL, 
    toDay BOOL NULL, 
    toImportant BOOL NULL, 
    description VARCHAR(100) NULL
);

CREATE TABLE Step (
    id INTEGER NOT NULL IDENTITY, 
    checked BOOL NULL, 
    description VARCHAR(100) NULL
);