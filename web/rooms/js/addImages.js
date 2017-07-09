const CLOUDINARY_URL = 'https://api.cloudinary.com/v1_1/ddo0zj7af/upload';
const CLOUDINARY_UPLOAD_PRESET = 'dcnrptxz';

function getParameterByName(name, url) {
    if (!url) url = window.location.href;
    name = name.replace(/[\[\]]/g, "\\$&");
    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, " "));
}

function getRoomId() {
    var url = window.location['href'];
    return getParameterByName('id', url);
}
function saveOnServer(url) {
    var id = getRoomId();
    $.post('/rooms/add_image', {room_id: id, image_url: url},
        function (data) {
            if (data['error'])
                console.log('WTF SIMON');
            else console.log('success');
        });
}
function uploadImage(file) {

    var formData = new FormData();
    formData.append('file', file);
    formData.append('upload_preset', CLOUDINARY_UPLOAD_PRESET);
    axios({
        url: CLOUDINARY_URL,
        method: 'POST',
        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
        data: formData
    }).then(function (res) {
        console.log('yaaay');
        var url = res['data']['url'];
        saveOnServer(url);
        console.log(url);

    }).catch(function (err) {
        console.log('nooo');
        console.log(err);
    });
}
function handleFileSelect(evt) {
    evt.stopPropagation();
    evt.preventDefault();

    var files = evt.target.files; // FileList object.
    console.log(files);
    // files is a FileList of File objects. List some properties.
    var output = [];
    for (var i = 0, f; f = files[i]; i++) {
        uploadImage(f);
    }
    document.getElementById('image-list').innerHTML = '<ul>' + output.join('') + '</ul>';
}

function handleDragOver(evt) {
    evt.stopPropagation();
    evt.preventDefault();
    evt.dataTransfer.dropEffect = 'copy'; // Explicitly show this is a copy.
}

// Setup the dnd listeners.
var dropZone = document.getElementById('drop_zone');
dropZone.addEventListener('dragover', handleDragOver, false);
dropZone.addEventListener('drop', handleFileSelect, false);
document.getElementById('imageup').addEventListener('change', handleFileSelect, false);
$('#drop_zone').click(function () {
    $('#imageup').trigger('click')
});