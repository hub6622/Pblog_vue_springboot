<template>
  <div style="width: 100%;min-height: 1000px">
    <el-card style="max-width: 100%">
      <template #header>
        <div class="card-header">
          <span>文章</span>
        </div>
      </template>
      <el-table :data="articleList" style="width: 100%" @row-click="pushTo">
        <el-table-column prop="title" label="标题" width="250"/>
        <el-table-column prop="author.name" label="作者"/>
        <el-table-column prop="category" label="类别" width="150"/>
        <el-table-column prop="viewCounts" label="浏览量" width="150"/>
        <el-table-column prop="commentCounts" label="评论量" width="150"/>
        <el-table-column prop="createTime" label="发布时间" width="150"/>
      </el-table>
      <template #footer>Footer content</template>
    </el-card>
  </div>
</template>

<script setup>

import router from "@/router/router.js";
import {useRoute} from "vue-router";
import {articleListService, articleListByCategoryService} from "@/api/article.js";
import {ElMessage} from "element-plus";
import {reactive, ref, watch} from "vue";

const articleList = ref([
  {
    title: "标题",
    content: "内容",
    category: "类别",
    createTime: "创建时间",
    viewCounts: 1,
    commentCounts: 22,
    author: {
      name: "作者"
    }
  }
])
const pushTo = (article) => {
  router.push({path: "/article/articleMain/"+article.id})
}

const route = useRoute();
const cate = ref(route.params.cate);
watch(() => route.params.cate, (newCate) => {
  if (!newCate && newCate === undefined) {
    getArticleList()
    console.log("id为空")
  } else {
    articleListByCategory(newCate);
    console.log("id不为空" + newCate)
  }
  cate.value = newCate;
});
const articleListByCategory = async (cate) => {
  let result = await articleListByCategoryService(cate);
  articleList.value = result.data;
}
const getArticleList = async () => {
  let result = await articleListService();
  articleList.value = result.data;
}
getArticleList()
</script>

<style scoped>

</style>