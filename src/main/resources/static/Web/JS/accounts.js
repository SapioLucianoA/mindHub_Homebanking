const { createApp } = Vue

createApp({
  data() {
    return {
      message: 'Hello Vue!',
      clients: [],
      accounts: [],
    }
  },

  created () {
    axios
      .get('http://localhost:8080/api/clients/1')
      .then(response => {
        this.clients = response.data
        this.accounts = this.clients.accounts
        this.accounts.sort((a, b) => b.number - a.number )
        console.log(this.accounts)
      })
    
  },
})
.mount('#app')


