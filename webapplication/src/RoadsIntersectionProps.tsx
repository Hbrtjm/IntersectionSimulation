import { RoadProps } from "./Props/RoadProps";

export interface RoadIntersectionProps
{
    north: RoadProps[],
    east: RoadProps[],
    west: RoadProps[],
    south: RoadProps[]
};
