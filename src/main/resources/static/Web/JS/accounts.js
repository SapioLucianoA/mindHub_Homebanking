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
        this.accounts = this.clients.accounts;
        this.accounts.sort((a, b) => b.number - a.number );
        console.log(this.accounts);

        console.log(this.clients)
        this.loans = this.clients.loans;
        console.log(this.loans)
      })
    
  },
})
.mount('#app')


