<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>

export default {
  name: "App",
  setup() {
  },
};
</script>

<style lang="scss">
#app {
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  height: auto;
  width: 100%;
  font-family: "Binggrae";
}

body {
  height: 100vh;
  margin-top: 0px!important;
  justify-content: center;
  font-family: "Binggrae";
}
</style>
