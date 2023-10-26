const { createApp } = Vue

createApp({
  data() {
    return {
      message: 'Hello Vue!',
      clients: [],
      email: '',
      password: '',
      name:'',
      lastName: '',
        }
    },
methods: {
    submitForm() {
        axios.post('/api/login', `email=${this.email}&password=${this.password}` )
        .then(response => {
            console.log(response)
        window.location.href = `/web/pages/accounts.html`;
        })
        .catch(error => {
            alert('Email or Password invalid');
        });
    },
    register(){
        axios.post(`/api/clients`, `name=${this.name}&lastName=${this.lastName}&email=${this.email}&password=${this.password}`  
        )
        .then(response => {
            console.log(response)
            window.location.href = `/web/index.html`
        })
        .catch(error => {
            console.log(error);
        });
    }
    
}}).mount('#app')