const { createApp } = Vue

createApp({
  data() {
    return {
      message: '',
      clients: [],
      accounts: [],
      account: [],
      transactions:[],
    }
  },

  created () {
    let params = new URLSearchParams(window.location.search).get(`accountId`);
    console.log(params)
    axios
      .get('http://localhost:8080/api/accounts')
      .then(response => {
        this.accounts = response.data
        this.account = this.accounts.find(account => String(account.id) === String(params)) 
        this.transactions = this.account.transactions
        this.transactions.sort((a, b) => b.id - a.id)
        console.log(this.transactions)
        this.checktransactions;
      })
    
  },
  computed: {
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
    checktransactions() {
      if (this.transactions.length === 0) {
        this.message = 'You dont have any capytransactions to see';
      }
    }
  },
})
.mount('#app')