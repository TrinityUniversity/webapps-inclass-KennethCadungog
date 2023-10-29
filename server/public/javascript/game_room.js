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

let player = {
    x: 0, y: 0
};


let updateZone = function() {
    requestAnimationFrame(updateZone);
    context.clearRect(0,0,window_width,window_height);
    for(const id in players) {
        const player = players[id];
        context.fillStyle = 'red';
        context.fillRect(player.x,player.y,50,50);
    }

    player.x = Math.max(0,Math.min(player.x, window_width - 50));
    player.y = Math.max(0,Math.min(player.y, window_height - 50));
    context.fillRect(player.x,player.y,50,50);
};
updateZone();

socket.onmessage = (event) => {
    const data = JSON.parse(event.data);
    players = data;
    //draw();
    updateZone();
};

document.addEventListener("keydown", (event) => {
    if(event.code === 'ArrowUp') player.y -= 5;
    if(event.code === 'ArrowDown') player.y += 5;
    if(event.code === 'ArrowLeft') player.x -= 5;
    if(event.code === 'ArrowRight') player.x += 5;

    if(event.code === 'KeyW' || event.code === 'KeyS') player.y = 0;
    if(event.code === 'KeyA' || event.code === 'KeyD') player.x = 0;

    socket.send(JSON.stringify(player));
});

