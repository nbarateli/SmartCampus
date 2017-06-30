function onSignIn(googleUser) {
    var profile = googleUser.getBasicProfile();
    var id_token = googleUser.getAuthResponse().id_token;
    var xhr = new XMLHttpRequest();
    xhr.open('POST', '/tokensignin');
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.onload = function () {

        console.log('Signed in as: ' + xhr.responseText);
        if (xhr.responseText.toString().indexOf("error") !== -1) {
            signOut();
        }
    };
    xhr.send('idtoken=' + id_token);
    console.log(profile.getEmail());
}

function signOut() {
    var auth2 = gapi.auth2.getAuthInstance();
    auth2.signOut().then(function () {
        var xhr = new XMLHttpRequest();
        xhr.open('GET', '/signout');
        xhr.onload = function () {
            console.log(xhr.responseText);
        };
        xhr.send(null);
    });
}