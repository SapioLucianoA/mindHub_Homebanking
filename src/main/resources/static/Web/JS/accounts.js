const { createApp } = Vue

createApp({
  data() {
    return {
      message: '',
      clients: [],
      accounts: [],
      loans: [],
    }
  },

  created () {
    axios
      .get('http://localhost:8080/api/clients/1')
      .then(response => {
        this.clients = response.data;
        console.log(this.clients)


        this.accounts = this.clients.accounts;
        this.accounts.sort((a, b) => b.number - a.number );
        console.log(this.accounts);


        this.loans = this.clients.loans;
        this.accounts.sort((a, b,)=>b.loanId - a.loanId )
        
        this.checkLoans;
      })
    
  },
  computed: {
    checkLoans() {
      if (this.loans.length === 0) {
        this.message = 'You don`t have Capydollars? Well, take a loan with us with few requeriments and many forms of payment methods';
      }
    }
  },
})
.mount('#app')


