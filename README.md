# TheaterReservationApp
A project that implements a web-based theater reservation system, made by group 4 for ENSF 614. The client connects to a broser GUI made with ReactJS, which is served by a backend Java spring-boot application. State is maintained through a cloud-hosted MySQL database.

## Instructions to run
In order to run this program, you must have Java and NodeJS installed on your system. 
1. In order to run the backend server, download the 'group4.jar' file and the 'group4Config.zip' file. Unzip the config folder to find a file called 'application.properties'. __Put this file in the same directory as the jar file.__
2. Open a command terminal in the directory containing the 'group4.jar' file and the 'application.properties' file. Run the command ```java -jar group4.jar``` to start the server. If the server stops with an error, please ensure that both files are in the same directory.
2. Next, to run the frontend React app, download and unpack the 'group4Web.zip' file.
3. Open a command console and navigate into the 'frontend' folder unpacked from the zip file. Run the command 'npm install' to initialize the required packages.
4. Run the command 'npm run dev'. You should now be able to open a browser at http://localhost:3000 to use the app.
5. Our SQL database is hosted on a cloud service and our spring-boot server is already setup to connect to this database on startup. If you instead want to set up a local database using the dump file, you must complete additional steps:
    1. Ensure that both Maven and SQL are installed on your system.
    2. Initialize a local database using the 'group4.sql' dump file. Take note of the database URL, username and password.
    3. Download and unpack the 'group4java.zip' file and navigate into the 'java-backend' folder.
    4. Add the 'application.properties' file found in 'group4Config.zip' to this folder and replace the following variables with your local database information:
    ```
    spring.datasource.url=<replace with your url>
    spring.datasource.username=<replace with your username>
    spring.datasource.password=<replace with your password>
    ```
    5. From the 'java-backend' folder, run 'mvn spring-boot:run' from the console to start the backend server.

## File locations
Our backend and frontend directories follow a folder structure defined by Maven and React, respectively. In the backend folder, all .java files and packages are located under src/main/java/com/project/java_backend. In the frontend folder, all React pages are found under src/app and all components under src/components.