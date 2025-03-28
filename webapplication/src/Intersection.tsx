import Road from "./Road";
import { IntersectionProps } from "./Props/IntersectionProps";
import "./Styles/Intersection.css";
import Divider from "./Divider";
import EmptyRoad from "./EmptyRoad";

// This mess below can be heavily simplified, but I don't have time now

const Intersection: React.FC<IntersectionProps & { step: () => void; addRoad: (dir: "north" | "south" | "east" | "west") => void; addCarToRoad: (roadId: string, dir: "north" | "south" | "east" | "west") => void; }> = ({ roadsDirections: { north, east, west, south }, step, addRoad, addCarToRoad }) => { 
    return (
  <div id="intersection" className="grid grid-cols-3 p-4 place-items-center">
    <div className="bg-green-500 aspect-square"></div>
    <div className="flex flex-row aspect-square" style={{ transform: `rotate(180deg)` }}>
      <button onClick={() => addRoad("north")} className="text-white bg-gray-800 hover:bg-gray-900 focus:outline-none focus:ring-4 focus:ring-gray-300 font-medium rounded-lg text-sm px-5 py-2.5 me-2 mb-2 dark:bg-gray-800 dark:hover:bg-gray-700 dark:focus:ring-gray-700 dark:border-gray-700">+</button>
      {north.map((road) => (
        <Road key={road.id} {...road} addCar={() => addCarToRoad(road.id, "north")} />
      ))}
      <Divider />
      {south.map((_) => (
        <EmptyRoad  />
      ))}
    </div>
    <div className="bg-green-500 flex flex-row aspect-square"></div>
    <div className="flex flex-row aspect-square flex justify-end" style={{ transform: `rotate(90deg)` }}>
      <button onClick={() => addRoad("west")} className="text-white bg-gray-800 hover:bg-gray-900 focus:outline-none focus:ring-4 focus:ring-gray-300 font-medium rounded-lg text-sm px-5 py-2.5 me-2 mb-2 dark:bg-gray-800 dark:hover:bg-gray-700 dark:focus:ring-gray-700 dark:border-gray-700">+</button>
      {west.map((road) => (
        <Road key={road.id} {...road} addCar={() => addCarToRoad(road.id, "west")} />
      ))}      
      <Divider/>
      {east.map((_) => (
        <EmptyRoad />
      ))}
    </div>
    <div className="flex w-full h-full justify-center items-center bg-gray-700">
        <button onClick={step} className="text-white bg-gray-800 hover:bg-gray-900 focus:outline-none focus:ring-4 focus:ring-gray-300 font-medium rounded-lg text-sm px-5 py-2.5 me-2 mb-2 dark:bg-gray-800 dark:hover:bg-gray-700 dark:focus:ring-gray-700 dark:border-gray-700">Step</button>
    </div>
    <div className="flex flex-row aspect-square" style={{ transform: `rotate(270deg)` }}>
      <button onClick={() => addRoad("east")} className="text-white bg-gray-800 hover:bg-gray-900 focus:outline-none focus:ring-4 focus:ring-gray-300 font-medium rounded-lg text-sm px-5 py-2.5 me-2 mb-2 dark:bg-gray-800 dark:hover:bg-gray-700 dark:focus:ring-gray-700 dark:border-gray-700">+</button>
      {east.map((road) => (
        <Road key={road.id} {...road} addCar={() => addCarToRoad(road.id, "east")} />
      ))}      
      <Divider />
      {west.map((_) => (
        <EmptyRoad />
      ))}
    </div>
    <div className="bg-green-500 flex flex-row aspect-square"></div>
    <div className="flex flex-row aspect-square flex justify-start">
        <button onClick={() => addRoad("south")} className="text-white bg-gray-800 hover:bg-gray-900 focus:outline-none focus:ring-4 focus:ring-gray-300 font-medium rounded-lg text-sm px-5 py-2.5 me-2 mb-2 dark:bg-gray-800 dark:hover:bg-gray-700 dark:focus:ring-gray-700 dark:border-gray-700">+</button>
        {south.map((road) => (
        <Road key={road.id} {...road} addCar={() => addCarToRoad(road.id, "south")} />
      ))}      
      <Divider />
      {north.map((_) => (
        <EmptyRoad />
      ))}
    </div>
    <div className="bg-green-500 flex flex-row aspect-square"></div>
  </div>
); } 

export default Intersection;

