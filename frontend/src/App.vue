<template>
  <div id="app">
    <router-view />
    <!-- <footer> -->
      <!-- <router-link to="/about">© 2024 Altscherz</router-link> -->
    <!-- </footer> -->
  </div>
</template>

<style>
nav {
  padding: 1em;
}

nav a {
  margin: 0 1em;
  text-decoration: none;
  color: #42b983;
}

nav a.router-link-exact-active {
  font-weight: bold;
}

footer {
  position: relative;
  bottom: 0;
  width: 100%;
  text-align: center;
  padding: 1em 0;
  background-color: #f8f9fa;
}

footer a {
  text-decoration: none;
  color: #42b983;
}
</style>