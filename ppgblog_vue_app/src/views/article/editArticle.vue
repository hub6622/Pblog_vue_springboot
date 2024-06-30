<template>
  <div class="input-text">
    <span style="font-size: 29px">请输入标题</span>
    <el-input v-model="article.title" placeholder="请输入文章标题" class="input-title"></el-input>
    <span style="font-size: 29px">文章类型</span><br>
    <el-select v-model="article.category" placeholder="Select" style="width: 100%">
      <el-option v-for="(item, index) in categoryList" :key="item" :label="item" :value="item"/>
    </el-select>
<!--    <el-input v-model="article.category" placeholder="请输入文章类型" class="input-title"></el-input>-->
  </div>
  <div class="container">
    <div id="vditor"></div>
  </div>
  <div class="button-wrapper">
    <el-button type="primary" class="button-wrapper" @click="uploadArticle" v-if="isAddArticle">提交</el-button>
    <el-button type="primary" class="button-wrapper" @click="editArticle" v-else>修改</el-button>
  </div>
</template>

<script setup lang="ts">
import Vditor from "vditor";
import "vditor/src/assets/less/index.less"
import {computed, onBeforeUnmount, onMounted, ref, watch} from "vue";
import {addArticleService, getCategoryService, articleListByIdService} from "@/api/article"
import {useRoute} from "vue-router"
import router from "@/router/router"
import {ElMessage} from "element-plus";

let vditor
const route = useRoute()

interface Article {
  id: number;
  title: string;
  category: string
  content: string;
}

const article = ref<Article>({
  id: 0,
  title: '请输入标题',
  category: '请输入类型',
  content: '请输入内容'
})
const isAddArticle = ref(true)
const categoryList = ref([])
onMounted(() => {
  const getCategory = async () => {
    let result = await getCategoryService()
    categoryList.value = result.data
  }
  getCategory()
  const getArticleById = async () => {
    let result = await articleListByIdService(route.params.articleId);
    article.value = result.data;
    initVditor()
  }
  const initVditor = () => {
    vditor = new Vditor('vditor', {
      value: '### 测试',
      height: 1000,
      width: '100%',
      mode: 'sv',
      toolbar: [
        'emoji', 'headings', 'bold', 'italic', 'strike', '|',
        'link', 'list', 'check', 'quote', 'code', '|',
        'inline-code', 'table', 'undo', 'redo', '|',
        'fullscreen', 'outline', 'edit-mode', 'help'
      ],
      after() {
        vditor.setValue(article.value.content)
      },
      input(value: string) {
        article.value.content = vditor.getHTML()
      }
    })
  }
  if (route.path === '/article/addArticle') {
    initVditor()
    article.value = {
      id: 0,
      title: '请输入标题',
      category: '请选择类型',
      content: '请输入内容'
    }
    isAddArticle.value = true
  } else {
    getArticleById()
    isAddArticle.value = false
  }
})
//销毁，避免内存泄漏，富文本编辑器基本都需要销毁
onBeforeUnmount(() => {
  vditor.destroy()
  vditor = null
})
watch(() => route.path, (newValue, oldValue) => {
  if (newValue === '/article/addArticle') {
    article.value = {
      id: 0,
      title: '请输入标题',
      category: '比如Vue',
      content: '请输入内容'
    }
    vditor.setValue(article.value.content)
    isAddArticle.value = true
  } else {
    isAddArticle.value = false
  }
})
const uploadArticle = async () => {
  await addArticleService(article.value)
  ElMessage.success("发布文章成功")
  router.push('/')
}
const editArticle = async () => {
  await addArticleService(article.value)
  ElMessage.success("修改文章成功")
  router.push('/controlArticle')
}
</script>

<style scoped>
.container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 800px;
}

#vditor {
  width: 1000px;
  height: 700px;
}

.button-wrapper {
  width: 250px;
  height: 50px;
  margin: 10px auto;
}

.input-text {
  margin-bottom: 10px;
}
</style>