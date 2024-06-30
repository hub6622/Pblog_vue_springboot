<template>
  <div class="replyCom">
    <div>
      <el-row>
        <el-col :span="2">
          <div class="demo-basic--circle">
            <div>
              <el-avatar shape="square" :size="50" :src="replyData.replyAuthor.avatar"/>
            </div>
          </div>
        </el-col>
        <el-col :span="12">
          <div class="userInfo">
            <span>用户:{{ replyData.replyAuthor.name }}</span>
            <div>
              <span>评论时间:{{ replyData.replyTime }}</span>
            </div>
          </div>
        </el-col>
      </el-row>
    </div>
    <div class="comment-content">
        <div class="text">{{ replyData.content }}</div>
    </div>
  </div>
</template>

<script setup lang="ts">
import {defineProps, reactive, ref} from 'vue'
interface ReplyData {
  id: number;
  replyAuthor: {
    name: string;
    avatar: string;
  },
  replyTime: string,
  content: string,
}
const props = defineProps<{ replyData: ReplyData }>()

</script>

<style lang="scss" scoped>
.text{
  font-size: 18px;
  color: #333;
  font-weight: 500;
  margin-top: 10px;
}
.replyCom{
  margin-top: 20px;
}
.userInfo {
  margin-left: -20px;
}
.comment-content {
  margin-left: 60px;
}
.replyBut{
  border: none;
  cursor: pointer;
  border-radius: 4px;
}
.replyBut:hover{
  background-color: skyblue;
}
</style>