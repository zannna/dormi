let lastChosenColor = 0;
const colors = ["#e0d299", "#e897a4", "#c89af5", "#9aa5f5", "#9af5ed", "#b36b90", "#E8B293", "#6b96b3", "#54BAA2", "#76BBC0", "#FFB5C2", "#D9D9D9", "#75c7c7", "#BCD29F", "#A69FD2", "#D29292"];

export const getColor = () => {
    return colors[lastChosenColor];
}
export const setNewColor = () => {

    if (lastChosenColor < colors.length - 1)
        lastChosenColor++;

    else
        lastChosenColor = 0;
}

