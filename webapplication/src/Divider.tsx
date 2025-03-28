const Divider = ({ rotation = 0 }) => (
  <div 
    className="w-1 bg-white" 
    style={{ transform: `rotate(${rotation}deg)` }}
  >
  </div>
);

export default Divider;
