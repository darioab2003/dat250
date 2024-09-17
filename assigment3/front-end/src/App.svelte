<script>
    let username = '';
    let email = '';
    let pollId = ''; // ID de la encuesta
    let caption = '';
    let presentationOrder = '';
    let question = '';
    let publishedAt = ''; // Esto se llenará automáticamente
    let validUntil = '';
    let responseMessageVoteOption = '';
    let responseMessageUser = '';
    let responseMessagePoll = '';
    let votePublishedAt = ''; // Esto se llenará automáticamente
    let voteOptionId = ''; // ID de la opción de voto
    let responseMessageVote = '';

    // Función para crear usuario
    function createUser() {
        fetch('http://localhost:8080/users', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                username: username,
                email: email,
            }),
        })
            .then(response => response.json())
            .then(data => {
                responseMessageUser = 'User created successfully!';
                console.log('User created:', data);
            })
            .catch(error => {
                responseMessageUser = 'Error creating user.';
                console.error('Error:', error);
            });
    }

    // Función para crear encuesta (poll)
    function createPoll() {
        const publishedAt = new Date().toISOString(); // Fecha actual en formato ISO
        fetch('http://localhost:8080/polls', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                question: question,
                publishedAt: publishedAt,
                validUntil: validUntil + 'T23:59:59Z', // Agregar hora y Z al final
                user: {
                    username: username,
                    email: email,
                },
            }),
        })
            .then(response => {
                if (!response.ok) {
                    return response.json().then(err => { throw err; });
                }
                return response.json();
            })
            .then(data => {
                responseMessagePoll = 'Poll created successfully!';
                console.log('Poll created:', data);
            })
            .catch(error => {
                responseMessagePoll = `Error creating poll: ${error.message}`;
                console.error('Error:', error);
            });
    }

    // Función para obtener la encuesta por su ID
    function getPollById(pollId) {
        return fetch(`http://localhost:8080/polls/${pollId}`)
            .then(response => {
                if (!response.ok) {
                    return response.json().then(err => { throw err; });
                }
                return response.json();
            })
            .catch(error => {
                console.error('Error fetching poll:', error);
                throw error;
            });
    }

    // Función para crear opción de voto (vote option)
    function createVoteOption() {
        // Obtener la encuesta por su ID antes de crear la opción de voto
        getPollById(pollId)
            .then(pollData => {
                // Crear la opción de voto con los datos de la encuesta obtenida
                return fetch('http://localhost:8080/voteoptions', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({
                        caption: caption,
                        presentationOrder: Number(presentationOrder),
                        poll: {
                            question: pollData.question,
                            publishedAt: pollData.publishedAt,
                            validUntil: pollData.validUntil,
                            user: {
                                username: pollData.user.username,
                                email: pollData.user.email,
                            },
                        },
                    }),
                });
            })
            .then(response => {
                if (!response.ok) {
                    return response.json().then(err => { throw err; });
                }
                return response.json();
            })
            .then(data => {
                responseMessageVoteOption = 'Vote option created successfully!';
                console.log('Vote option created:', data);
            })
            .catch(error => {
                responseMessageVoteOption = `Error creating vote option: ${error.message}`;
                console.error('Error:', error);
            });
    }

    // Función para realizar una votación (vote)
    function castVote() {
        const votePublishedAt = new Date().toISOString(); // Fecha actual en formato ISO
        fetch('http://localhost:8080/votes', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                publishedAt: votePublishedAt,
                user: {
                    username: username,
                    email: email,
                },
                voteOption: {
                    id: voteOptionId, // Usar el ID de la opción de voto
                },
            }),
        })
            .then(response => {
                if (!response.ok) {
                    return response.json().then(err => { throw err; });
                }
                return response.json();
            })
            .then(data => {
                responseMessageVote = 'Vote cast successfully!';
                console.log('Vote cast:', data);
            })
            .catch(error => {
                responseMessageVote = `Error casting vote: ${error.message}`;
                console.error('Error:', error);
            });
    }
</script>

<style>
    .background {
        background-color: #007bff;
        height: 100vh;
        display: flex;
        justify-content: center;
        align-items: center;
    }

    .container, .container1, .container2, .container3 {
        max-width: 300px;
        margin: 0 20px;
        padding: 20px;
        border: 1px solid #ff0000;
        border-radius: 5px;
        background-color: #36bc3c;
    }

    .form-group {
        margin-bottom: 1em;
    }

    .form-group label {
        display: block;
        margin-bottom: 0.5em;
    }

    .form-group input, .form-group select {
        width: 100%;
        padding: 0.5em;
        border: 1px solid #ccc;
        border-radius: 5px;
    }

    .button {
        padding: 0.5em 1em;
        background-color: #007bff;
        color: white;
        border: none;
        border-radius: 5px;
        cursor: pointer;
    }

    .button:hover {
        background-color: #0056b3;
    }
</style>

<!-- Fondo azul que ocupa toda la pantalla -->
<div class="background">
    <div class="container">
        <h1>Create User</h1>
        <div class="form-group">
            <label for="username">Username:</label>
            <input type="text" id="username" bind:value={username} placeholder="Enter username" />
        </div>
        <div class="form-group">
            <label for="email">Email:</label>
            <input type="email" id="email" bind:value={email} placeholder="Enter email" />
        </div>
        <button class="button" on:click={createUser}>Create User</button>
        <p>{responseMessageUser}</p>
    </div>

    <div class="container1">
        <h1>Create Poll</h1>
        <div class="form-group">
            <label for="question">Poll Question:</label>
            <input type="text" id="question" bind:value={question} placeholder="Enter poll question" />
        </div>
        <div class="form-group">
            <label for="validUntil">Valid Until:</label>
            <input type="date" id="validUntil" bind:value={validUntil} placeholder="Enter valid until date" />
        </div>
        <button class="button" on:click={createPoll}>Create Poll</button>
        <p>{responseMessagePoll}</p>
    </div>

    <div class="container2">
        <h1>Create Vote Option</h1>
        <div class="form-group">
            <label for="pollId">Poll ID:</label>
            <input type="text" id="pollId" bind:value={pollId} placeholder="Enter poll ID" />
        </div>
        <div class="form-group">
            <label for="caption">Caption:</label>
            <input type="text" id="caption" bind:value={caption} placeholder="Enter caption" />
        </div>
        <div class="form-group">
            <label for="presentationOrder">Presentation Order:</label>
            <input type="number" id="presentationOrder" bind:value={presentationOrder} placeholder="Enter presentation order" />
        </div>
        <button class="button" on:click={createVoteOption}>Create Vote Option</button>
        <p>{responseMessageVoteOption}</p>
    </div>

    <div class="container3">
        <h1>Cast Vote</h1>
        <div class="form-group">
            <label for="voteOptionId">Select Vote Option ID:</label>
            <input type="text" id="voteOptionId" bind:value={voteOptionId} placeholder="option-1" />
        </div>
        <button class="button" on:click={castVote}>Cast Vote</button>
        <p>{responseMessageVote}</p>
    </div>
</div>
