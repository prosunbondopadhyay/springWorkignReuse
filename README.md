# springBootReuse – Single deployable Spring Boot app with reusable libraries (SAP BTP Cloud Foundry ready)

This project shows how to **reuse** functionality from other Spring code **as libraries** and deploy **one final JAR** to **SAP BTP Cloud Foundry**.
It contains:
- `reuseAppLib/libA` – greeting components (+ optional controller)
- `reuseAppLib/LibB` – environment info components
- `MainSpringApp` – the **only runnable** Spring Boot app that depends on both libraries

## Prerequisites
- Java 17
- Maven 3.9+
- Cloud Foundry CLI (`cf`), logged into your BTP subaccount
- Optional: SAP Business Application Studio (BAS) – Java dev space

## Build (root of repo)
```bash
mvn -q clean package
```
This builds all modules and creates **one executable JAR** at:
```
MainSpringApp/target/MainSpringApp-1.0.0.jar
```

## Run locally
From the root:
```bash
# Option A: run the packaged jar
java -jar MainSpringApp/target/MainSpringApp-1.0.0.jar

# Option B: dev mode
mvn -q -pl :MainSpringApp -am spring-boot:run
```
Try:
- http://localhost:8080/demo
- http://localhost:8080/greet?name=Alice  (provided `greeting.web-enabled=true`)

### Customize
Edit `MainSpringApp/src/main/resources/application.yml` or override via env variables:
- `GREETING_TEMPLATE`
- `GREETING_WEB_ENABLED` (true/false)
- `SERVER_PORT`

## Deploy to SAP BTP Cloud Foundry
1. **Login and target your space**
   ```bash
   cf login -a <your-cf-api-endpoint>
   cf target -o <your-org> -s <your-space>
   ```
2. **Build the jar**
   ```bash
   mvn -q -pl :MainSpringApp -am package
   ```
3. **Push using the manifest in MainSpringApp**
   ```bash
   cd MainSpringApp
   cf push
   ```
   - The manifest uses the **SAP Java Buildpack** (`sap_java_buildpack`), sets memory and a **random route** for convenience.
   - Once deployed, the route is printed in the output. Open:
     - `https://<random-route>/demo`
     - `https://<random-route>/greet?name=Alice`

> Tip: You can also push from the repo root:
> ```bash
> cf push -f MainSpringApp/manifest.yml
> ```

## Use in SAP Business Application Studio (BAS)
1. Create/open a **Java** dev space.
2. **File → Upload Project** and select this zip (or clone from your repo).
3. Open an integrated terminal in the root folder and run:
   ```bash
   mvn -q clean package
   mvn -q -pl :MainSpringApp -am spring-boot:run
   ```
   Visit the local port exposed by BAS preview (usually shows a URL in the terminal).
4. To deploy from BAS:
   ```bash
   cf login -a <your-cf-api-endpoint>
   cf target -o <your-org> -s <your-space>
   mvn -q -pl :MainSpringApp -am package
   cf push -f MainSpringApp/manifest.yml
   ```

## Project structure
```
springBootReuse
├── pom.xml
├── MainSpringApp
│   ├── pom.xml
│   ├── manifest.yml
│   └── src
│       ├── main
│       │   ├── java/com/example/main/MainSpringApp.java
│       │   └── resources/application.yml
├── reuseAppLib
│   ├── libA
│   │   ├── pom.xml
│   │   └── src/main/java/com/example/liba/...
│   │       └── resources/META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports
│   └── LibB
│       ├── pom.xml
│       └── src/main/java/com/example/libb/...
│           └── resources/META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports
```

## Why this works
- `libA` and `LibB` are **plain JARs** with **auto-configuration**. When they’re on the classpath, Spring Boot wires them automatically.
- Only `MainSpringApp` is a Spring Boot application with an embedded server, producing **one deployable file**.
- On Cloud Foundry, the manifest points to that single JAR, and the SAP Java Buildpack runs it.

## Troubleshooting
- **Port already in use locally**: change `server.port` in `application.yml` or set `SERVER_PORT`.
- **Two embedded servers error**: ensure only the main app depends on `spring-boot-starter-web`. Libraries should not.
- **Route not reachable on BTP**: check org/space, security rules, or remove `random-route: true` and map a custom route.
- **Version mismatch**: keep Spring Boot versions aligned via the parent POM.
