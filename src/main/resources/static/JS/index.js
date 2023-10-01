const { createApp } = Vue

createApp({
  data() {
    return {
      message: 'Hello Vue!',
      clients: [],
    }
  },

  created() {
    fetch(`http://localhost:8080/clients`)
      .then(function(Response) {
        return Response.json();
      })
      .then((data) => {
        console.log(data);
        this.clients = data._embedded.clients;
        console.log(this.clients);


      })
      .catch (function(error) {
        console.log(error);
      });
  },

  methods: {
    enviarDatos() {
      let datos = new FormData(document.getElementById('clientFormulary'));
      let object = {};
      datos.forEach(function(value, key){
          object[key] = value;
      });
      let json = JSON.stringify(object);
      
      fetch('http://localhost:8080/clients', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: json
      })
      .then(response => response.json())
      .then(data => console.log(data))
      .catch((error) => console.error('Error:', error));
  }


},
}).mount('#app')

