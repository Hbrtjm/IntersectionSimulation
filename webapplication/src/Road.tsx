import React from "react";
import { RoadProps } from "./Props/RoadProps";
import Car from "./Car";
import TrafficLight from "./TrafficLights";

const Road: React.FC<RoadProps> = ({ id, addCar, cars }) => { 
    const maxCars = 5;
    const reducedCars = cars.slice(0,maxCars);
    return ( 
    <div className="flex flex-col h-100 w-20 content-around items-center bg-gray-500 border-t-10 border-white-600" >
        <TrafficLight state="green"/>
            {reducedCars.map((car) => (
                <Car key={id} 
                    color={car.color} 
                    id={car.id} 
                    /> 
            ))}
        <button onClick={addCar} className="text-white bg-gray-800 hover:bg-gray-900 focus:outline-none focus:ring-4 focus:ring-gray-300 font-medium rounded-full text-sm px-5 py-2.5 me-2 mb-2 dark:bg-gray-800 dark:hover:bg-gray-700 dark:focus:ring-gray-700 dark:border-gray-700">+</button>
  </div>
) } ;

export default Road;
