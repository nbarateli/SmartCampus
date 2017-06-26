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
};
function workMaps(map) {
    var areas = map.childNodes;
    for (i = 0; i < areas.length; i++) {
        areas[i].addEventListener("mouseover", myHover);
        areas[i].addEventListener("click", myClick);
    }
}
function myClick(){
    //testing
    var id = this.id;
    alert(id);
    //to do implementation
}
function myHover() {
    //testing
    var id = this.id;
    document.getElementById("par").innerHTML = id;
    //to do implementation
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

