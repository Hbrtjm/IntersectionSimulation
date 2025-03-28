import CarImage from "./assets/Car.svg";
import "./Styles/Colors.css"

interface CarDisplayProps
{
    id: string;
    color: string;
}

const Car = ({ id , color }: CarDisplayProps) => (
  <div
    className="w-10 h-20 flex justify-center items-center car"
  >
    <img src={CarImage} className={color} alt={id.toString()}></img>
  </div>
);

export default Car;
