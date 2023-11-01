const { createApp } = Vue

createApp({
  data() {
    return {
      accounts:[],
      amount:"",
      number:"",
      number2:"",
      description:"",
      picked: null,
    }
  },

  created () {
    axios.get('/api/clients/current/accounts')
      .then(response => {
        console.log(response)
        this.accounts = response.data
      })
  },

  methods: {
    isBlank(str) {
      return (!str || /^\s*$/.test(str));
    },

    SendTransaction(){
      if (this.isBlank(this.number)) {
        alert('Please, complete the origin account.');
        return;
      }
      if (this.isBlank(this.number2)) {
        alert('Please, complete the account to send the transaction.');
        return;
      }
      if (this.isBlank(this.description)) {
        alert('Please,  complete the description.');
        return;
      }
      if(confirm('¿are you sure to send this transaction? Please take a another look before to send'))
        axios.post('/api/transactions', `amount=${(this.amount)}&number=${this.number}&number2=${this.number2}&description=${this.description}`)
          .then(response => {
            console.log(response)
            window.location.href = '/web/pages/accounts.html'
          })
          .catch(error => {
            if (error.response) {
                // La solicitud se hizo y el servidor respondió con un código de estado
                // que cae fuera del rango de 2xx
                console.log(error.response.data);
                console.log(error.response.status);
                console.log(error.response.headers);
            } else if (error.request) {
                // La solicitud se hizo pero no se recibió ninguna respuesta
                console.log(error.request);
            } else {
                // Algo sucedió en la configuración de la solicitud que provocó un error
                console.log('Error', error.message);
            }
            console.log(error.config);
        });
    },

    logout() {
      axios.post('/api/logout')
        .then(response => {
          console.log(response);
          window.location.href = '/web/index.html';
        })
        .catch(error => {
          alert(error);
        });
    },
  }
})
.mount('#app')
