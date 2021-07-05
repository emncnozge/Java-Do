CREATE TABLE USERS (
    userId INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY(Start with 1, Increment by 1),
    name VARCHAR(50) NOT NULL,
    surname VARCHAR(50) NOT NULL,
    bornDate DATE NOT NULL,
    email VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    registerDate DATE NOT NULL,
    notebookCount INT NOT NULL,
    noteCount INT NOT NULL,
    lastLoginDate DATE NOT NULL,
    isDeleted BOOLEAN NOT NULL
);
CREATE TABLE NOTEBOOK (
    notebookId INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY(Start with 1, Increment by 1),
    userId INT NOT NULL,
    FOREIGN key (userId) REFERENCES USERS(userId),
    notebookName LONG VARCHAR NOT NULL,
    createdDate DATE NOT NULL,
    lastEdited DATE NOT NULL,
    isDeleted BOOLEAN NOT NULL
);
CREATE TABLE TAG(
    tagId INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY(Start with 1, Increment by 1),
    userId INT NOT NULL,
    FOREIGN KEY (userId) REFERENCES USERS(userId),
    tagName LONG VARCHAR NOT NULL,
    isDeleted BOOLEAN NOT NULL
);
CREATE TABLE REMINDER(
    reminderId INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY(Start with 1, Increment by 1),
    userId INT NOT NULL,
    FOREIGN KEY (userId) REFERENCES USERS(userId),
    reminderName LONG VARCHAR NOT NULL,
    reminderDate DATE NOT NULL,
    reminderContent LONG VARCHAR NOT NULL,
    reminderColor VARCHAR(25) NOT NULL,
    isDeleted BOOLEAN NOT NULL
);
CREATE TABLE NOTE(
    noteId INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY(Start with 1, Increment by 1),
    notebookId INT NOT NULL,
    FOREIGN KEY (notebookId) REFERENCES NOTEBOOK(notebookId),
    userId INT NOT NULL,
    FOREIGN KEY (userId) REFERENCES USERS(userId),
    noteName LONG VARCHAR NOT NULL,
    title LONG VARCHAR NOT NULL,
    tags LONG VARCHAR NOT NULL,
    noteContent LONG VARCHAR NOT NULL,
    createdDate DATE NOT NULL,
    lastEdited DATE NOT NULL,
    isDeleted BOOLEAN NOT NULL
);