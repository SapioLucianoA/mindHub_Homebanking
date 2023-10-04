const { createApp } = Vue

createApp({
  data() {
    return {
      message: 'Hello Vue!',
      clients: [],
    }
  },

  created() {
    axios.get('http://localhost:8080/clients')
      .then((response) => {
        console.log(response.data);
        this.clients = response.data._embedded.clients;
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
      datos.forEach(function(value, key){
        if (!value || value.trim() === '') {
          alert('Por favor completa todos los campos requeridos.');
          return;
        }
        object[key] = value;
      });
    
      // Si algún campo estaba vacío, no continuar
      if (Object.keys(object).length !== Array.from(datos.keys()).length) {
        return;
      }
    
      let json = JSON.stringify(object);
      
      axios.post('http://localhost:8080/clients', json, {
        headers: {
          'Content-Type': 'application/json'
        }
      })
      .then((response) => console.log(response.data))
      .catch((error) => console.error('Error:', error));
    },
    
    
    validarDatos(datos) {
      // Aquí puedes agregar tu lógica para validar los datos
      // Por ejemplo, puedes verificar que todos los campos requeridos estén presentes y no estén vacíos
      for (let key in datos) {
        if (!datos[key] || datos[key].trim() === '') {
          return false;
        }
      }
      
      return true;
    },
    eliminarDatos(clientId) {
      axios.delete(`http://localhost:8080/clients/${clientId}`, {
        headers:{
          'Content-Type': 'application/json'
        }
      })
      .then((response) => console.log(response.data))
      .catch((error) => console.error('Error:', error));
    },
  }
   
}).mount('#app')


