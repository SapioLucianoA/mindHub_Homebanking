const { createApp } = Vue

createApp({
  data() {
    return {
      message: '',
      preAccounts:[],
      clients: [],
      accounts: [],
      loans: [],
      accountNumber: '',
      selectedAccountClass: '',
      accountClasses: ['GENERIC', 'SAVING'],
      number:'',
      status:'',
      accountClass:'',
      selectedLoan: null,
      installment: 0
    }
  },

  created () {
    axios
      .get('/api/clients/current')
      .then(response => {
        console.log(response.data.name)
        this.clients = response.data;
        console.log(this.clients)


        this.preAccounts = this.clients.accounts;
        this.accounts = this.preAccounts.filter(account => account.status === "ACTIVE")
        this.accounts.sort((a, b) => b.number - a.number );
        console.log(this.accounts);


        this.loans = this.clients.loans;
        this.accounts.sort((a, b,)=>b.loanId - a.loanId )
        
        this.checkLoans;

        this.updateAccountNumber();
      })
    
  },
  methods: {
    payLoan(){
      axios.post("/api/client/pay", `number=${this.number}&loanName=${this.selectedLoan.name}&amount=${this.installment}`)
      .then(response => {
        console.log(response.data);
      })
      .catch(error => {
        if (error.response) {
            // La solicitud se hizo y el servidor respondió con un código de estado
            // que cae fuera del rango de 2xx
            console.log(error.response.data);
            alert(error.response.status + "- " + error.response.data);
            console.log(error.response.headers);
        } else if (error.request) {
            // La solicitud se hizo pero no se recibió ninguna respuesta
            console.log(error.request);
        } else {
            // Algo sucedió en la configuración de la solicitud que provocó un error
            alert('Error', error.message);
        }
        console.log(error.config);
    });
     },
    

    updateInstallment() {
      this.installment = this.selectedLoan.amount / this.selectedLoan.payments;
    },
  deleteAccount(number, status){
    axios.patch(`/api/clients/remove/account`,`number=${number}&status=${status}`)
  .then(response =>{
    console.log(response)
    alert("Account remove!")
    window.location.href = `/web/pages/accounts.html`
    
  })
  .catch(error => {
    if (error.response) {
        // La solicitud se hizo y el servidor respondió con un código de estado
        // que cae fuera del rango de 2xx
        console.log(error.response.data);
        alert(error.response.status + "- " + error.response.data);
        console.log(error.response.headers);
    } else if (error.request) {
        // La solicitud se hizo pero no se recibió ninguna respuesta
        console.log(error.request);
    } else {
        // Algo sucedió en la configuración de la solicitud que provocó un error
        alert('Error', error.message);
    }
    console.log(error.config);
});
 },

    newAccount(){
    axios
    .post("/api/clients/current/accounts", `accountClass=${this.selectedAccountClass}`)
    .then(response =>{
      console.log(response)
      alert("Account created")
      window.location.href = `/web/pages/accounts.html`
      
    })
    .catch(error => {
      if (error.response) {
          // La solicitud se hizo y el servidor respondió con un código de estado
          // que cae fuera del rango de 2xx
          console.log(error.response.data);
          alert(error.response.status + "- " + error.response.data);
          console.log(error.response.headers);
      } else if (error.request) {
          // La solicitud se hizo pero no se recibió ninguna respuesta
          console.log(error.request);
      } else {
          // Algo sucedió en la configuración de la solicitud que provocó un error
          alert('Error', error.message);
      }
      console.log(error.config);
  });
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
  
},


  computed: {
    checkLoans() {
      if (this.loans.length === 0) {
        this.message = 'you dont have any loan to see here <a href="./loan-aplication.html">click here</a> to create a loan.';
      }
    },
    
}})
.mount('#app')


