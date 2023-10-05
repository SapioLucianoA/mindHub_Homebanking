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
      .then(response => (this.clients = response.data)
      
      )
    axios
      .get('http://localhost:8080/api/accounts')
      .then(response => (this.accounts = response.data))
  }
})
.mount('#app')


