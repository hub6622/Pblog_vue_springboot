<template>
  <div class="user-comment">
    <div>
      <el-row>
        <el-col :span="2">
          <div class="demo-basic--circle">
            <div>
              <el-avatar shape="square" :size="50" :src="comments.commentAuthor.avatar"/>
            </div>
          </div>
        </el-col>
        <el-col :span="12">
          <div class="userInfo">
            <span>用户:{{ comments.commentAuthor.name }}</span>
            <div>
              <span>{{ commentIndex + 1 }}楼</span>
              <span>评论时间:{{ comments.commentTime }}</span>
            </div>
          </div>
        </el-col>
      </el-row>
    </div>
    <div class="comment-content">
      <div>
        <div class="text">{{ comments.content }}</div>
        <div v-if="replyShow">
          <el-input v-model="replyContent" type="textarea" :rows="2" placeholder="请输入内容" />
          <el-button style="float: right" type="primary" @click="addCommentReply">回复</el-button>
        </div>
        <button v-else class="replyBut" @click="replyShowFun">回复</button>
      </div>
      <div>
        <div v-for="(item, index) in displayedReplies" :key="index">
          <replyCommentVue :replyData="item"/>
        </div>
        <div v-if="!showAllReplies && comments.commentReply.length > 2"
             @click="showAllReplies = true">
          加载更多回复 ...
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import {computed, defineProps, ref, defineEmits} from 'vue'
import replyCommentVue from '@/components/replyComment.vue';
import {addCommentReplyService} from "@/api/article"
import {ElMessage, emitChangeFn} from "element-plus";
interface CommentData {
  id: number;
  commentAuthor: {
    name: string;
    avatar: string;
  },
  commentTime: string;
  content: string;
  commentOrder: number;
  commentReply:
      {
        id: number;
        replyAuthor: {
          name: string;
          avatar: string;
        },
        replyTime: string,
        content: string,
      }[]
}

const emit = defineEmits(["updateReply"])
const props = defineProps<{ comments: CommentData, commentIndex: number }>()
const showAllReplies = ref(false);

const displayedReplies = computed(() => {
  const comment = props.comments;
  if (showAllReplies.value || comment.commentReply.length <= 2) {
    return comment.commentReply;
  } else {
    return comment.commentReply.slice(0, 2);
  }
});

const replyShow = ref(false)
const replyContent = ref('')
const replyShowFun = () => {
  replyShow.value = !replyShow.value
}
const addCommentReply = async () => {
  await addCommentReplyService({content: replyContent.value, commentId: props.comments.id})
  ElMessage.success("回复成功")
  emit('updateReply')
  replyShowFun()
}
</script>

<style lang="scss" scoped>
.text{
  font-size: 18px;
  color: #333;
  font-weight: 500;
  margin-top: 10px;
}
.user-comment {
  margin-top: 50px;
}
.comment-content {
  margin-left: 70px;
}
.userInfo {
  margin-left: -30px;
}
.userInfo span{
  margin-left: 10px;
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