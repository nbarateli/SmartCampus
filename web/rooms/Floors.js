/**
 * Created by Shota on 6/26/2017.
 */
window.addEventListener('load', load, false);
function load() {
    var map1 = document.getElementById("map1");
    var map2 = document.getElementById("map2");
    var map3 = document.getElementById("map3");
    var map4 = document.getElementById("map4");
    workMaps(map1);
    workMaps(map2);
    workMaps(map3);
    workMaps(map4);
    var span = document.getElementById("closeModal");
    var modal = document.getElementById('myModal');
    span.onclick = function () {
        modal.style.display = "none";
    };
    window.onclick = function(event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }
};
function workMaps(map) {
    var areas = map.childNodes;
    for (i = 0; i < areas.length; i++) {
        areas[i].addEventListener("mouseover", myHover);
        areas[i].addEventListener("click", myClick);
    }
}


function myClick() {
    //testing
    var id = this.id;
    var modal = document.getElementById('myModal');
    modal.style.display = "block";
}

function myHover() {
	var id = this.id;
    var rect = document.getElementById(id).getBoundingClientRect();
    var coords = this.getAttribute("coords").split(",");
    var leftPos = +rect.left + 130 + +coords[2];
    var topPos = +rect.top + +coords[3];

    console.log("sum is: "+ leftPos +" "+ topPos);
    console.log(leftPos +" "+ topPos);
    var div = document.getElementById("popup-div");
    div.removeChild(div.childNodes[0]);
    var text = document.createTextNode("this room is: " + id); div.appendChild(text);
    
    div.style.backgroundColor = "yellow";
    div.style.border = "1px";
    div.style.position = "absolute";
    div.style.left = leftPos+"px"; div.style.top = topPos+"px"; //div.style.right = "2px"; div.style.bottom = "2px";
    div.style.height = "100px"; div.style.width = "100px";
    document.body.appendChild(div);

    document.getElementById("par").innerHTML = id;
}
function floorChange() {
    var x = document.getElementById("floors").value;
    if (x === "first") showFirst();
    if (x === "second") showSecond();
    if (x === "third") showThird();
    if (x === "fourth") showFourth();
}
function showFirst() {
    document.getElementById("imgmap2").style.display = "none";
    document.getElementById("imgmap3").style.display = "none";
    document.getElementById("imgmap4").style.display = "none";
    document.getElementById("imgmap1").style.display = "unset";
}
function showSecond() {
    document.getElementById("imgmap1").style.display = "none";
    document.getElementById("imgmap3").style.display = "none";
    document.getElementById("imgmap4").style.display = "none";
    document.getElementById("imgmap2").style.display = "unset";
}
function showThird() {
    document.getElementById("imgmap1").style.display = "none";
    document.getElementById("imgmap2").style.display = "none";
    document.getElementById("imgmap4").style.display = "none";
    document.getElementById("imgmap3").style.display = "unset";
}
function showFourth() {
    document.getElementById("imgmap1").style.display = "none";
    document.getElementById("imgmap2").style.display = "none";
    document.getElementById("imgmap3").style.display = "none";
    document.getElementById("imgmap4").style.display = "unset";
}

