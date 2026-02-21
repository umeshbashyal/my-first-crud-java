
# mysqlconection — Simple Java CRUD (MySQL + Frontend + REST)

## Summary
This is a simple **Spring Boot** application that performs **CRUD** operations on a MySQL database.

It supports:

- **Frontend (HTML/Thymeleaf)** pages for Create/List/Edit/Delete.
- **REST API** endpoints (`/api/...`) for JSON-based create/read/update/delete.

The sample entity used is `Person` (`id`, `name`, `email`).

## Tech stack / Versions

- **Java (project target)**: `17` (configured in `pom.xml`)
- **Spring Boot**: `3.3.2` (configured in `pom.xml`)
- **Build tool**: Maven
- **Database**: MySQL
- **ORM**: Spring Data JPA (Hibernate)
- **Templates**: Thymeleaf

Main dependencies (from `pom.xml`):

- `spring-boot-starter-web`
- `spring-boot-starter-thymeleaf`
- `spring-boot-starter-data-jpa`
- `mysql-connector-j` (runtime)
- `spring-boot-starter-validation`

## Project structure (important files)

- `src/main/java/com/example/mysqlconection/MysqlconectionApplication.java`
  - Spring Boot entry point.
- `src/main/resources/application.properties`
  - MySQL connection config + JPA settings.
- `src/main/java/com/example/mysqlconection/model/Person.java`
  - JPA entity mapping to table `people`.
- `src/main/java/com/example/mysqlconection/repository/PersonRepository.java`
  - `JpaRepository<Person, Long>` (Spring auto-generates implementation).
- `src/main/java/com/example/mysqlconection/service/PersonService.java`
  - Business layer calling repository.
- `src/main/java/com/example/mysqlconection/controller/PersonWebController.java`
  - MVC controller for HTML pages.
- `src/main/java/com/example/mysqlconection/controller/PersonRestController.java`
  - REST controller for JSON API.
- `src/main/resources/templates/people/list.html`
  - List page.
- `src/main/resources/templates/people/form.html`
  - Create/Edit form.

## Database setup

1. Ensure MySQL is running on `localhost:3306`.
2. Create database:

```sql
CREATE DATABASE crud_db;
```

The table is created/updated automatically because:

- `spring.jpa.hibernate.ddl-auto=update`

### Connection configuration
From `application.properties`:

- **DB URL**: `jdbc:mysql://localhost:3306/crud_db...`
- **Username**: `root`
- **Password**: `Ujan`

## Run the application

### Option 1: IntelliJ IDEA (recommended)

- Open this folder as a project: `pra_practice/mysqlconection`
- Run `MysqlconectionApplication.java` (green ▶ next to `main`)

### Option 2: Maven CLI

If Maven is installed and available in PATH:

```bash
mvn spring-boot:run
if you want to close the server just press ctrl+c .
```

## URLs

### Frontend (HTML)

- Home redirect:
  - `GET http://localhost:8080/` → redirects to `/people`
- People UI:
  - `GET http://localhost:8080/people`
  - `GET http://localhost:8080/people/new`
  - `GET http://localhost:8080/people/{id}/edit`
  - `POST http://localhost:8080/people` (create)
  - `POST http://localhost:8080/people/{id}` (update)
  - `POST http://localhost:8080/people/{id}/delete` (delete)

### REST API (JSON)

Base path: `/api/people`

- `GET /api/people` → list
- `GET /api/people/{id}` → single
- `POST /api/people` → create
- `PUT /api/people/{id}` → update
- `DELETE /api/people/{id}` → delete

Example create request:

```http
POST /api/people
Content-Type: application/json

{
  "name": "Umesh",
  "email": "umesh@example.com"
}
```

## Request flow (how the project works)

### A) Frontend flow (HTML → Controller → Service → Repository → DB)

1. User opens `/people`.
2. `PersonWebController.list()` calls `PersonService.findAll()`.
3. `PersonService.findAll()` calls `PersonRepository.findAll()`.
4. JPA/Hibernate fetches data from MySQL and returns a `List<Person>`.
5. Controller returns view `people/list` → Thymeleaf renders `templates/people/list.html`.

**Create**:

1. User opens `/people/new`.
2. Form submits `POST /people`.
3. `@Valid` triggers validations defined in `Person` (`@NotBlank`, `@Email`).
4. On success, `PersonService.create()` calls `personRepository.save(person)`.
5. Hibernate performs `INSERT` into table `people`.
6. App redirects back to `/people`.

**Update**:

1. User opens `/people/{id}/edit`.
2. Form submits `POST /people/{id}`.
3. `PersonService.update(id, input)` loads existing record, sets fields, then saves.
4. Hibernate performs `UPDATE`.

**Delete**:

1. User clicks delete (form submit `POST /people/{id}/delete`).
2. `PersonService.delete(id)` calls `personRepository.deleteById(id)`.
3. Hibernate performs `DELETE`.

### B) REST flow (JSON → REST Controller → Service → Repository → DB)

1. Client calls `/api/people` endpoints.
2. `PersonRestController` receives JSON and converts it to `Person`.
3. It calls `PersonService` methods.
4. Service calls `PersonRepository`.
5. JPA/Hibernate executes SQL via MySQL driver.

## Notes

- If you see `mvn is not recognized`, Maven is not installed or not added to PATH. Using IntelliJ IDEA run configuration is the easiest way to run the project.
