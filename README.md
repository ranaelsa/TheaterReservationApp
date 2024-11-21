# TheaterReservationApp
A project that implements a web-based theater reservation system, made by group 4 for ENSF 614. The client connects to a broser GUI made with ReactJS, which is served by a backend Java spring-boot application. State is maintained through a cloud-hosted MySQL database.

## Instructions to run
In order to run this program, you must have Java and NodeJS installed on your system. 
1. The backend server has been packaged into a single .jar file, simply download run 'group4.jar' to start the server.
2. Next, to run the frontend React app, download and unpack the 'group4Web.zip' file.
3. Open a command console and navigate into the 'frontend' folder unpacked from the zip file. Run the command 'npm install' to initialize the required packages.
4. Run the command 'npm start'. You should now be able to open a browser at http://localhost:3000 to use the app.
5. Our SQL database is hosted on a cloud service and our spring-boot server is already setup to connect to this database on startup. If you instead want to set up a local database using the dump file, you must complete additional steps:
    1. Ensure that Maven and SQL are installed on your system.
    2. Initialize a local database using the 'group4.sql' dump file. Take note of the database URL, username and password.
    3. Download and unpack the 'group4java.zip' and navigate into the 'java-backend' folder.
    4. Open the 'application.properties' file and replace the following variables with your local database information:
    ```
    spring.datasource.url=<replace with your url>
    spring.datasource.username=<replace with your username>
    spring.datasource.password=<replace with your password>
    ```
    5. Open a command console and navigate into this folder, then run 'mvn spring-boot:run' to start the backend server.
