const { createApp } = Vue

createApp({
  data() {
    return {
      message: '',
      clients: [],
      cards:[],
      creditCards: [],
      debitCards:[],
      messageCredit: '',
      messageDebit: '',
      type:'',
      color:'',
      name:'',
      lastName:'',
      showCVV: false,
      limitCard: '',
      today: '',
      number: '',
      status:'',

    }
  },

  created () {
      axios
      .get('/api/clients/current/cards')
      .then(response => {

        
        this.cards = response.data
        this.today = new Date;  
        console.log(this.cards)

        this.checkCards;

        this.creditCards = this.cards.filter(card => card.type ===  "CREDIT" )

        this.debitCards = this.cards.filter(card => card.type === "DEBIT")
        console.log(this.creditCards)
        console.log(this.debitCards)
        this.checkCardsCredit;
        this.checkCardsDebit;
        this.checkLimitCards();
        
      })

  },
  computed: {
    checkCards() {
      if (this.cards.length === 0) {
        this.message = 'You dont have any capyCards to see';
      }
    },
    checkCardsCredit(){
        if (this.creditCards.length === 0) {
            this.messageCredit = 'You dont have any to see here :c';
          }
    },
    checkCardsDebit(){
        if (this.debitCards.length === 0) {
            this.messageDebit = 'You dont have any to see here :c';
          }
    },
  },
  methods: {
    logout() {
      axios.post('/api/logout')
        .then(response => {
          
          console.log(response);
          
          // Redirige a "./web/index.html"
          window.location.href = '/web/index.html';
        })
        .catch(error => {
          if (error.response) {
              // La solicitud se hizo y el servidor respondió con un código de estado
              // que cae fuera del rango de 2xx
              console.log(error.response.data);
              alert(error.response.status + " " + error.response.data);
              console.log(error.response.headers);
          } else if (error.request) {
              // La solicitud se hizo pero no se recibió ninguna respuesta
              alert(error.request);
          } else {
              // Algo sucedió en la configuración de la solicitud que provocó un error
              console.log('Error', error.message);
          }
          console.log(error.config);
      });
    },
    checkLimitCards() {
      if(this.cards.length >= 6){
        this.limitCard = 'd-none'
      }
    },
  isBlank(str) {
      return (!str || /^\s*$/.test(str));
  },

  deleteCard(number, status){
    if (!number || !status) {
      alert('Number and status are required');
      return;
    }
  
    axios.patch('/api/client/remove/card', `number=${number}&status=${status}`)
    .then(response => {
      console.log(response)
      window.location.href = `/web/pages/cards.html`
    }) .catch(error => {
  if (error.response) {
      // La solicitud se hizo y el servidor respondió con un código de estado
      // que cae fuera del rango de 2xx
      console.log(error.response.data);
      alert(error.response.status + " " + error.response.data);
      console.log(error.response.headers);
  } else if (error.request) {
      // La solicitud se hizo pero no se recibió ninguna respuesta
      alert(error.request);
  } else {
      // Algo sucedió en la configuración de la solicitud que provocó un error
      console.log('Error', error.message);
  }
  console.log(error.config);
});

    },

    newCard(){
      if (this.isBlank(this.name)) {
        alert('Please, complete your First Name.');
        return;
      }
      if (this.isBlank(this.lastName)) {
        alert('Please, complete your Last Name.');
        return;
      }
      if (this.isBlank(this.type)) {
        alert('Please, select your card type.');
        return;
      }
      if (this.isBlank(this.color)) {
        alert('Please, select your card color.');
        return;
      }

      axios.post("/api/clients/current/cards", `type=${this.type}&color=${this.color}&name=${this.name}&lastName=${this.lastName}`)
      .then(response =>
        console.log(response),
        
        window.location.href = `/web/pages/cards.html`
      )
      .catch(error => {
        if (error.response) {
            // La solicitud se hizo y el servidor respondió con un código de estado
            // que cae fuera del rango de 2xx
            console.log(error.response.data);
            alert(error.response.status + " " + error.response.data);
            console.log(error.response.headers);
        } else if (error.request) {
            // La solicitud se hizo pero no se recibió ninguna respuesta
            alert(error.request);
        } else {
            // Algo sucedió en la configuración de la solicitud que provocó un error
            console.log('Error', error.message);
        }
        console.log(error.config);
    });
    },
    maskCVV(cvv) {
      return cvv.replace(/./g, '*');
    },
  }
}
)

.mount('#app')