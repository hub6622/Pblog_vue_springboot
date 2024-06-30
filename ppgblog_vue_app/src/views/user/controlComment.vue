<template>
  <div>
    <el-card style="max-width: 100%;margin-top: 20px">
      <template #header>
        <div class="card-header">
          <span>我的评论</span>
        </div>
      </template>
      <el-table :data="commentList" style="width: 100%">
        <el-table-column prop="content" label="评论内容"/>
        <el-table-column prop="commentTime" label="发布时间"/>
        <el-table-column prop="id">
          <template v-slot="{row}">
            <el-button class="optBtn"
                       type="danger" icon="el-icon-delete" circle
                       @click.prevent="commentDel(row.id)">
              <Delete style="width: 1em; height: 1em; margin-right: 8px"></Delete>
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-table :data="replyList" style="width: 100%;margin-top: 20px">
        <el-table-column prop="content" label="回复内容"/>
        <el-table-column prop="replyTime" label="发布时间"/>
        <el-table-column prop="id">
          <template v-slot="{row}">
            <el-button class="optBtn"
                       type="danger" icon="el-icon-delete" circle
                       @click.prevent="replyDel(row.id)">
              <Delete style="width: 1em; height: 1em; margin-right: 8px"></Delete>
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import {getUserCommentService, userCommentDeleteService, userReplyDeleteService} from "@/api/user.js"
import {Delete, View} from "@element-plus/icons-vue";
import {ref} from "vue";
import {ElMessage} from "element-plus";


const commentList = ref([])
const replyList = ref([])
const getCommentList = async () => {
  let result = await getUserCommentService();
  commentList.value = result.data;
  replyList.value = result.data[0].commentReply;
  console.log(result.data)
}
getCommentList()
const commentDel = async (id) => {
  ElMessage.success("删除成功")
  await userCommentDeleteService(id)
  await getCommentList()
}
const replyDel = async (id) => {
  ElMessage.success("删除成功")
  await userReplyDeleteService(id)
  await getCommentList()
}
defineExpose({getCommentList})
</script>

<style lang="scss" scoped>

</style>