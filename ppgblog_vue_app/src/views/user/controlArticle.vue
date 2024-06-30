<template>
  <div style="width: 100%;min-height: 1000px">
    <el-card style="max-width: 100%">
      <template #header>
        <div class="card-header">
          <span>文章</span>
        </div>
      </template>
      <el-table :data="articleList" style="width: 100%">
        <el-table-column prop="title" label="标题" width="250"/>
        <el-table-column prop="author.name" label="作者" width="100"/>
        <el-table-column prop="category" label="类别" width="100"/>
        <el-table-column prop="viewCounts" label="浏览量" width="100"/>
        <el-table-column prop="commentCounts" label="评论量" width="100"/>
        <el-table-column prop="createTime" label="发布时间"/>
        <el-table-column prop="id">
          <template v-slot="{row}">
            <el-button class="optBtn"
                       type="danger" icon="el-icon-delete" circle
                       @click.prevent="articleDel(row.id)">
              <Delete style="width: 1em; height: 1em; margin-right: 8px"></Delete>
            </el-button>
            <el-button class="optBtn"
                       type="warning"
                       icon="el-icon-edit" circle
                       @click.prevent="articleEdit(row.id)">
              <Edit style="width: 1em; height: 1em; margin-right: 8px"/>
            </el-button>
            <el-button class="optBtn"
                       type="primary"
                       icon="el-icon-edit" circle
                       @click.prevent="pushTo(row.id)">
              <View style="width: 1em; height: 1em; margin-right: 8px"/>
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    <control-comment-vue ref="commentRef"/>
  </div>
</template>

<script setup>
import controlCommentVue from "@/views/user/controlComment.vue"
import router from "@/router/router.js";
import {getArticleByUserService, deleteArticleService} from "@/api/article.js";
import {ElMessage} from "element-plus";
import {ref, watch} from "vue";
import {Delete, View} from "@element-plus/icons-vue";

const articleList = ref([])
const commentRef = ref(null)
const pushTo = (id) => {
  router.push({path: "/article/articleMain/" + id})
}
const getArticleList = async () => {
  let result = await getArticleByUserService();
  articleList.value = result.data;
}
getArticleList()
const articleDel = async (id) =>{
  await deleteArticleService(id);
  ElMessage.success("删除成功")
  await getArticleList()
  await commentRef.value.getCommentList()
}
const articleEdit = (id) =>{
  router.push({path: "/article/editArticle/" + id})
}
</script>

<style scoped>

</style>