const CLOUDINARY_URL = 'https://api.cloudinary.com/v1_1/ddo0zj7af/upload';
const CLOUDINARY_UPLOAD_PRESET = 'dcnrptxz';
let uploaded, toUpload, finished;
let progBar;

function getRoomId() {
    var url = window.location['href'];
    return getParameterByName('id', url);
}

function saveOnServer(url) {
    var id = getRoomId();
    $.post('/rooms/add_image', {room_id: id, image_url: url},
        function (data) {
            if (data['error']) {
                console.log('WTF SIMON');
                finished++;
                updateProgress();
            }
            else {
                console.log('success');
                finished++;
                uploaded++;
                updateProgress();
            }
        });
}

function getPercentage() {
    return 100 * ( uploaded / toUpload);
}

function updateProgress() {
    let percentage = getPercentage();

    if (percentage === 100) {
        prog.classList.add("progress-bar-success");
    } else {
        prog.classList.remove('progress-bar-success')
    }
    if (finished === toUpload) {
        let msg = document.getElementById('successMessage');
        msg.innerText = 'წარმატებით დაემატა ' + toUpload + '-დან ' + uploaded + ' სურათი!';
        $('#successMessage').show();
    }
    progBar.style.width = percentage + '%';
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
        finished++;
    });
}

function uploadFiles(files) {
    console.log(files);
    // files is a FileList of File objects. List some properties.
    var output = [];
    toUpload = files.length;
    progBar = document.getElementById('prog');
    uploaded = finished = 0;
    updateProgress();
    for (var i = 0, f; f = files[i]; i++) {
        uploadImage(f);
    }

}

function handleFileSelect(evt) {
    evt.stopPropagation();
    evt.preventDefault();
    uploadFiles(evt.target.files);
}

function handleDragOver(evt) {
    evt.stopPropagation();
    evt.preventDefault();
    evt.dataTransfer.dropEffect = 'copy'; // Explicitly show this is a copy.
}

// Setup the dnd listeners.
var dropZone = document.getElementById('drop_zone');
dropZone.addEventListener('dragover', handleDragOver, false);
// dropZone.addEventListener('drop', handleFileSelect, false);
dropZone.ondrop = function (e) {
    e.preventDefault();
    this.className = 'drop-zone';
    uploadFiles(e.dataTransfer.files);
};
document.getElementById('imageup').addEventListener('change', handleFileSelect, false);
$('#drop_zone').click(function () {
    $('#imageup').trigger('click')
});
