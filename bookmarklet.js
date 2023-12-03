javascript:(function() {
    const url = window.location.href;
    const day = parseInt(url.split('/').slice(-2, -1)[0]);

    fetch(url)
        .then(response => response.text())
        .then(content => {
            const data = day + ';' + content;

            fetch('http://localhost:8880/day', {
                method: 'POST',
                headers: {
                    'Content-Type': 'text/plain'
                },
                body: data
            })
                .then(() => {
                    window.location.href = url.replace(`/${day}/`, `/${day + 1}/`);
                })
                .catch(() => {
                    window.location.href = url.replace(`/${day}/`, `/${day + 1}/`);
                });
        });
})();
