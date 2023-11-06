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
            if (error.response) {
                // La solicitud se hizo y el servidor respondió con un código de estado
                // que cae fuera del rango de 2xx
                console.log(error.response.data);
                alert(error.response.status + " " + error.response.data);
                console.log(error.response.headers);
        }});
    },
    register(){
        axios.post(`/api/clients`, `name=${this.name}&lastName=${this.lastName}&email=${this.email}&password=${this.password}`  
        )
        .then(response => {
            console.log(response)
            window.location.href = `/web/index.html`
        })
        .catch(error => {
            if (error.response) {
                // La solicitud se hizo y el servidor respondió con un código de estado
                // que cae fuera del rango de 2xx
                console.log(error.response.data);
                alert(error.response.status + " " + error.response.data);
                console.log(error.response.headers);
        }});
    },
    
    
}}).mount('#app')