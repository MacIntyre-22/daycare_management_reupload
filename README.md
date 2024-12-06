# Daycare Management App
#### An app for managing a database of students, guardians, and staff.
### By: Kasey, Simran, Alex, Ben

## Description
Our Daycare Management App is made to manage a database of students, guardians, and staff members of a daycare. 
It contains a page for each of those three main tables and each page contains a table of the information. The pages also includes extra data like
bar graphs and pie charts for information relating to that table. This makes it easy for a manager to have a broad overview of the whole
daycare including the guardians of the students. The app also includes a page for all the relational tables in the database so that information
can be added updated or deleted if needed.

## Getting Started Guide
Using the app is very simple as our user interface is basic and easy to understand. Once signed in with a database to connect to, you will 
be taken to the 
main page of the app. The main page is the students page where you are able to see a table of all students with more information about the 
table at the bottom of the screen.
The three buttons at the bottom of the screen are add, update and delete options based on what page you are on will affect that page's table. 
On the left is an arrow that slides out 
a navigation bar. This navigation bar has more buttons that can display graphs and charts when selected, as well as display or reload the main table.

## Database Schema
### Students
| Column     | Datatype    |
|------------|-------------|
| student_id | INT(11)         |
| first_name | VARCHAR(20) | 
| last_name  | VARCHAR(20) |
| birthdate  | date        | 
| room_id    | INT(11)     | 


### Guardians
| Column      | Datatype    |
|-------------|-------------|
| guardian_id | INT(11)         |
| first_name  | VARCHAR(20) | 
| last_name   | VARCHAR(20) |
| phone       | VARCHAR(15) | 
| email       | VARCHAR(50) |
| city_id     | INT(11)     |
| street_num  | INT(11)     |
| street_name | VARCHAR(20) |

### Staff
| Column      | Datatype     |
|-------------|--------------|
| staff_id    | INT(11)      |
| first_name  | VARCHAR(20)  | 
| last_name   | VARCHAR(20)  |
| wage        | DECIMAL(5,2) | 
| room_id     | INT(11)      |
| position_id | INT(11)      |

### Positions
    Shows Position to id relation
| Column        | Datatype     |
|---------------|--------------|
| position_id   | INT(11)      |
| position_name | VARCHAR(20)  | 

### Citites
    Shows City to id relation
| Column    | Datatype     |
|-----------|--------------|
| city_id   | INT(11)      |
| city_name | VARCHAR(20)  | 

### Guardian Student Relation
    Shows Guardian to Student relation with an id
| Column      | Datatype |
|-------------|----------|
| id          | INT(11)  |
| student_id  | INT(11)  |
| guardian_id | INT(11)  | 