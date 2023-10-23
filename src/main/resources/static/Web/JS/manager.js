const { createApp } = Vue

createApp({
  data() {
    return {
      message: 'Hello Vue!',
      clients: [],
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
    enviarDatos() {
      let datos = new FormData(document.getElementById('clientFormulary'));
      let object = {};
      for (let [key, value] of datos) {
        if (!value || value.trim() === '') {
          alert('Por favor completa todos los campos requeridos.');
          return;
        }
        object[key] = value;
      }
  
      // Si algún campo estaba vacío, no continuar
      if (Object.keys(object).length !== Array.from(datos.keys()).length) {
        return;
      }
  
      let json = JSON.stringify(object);
      
      axios.post('http://localhost:8080/api/clients', json, {
        headers: {
          'Content-Type': 'application/json'
        }
      })
      .then((response) => console.log(response.data))
      .catch((error) => console.error('Error:', error));
    },
  }
  
   
}).mount('#app')


