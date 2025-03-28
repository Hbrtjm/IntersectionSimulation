# IntersectionSimulation

## Overview

This project simulates an intelligent traffic light system at a 4-way intersection. The goal is to design and implement a system that adjusts light cycles based on traffic intensity on each road. The system operates via commands in JSON format, where each command dictates the action of vehicles at the intersection. This is a recruitment project.

---

## Technologies Used

### Backend
- **Kotlin** with **Ktor** for the backend simulation logic.
- **Docker**: Created a docker image for the application.

### Frontend
- **React** (set up with Vite) for the frontend interface. It allows basic visualization of the simulation, though not fully operational yet.
- **Docker**: Created a docker image for the application.

### Testing
- **Backend Testing**: Was done using **JUnit**

---

## Usage

### Backend
To run the backend server run Application.kt.

### FileExecutor
To run the simulation from a *.json file, you can execute the program using the `FileExecutor.kt` file. The application requires an input file and optionally an output file as arguments, as shown below:
   ```bash
   java -jar FileExecutor.jar inputFile [outputFile]
