export const initialState = {
    colors: Array.from({ length: 420 }, (_, i) => "0"),
    reserved: [],
    isInitialized: false,
    showPopup: false,
    clickPosition: { x: 700, y: 800 },
    block: true,
    modalPosition: { x: 0, y: 0 },
    clickedHour: null,
    reservedColors: [],
    reservedHours: "",
  };
  
  export const reducer = (state, action) => {
    switch (action.type) {
      case "SET_COLORS":
        return { ...state, colors: action.payload };
      case "SET_RESERVED":
        return { ...state, reserved: action.payload };
      case "TOGGLE_INITIALIZATION":
        return { ...state, isInitialized: !state.isInitialized };
      case "TOGGLE_POPUP":
        return { ...state, showPopup: !state.showPopup };
      case "SET_CLICK_POSITION":
        return { ...state, clickPosition: action.payload };
      case "TOGGLE_BLOCK":
        return { ...state, block: !state.block };
      case "SET_MODAL_POSITION":
        return { ...state, modalPosition: action.payload };
      case "SET_CLICKED_HOUR":
        return { ...state, clickedHour: action.payload };
      case "SET_RESERVED_COLORS":
        return { ...state, reservedColors: action.payload };
      case "SET_RESERVED_HOURS":
        return { ...state, reservedHours: action.payload };
      default:
        return state;
    }
  };