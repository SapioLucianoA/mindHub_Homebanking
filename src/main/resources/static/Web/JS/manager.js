const { createApp } = Vue

createApp({
  data() {
    return {
      message: 'Hello Vue!',
      email: '',
      password: '',
      name:'',
      lastName: '',
      clientRole:'',
      
    }
  },

  created() {
    axios.get('http://localhost:8080/api/clients')
      .then((response) => {
        console.log(response.data);
        this.clients = response.data;
        console.log(this.clients);
      })
      .catch((error) => {
        console.log(error);
      });
  },

  methods: {
    register(){
      axios.post(`/api/client/admin`, `name=${this.name}&lastName=${this.lastName}&email=${this.email}&password=${this.password}&clientRole=${this.clientRole}`  
      )
      .then(response => {
          console.log(response)
          alert("register succes!")
          window.location.href = `/web/pages/manager.html`
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
      });;
  },
  
 
   
}}).mount('#app')


