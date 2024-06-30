<template>
  <div class="common-layout">
    <el-container>
      <header-vue/>
      <el-container class="align-container">
        <el-main>
          <router-view></router-view>
        </el-main>
        <asideCom class="float-right" v-if="showOrNot"></asideCom>
      </el-container>
      <el-footer>
        <footer-vue/>
      </el-footer>
    </el-container>
  </div>
</template>

<script setup lang="ts">
import asideCom from '@/components/asideMenu.vue'
import headerVue from '@/components/header.vue'
import footerVue from '@/components/footer.vue'
import {ref, watch} from "vue";
import {useRoute} from "vue-router"

const route = useRoute()
const showOrNot = ref(true)
if(route.path === '/userInfo'){
  showOrNot.value = false
}else {
  showOrNot.value = true
}
watch(() => route.path, (newValue, oldValue) => {
  if (newValue === '/userInfo') {
    showOrNot.value = false
  } else {
    showOrNot.value = true
  }
})
</script>

<style lang="scss" scoped>
.common-layout {
  overflow-y: scroll;
  width: 100%;
  min-width: 2000px;
  color: #5e606f;
  left: 0;
  top: 0;
}
.align-container{
  width: 70%;
  margin: 50px auto 0;
}

.el-container {
  height: 100%;
  background-color: whitesmoke;
}

.el-footer, .el-header {
  width: 100%;
  background-color: #545c64;
}
</style>