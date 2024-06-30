import { createRouter, createWebHistory} from 'vue-router';
import LoginVue from '@/views/Login.vue';
import LayoutVue from '@/views/Layout.vue';
import articleCategory from '@/views/article/category.vue'
import articleMain from '@/views/article/articleMain.vue'
import addArticle from '@/views/article/editArticle.vue'
import controlArticle from '@/views/user/controlArticle.vue'
import userInfo from '@/views/user/userInfo.vue'

const routes = [
    {path: '/login', component: LoginVue},
    {
        path: '/',
        redirect: '/article/category',
        component: LayoutVue,
        children: [
            {path: '/article/category/:cate', component: articleCategory},
            {path: '/article/category', component: articleCategory},
            {path: '/article/articleMain/:articleId',name: 'articleMain', component: articleMain},
            {path: '/article/addArticle', component: addArticle},
            {path: '/controlArticle', component: controlArticle},
            {path: '/article/editArticle/:articleId', component: addArticle},
            {path: '/userInfo', component: userInfo},
        ]
    },
]

const router = createRouter({
    history: createWebHistory(),
    routes,
});
export default router;