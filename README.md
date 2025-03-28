# IntersectionSimulation

## Overview

This project simulates an intelligent traffic light system at a 4-way intersection. The goal is to design and implement a system that adjusts light cycles based on traffic intensity on each road. The system operates via commands in JSON format, where each command dictates the action of vehicles at the intersection. This is a recruitment project.

---

# Algorithm Overview

The traffic light system is driven by an algorithm designed to prioritize road directions based on traffic intensity while avoiding conflicts between opposite directions.

1. **Traffic Counting and Direction Prioritization:**
   The main controller class is responsible for analyzing traffic data across all roads at the intersection. The task of this controller is to calculate the busiest roads by counting the number of vehicles waiting on each road.

2. **Collision Detection:**
   The algorithm includes a collision detection, so that there are no conflicting green lights for opposite directions. It checks for potential conflicts by analyzing the direction pairs and using a binary mapping system to represent each direction's maneuver across the intersection. If any two road directions overlap (i.e., share the same space on the intersection), the algorithm does not allow both directions to be given a green light. The details of collition detection are written in the 'TrafficCounterController.kt' file.

3. **Vehicle Flow Simulation:**
   Once the busiest road directions are selected, the system steps through the simulation. Vehicles move through the intersection according to the green lights assigned to the highest traffic roads. The system simulates the flow of traffic.

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
To run the backend server, run `Application.kt`.

### FileExecutor
To run the simulation from a *.json file, you can execute the program using the `FileExecutor.kt` file. The application requires an input file and optionally an output file as arguments, as shown below:
   ```bash
   java -jar FileExecutor.jar inputFile [outputFile]
