/**
 *  Code for the game room application.
 */

const canvas = document.getElementById("canvas");
const context = canvas.getContext("2d");
canvas.style.background = "#ff8";

let window_width = window.innerWidth;
let window_height = 600;
canvas.width = window_width;
canvas.height = window_height;

const socketRoute = document.getElementById("ws-route").value;
const socket = new WebSocket(socketRoute.replace("http","ws")); 

let players = {};
let player_size = 50;

function generateRandomStart() {
    const initialXPos = Math.random() * (window_width - player_size)
    const initialYPos = Math.random() * (window_height - player_size)
    return {x: initialXPos, y: initialYPos};
}

document.addEventListener("keydown", (event) => {
    const player = players[socket.id] = generateRandomStart();
    if (event.key === 'ArrowUp' && player.y > 0) player.y -= 5;
    else if (event.key === 'ArrowDown' && player.y < (window_height - player_size)) player.y += 5;
    else if (event.key === 'ArrowLeft' && player.x > 0) player.x -= 5;
    else if (event.key === 'ArrowRight' && player.x < (window_width - player_size)) player.x += 5;

    socket.send(JSON.stringify(player));
});

socket.onmessage = (event) => {
    const data = JSON.parse(event.data);
    players = data;
    draw();
};

function draw() {
    context.clearRect(0,0,canvas.width,canvas.height);

    for(const id in players) {
        const player = players[id];
        context.fillStyle = 'red';
        context.fillRect(player.x,player.y,50,50);
    }

    requestAnimationFrame(draw);
}