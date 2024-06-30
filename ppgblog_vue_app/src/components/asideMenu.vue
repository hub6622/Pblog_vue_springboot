<template>
  <el-aside>
    <span style="font-size: 100px;color: pink">Pblog</span><br>
    <el-radio-group v-model="isCollapse" style="margin-bottom: 20px">
      <el-radio-button :value="false">展开</el-radio-button>
      <el-radio-button :value="true">收起</el-radio-button>
    </el-radio-group>
    <el-menu
        default-active="2"
        class="el-menu-vertical-demo"
        :collapse="isCollapse"
        @open="handleOpen"
        @close="handleClose"
    >
      <el-menu-item index="1">
        <el-icon>
          <HomeFilled/>
        </el-icon>
        <template #title>
          <router-link :to="'/'">返回主页
          </router-link>
        </template>
      </el-menu-item>
      <el-sub-menu>
        <template #title>
          <el-icon>
            <icon-menu/>
          </el-icon>
          <span>文章分类</span>
        </template>
        <el-menu-item-group>
          <template #title><span>语言</span></template>
          <el-menu-item v-for="(item, index) in categoryList" :key="index" index="2">
            <router-link :to="'/article/category/'+item">
              {{ item }}
            </router-link>
          </el-menu-item>
        </el-menu-item-group>
      </el-sub-menu>
    </el-menu>
    <el-card style="margin-top: 20px">
      <div class="card-header">
        <span>最热文章</span>
      </div>
      <p v-for="(item, index) in articleList" :key="index" class="article-text">
        <router-link :to="'/article/articleMain/'+item.id">
          {{ item.title }}
        </router-link>
      </p>
    </el-card>
  </el-aside>
</template>

<script lang="ts" setup>
import {reactive, ref, onMounted} from 'vue'
import {HomeFilled, Document, Menu as IconMenu, Location, Setting,} from '@element-plus/icons-vue'
import {getCategoryService, hotArticleService} from "@/api/article"

const isCollapse = ref(false)
const handleOpen = (key: string, keyPath: string[]) => {
  console.log(key, keyPath)
}
const handleClose = (key: string, keyPath: string[]) => {
  console.log(key, keyPath)
}
const categoryList = ref([])
const articleList = ref([])
onMounted(async () => {
  let result = await getCategoryService();
  let result2 = await hotArticleService()
  articleList.value = result2.data;
  categoryList.value = result.data;
})

</script>

<style>
a {
  text-decoration: none;
  color: black;
}
.article-text a{
  font-size: 17px;
  color: palevioletred;
}
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 300px;
  min-height: 100px;
}

.el-aside {
  position: relative;
}
</style>