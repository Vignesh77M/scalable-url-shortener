function shortenUrl() {
    const longUrl = document.getElementById("longUrl").value;

    fetch("/api/shorten", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ longUrl: longUrl })
    })
    .then(response => response.text())
    .then(code => {
        const shortUrl = window.location.origin + "/api/" + code;
        document.getElementById("result").innerHTML =
            `<a href="${shortUrl}" target="_blank">${shortUrl}</a>`;
    })
    .catch(err => {
        document.getElementById("result").innerText = "Error creating URL";
    });
}
