import { createApp, h } from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./common/lib/store";
import VueAxios from "./common/lib/axios";
import axios from "./common/lib/axios";

const app = createApp({
  render: () => h(App),
});
app.use(VueAxios, axios);
app.use(store);
app.use(router);

app.mount("#app");
