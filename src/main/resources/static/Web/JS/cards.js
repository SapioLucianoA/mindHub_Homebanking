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
    }
  },

  created () {
    axios
      .get('http://localhost:8080/api/clients/current')
      .then(response => {
        this.clients = response.data
        
        this.cards = this.clients.cards
        this.checkCards;

        console.log(this.cards)

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
          
          console.error(error);
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
        alert(error);
    });
    },
    maskCVV(cvv) {
      return cvv.replace(/./g, '*');
    },
  }
}
)

.mount('#app')