const { createApp } = Vue

createApp({
  data() {
    return {
      message: '',
      clients: [],
      accounts: [],
      loans: [],
      accountNumber: '',
      accountClass: '',
    }
  },

  created () {
    axios
      .get('/api/clients/current')
      .then(response => {
        console.log(response.data.name)
        this.clients = response.data;
        console.log(this.clients)


        this.accounts = this.clients.accounts;
        this.accounts.sort((a, b) => b.number - a.number );
        console.log(this.accounts);


        this.loans = this.clients.loans;
        this.accounts.sort((a, b,)=>b.loanId - a.loanId )
        
        this.checkLoans;
        this.updateAccountNumber()
      })
    
  },
  methods: {
    newAccount(){
    axios
    .post("/api/clients/current/accounts")
    .then(response =>{
      console.log(response)
      window.location.href = `/web/pages/accounts.html`
      
    })
  },
  updateAccountNumber() {
    if (this.accounts.length >= 3) {
      this.accountClass = 'd-none';
    }
  },
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
  }
  

,
  computed: {
    checkLoans() {
      if (this.loans.length === 0) {
        this.message = 'You don`t have Capydollars? Well, take a loan with us with few requeriments and many forms of payment methods';
      }
    },
    
}})
.mount('#app')


