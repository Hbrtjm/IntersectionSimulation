import "./Styles/Car.css";
import { RoadProps } from "./Props/RoadProps";
import Intersection from "./Intersection";
import { IntersectionProps } from "./Props/IntersectionProps";
import { useState } from "react";
import { CarProps } from "./Props/CarProps";
import axios from "axios";

function choose(choices: string[]) {
  var index = Math.floor(Math.random() * choices.length);
  return choices[index];
}

function App() {
  const [northRoads, setNorthRoads] = useState<RoadProps[]>([
    { id: "North_1", addCar: () => {}, direction: "North", cars: [{ id: "1", color:"green" , startDirection: "north", exitDirection: "east" }]},
  ]);

  const [westRoads, setWestRoads] = useState<RoadProps[]>([
    { id: "West_1", addCar: () => {}, direction: "West", cars: [{ id: "2", color:"green" , startDirection: "west", exitDirection: "east"  }]},
  ]);

  const [eastRoads, setEastRoads] = useState<RoadProps[]>([
    { id: "East_1", addCar: () => {}, direction: "East", cars: [{ id: "3", color:"green" , startDirection: "east", exitDirection: "east"  }]},
  ]);

  const [southRoads, setSouthRoads] = useState<RoadProps[]>([
    { id: "South_1", addCar: () => {}, direction: "South", cars: [{ id: "4", color:"green" , startDirection: "south", exitDirection: "east"  }]},
  ]);

  const addRoad = (direction: "north" | "south" | "east" | "west") => {
    const newRoad: RoadProps = {
      id: `${direction}_${Date.now()}`,
      addCar: () => {},
      direction,
      cars: []
    };
    if (direction === "north") setNorthRoads([...northRoads, newRoad]);
    if (direction === "south") setSouthRoads([...southRoads, newRoad]);
    if (direction === "east") setEastRoads([...eastRoads, newRoad]);
    if (direction === "west") setWestRoads([...westRoads, newRoad]);
  };

  const step = async () => {
    try {
      const context = {        
        commands : 
        {
          type: "step",
        }
      };
  
      const response = await axios.post(
        '/api/commands',
        JSON.stringify(context),
        {
          headers: { "Content-Type": "application/json" },
        }
      );
  
      if (response.status === 200) {
        const leftVehicles: string[] = response.data["leftVehicles"];
        if (leftVehicles) // I've gotten empty responses, I should configure the routing, but I don't have time anymore
          setNorthRoads((prevRoads) =>
            prevRoads.map((road) => ({
              ...road,
              cars: road.cars.filter((car) => !leftVehicles.includes(car.id)),
            }))
          );
      }
    } catch (error) {
      console.error("Error in step:", error);
      return false;
    }
  };
  
  const pushNewCar = async (car: CarProps): Promise<boolean> => {
    try {
      const context = {
        commands : 
        {
        type: "addVehicle",
        vehicleId: car.id,
        startRoad: car.startDirection,
        endRoad: car.exitDirection,
        }
      };
  
      const response = await axios.post(
        '/api/commands',
        JSON.stringify(context),
        {
          headers: { "Content-Type": "application/json" },
        }
      );
  
      return response.status === 200;
    } catch (error) {
      console.error("Error adding vehicle:", error);
      return false;
    }
  };

  const addCarToRoad = async (roadId: string, direction: "north" | "south" | "east" | "west") => {
    const updateRoads = async (roads: RoadProps[]) => {
      return Promise.all(roads.map(async (road) => { 
        if (road.id === roadId) {
          const newCar: CarProps = {
            id: `${road.id}_car${road.cars.length + 1}`,
            color: choose(["red", "orange", "blue", "yellow", "green"]) as ("red" | "orange" | "blue" | "yellow" | "green"),
            startDirection: road.id.split("_")[0] as "north" | "south" | "east" | "west",
            exitDirection: choose(["north", "south", "east", "west"]) as "north" | "south" | "east" | "west", // Randomized, because I didn't have time to implement the arrows
          };
  
          const addedOrNot = await pushNewCar(newCar);
          if (addedOrNot) {
            return { ...road, cars: [...road.cars, newCar] };
          }
        }
        return road;
      }));
    };
  
    if (direction === "north") setNorthRoads(await updateRoads(northRoads));
    if (direction === "south") setSouthRoads(await updateRoads(southRoads));
    if (direction === "east") setEastRoads(await updateRoads(eastRoads));
    if (direction === "west") setWestRoads(await updateRoads(westRoads));
  };

  const roadsDirections: IntersectionProps["roadsDirections"] = {
    north: northRoads,
    east: eastRoads,
    west: westRoads,
    south: southRoads,
  };

  return (
    <div>
      <h1 className="text-center">Intersection Simulation</h1>
      <Intersection roadsDirections={roadsDirections} step={step} addRoad={addRoad} addCarToRoad={addCarToRoad} />
    </div>
  );
}

export default App;
