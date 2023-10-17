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
    }
  },

  created () {
    axios
      .get('http://localhost:8080/api/clients/3')
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
})
.mount('#app')