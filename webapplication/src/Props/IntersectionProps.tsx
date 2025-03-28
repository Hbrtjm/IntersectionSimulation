import { RoadProps } from "./RoadProps";

export interface IntersectionProps {
  roadsDirections: {
    north: RoadProps[];
    east: RoadProps[];
    west: RoadProps[];
    south: RoadProps[];
  };
}
