const { createApp } = Vue

createApp({
  data() {
    return {
      message: 'Hello Vue!',
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
      })
    
  },
})
.mount('#app')