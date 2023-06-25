# CMS-Backend
Contact Management System manages contacts of the users; providing
functionality like create, read, update and delete.

### <u>About</u>
CMS comes with gradle wrapper, java 17 and spring boot 3.1.
It uses data jpa for db operations and H2 in memory db with pre set of schema and data configured int.\n

It Provides following operations
1. Update contact details using id.
2. Search contact using any combination first name, last name, email.
3. Delete contact using id.
4. Create contact.


### <u>Pre-Requisite</u>
1. Java 17

### <u>Local-Run</u>
1. gradlew clean build
2. Navigate to /build/libs
3. java -jar cms-0.0.1-SNAPSHOT.jar

OR

1. gradlew bootrun

### <u>Important URLs</u>
Enable h2-console using property <b>spring.h2.console.enabled=true</b>
1. http://localhost:7016/api/h2-console
2. http://localhost:7016/api/swagger-ui.html