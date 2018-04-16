# Mashed Tomatoes 
A better, rotten tomatoes.

## Installation
This project requires Java and Maven to run.
Optionally, frontend development work uses NodeJS.


Verify that Java, Maven and NodeJS (only frontend devs) are installed.

```
java -version
javac -version
mvn -version
nodejs --version
```

In the project root, run this command to install dependencies.

```
mvn install
```

## Usage
All development code is written in the src/ folder.
To start a local Tomcat server run (defaults to port 8080):

```
mvn exec:java
```

See this link for changing MySQL server to UTF-8
https://stackoverflow.com/questions/30061227/mysql-tables-are-not-utf-8-even-though-i-instructed-hibernate-to-do-create-them
