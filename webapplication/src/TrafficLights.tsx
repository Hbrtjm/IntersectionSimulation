import React from "react";
import { TrafficLightProps } from "./Props/TrafficLightProps";

const TrafficLight: React.FC<TrafficLightProps> = ({ state }) => (
  <div className="flex flex-col items-center p-2 bg-black w-12 h-32 rounded-md gap-1">
    <div className={`w-8 h-8 rounded-full ${state === "red" ? "bg-red-500" : "bg-gray-800"}`}></div>
    <div className={`w-8 h-8 rounded-full ${state === "yellow" ? "bg-yellow-500" : "bg-gray-800"}`}></div>
    <div className={`w-8 h-8 rounded-full ${state === "green" ? "bg-green-500" : "bg-gray-800"}`}></div>
  </div>
);

export default TrafficLight;
