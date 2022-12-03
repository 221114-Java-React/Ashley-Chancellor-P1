# Project 1: Mock Employee Reimbursement System

## Phase 1

### Introduction
* Ashley Chancellor
* Java React Full Stack Developer
* Revature

### About this project
* API that supports mock expense reimbursement system
* Manage process of reimbursing employees for expenses incurred while on company time
* Conforms to RESTful architecture constraints
* Demonstration of back-end API implementation
* Exhibition of working knowledge with Java, PostGreSql, other tech

### User story
#### Supported features
**Prospective users**
* Request registration with system

**All registered employees**
* Log in
* Submit new reimbursement requests
* View past tickets, including pending requests

**Finance managers**
* View all employee reimbursement requests, including resolved tickets
* Approve and deny requests for expense reimbursement

**Administrators**
* Set user roles for all employees
* Approve and deny registration requests
* Deactivate and reactivate user accounts
* Reset a registered user's password

##### System Use Case Diagrams
![System Use Case Diagrams](https://raw.githubusercontent.com/220207-java-enterprise/assignments/main/foundations-project/imgs/ERS%20Use%20Case%20Diagram.png)

##### Reimbursement Status State Flow
![Reimbursement Status State Flow](https://raw.githubusercontent.com/220207-java-enterprise/assignments/main/foundations-project/imgs/ERS%20State%20Flow%20Diagram.png)

### Employee Relational Database
#### 3rd Normal Form
Levels of normalization:
1. All data must be atomic, should have unique identifier (1NF)
2. No partial dependencies – all data must be identified by single column (2NF)
3. No transitive dependencies – no column is dependent on column that is not PK (3NF)

##### Relational Data Model
![Relational Model](https://github.com/220207-java-enterprise/assignments/blob/main/foundations-project/imgs/ERS%20Relational%20Model.png)

### RESTful architecture
#### What is a REST API?
* **RE**presentational **S**tate **T**ransfer
* **A**pplication **P**rogramming **I**nterface conforming to set of architectural constraints
* Allows for interaction with RESTful web services
* As opposed to **SOAP** (**S**imple **O**bject **A**ccess **P**rotocol)

#### How does my project conform to REST?
1. **Uniform interface (UI):** All resources are uniquely identifiable through single URL (http://localhost:8080/ers)
   * Manipuating resources only possible by using underlying methods of network protocol (POST, GET, PUT) with HTTP
2. **Client–server-based:** client & server are decoupled
   * UI & request-gathering concerns are client's domain
   * Data access, workload management, security are server's domain
3. **Stateless:** All client–server operations do not store session state
   * All state management that is required is on client end, not server's
4. **RESTful resource caching:** All resources allow caching unless explicitly indicated that caching is not possible
   * GET requests cachable by default
   * POST/PUT requests not cacheable
5. **Layered:** Multiple layers of services
6. **Code on demand:** Server sends back static representations of resources in JSON form

#### Richardson Maturity Model
![Richardson Maturity Model](https://camo.githubusercontent.com/a6f22e2c3ab71e9091d345b82e56baa4e5515cdb64345415b0a89179b6386080/68747470733a2f2f7265737466756c6170692e6e65742f77702d636f6e74656e742f75706c6f6164732f52696368617264736f6e2d4d617475726974792d4d6f64656c2e6a7067)

#### API maturity level
Level 2: multiple URI (Uniform Resource Identifier)-based resources & verbs

### Layers
* Models – instantiated class objects; relates to DB tables (User, UserRole, Reimbursement, etc)
* DAOs (database access objects) – return data from DB (UserDAO, UserRoleDAO, ReimbursementDAO, etc)
* Services – validate & retrieve data from DAOs (UserService, ReimbursementService, TokenService)
* Handlers – handle HTTP verbs & endpoints (UserHandler, ReimbursementHandler, AuthHandler)
* Router – maps handled HTTP verbs/endpoints to Javalin app

⢀⡴⠑⡄⠀⠀⠀⠀⠀⠀⠀⣀⣀⣤⣤⣤⣀⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀                                                                                      
⠸⡇⠀⠿⡀⠀⠀⠀⣀⡴⢿⣿⣿⣿⣿⣿⣿⣿⣷⣦⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀                                                                                      
⠀⠀⠀⠀⠑⢄⣠⠾⠁⣀⣄⡈⠙⣿⣿⣿⣿⣿⣿⣿⣿⣆⠀⠀⠀⠀⠀⠀⠀⠀                                                                                      
⠀⠀⠀⠀⢀⡀⠁⠀⠀⠈⠙⠛⠂⠈⣿⣿⣿⣿⣿⠿⡿⢿⣆⠀⠀⠀⠀⠀⠀⠀                                                                                      
⠀⠀⠀⢀⡾⣁⣀⠀⠴⠂⠙⣗⡀⠀⢻⣿⣿⠭⢤⣴⣦⣤⣹⠀⠀⠀⢀⢴⣶⣆                                                                                      
⠀⠀⢀⣾⣿⣿⣿⣷⣮⣽⣾⣿⣥⣴⣿⣿⡿⢂⠔⢚⡿⢿⣿⣦⣴⣾⠁⠸⣼⡿                                                                                      
⠀⢀⡞⠁⠙⠻⠿⠟⠉⠀⠛⢹⣿⣿⣿⣿⣿⣌⢤⣼⣿⣾⣿⡟⠉⠀⠀⠀⠀⠀                                                                                      
⠀⣾⣷⣶⠇⠀⠀⣤⣄⣀⡀⠈⠻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡇⠀⠀⠀⠀⠀⠀                                                                                      
⠀⠉⠈⠉⠀⠀⢦⡈⢻⣿⣿⣿⣶⣶⣶⣶⣤⣽⡹⣿⣿⣿⣿⡇⠀⠀⠀⠀⠀⠀                                                                                      
⠀⠀⠀⠀⠀⠀⠀⠉⠲⣽⡻⢿⣿⣿⣿⣿⣿⣿⣷⣜⣿⣿⣿⡇⠀⠀⠀⠀⠀⠀                                                                                      
⠀⠀⠀⠀⠀⠀⠀⠀⢸⣿⣿⣷⣶⣮⣭⣽⣿⣿⣿⣿⣿⣿⣿⠀⠀⠀⠀⠀⠀⠀                                                                                      
⠀⠀⠀⠀⠀⠀⣀⣀⣈⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠇⠀⠀⠀⠀⠀⠀⠀                                                                                      
⠀⠀⠀⠀⠀⠀⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠃⠀⠀⠀⠀⠀⠀⠀⠀                                                                                      
⠀⠀⠀⠀⠀⠀⠀⠹⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⠟⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀                                                                                      
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⠛⠻⠿⠿⠿⠿⠛⠉                                                                                                    

### Tech stack
* Java SDK 8 – coding for ERS API
* IntelliJ – IDE
* Git – version control tool
* PostGreSQL 10 – database
* Docker – hosts PostGres via virtual machine
* DBeaver – database tool/user interface
* Apache Maven – dependencies
* JDBC – connect code to database
* Javalin – web server framework
* JSON Web Tokens – used to authenticate & identify users
* JUnit – implements testing for Java code
* Mockito – mocking framework for testing
* Postman – test endpoints of API
