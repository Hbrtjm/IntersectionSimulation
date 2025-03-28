import { CarProps } from "./CarProps";

export interface RoadProps {
  id: string;
  addCar: () => void
  direction: string;
  cars: CarProps[];
//   rotation: number;
}
